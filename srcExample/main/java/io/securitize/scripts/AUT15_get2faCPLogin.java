package io.securitize.scripts;

import io.securitize.infra.utils.Authentication;
import io.securitize.infra.config.*;

public class AUT15_get2faCPLogin {
    public static void main(String[] args) {
        System.out.println(Authentication.getTOTPCode(Users.getProperty(UsersProperty.automationCp2FaSecret)));

    }
}