package io.securitize.scripts;

import io.securitize.infra.utils.SshRemoteExecutor;

public class AUT146_WalletRegistrationBypass {
    public static void main(String[] args) {
        SshRemoteExecutor.executeScript("walletRegistrationBypass.sh");
    }
}