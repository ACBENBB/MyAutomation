package io.securitize.scripts;

import io.securitize.infra.api.primetrust.*;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;

public class CashAccount_Unlink {

    public static void main(String[] args) {

        PrimeTrustAPI.unlinkAccountByEmail("securitize_automation+aut426@securitize.io");

    }

}