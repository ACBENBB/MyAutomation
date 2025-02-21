package io.securitize.scripts;

import io.securitize.infra.api.anticaptcha.AntiCaptchaApi;
import io.securitize.infra.exceptions.InsufficientFundsException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class AUT694_BalanceCheck_AntiCaptcha {
    private static final String LOG_FILE_NAME = "anti-captcha-balance.log";
    private static final double MINIMAL_BALANCE = 2.0;

    public static void main(String[] args) throws IOException {
        removeOldLog();

        AntiCaptchaApi antiCaptchaApi = new AntiCaptchaApi();
        double balance = antiCaptchaApi.getBalance();

        // write log
        var logFilePath = Paths.get(LOG_FILE_NAME);
        if (balance >= MINIMAL_BALANCE) {
            String fullText = String.format("Balance is above defined limit: %s >= %s", balance, MINIMAL_BALANCE);
            Files.write(logFilePath, fullText.getBytes());
            info(fullText);
        } else {
            String fullText = String.format("Current balance is too low! %s < %s", balance, MINIMAL_BALANCE);
            Files.write(logFilePath, fullText.getBytes());
            errorAndStop(fullText, false, new InsufficientFundsException("At least one wallet doesn't have enough balance"));
        }
    }

    private static void removeOldLog() {
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE_NAME));
        } catch (IOException e) {
            errorAndStop("An error occur trying to delete old log file. Won't resume as it might cause confusion with previous runs", false, new RuntimeException(e));
        }
    }
}