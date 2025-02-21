package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanelPlus.CpPlusCorporateBondLedgerPage;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.testng.annotations.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AUT573_JP_CpPlus_Corporate_Bond_Ledger extends AbstractJpFlow {

    private static final boolean RETRY_FLAG = true;
    private static final int AT_THE_TOP_ROW = 1;

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT573 - Corporate Bond Ledger")
    public void AUT573_JP_CpPlus_Corporate_Bond_Ledger_test() {
        String domainName = getDomainNameWithAssert();
        String tokenName = "SEC 34";
        String tokenId = getTokenIdWithAssert(tokenName);
        List<String> investorNamesInHolders = getHolderNames(tokenId);
        cpPlusLogin(domainName, tokenName, RETRY_FLAG);
        CpPlusCorporateBondLedgerPage corporateBondLedgerPage = cpPlusCreateCorporateBondLedgerPage(tokenName, AT_THE_TOP_ROW, RETRY_FLAG);
        String ledgerContent = cpPlusDownloadCorporateBondLedger(corporateBondLedgerPage, AT_THE_TOP_ROW);
        List<String> investorNamesInLedger = getInvestorNamesFromCorporateBondLedger(ledgerContent);
        assertThat(investorNamesInLedger).containsExactlyInAnyOrderElementsOf(investorNamesInHolders);
        cpPlusLogout(RETRY_FLAG);
    }
}
