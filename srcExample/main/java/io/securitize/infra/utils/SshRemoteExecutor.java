package io.securitize.infra.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.abstractClass.AbstractTest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

import static io.securitize.infra.reporting.MultiReporter.*;

public class SshRemoteExecutor {

    private static final int SESSION_TIMEOUT = 2 * 60 * 1000; // 2 minutes
    private static final int CHANNEL_TIMEOUT = 5000;

    public static String executeScript(String scriptFileNameToExecute) {
        String remoteHost = Users.getProperty(UsersProperty.clusterIp);
        String username = Users.getProperty(UsersProperty.clusterUsername);
        String privKeyPath = Users.getProperty(UsersProperty.clusterPemPath);
        String scriptsPath = Users.getProperty(UsersProperty.clusterScriptsPath);
        String scriptFileFullPath = scriptsPath + scriptFileNameToExecute;

        return executeScript(remoteHost, username, privKeyPath, scriptFileFullPath);
    }

    public static String executeScript(String remoteHost, String username, String privateKeyFilePath, String scriptToExecute) {
        final String[] externalResult = new String[1];
        final Exception[] exceptionInConnectionThread = new Exception[1];

        String name = AbstractTest.getCurrentTestName() + ":Secondary Thread";
        Thread secondaryThread = new Thread(() -> {

            Session jschSession = null;
            StringBuilder result = new StringBuilder();
            ByteArrayOutputStream errStream;

            info("Starting execution process of remote script via ssh");

            if (!new File(privateKeyFilePath).exists()) {
                warning("Private key file was not found. Skipping remote script execution", false);
                return;
            }

            try {
                info("Connecting to remote host of: " + remoteHost);

                JSch jsch = new JSch();
                jschSession = jsch.getSession(username, remoteHost);
                jschSession.setConfig("StrictHostKeyChecking", "no");

                // authenticate using private key
                jsch.addIdentity(privateKeyFilePath);

                // connect to remote machine
                jschSession.connect(SESSION_TIMEOUT);
                info("Connected!");

                info("Running remote command: " + scriptToExecute);
                ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");

                // prepare the command
                String scriptsPath = Users.getProperty(UsersProperty.clusterScriptsPath);
                String commandSingletonWrapper = scriptsPath + "runCommandAsSingleton.sh";
                channelExec.setCommand(commandSingletonWrapper + " " + scriptToExecute);

                // store any errors in a dedicated stream for later logging
                errStream = new ByteArrayOutputStream();
                channelExec.setErrStream(errStream);
                InputStream in = channelExec.getInputStream();

                // run the script
                channelExec.connect(CHANNEL_TIMEOUT);

                // read the result from remote server
                byte[] tmp = new byte[1024];
                while (true) {
                    // try to read incoming messages
                    StringBuilder message = new StringBuilder();
                    while (in.available() > 0) {
                        int i = in.read(tmp, 0, 1024);
                        if (i < 0) break;
                        message.append(new String(tmp, 0, i));
                    }
                    // if incoming message has content, print it out
                    if (message.toString().trim().length() > 0) {
                        info(message.toString());
                    }

                    if (channelExec.isClosed()) {
                        if (in.available() > 0) continue;

                        if (errStream.size() == 0) {
                            info("No errors found");
                        } else {
                            errorAndStop("Error running remote ssh script: " + errStream.toString(), false);
                        }
                        result.append("exit-status: ").append(channelExec.getExitStatus());
                        info("Output of remote script: " + System.lineSeparator() + result);
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ee) {
                        // we don't care if interrupted
                    }
                }

                info("Finished! Closing connection...");
                channelExec.disconnect();

            } catch (JSchException | IOException e) {
                exceptionInConnectionThread[0] = e;
                errorAndStop("An error occur running remote script. Details: " + e, false);
            } finally {
                if (jschSession != null) {
                    jschSession.disconnect();
                }
            }

            externalResult[0] = result.toString();
        }, name);

        long startTime = System.currentTimeMillis();
        secondaryThread.start();
        while (secondaryThread.isAlive()) {
            info("Waiting for script to finish on secondary thread... Meanwhile keeping browser busy to avoid timeout...");
            if (Browser.getDriver() != null) {
                Browser.getDriver().findElements(By.xpath("//body"));
            }
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                // if we are interrupted, quit this endless loop
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        Duration d = Duration.ofMillis(endTime - startTime);
        long minutesPart = d.toMinutes();
        long secondsPart = d.minusMinutes( minutesPart ).getSeconds();
        info("Script finished in " + minutesPart + " minutes and " + secondsPart + " seconds");

        if (exceptionInConnectionThread[0] != null) {
            warning("An error occur trying to connect to remote host for script execution. Details: " + ExceptionUtils.getStackTrace(exceptionInConnectionThread[0]), false);
        }

        return externalResult[0];
    }
}