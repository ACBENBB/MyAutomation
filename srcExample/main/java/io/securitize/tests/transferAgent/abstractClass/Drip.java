package io.securitize.tests.transferAgent.abstractClass;

import io.securitize.infra.api.TransferAgentAPI;
import io.securitize.infra.webdriver.Browser;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSignatures;
import io.securitize.tests.transferAgent.testData.DistributionData;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.function.Function;

public class Drip extends AbstractDistributions {

    public void downloadCSVFromDistributionDetailsPage() {
        downloadCSVFromDistributionDetails();
        try {
            getLastDownloadedDistributionCsvFile();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        getBrowser().closeLastTabAndSwitchToPreviousOne();
    }

    public String writePayoutMethodCSVColumn() throws IOException {
        int csvRowsCount = distribution.getRowCount();
        String outputCsvFileName = "dividendUpload.csv";
        distribution.updateCsvFileForForAutoIssuance("payoutMethod", csvRowsCount,outputCsvFileName, "reinvest");
        // Construct the absolute path for the CSV file
        String absoluteCsvPath = new File(outputCsvFileName).getAbsolutePath();
        File csvFile = new File(absoluteCsvPath);
        if (csvFile.exists() && csvFile.isFile()) {
            System.out.println("New CSV file location: " + absoluteCsvPath);
            return absoluteCsvPath;
        } else {
            System.out.println("Couldt find CSV file path: " + absoluteCsvPath);
            return null;
        }
    }

    public LinkedHashMap<String, String> getInitialInvestorsTokenBalance() {

        int taxWithholdingRate = distribution.getCSVColumnIndex("taxWithholdingRate");
        int taxWithholdingAmount = distribution.getCSVColumnIndex("taxWithholdingAmount");
        int payoutAmount = distribution.getCSVColumnIndex("payoutAmount");
        int payoutAmountWithoutTaxes = distribution.getCSVColumnIndex("payoutAmountWithoutTaxes");

        LinkedHashMap<String, String> initialInvestorsTokenBalance = new LinkedHashMap<>();
        int csvRowsCount = distribution.getRowCount();
        for(int i = 0; csvRowsCount > i; i++) {
            String cpId = distribution.getCSVInvestorDetailByIndex(i, "CPiD");
            String totalNumberOfSecurities = distribution.getCSVInvestorDetailByIndex(i, "totalNumberOfSecurities");
            initialInvestorsTokenBalance.put(cpId, totalNumberOfSecurities);
        }
        return initialInvestorsTokenBalance;

    }

    public int getTotalInvestorsCount() {
        return distribution.getRowCount();
    }

    // TODO delete?
    public void validateSignaturesCountCreated(int totalInvestorsCount) {
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
        int actualSignatureElements = cpSignatures.getPendingTransactionsCount();
        int expectedSignatureElements = calculateSignatures(totalInvestorsCount);
        getBrowser().waitForPageStable();
        Assert.assertEquals(actualSignatureElements, expectedSignatureElements);
    }

    public int calculateSignatures(int totalInvestorsCount) {
        return (int) Math.ceil((double) totalInvestorsCount / 100);
    }

    public void clickSignAllTransactionsBtn() {
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
        cpSignatures.clickSignAllTransactionsBtn();
    }

    public void signAllPendingTransactions(DistributionData distributionData) {
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
        cpSignatures.signAllPendingTransactions(distributionData);
    }

    public void clearAllPendingTransactions(DistributionData distributionData) {
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
        cpSignatures.signAllPendingTransactions(distributionData);
    }

    public void waitForTransactionsCount(int totalInvestorsCount) {
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
        cpSignatures.waitForTransactionsCount(totalInvestorsCount);
    }

    public void waitForNoRecordsFoundMsgVisible() {
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
        cpSignatures.waitForNoRecordsFoundMsgVisible();
    }

    public void assertTotalNumberOfIssuedSecurities(DistributionData distributionData) {

        // keep trying to get issues securities until the expected result matches.
        Function<String, Boolean> internalWaitForDistributionStatus = t -> {
            double actualTotalNumberOfIssuedSecurities = getTotalNunmberOfIssuedSecurities(distributionData);
            double expectedTotalNumberOfIssuedSecurities = (distributionData.initialTotalNumberOfIssuedSecurities + distributionData.totalTokensToBeIssued);

            // Round the expected value to 3 decimal places
            BigDecimal actualTotalNumberOfIssuedSecuritiesRounded = BigDecimal.valueOf(actualTotalNumberOfIssuedSecurities).setScale(2, RoundingMode.HALF_UP);
            BigDecimal expectedTotalNumberOfIssuedSecuritiesRounded = BigDecimal.valueOf(expectedTotalNumberOfIssuedSecurities).setScale(2, RoundingMode.HALF_UP);

            System.out.println("totalTokensToBeIssued - > " + distributionData.totalTokensToBeIssued);
            System.out.println("actualTotalNumberOfIssuedSecuritiesRounded - > " + actualTotalNumberOfIssuedSecuritiesRounded);
            System.out.println("expectedTotalNumberOfIssuedSecuritiesRounded - > " + expectedTotalNumberOfIssuedSecuritiesRounded);

            if(expectedTotalNumberOfIssuedSecuritiesRounded.equals(actualTotalNumberOfIssuedSecuritiesRounded)) {
                return true;
            } else {
                return false;
            }
        };
        String description = "Expecting Issued Number Of Securities To Be: " + (distributionData.initialTotalNumberOfIssuedSecurities + distributionData.totalTokensToBeIssued);
        Browser.waitForExpressionToEqual(internalWaitForDistributionStatus, null, true, description, 600, 10000);

    }

    public double getTotalNunmberOfIssuedSecurities(DistributionData distributionData) {

        TransferAgentAPI transferAgentAPI = new TransferAgentAPI();
        return Double.parseDouble(
                transferAgentAPI.getIssuedSecurities(distributionData.issuerId, distributionData.tokenId)
        );

    }

}
