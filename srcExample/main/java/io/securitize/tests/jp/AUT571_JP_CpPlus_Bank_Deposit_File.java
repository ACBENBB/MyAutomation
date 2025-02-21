package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanelPlus.CpPlusBankDepositFilePage;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.testng.annotations.Test;

public class AUT571_JP_CpPlus_Bank_Deposit_File extends AbstractJpFlow {

    private static final boolean RETRY_FLAG = true;
    private static final int AT_THE_TOP_ROW = 1;

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT571 - Bank Deposit File")
    public void AUT571_JP_CpPlus_Bank_Deposit_File_test() {
        String payYear = "2027";
        String payMonth = "2";
        boolean isInterestPay = true;
        boolean isRedemptionPay = false;
        String domainName = getDomainNameWithAssert();
        String tokenName = "SEC 36";
        cpPlusLogin(domainName, tokenName, RETRY_FLAG);
        CpPlusBankDepositFilePage bankDepositFilePage = cpPlusCreateBankDepositFile(
                tokenName, payYear, payMonth, isInterestPay, isRedemptionPay, AT_THE_TOP_ROW, RETRY_FLAG);
        cpPlusBankDepositFileDownloadTheLatest(bankDepositFilePage, AT_THE_TOP_ROW);
        cpPlusLogout(RETRY_FLAG);
    }
}
