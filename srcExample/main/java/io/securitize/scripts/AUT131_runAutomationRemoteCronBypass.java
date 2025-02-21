package io.securitize.scripts;

import io.securitize.infra.utils.SshRemoteExecutor;

public class AUT131_runAutomationRemoteCronBypass {
    public static void main(String[] args) {
        SshRemoteExecutor.executeScript("automation.sh");
    }
}
