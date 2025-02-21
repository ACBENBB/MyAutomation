package io.securitize.tests.controlPanel.abstractClass;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.RandomGenerator;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpInvestorDetailsPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpInvestorDetailsPageNewUI;
import io.securitize.tests.InvestorDetails;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.*;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public abstract class AbstractCpInvestments extends AbstractCpInvestors {

    private static final int INVESTMENT_AMOUNT_USD = 10;
    private static final int INVESTMENT_AMOUNT_ETH = 1;
    private CpInvestorDetailsPage investorDetailsPage;
    private CpInvestorDetailsPageNewUI investorDetailsPageNewUI;
    private String investmentCurrency;
    private int investmentAmount;

    public void cpAddInvestment(String investmentCurrency, String currencyName, InvestorDetails investorDetails) {
        startTestLevel("Set currency type and investment amount");
        if (investmentCurrency.equals("USD")) {
            investmentAmount = INVESTMENT_AMOUNT_USD;
        } else if (investmentCurrency.equals("ETH")) {
            investmentAmount = INVESTMENT_AMOUNT_ETH;
        } else {
            errorAndStop("Currency type not yet supported: " + investmentCurrency, false);
        }
        endTestLevel(false);

        startTestLevel("Add investment");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.clickAddInvestment().selectCurrency(investmentCurrency).clickOk();
        int initialFunded = investorDetailsPage.getTotalFunded();
        Assert.assertEquals(initialFunded, 0, "Initial funded value should be zero!");
        endTestLevel();

        startTestLevel("Add investment transaction");
        String todaysDate = DateTimeUtils.currentDate("MMM dd, yyyy");
        String txId = RandomGenerator.randomString(8);

        investorDetailsPage.clickAddTransaction()
                .typeDate(todaysDate)
                .setType("Deposit")
                .typeAmount(investmentAmount)
                .selectCurrency(investmentCurrency)
                .typeTxId(txId)
                .clickOk();

        investorDetailsPage.transactionsCard.waitForTableToContainNumberOfRows(1);

        // validate creation date
        String actualDateAsString = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Date");
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(actualDateAsString);
        if ((groups.size() == 0) || (groups.get(0).getDates().size() == 0)) {
            errorAndStop("Error! Unable to parse date format from: " + actualDateAsString, true);
        }
        // validate investment type
        String actualType = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Type");
        Assert.assertEquals(actualType.toLowerCase(), "deposit", "transaction type should be 'deposit'");

        // validate amount
        String actualAmountAsString = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Amount");
        int actualAmount = Integer.parseInt(actualAmountAsString);
        Assert.assertEquals(actualAmount, investmentAmount, "actual investment amount doesn't match entered value");

        // validate currency
        String actualCurrency = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Currency");
        Assert.assertEquals(actualCurrency, currencyName, "actual currency doesn't match entered value");

        // validate usd value
        String actualUSDValue = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "USD Value");
        if (investmentCurrency.equals("USD")) {
            Assert.assertEquals(actualUSDValue, "$" + investmentAmount, "actual USD value doesn't match entered value");
        }

        // validate source
        String actualSource = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Source");
        Assert.assertEquals(actualSource, "manual", "source should be manual");

        // validate TX ID
        String actualTxId = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "TX ID");
        Assert.assertEquals(actualTxId, txId, "actual TX ID value doesn't match entered value");

        int updatedFunded = investorDetailsPage.getTotalFunded();
        Assert.assertEquals(updatedFunded, investmentAmount, "funded value should have been updated!");
        endTestLevel();

        startTestLevel("Change 'Subscription Agreement' Status to 'Confirmed'");
        investorDetailsPage.clickEditInvestment()
                .typePledgedAmount(investorDetails.getPledgedAmount())
                .setInvestmentSubscriptionAgreement("Confirmed")
                .setSignatureDate(todaysDate)
                .clickSaveChanges();
        endTestLevel();
    }

    public void cpAddInvestmentNewUI(String currency) {
        startTestLevel("Navigate to investment tab and open all sections");
        investorDetailsPageNewUI.clickInvestmentTab();
        endTestLevel();

        startTestLevel("Set currency type and investment amount");
        investmentAmount = setInvestmentAmount(currency);
        endTestLevel(false);

        startTestLevel("Add pledge to investor");
        cpAddPledge(currency, investmentAmount);
        endTestLevel();

        startTestLevel("Add deposit transaction to investor to investor");
        cpAddTransaction(currency, investmentAmount, "deposit");
        endTestLevel();
    }

    public int setInvestmentAmount(String currency) {
        int investmentAmount = 0;
        investmentCurrency = currency;
        if (currency.equals("USD")) {
            investmentAmount = INVESTMENT_AMOUNT_USD;
        } else if (currency.equals("ETH")) {
            investmentAmount = INVESTMENT_AMOUNT_ETH;
        } else {
            errorAndStop("Currency type not yet supported: " + currency, false);
        }
        return investmentAmount;
    }


    public void cpAddPledge(String currency, int investmentAmount) {
        startTestLevel("Add investment");
        investorDetailsPageNewUI = new CpInvestorDetailsPageNewUI(getBrowser());
        investorDetailsPageNewUI.clickAddInvestment()
                .selectCurrency(currency)
                .addPledgeAmount(String.valueOf(investmentAmount))
                .selectSA()
                .clickSaveButton();
        endTestLevel();

        startTestLevel("Validate pledge details");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(investorDetailsPageNewUI.getPledgedAmount()
                .matches(".*\\b10\\b.*"));
        softAssert.assertTrue(DateTimeUtils.validateDate("Pledged Date", "dd MMM yyyy", investorDetailsPageNewUI.getActualPledgedDate()));
        softAssert.assertEquals(investorDetailsPageNewUI.getActualPledgeInitiator(), "Operator");
        softAssert.assertTrue(investorDetailsPageNewUI.getActualTotalFunded()
                .matches(".*\\b0\\b.*"));
        softAssert.assertEquals(investorDetailsPageNewUI.getSubscriptionAgreement(), "Confirmed");
        softAssert.assertTrue(DateTimeUtils.validateDate("Signature Date", "dd MMM yyyy", investorDetailsPageNewUI.getActualSignatureDate()));
        softAssert.assertAll();
        endTestLevel();
    }

    public void cpAddTransaction(String currency, int txAmount, String txType) {
        startTestLevel("refresh page to add transaction");
        // remove this section after bug ISR2-2299 is fixed
        // direct link  - https://securitize.atlassian.net/browse/ISR2-2299
        getBrowser().refreshPage();
        investorDetailsPageNewUI.clickInvestmentTab();
        endTestLevel();

        startTestLevel("Add investment transaction");
        String txId = RandomGenerator.randomString(8);
        investorDetailsPageNewUI.clickAddTransaction()
                .setType(txType)
                .selectCurrency()
                .typeAmount(txAmount)
                .typeTxId(txId)
                .clickSave();

        startTestLevel("Validate deposit transaction details");
        SoftAssert softAssert = new SoftAssert();
        // remove comment after fixing defect - ISR2-2300
        // direct link - https://securitize.atlassian.net/browse/ISR2-2300
        // Assert.assertTrue(validateDateNewUI("Transaction Date", "dd MMM yyyy",investorDetailsPageNewUI.getActualTxTime()));
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTxRound(), investorDetailsPageNewUI.getTxInvestmentRoundName());
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTxType(), txType);
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTxAmount(), String.valueOf(txAmount));
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTxCurrency(), currency);
        softAssert.assertTrue(investorDetailsPageNewUI.getActualTxUsdValue().matches(".*\\b" + txAmount + "\\b.*"));
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTxSource(), "manual");
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTxId(), txId);
        softAssert.assertAll();
        endTestLevel();
    }

    public void cpAddEvergreenInvestment(String investmentCurrency, String currencyName, InvestorDetails investorDetails) {

        int investmentAmount = 0;

        startTestLevel("Set currency type and investment amount");
        if (investmentCurrency.equals("USD")) {
            investmentAmount = INVESTMENT_AMOUNT_USD;
        } else if (investmentCurrency.equals("ETH")) {
            investmentAmount = INVESTMENT_AMOUNT_ETH;
        } else {
            errorAndStop("Currency type not yet supported: " + investmentCurrency, false);
        }
        endTestLevel(false);

        startTestLevel("Add investment");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.clickAddInvestmentWithJs()
                .selectCurrency(investmentCurrency)
                .clickOk();
        getBrowser().refreshPage();
        int initialFunded = investorDetailsPage.getTotalFunded();
        Assert.assertEquals(initialFunded, 0, "Initial funded value should be zero!");
        endTestLevel();


        startTestLevel("Add investment transaction");
        String todaysDate = DateTimeUtils.currentDate("MMM dd, yyyy");
        String txId = RandomGenerator.randomString(8);

        investorDetailsPage.clickAddTransaction()
                .typeDate(todaysDate)
                .setType("Deposit")
                .typeAmount(investmentAmount)
                .selectCurrency(investmentCurrency)
                .typeTxId(txId)
                .clickOk();

        investorDetailsPage.transactionsCard.waitForTableToContainNumberOfRows(1);

        // validate creation date
        String actualDateAsString = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Date");
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(actualDateAsString);
        if ((groups.size() == 0) || (groups.get(0)
                .getDates()
                .size() == 0)) {
            errorAndStop("Error! Unable to parse date format from: " + actualDateAsString, true);
        }

        // validate investment type
        String actualType = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Type");
        Assert.assertEquals(actualType.toLowerCase(), "deposit", "transaction type should be 'deposit'");

        // validate amount
        String actualAmountAsString = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Amount");
        int actualAmount = Integer.parseInt(actualAmountAsString);
        Assert.assertEquals(actualAmount, investmentAmount, "actual investment amount doesn't match entered value");

        // validate currency
        String actualCurrency = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Currency");
        Assert.assertEquals(actualCurrency, currencyName, "actual currency doesn't match entered value");

        // validate usd value
        String actualUSDValue = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "USD Value");
        if (investmentCurrency.equals("USD")) {
            Assert.assertEquals(actualUSDValue, "$" + investmentAmount, "actual USD value doesn't match entered value");
        }

        // validate source
        String actualSource = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Source");
        Assert.assertEquals(actualSource, "manual", "source should be manual");

        // validate TX ID
        String actualTxId = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "TX ID");
        Assert.assertEquals(actualTxId, txId, "actual TX ID value doesn't match entered value");

        int updatedFunded = investorDetailsPage.getTotalFunded();
        Assert.assertEquals(updatedFunded, investmentAmount, "funded value should have been updated!");
        endTestLevel();

        startTestLevel("Change 'Subscription Agreement' Status to 'Confirmed'");
        investorDetailsPage.clickEditInvestment()
                .typePledgedAmount(investorDetails.getPledgedAmount())
                .setInvestmentSubscriptionAgreement("Confirmed")
                .setSignatureDate(todaysDate)
                .clickSaveChanges();
        endTestLevel();

    }

    public void cpAddSecondEvergreenInvestment(String investmentCurrency) {

        int investmentAmount = 0;

        startTestLevel("Set currency type and investment amount");
        if (investmentCurrency.equals("USD")) {
            investmentAmount = INVESTMENT_AMOUNT_USD;
        } else if (investmentCurrency.equals("ETH")) {
            investmentAmount = INVESTMENT_AMOUNT_ETH;
        } else {
            errorAndStop("Currency type not yet supported: " + investmentCurrency, false);
        }
        endTestLevel(false);

        startTestLevel("Add second Evergreen investment");
        String todaysDate = DateTimeUtils.currentDate("MMM dd, yyyy");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        getBrowser().refreshPage();
        investorDetailsPage.clickAddEvergreenInvestment()
                .selectCurrency(investmentCurrency)
                .setPledgedAmount(investmentAmount)
                .setSignatureDate(todaysDate)
                .selectSubscriptionAgreement("Confirmed")
                .clickOk();

        startTestLevel("Add investment transaction");
        String txId = RandomGenerator.randomString(8);

        investorDetailsPage.clickAddTransaction()
                .typeDate(todaysDate)
                .setType("Deposit")
                .typeAmount(investmentAmount)
                .selectCurrency(investmentCurrency)
                .typeTxId(txId)
                .clickOk();

        investorDetailsPage.transactionsCard.waitForTableToContainNumberOfRows(1);

        // validate creation date
        String actualDateAsString = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Date");
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(actualDateAsString);
        if ((groups.size() == 0) || (groups.get(0)
                .getDates()
                .size() == 0)) {
            errorAndStop("Error! Unable to parse date format from: " + actualDateAsString, true);
        }

        // validate pledged amount
        String actualPledged = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Pledge");
        Assert.assertEquals(actualPledged, investmentAmount + " USD", "actual pledge amount doesn't match entered value");

        // validate funded amount
        String actualFunded = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Funded");
        Assert.assertEquals(actualFunded, investmentAmount + " USD", "actual funded amount doesn't match entered value");

        // validate subscription agreement
        String actualSubscriptionAgreement = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Subscription Agreement");
        Assert.assertEquals(actualSubscriptionAgreement, "confirmed", "actual subscription agreement doesn't match entered value");

        // validate signature date
        String actualSignatureDate = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Signature Date");
        Assert.assertEquals(actualSignatureDate, "-", "actual signature date doesn't match entered value");

        // validate investment status
        String actualInvestmentStatus = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Investment Status");
        Assert.assertEquals(actualInvestmentStatus, "confirmed", "actual investment status doesn't match entered value");
        endTestLevel();

    }

    public void cpAddInvestmentNonDefaultToken(String investmentCurrency) {

        int investmentAmount = 0;

        startTestLevel("Set currency type and investment amount");
        if (investmentCurrency.equals("USD")) {
            investmentAmount = INVESTMENT_AMOUNT_USD;
        } else if (investmentCurrency.equals("ETH")) {
            investmentAmount = INVESTMENT_AMOUNT_ETH;
        } else {
            errorAndStop("Currency type not yet supported: " + investmentCurrency, false);
        }
        endTestLevel(false);

        startTestLevel("Add investment");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.clickAddInvestment()
                .selectCurrency(investmentCurrency)
                .clickOk();
        getBrowser().refreshPage();
        int initialFunded = investorDetailsPage.getTotalFunded();
        Assert.assertEquals(initialFunded, 0, "Initial funded value should be zero!");
        endTestLevel();


        startTestLevel("Add investment transaction");
        String todaysDate = DateTimeUtils.currentDate("MMM dd, yyyy");
        String txId = RandomGenerator.randomString(8);

        investorDetailsPage.clickAddTransaction()
                .typeDate(todaysDate)
                .setType("Deposit")
                .typeAmount(investmentAmount)
                .selectCurrency(investmentCurrency)
                .typeTxId(txId)
                .clickOk();

        investorDetailsPage.transactionsCard.waitForTableToContainNumberOfRows(1);
    }

    public void cpAddInvestmentForMktsOverviewSandbox(String investmentCurrency) {

        int investmentAmount = 0;

        startTestLevel("Set currency type and investment amount");
        if (investmentCurrency.equals("USD")) {
            investmentAmount = INVESTMENT_AMOUNT_USD;
        } else if (investmentCurrency.equals("ETH")) {
            investmentAmount = INVESTMENT_AMOUNT_ETH;
        } else {
            errorAndStop("Currency type not yet supported: " + investmentCurrency, false);
        }
        endTestLevel(false);

        startTestLevel("Add investment");
        investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.selectRound("TokenMarket round");
        investorDetailsPage.clickAddInvestment().selectCurrency(investmentCurrency).clickOk();
        getBrowser().refreshPage();
        int initialFunded = investorDetailsPage.getTotalFunded();
        Assert.assertEquals(initialFunded, 0, "Initial funded value should be zero!");
        endTestLevel();


        startTestLevel("Add investment transaction");
        String todaysDate = DateTimeUtils.currentDate("MMM dd, yyyy");
        String txId = RandomGenerator.randomString(8);

        investorDetailsPage.clickAddTransaction().typeDate(todaysDate).setType("Deposit").typeAmount(investmentAmount).selectCurrency(investmentCurrency).typeTxId(txId).clickOk();

        investorDetailsPage.transactionsCard.waitForTableToContainNumberOfRows(1);
        endTestLevel();
    }
}
