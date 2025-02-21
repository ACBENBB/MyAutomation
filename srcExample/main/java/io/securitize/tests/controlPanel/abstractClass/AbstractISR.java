package io.securitize.tests.controlPanel.abstractClass;

import io.securitize.tests.controlPanel.pojo.ISR_TestData;

public abstract class AbstractISR extends AbstractCpIssuerConfiguration {

    public enum ISR_TestScenario {
        AUT3_ISR_INVESTOR_REGISTRATION("Investor Registration flow + issuance"),
        //TODO @Matias Auslender : pls. represent the enum values in UPPERCASE letters as 'AUT3_ISR_INVESTOR_REGISTRATION' on line 8
        AUT73_ISR_Investor_Registration_ETH("Issuer registration + ETH Investment"),
        AUT76_ISR_ExportList(" Verifies all the export lists"),
        AUT77_ISR_Verify_Existing_Investor("Verifies data from existing investor"),
        AUT135_ISR_Edit_Existing_Investor_Validation("Verifies data from an edit of an existing investor"),
        AUT388_ISR_Fundraise_Screen("Fundraise screen"),
        AUT413_ISR_Internal_TBE_Transfer("Internal TBE transfer"),
        AUT472_ISR_Login_And_Audit("Login and validate the login event shows in the Audit page"),
        AUT507_ISR_Privacy_Control("Check privacy control OFF and ON"),
        AUT519_ISR_Bulk_Issuance("Verifies Bulk Issuance is performed properly"),
        AUT532_ISR_Evergreen_Investments("Evergreen Round + Evergreen Investments"),
        AUT535_ISR_Lock_Tokens_TBE("Lock and unlock Tokens - TBE"),
        AUT538_ISR_Markets_Overview("Markets Overview"),
        AUT540_ISR_Login_Auth_test("Non Corporate Email Login"),
        AUT542_ISR_Update_Control_Book("Control Book test - TBE and Wallet"),
        AUT547_ISR_Add_Edit_Delete_Rounds("Add Edit Delete Rounds"),
        AUT608_ISR_Lock_Tokens_Wallet("Lock Tokens - Wallet"),
        AUT609_ISR_Unlock_Tokens_Wallet("Unlock Tokens - Wallet"),
        AUT618_ISR_Lost_Shares("Operational Procedure - Lost Shares"),
        AUT619_ISR_Destroy_Tokens_Wallet("Destroy Tokens - wallet"),
        AUT620_ISR_Destroy_Tokens_TBE("Destroy Tokens - TBE"),
        AUT624_ISR_Hold_Trading("Operational Procedures - Hold Trading"),
        AUT563_ISR_Affiliate_Management("Affiliate Management"),
        AUT690_ISR_New_UI_Sanity("New UI - Investor Registration flow + issuance"),
        AUT702_ISR_TBE_To_Wallet_Transfer("TBE to wallet transfer");
        private final String displayName;

        ISR_TestScenario(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public ISR_TestData createTestDataObject(ISR_TestScenario isr_testScenario) {
        return new ISR_TestData(isr_testScenario);
    }

}
