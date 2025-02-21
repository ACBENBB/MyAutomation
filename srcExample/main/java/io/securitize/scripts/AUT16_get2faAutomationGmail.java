package io.securitize.scripts;

import io.securitize.infra.utils.Authentication;

public class AUT16_get2faAutomationGmail {
    public static void main(String[] args) {
        System.out.println(Authentication.getTOTPCode("DEWZVERVO6IPUSY7LUONJG4W2AX4LNZC"));

    }
}
