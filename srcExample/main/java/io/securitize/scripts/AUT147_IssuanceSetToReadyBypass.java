package io.securitize.scripts;

import io.securitize.infra.utils.SshRemoteExecutor;

public class AUT147_IssuanceSetToReadyBypass {
    public static void main(String[] args) {
        SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
    }
}







