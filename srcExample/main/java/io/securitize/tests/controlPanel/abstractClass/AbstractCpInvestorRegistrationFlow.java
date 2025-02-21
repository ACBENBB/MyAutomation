package io.securitize.tests.controlPanel.abstractClass;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import com.neovisionaries.i18n.CountryCode;
import com.opencsv.exceptions.CsvException;
import io.securitize.infra.api.anticaptcha.AntiCaptchaApi;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.exceptions.IssuanceNotSuccessTimeoutException;
import io.securitize.infra.exceptions.WalletNotReadyTimeoutException;
import io.securitize.infra.utils.*;
import io.securitize.infra.wallets.EthereumWallet;
import io.securitize.infra.webdriver.Browser;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import io.securitize.pageObjects.controlPanel.cpIssuers.investorDetailsCards.GeneralCard;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractCpInvestorRegistrationFlow extends AbstractUiTest {

    // Pages title = final
    private final String onboarding = "onboarding";
    private final String operationalProcedures = "operational procedures";
    private final String holders = "holders";
    private final String controlBook = "control book";
    private final String signatures = "signatures";
    private final String fundraise = "fundraise";
    private final String distributions = "distributions";
    private final String securitiesTransactions = "securities transactions";
    private final String msf = "msf";
    private final String configurationVariables = "configuration variables";

    // Investor data
    private CpInvestorDetailsPage investorDetailsPage;
    private CpInvestorDetailsPageNewUI investorDetailsPageNewUI;
    private String investorDirectUrl;
    private String investorDirectUrlNewUI;
    private String investorExternalID;
    private String walletAddress;
    private String walletName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String investmentCurrency;
    private double investmentAmount;

    // Tokens variables - final
    private static final int INVESTMENT_AMOUNT_USD = 10;
    private static final int INVESTMENT_AMOUNT_ETH = 1;
    private static final double INVESTMENT_AMOUNT_DECIMAL = 10.55;
    private static final int ISSUED_TOKENS = 5;
    private static final double ISSUED_TOKENS_DECIMAL = 5.123;
    private static final int TBE_ISSUED_TOKENS = 6;
    private static final double TBE_ISSUED_TOKENS_DECIMAL = 6.234;
    private static final int ISSUED_TOKENS_CB = 1;
    private static final int NUMBER_OF_WALLETS_LOST_SHARES = 2;

    // Tokens variables
    private int tokensWallet;
    private int tokensTBE;
    private String tokenTicker;
    private String tokenName;
    private int issuanceCounter;

    // Procedures variables
    private int tokensCanBeProcedure;
    private int tokensToBeProcedure;
    private int tokensAfterProcedure;
    private int expectedLockTokensAfterProcedure;
    private int totalLockTokens;
    private int numOfSignatures = 0;
    private String fullOrPartially;
    private String freezeType;

    // General variables
    private String errorMessage;
    private final String NON_VALID_SEARCH = "ThisInvestorShouldNotExist";

    public enum ISR_TestScenario {
        AUT3_ISR_Investor_Registration("Investor Registration flow + issuance"),
        AUT73_ISR_Investor_Registration_ETH("Issuer registration + ETH Investment"),
        AUT74_ISR_InvestorRegistration_FBO_USD("Control panel FBO Individual registration"),
        AUT76_ISR_ExportList(" Verifies all the export lists"),
        AUT77_ISR_Verify_Existing_Investor("Verifies data from existing investor"),
        AUT82_ISR_Login_2FA(" Google SSO and Email/Password + 2FA"),
        AUT134_ISR_Import_Investors("Import investor"),
        AUT135_ISR_Edit_Existing_Investor_Validation("Verifies data from an edit of an existing investor"),
        AUT228_ISR_MSF_Verify_Existing_Investor("MSF - Verify existing investor"),
        AUT234_ISR_Securities_Transactions_Existing_Investor("Securities Transactions - Verify existing investor's transactions"),
        AUT388_ISR_Fundraise_Screen("Fundraise screen"),
        AUT409_ISR_Holders_Screen("Holders screen"),
        AUT413_ISR_Internal_TBE_Transfer("Internal TBE transfer"),
        AUT472_ISR_Login_And_Audit("Login and validate the login event shows in the Audit page"),
        AUT507_ISR_Privacy_Control("Check privacy control OFF and ON"),
        AUT519_ISR_Bulk_Issuance("Verifies Bulk Issuance is performed properly"),
        AUT523_ISR_Decimal_Flow("Investor Registration flow + issuance with decimal"),
        AUT532_ISR_Evergreen_Investments("Evergreen Round + Evergreen Investments"),
        AUT535_ISR_Lock_Tokens_TBE("Lock Tokens - TBE"),
        AUT536_ISR_Unlock_Tokens_TBE("Unlock Tokens - TBE"),
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

    public ISR_TestData createTestDataObject(AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario) {
        return new ISR_TestData(testScenario);
    }

    public void cpLoginUsingEmailAndPassword() {
        cpLoginUsingEmailAndPassword(null, null);
    }

    public void cpLoginUsingEmailAndPassword(String email, String password) {

        startTestLevel("Login to control panel using email and password + 2FA ");
        String url = MainConfig.getCpUrl();
        getBrowser().navigateTo(url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());

        if (!MainConfig.isProductionEnvironment()) {
            if (email == null) {
                loginPage.loginCpUsingEmailPassword(false);
            } else {
                loginPage.loginCpUsingEmailPassword(email, password, false);
            }
        } else {
            loginPage.setUsernameAndPassword(email, password);
            startTestLevel("use Anti-Captcha to solve the reCaptcha");
            AntiCaptchaApi antiCaptchaApi = new AntiCaptchaApi();
            antiCaptchaApi.solveRecaptchaWithRetries(getBrowser().getCurrentUrl(), loginPage);
            endTestLevel();
        }
        endTestLevel();

        startTestLevel("Populate Login 2FA with the secret key");
        CpLoginPage2FA loginPage2Fa = new CpLoginPage2FA(getBrowser());
        loginPage2Fa.obtainPrivateKey().generate2FACode().setTwoFaCodeInUI();
        endTestLevel();
    }

    public void cpSelectIssuer(String issuerName, boolean isPropertyNeeded) {
        if (!isPropertyNeeded) {
            startTestLevel("Select Issuer");
            CpSelectIssuer selectIssuer = new CpSelectIssuer(getBrowser());
            selectIssuer.clickViewIssuerByName(issuerName);
            endTestLevel();
        } else {
            cpSelectIssuer(issuerName);
        }
    }

    public void cpSelectIssuer(String issuerName) {
        startTestLevel("Select Issuer");
        String issuerFullName = Users.getIssuerDetails(issuerName, IssuerDetails.issuerFullName);
        CpSelectIssuer selectIssuer = new CpSelectIssuer(getBrowser());
        selectIssuer.clickViewIssuerByName(issuerFullName);
        endTestLevel();
    }

    public void navigateToPage(String pageName) {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.navigateToPageInSideMenu(pageName);
    }

    public void cpInvestorRegistration(InvestorDetails investorDetails, boolean addCustodian) {
        startTestLevel("Add investor");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        CpAddInvestor addInvestor = onBoarding.clickAddInvestor();
        String state = addInvestor
                .typeFirstName(investorDetails.getFirstName())
                .typeMiddleName(investorDetails.getMiddleName())
                .typeLastName(investorDetails.getLastName())
                .typeEmail(investorDetails.getEmail())
                .selectCountry(investorDetails.getCountry())
                .selectRandomState();
        if (addCustodian) {
            addInvestor.selectCustodian(investorDetails.getCustodian());
        }
        investorDetailsPage = addInvestor.clickOK();
        endTestLevel();

        startTestLevel("Saving External ID and investor url for future use");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        investorExternalID = investorDetailsPage.getExternalInvestorId();
        endTestLevel();

        startTestLevel("Validate investor details");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(investorDetailsPage.getFirstName(), investorDetails.getFirstName(), "incorrect first name, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPage.getMiddleName(), investorDetails.getMiddleName(), "incorrect middle name, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPage.getLastName(), investorDetails.getLastName(), "incorrect last name, this is not the value we set!");
        if (addCustodian) {
            softAssert.assertEquals(investorDetailsPage.getFBOEmail(), investorDetails.getEmail(), "incorrect email, this is not the value we set!");
        } else {
            softAssert.assertEquals(investorDetailsPage.getEmail(), investorDetails.getEmail(), "incorrect email, this is not the value we set!");
        }
        softAssert.assertEquals(investorDetailsPage.getCountry(), investorDetails.getCountry(), "incorrect country, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPage.getState(), state, "incorrect state, this is not the value we set!");
        if (addCustodian) {
            softAssert.assertEquals(investorDetailsPage.getCustodian(), investorDetails.getCustodian(), "incorrect custodian, this is not the value we set!");
        }
        softAssert.assertAll();
        endTestLevel();

        startTestLevel("Add investor details");
        investorDetailsPage.clickEdit()
                .typePhoneNumber(investorDetails.getPhoneNumber())
                .typeBirthDate(investorDetails.getBirthDate())
                .setRandomGender()
                .typeAddress(investorDetails.getAddress())
                .typePostalCode(investorDetails.getPostalCode() + "")
                .typeCity(investorDetails.getCity())
                .selectFATCA(investorDetails.getCountry())
                .typeComment(investorDetails.getComment())
                .typeAddressAdditionalInfo(investorDetails.getStreetAdditionalInfo())
                .clickSaveChanges();
        endTestLevel();
    }

    public void cpInvestorRegistrationNewUI(InvestorDetails investorDetails, boolean addCustodian) {
        startTestLevel("Add investor");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        CpAddInvestor addInvestor = onBoarding.clickAddInvestor();
        String state = addInvestor.typeFirstName(investorDetails.getFirstName())
                .typeMiddleName(investorDetails.getMiddleName())
                .typeLastName(investorDetails.getLastName())
                .typeEmail(investorDetails.getEmail())
                .selectCountry(investorDetails.getCountry())
                .selectRandomState();
        if (addCustodian) {
            addInvestor.selectCustodian(investorDetails.getCustodian());
        }
        investorDetailsPage = addInvestor.clickOK();
        endTestLevel();

        startTestLevel("Saving External ID and investor url for future use");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        investorExternalID = investorDetailsPage.getExternalInvestorId();
        endTestLevel();

        startTestLevel("Saving new url and navigate to New UI investor details page");
        investorDirectUrlNewUI = investorDirectUrl.replace("/investors/", "/investors-mfe/");
        navigateNewInvestorDirectUrl(investorDirectUrlNewUI);
        endTestLevel();

        startTestLevel("Saving token ticker and token name");
        tokenTicker = investorDetailsPageNewUI.getTokenTicker();
        tokenName = getTokenName(tokenTicker);
        firstName = investorDetailsPageNewUI.getFirstName();
        endTestLevel();

        startTestLevel("Open all sections to show all data fields");
        investorDetailsPageNewUI.clickGeneralInformation();
        endTestLevel();

        startTestLevel("Validate investor details - New UI");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(investorDetailsPageNewUI.getFirstName(), investorDetails.getFirstName(), "incorrect first name, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPageNewUI.getMiddleName(), investorDetails.getMiddleName(), "incorrect middle name, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPageNewUI.getLastName(), investorDetails.getLastName(), "incorrect last name, this is not the value we set!");
        if (addCustodian) {
            softAssert.assertEquals(investorDetailsPageNewUI.getFBOEmail(), investorDetails.getEmail(), "incorrect email, this is not the value we set!");
        } else {
            softAssert.assertEquals(investorDetailsPageNewUI.getEmail(), investorDetails.getEmail(), "incorrect email, this is not the value we set!");
        }
        softAssert.assertEquals(investorDetailsPageNewUI.getCountry(), investorDetails.getCountry(), "incorrect country, this is not the value we set!");
        // We have a bug here - I keep the note to remember - ISR2-2271
        // direct link - https://securitize.atlassian.net/browse/ISR2-2271
        //Assert.assertEquals(newInvestorDetailsPage.getState(), state, "incorrect state, this is not the value we set!");
        if (addCustodian) {
            softAssert.assertEquals(investorDetailsPageNewUI.getCustodian(), investorDetails.getCustodian(), "incorrect custodian, this is not the value we set!");
        }
        softAssert.assertAll();
        endTestLevel();

        startTestLevel("Add investor details");
        investorDetailsPageNewUI.clickEdit().typePhoneNumber(
                        investorDetails.getPhoneNumber())
                .typeBirthDate()
                .setRandomGender()
                .typeAddress(investorDetails.getAddress())
                .typePostalCode(investorDetails.getPostalCode() + "")
                .typeCity(investorDetails.getCity())
                .selectFATCA(investorDetails.getCountry())
                .typeComment(investorDetails.getComment())
                .typeAddressAdditionalInfo(investorDetails.getStreetAdditionalInfo())
                .clickSave();
        endTestLevel();
    }

    public void cpAddDocument(String frontImagePath) {
        startTestLevel("Add document to investor");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        CpAddDocument addDocumentPage = investorDetailsPage.clickAddDocument();
        // due to product behaving differently for different categories - sometimes shows display text
        // and sometimes showing inner value - we have to compare them both
        String randomlySelectedImageCategory = addDocumentPage.selectRandomCategory();
        String innerValueOfSelectedImageCategory = addDocumentPage.getInnerValueForDocumentCategory(randomlySelectedImageCategory);
        addDocumentPage.uploadImage(frontImagePath).clickOk();

        investorDetailsPage.documentsCard.waitForTableToContainNumberOfRows(1);

        // validate document type
        String actualCategory = investorDetailsPage.documentsCard.getDetailAtIndex(1, "Type");
        boolean documentCategoryMatch = actualCategory.equals(randomlySelectedImageCategory) || actualCategory.equals(innerValueOfSelectedImageCategory);
        Assert.assertTrue(documentCategoryMatch, "actual document category doesn't match set category");

        // validate document face
        String actualFace = investorDetailsPage.documentsCard.getDetailAtIndex(1, "Document Face");
        Assert.assertEquals(actualFace.toLowerCase(), "front", "actual document face doesn't match default value of 'front'");

        // validate creation date
        investorDetailsPage.validateDate("Documents", investorDetailsPage, "Creation Date");

        endTestLevel();
    }

    public String getTokenName(String tokenTicker) {
        switch (tokenTicker) {
            case "TTk1":
                tokenName = "TestToken1";
                break;
            case "SBT1":
                tokenName = "SBToken1";
                break;
            case "AUT Ticker":
                tokenName = "AUT Token";
                break;
            default:
                tokenName = "Unknown token ticker, please add manually";
                break;
        }
        return tokenName;
    }

    public void cpAddDocumentNewUI(String frontImagePath, String documentCategory, String documentFace, String documentTitle) {
        startTestLevel("Add document to investor");
        investorDetailsPageNewUI.clickDocumentsTab();
        CpAddDocumentNewUI addDocumentPage = investorDetailsPageNewUI.clickAddDocument();
        addDocumentPage.uploadImage(frontImagePath);
        addDocumentPage.addDocumentCategory();
        addDocumentPage.addDocumentFace();
        addDocumentPage.addDocumentTitle(documentTitle);
        addDocumentPage.addDocumentToken(tokenTicker);
        addDocumentPage.clickOk();
        addDocumentPage.clickDocumentsSection();
        endTestLevel();

        startTestLevel("Validate document details");
        SoftAssert softAssert = new SoftAssert();
        // validate document image
        // remove comment after fixing defect ISR2-2301
        // direct link - https://securitize.atlassian.net/browse/ISR2-2301
        // softAssert.assertTrue(investorDetailsPageNewUI.isDocumentImgDisplay());
        softAssert.assertEquals(investorDetailsPageNewUI.getActualDocumentsTitle(), documentTitle, "Document title doesn't match");
        softAssert.assertEquals(investorDetailsPageNewUI.getActualDocumentsType(), documentCategory, "Document title doesn't match");
        softAssert.assertEquals(investorDetailsPageNewUI.getActualDocumentsFace(), documentFace, "Document face doesn't match");
        softAssert.assertTrue(DateTimeUtils.validateDate("Creation Date", "MMM d, yyyy", investorDetailsPageNewUI.getActualDocumentCreationDate()));
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTokenOrigin(), "Internal", "Document origin doesn't match");
        // validate document token
        // remove comment after fixing defect  ISR2-2302
        // direct link - https://securitize.atlassian.net/browse/ISR2-2302
        // String actualToken = investorDetailsPageNewUI.getActualTokenName();
        //softAssert.assertEquals(actualToken, tokenName, "Document origin doesn't match");
        softAssert.assertAll();
        endTestLevel();
    }

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

    public void cpAddInvestmentNewUI(String currency, boolean decimal) {
        startTestLevel("Navigate to investment tab and open all sections");
        investorDetailsPageNewUI.clickInvestmentTab();
        endTestLevel();

        startTestLevel("Set currency type and investment amount");
        investmentAmount = setInvestmentAmount(currency, decimal);
        endTestLevel(false);

        startTestLevel("Add pledge to investor");
        cpAddPledge(currency, investmentAmount);
        endTestLevel();

        startTestLevel("Add deposit transaction to investor to investor");
        cpAddTransaction(currency, investmentAmount, "deposit");
        endTestLevel();
    }

    public double setInvestmentAmount(String currency, boolean decimal) {
        double investmentAmount = 0;
        investmentCurrency = currency;
        if (currency.equals("USD")) {
            if (decimal) {
                investmentAmount = INVESTMENT_AMOUNT_DECIMAL;
            } else {
                investmentAmount = INVESTMENT_AMOUNT_USD;
            }
        } else if (currency.equals("ETH")) {
            investmentAmount = INVESTMENT_AMOUNT_ETH;
        } else {
            errorAndStop("Currency type not yet supported: " + currency, false);
        }
        return investmentAmount;
    }

    public void cpAddPledge(String currency, double investmentAmount) {
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

    public void cpAddTransaction(String currency, double txAmount, String txType) {
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
        // mismatch between RC and SB keep this until currency name will be equal between them
        // softAssert.assertEquals(investorDetailsPageNewUI.getActualTxCurrency(), currency);
        softAssert.assertTrue(investorDetailsPageNewUI.getActualTxUsdValue().matches(".*\\b" + txAmount + "\\b.*"));
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTxSource(), "manual");
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTxId(), txId);
        softAssert.assertAll();
        endTestLevel();
    }

    public void cpAddWallet() {
        startTestLevel("Add wallet");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        EthereumWallet ethereumWallet = EthereumUtils.generateRandomWallet();
        if (ethereumWallet == null) return; // should never happen due to error_an_stop within
        walletAddress = ethereumWallet.getAddress();
        String walletName = "CP test wallet";
        info("New wallet address: " + walletAddress);
        investorDetailsPage.clickAddWallet()
                .typeName(walletName)
                .typeAddress(walletAddress)
                .clickOk();

        investorDetailsPage.walletsCard.waitForTableToContainNumberOfRows(1);

        // validate wallet name
        String actualWalletName = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Name");
        Assert.assertEquals(actualWalletName, walletName, "actual wallet name doesn't match entered value");

        // validate wallet address
        String actualWalletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        Assert.assertEquals(actualWalletAddress.toLowerCase(), walletAddress.toLowerCase(), "actual wallet address doesn't match entered value");

        // validate wallet status
        String actualWalletStatus = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(actualWalletStatus, "pending", "wallet initial status should be 'pending'");
        endTestLevel();


        startTestLevel("Set KYC & Accreditation");
        String kycComment = "comment_" + RandomGenerator.randomString(20);
        investorDetailsPage.clickEditKYC()
                .setCurrentKycStatus("Verified")
                .setCurrentAccreditationStatus("Confirmed")
                .setQualificationStatus("Confirmed")
                .typeKycComment(kycComment)
                .clickSaveChanges();
        endTestLevel();

   /*     startTestLevel("Validate wallet status changed to 'Syncing'");
        getBrowser().refreshPage();
        // This part of the code will be useless when we run the tests on the New UI - now sometimes makes the code fail for nothing
        investorDetailsPage.walletsCard.scrollTableIntoView(1);
        investorDetailsPage.walletsCard.waitForTableToContainNumberOfRows(1);
        actualWalletStatus = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(actualWalletStatus, "syncing", "wallet status after KYC edit should be 'syncing'");
        endTestLevel();*/

        startTestLevel("Wait for wallet status to become 'ready'");
        getBrowser().refreshPage();
        int waitTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeWalletToBecomeReadyQuorumSeconds);
        int intervalTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeWalletToBecomeReadyQuorumSeconds);
        // force cron jobs to run now - to make wallet 'ready' faster
        TestNetwork currentTestNetwork = getTestNetwork();
        if (!MainConfig.isProductionEnvironment() && (currentTestNetwork.needsBypass())) {
            SshRemoteExecutor.executeScript("walletRegistrationBypass.sh");
        }
        WalletNotReadyTimeoutException walletNotReadyTimeoutException = new WalletNotReadyTimeoutException("Wallet was not marked as ready even after " + waitTimeSecondsWalletReady + " seconds.");
        investorDetailsPage.walletsCard.waitForEntityInTableStatusToBecome(1, "ready", waitTimeSecondsWalletReady, intervalTimeSecondsWalletReady, walletNotReadyTimeoutException);
        endTestLevel();
    }

    public void cpAddWalletNewUI() {
        startTestLevel("Set wallet details");
        setWalletDetails();
        endTestLevel();

        startTestLevel("Add wallet");
        investorDetailsPageNewUI.clickTokensWalletTab();
        cpAddNewWallet();
        endTestLevel();

        startTestLevel("Validate Wallet details");
        validateWalletDetails();
        endTestLevel();
    }

    public void waitWalletToBeReady() {
        // We wait that wallet status change to 'syncing' but in some cases it is 'ready' immediately
        String actualWalletStatus = waitWalletChangeStatus("syncing");
        if (!actualWalletStatus.equals("ready")) {
            waitWalletChangeStatus("ready");
        }
    }

    public void setWalletDetails() {
        EthereumWallet ethereumWallet = EthereumUtils.generateRandomWallet();
        if (ethereumWallet == null) return; // should never happen due to error_an_stop within
        walletAddress = ethereumWallet.getAddress();
        walletName = "CP test wallet";
        info("New wallet address: " + walletAddress);
    }

    public void cpAddNewWallet() {
        investorDetailsPageNewUI.clickAddWallet()
                .typeName(walletName)
                .typeAddress(walletAddress)
                .clickOk();
    }

    public void validateWalletDetails() {
        // validate wallet name
        String actualWalletName = investorDetailsPageNewUI.getActualWalletName();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualWalletName, walletName, "actual wallet name doesn't match entered value");

        // validate wallet address
        String actualWalletAddress = investorDetailsPageNewUI.getActualWalletAddress();
        softAssert.assertEquals(actualWalletAddress.toLowerCase(), walletAddress.toLowerCase(), "actual wallet address doesn't match entered value");

        // validate wallet type
        String actualWalletType = investorDetailsPageNewUI.getActualWalletType();
        softAssert.assertEquals(actualWalletType.toLowerCase(), walletAddress.toLowerCase(), "actual wallet type doesn't match entered value");

        // validate wallet owner
        String actualWalletOwner = investorDetailsPageNewUI.getActualWalletOwner();
        softAssert.assertTrue(actualWalletOwner.toLowerCase()
                .contains(firstName), "actual wallet owner doesn't match entered value");

        // validate wallet status
        String actualWalletStatus = investorDetailsPageNewUI.getActualWalletStatus();
        softAssert.assertEquals(actualWalletStatus, "pending", "wallet initial status should be 'pending'");

        // validate wallet tokens pending
        String actualWalletTokensPending = investorDetailsPageNewUI.getActualWalletTokensPending();
        softAssert.assertEquals(actualWalletTokensPending, 0, "actual wallet tokens pending doesn't match entered value");

        // validate wallet tokens held
        String actualWalletTokensHeld = investorDetailsPageNewUI.getActualWalletTokensHeld();
        softAssert.assertEquals(actualWalletTokensHeld, 0, "actual wallet tokens held doesn't match entered value");

        // validate token name
        String actualWalletTokenName = investorDetailsPageNewUI.getActualWalletTokenName();
        softAssert.assertEquals(actualWalletTokenName, 0, "actual wallet tokens held doesn't match entered value");

        // validate wallet creation date
        String actualWalletCreationDate = investorDetailsPageNewUI.getActualWalletCreationDate();
        Assert.assertTrue(DateTimeUtils.validateDate("wallet creation date", "yyyy-MM-dd", actualWalletCreationDate));
    }

    public void setVerification() {

        startTestLevel("Set expected values to verification");
        String expectedKYC = "Verified";
        String expectedQualification = "Confirmed";
        String expectedAccreditation = "Confirmed";
        endTestLevel();

        startTestLevel("Navigate Verification tab");
        investorDetailsPageNewUI.clickVerificationTab();
        endTestLevel();

        startTestLevel("Set all verification");
        investorDetailsPageNewUI.clickEditVerification();
        investorDetailsPageNewUI.setKYCStatus(expectedKYC);
        investorDetailsPageNewUI.setQualificationStatus(expectedQualification);
        investorDetailsPageNewUI.setAccreditationStatus(expectedAccreditation);
        String comment = "comment_kyc_" + RandomGenerator.randomString(20);
        investorDetailsPageNewUI.addVerificationComment(comment);
        investorDetailsPageNewUI.clickSaveVerification();
        endTestLevel();

        startTestLevel("Verify verification saved");
        String actualKYC = investorDetailsPageNewUI.getActualKYC();
        Assert.assertEquals(expectedKYC, actualKYC);
        String actualQualification = investorDetailsPageNewUI.getActualQualification();
        Assert.assertEquals(expectedQualification, actualQualification);
        String actualAccreditation = investorDetailsPageNewUI.getActualAccreditation();
        Assert.assertEquals(expectedAccreditation, actualAccreditation);
        endTestLevel();
    }

    public String waitWalletChangeStatus(String expectedStatus) {
        getBrowser().refreshPage();
        investorDetailsPageNewUI.clickTokensWalletTab();
        startTestLevel("Check wallet status");
        String actualWalletStatus = investorDetailsPageNewUI.getActualWalletStatus();
        while (!actualWalletStatus.equals(expectedStatus)) {
            if (actualWalletStatus.equals("pending")) {
                errorAndStop("Wallet status should not be 'pending'", true);
            } else if (actualWalletStatus.equals("ready")) {
                break;
            } else if (actualWalletStatus.equals("cancelled")) {
                errorAndStop("Wallet status is cancelled", true);
                break;
            }
            getBrowser().waitForPageStable(Duration.ofSeconds(30));
            getBrowser().refreshPage();
            investorDetailsPageNewUI.clickTokensWalletTab();
            actualWalletStatus = investorDetailsPageNewUI.getActualWalletStatus();
        }
        endTestLevel();
        return actualWalletStatus;
    }

    public void cpAddIssuanceNewUI(String issueTo, boolean decimal) {

        startTestLevel("Set issuance details");
        String issuanceDescription = "AUT issue " + issueTo;
        double tokenAmount = setTokensAmount(issueTo, decimal);
        endTestLevel();

        startTestLevel("Navigate to wallet & tokens tab if not open");
        investorDetailsPageNewUI.clickTokensWalletTab();
        endTestLevel();

        startTestLevel("Add " + issueTo + " issuance");
        cpAddIssuance(issueTo, tokenAmount, investmentCurrency, issuanceDescription);
        endTestLevel();

        startTestLevel("Validate issuance details");
        validateIssuanceDetails(issueTo, tokenAmount, issuanceDescription);
        endTestLevel();

        startTestLevel("Update issuance counter");
        issuanceCounter++;
        endTestLevel();
    }

    public void validateIssuanceDetails(String issueTo, double issuanceAmount, String description) {
        // verify issuance counter - to know which issuance row we need to validate in case of many
        investorDetailsPageNewUI.setIssuanceRowIndex();

        // extract issuance id - for future use
        String issuanceID = investorDetailsPageNewUI.getActualIssuanceID();

        // validate creation date
        Assert.assertTrue(DateTimeUtils.validateDate("Creation Date", "yyyy-MM-dd", investorDetailsPageNewUI.getActualIssuanceCreated()));

        // validate issued tokens
        String actualIssuedAmount = investorDetailsPageNewUI.getActualIssuanceAmount();
        Assert.assertEquals(actualIssuedAmount, String.valueOf(issuanceAmount), "actual issued tokens amount doesn't match entered value");

        // validate source
        String actualSource = investorDetailsPageNewUI.getActualIssuanceSource();
        Assert.assertEquals(actualSource.toLowerCase(), "manual", "actual source should have been 'manual'");

        // validate target
        String actualTarget = investorDetailsPageNewUI.getActualIssuanceTarget().toLowerCase();
        if (actualTarget.equals("treasury")) {
            actualTarget = "TBE";
        }
        Assert.assertTrue(actualTarget.contains(issueTo), "actual target should have been '" + issueTo + "'");

        // validate status
        String actualStatus = investorDetailsPageNewUI.getActualIssuanceStatus().toLowerCase().trim();
        Assert.assertEquals(actualStatus, "processing", "actual status should have been 'processing'");

        // validate Issuance date
        String actualIssuanceDate = investorDetailsPageNewUI.getActualIssuanceDate();
        Assert.assertTrue(DateTimeUtils.validateDate("Issuance Date", "yyyy-MM-dd", actualIssuanceDate));

        // validate description
        String actualDescription = investorDetailsPageNewUI.getActualIssuanceDescription();
        Assert.assertEquals(actualDescription, description, "actual description doesn't match entered reason");
        endTestLevel();

        // validate token
        String actualToken = investorDetailsPageNewUI.getActualIssuanceTokenName();
        Assert.assertEquals(actualToken, tokenName, "actual description doesn't match entered reason");
        endTestLevel();

    }

    public double setTokensAmount(String tokensHeldIn, boolean decimal) {
        double tokenAmount = 0;
        if (tokensHeldIn.equals("TBE")) {
            if (decimal) {
                tokenAmount = ISSUED_TOKENS_DECIMAL;
            } else {
                tokenAmount = ISSUED_TOKENS;
            }
        } else if (tokensHeldIn.equals("wallet")) {
            if (decimal) {
                tokenAmount = TBE_ISSUED_TOKENS_DECIMAL;
            } else {
                tokenAmount = TBE_ISSUED_TOKENS;
            }
        } else {
            errorAndStop("No token type detekted", false);
        }
        return tokenAmount;
    }

    public void cpAddIssuance(String tokensHeldIn, double tokenAmount, String currency, String issuanceDescription) {
        boolean isIssuanceDetailsCorrect = investorDetailsPageNewUI.clickAddIssuance()
                .addIssuanceDetails(tokensHeldIn, tokenAmount, investmentAmount, currency, issuanceDescription);
        Assert.assertTrue(isIssuanceDetailsCorrect);
    }

    public void cpAddIssuances(InvestorDetails investorDetails, String issuanceTBEReason, String issuerName) {

        startTestLevel("Add issuance");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        SoftAssert softAssertion = new SoftAssert();
        String walletName = "CP test wallet";
        String walletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        getBrowser().refreshPage();
        String issuanceReason = "automatic test issuance";
        investorDetailsPage.clickAddIssuance()
                .selectTokenWalletId(walletName, walletAddress)
                .typeReason(issuanceReason)
                .typeIssuanceAmount(ISSUED_TOKENS)
                .clickOk(investorDetails.getFirstName(), investorDetails.getMiddleName(), investorDetails.getLastName(), ISSUED_TOKENS);

        investorDetailsPage.issuancesCard.waitForTableToContainNumberOfRows(1);

        // extract transaction id
        String transactionId = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "ID");
        info("transactionId = " + transactionId);

        // validate creation date
        investorDetailsPage.validateDate("Issuances", investorDetailsPage, "Created");

        // validate executed
        String actualExecuted = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Executed");
        Assert.assertEquals(actualExecuted, "-", "actual executed value isn't empty");

        // validate issued tokens
        String actualIssuedAmount = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Issue Amount");
        Assert.assertEquals(actualIssuedAmount.trim(), ISSUED_TOKENS + "", "actual issued tokens amount doesn't match entered value");

        // validate source
        String actualSource = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Source");
        Assert.assertEquals(actualSource, "manual", "actual source should have been 'manual'");

        // validate target
        String actualTarget = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Target");
        Assert.assertEquals(actualTarget, "wallet", "actual target should have been 'wallet'");

        // validate status
        String actualStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(actualStatus, "processing", "actual status should have been 'processing'");

        // validate Issuance date
        investorDetailsPage.validateDate("Issuances", investorDetailsPage, "Created");

        // validate description
        String actualDescription = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Description");
        Assert.assertEquals(actualDescription, issuanceReason, "actual description doesn't match entered reason");
        endTestLevel();

        startTestLevel("Add TBE issuance");
        investorDetailsPage.clickAddIssuance()
                .typeReason(issuanceTBEReason)
                .typeIssuanceAmount(TBE_ISSUED_TOKENS)
                .clickOk(investorDetails.getFirstName(), investorDetails.getMiddleName(), investorDetails.getLastName(), TBE_ISSUED_TOKENS);

        investorDetailsPage.issuancesCard.waitForTableToContainNumberOfRows(2);

        // extract TBE transaction id
        String transactionTBEId = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "ID");
        info("TBEtransactionId = " + transactionTBEId);

        // validate creation date
        investorDetailsPage.validateDate("Issuances", investorDetailsPage, "Created");


        // validate executed
        String actualTBEExecuted = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Executed");
        softAssertion.assertEquals(actualTBEExecuted, "-", "actual executed value isn't empty");

        // validate issued TBE tokens
        String actualTBEIssuedAmount = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Issue Amount");
        softAssertion.assertEquals(actualTBEIssuedAmount.trim(), TBE_ISSUED_TOKENS + "", "actual issued tokens amount doesn't match entered value");

        // validate source
        actualSource = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Source");
        softAssertion.assertEquals(actualSource, "manual", "actual source should have been 'manual'");

        // validate TBE target
        String actualTBETarget = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Target");
        softAssertion.assertEquals(actualTBETarget, "TBE", "actual target should have been 'TBE'");

        // validate status
        String actualTBEStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Status");
        softAssertion.assertEquals(actualTBEStatus, "processing", "actual status should have been 'processing'");

        // validate TBE Issuance date
        investorDetailsPage.validateDate("Issuances", investorDetailsPage, "Created");


        // validate TBE description
        String actualTBEDescription = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Description");
        softAssertion.assertEquals(actualTBEDescription, issuanceTBEReason, "actual description doesn't match entered reason");
        endTestLevel();

        startTestLevel("Sign the transactions");
        String investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpSignatures signatures = sideMenu.clickSignatures();
        WalletDetails walletDetails = Users.getIssuerWalletDetails(issuerName, getTestNetwork());

        //signing signatures
        for (int i = 0; i < 2; i++) {
            signatures.waitForSignaturePageToStabilize();
            signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                    .clickSignAllTransactionsButton()
                    .clickWalletTypeSignatureRadioButton()
                    .typeSignerAddress(walletDetails.getWalletAddress())
                    .typePrivateKey(walletDetails.getWalletPrivateKey())
                    .clickSignNow();
        }
        endTestLevel();

        startTestLevel("validate tokens arrived to investor");
        navigateInvestorDirectUrl(investorDirectUrl);
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);
        // force cron jobs to run now - to make transaction status to become 'success' faster
        TestNetwork currentTestNetwork = getTestNetwork();
        if (!MainConfig.isProductionEnvironment() && (currentTestNetwork.needsBypass())) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }

        // validate regular token status
        IssuanceNotSuccessTimeoutException issuanceNotSuccessTimeoutException = new IssuanceNotSuccessTimeoutException("Issuance was not marked as success even after " + waitTimeSecondsIssuanceReady + " seconds.");
        investorDetailsPage.issuancesCard.waitForEntityInTableStatusToBecome(1, "success", waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady, issuanceNotSuccessTimeoutException);
        String finalWalletIssuanceStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(finalWalletIssuanceStatus, "success", "actual status should have been 'success'");

        // validate tbe token status
        investorDetailsPage.issuancesCard.waitForEntityInTableStatusToBecome(2, "success", waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady, issuanceNotSuccessTimeoutException);
        String finalTBEStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Status");
        Assert.assertEquals(finalTBEStatus, "success", "actual status should have been 'success'");

        // validate tokens held
        int waitTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.waitTimeTokensToArriveInWalletQuorumSeconds);
        int intervalTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTokensToArriveInWalletQuorumSeconds);
        investorDetailsPage.walletsCard.waitForWalletToHoldNumberOfTokens(1, ISSUED_TOKENS, waitTimeSecondsTokensToArriveInWallet, intervalTimeSecondsTokensToArriveInWallet);
        String actualTokensHeld = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Tokens Held");
        Assert.assertEquals(actualTokensHeld.trim(), ISSUED_TOKENS + "", "actual tokens held should match issuanced tokens");

        // validate Securitize total amount of tokens
        int expectedTotalTokensHeld = ISSUED_TOKENS + TBE_ISSUED_TOKENS;
        investorDetailsPage.waitForTokensUpdate("Securitize Logo", expectedTotalTokensHeld);
        int totalTokensHeld = investorDetailsPage.getSecuritizeTotalTokenAmount();
        Assert.assertEquals(totalTokensHeld, expectedTotalTokensHeld, "Total of actual tokens held should match total issuanced tokens");
        endTestLevel();
    }

    public void cpImportInvestor(InvestorDetails investorDetails, String importCSVFilePath) throws IOException {

        startTestLevel("Updating csv file with random email");
        String investorEmail = investorDetails.getEmail();
        CSVUtils importInvestorFile = new CSVUtils(new File("src/main/resources/investorMetaData/import_investor.csv"));
        importInvestorFile.updateCSVInvestorEmailByIndex(1, "Email", investorEmail, importCSVFilePath);
        String absoluteCSVImportFilePath = new File("tempImportFile.csv").getAbsolutePath();
        endTestLevel();

        startTestLevel("Navigate to Onboarding screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Import Investor");
        CpImportInvestors importInvestorsPage = onBoarding.clickImportInvestor();
        importInvestorsPage.uploadFile(absoluteCSVImportFilePath)
                .clickImportInvestorButton();
        endTestLevel();

        startTestLevel("Filtering by investor's email");
        waitForInvestorToBeFound(investorDetails.getEmail());
        endTestLevel();
    }

    public void cpImportMultipleInvestor(InvestorDetails firstInvestorDetails, InvestorDetails secondInvestorDetails, InvestorDetails thirdInvestorDetails, InvestorDetails fourthInvestorDetails, String importCSVFilePath) throws IOException, InterruptedException, CsvException {

        startTestLevel("Updating csv file with random emails");
        CSVUtils importInvestorFile = new CSVUtils(new File("src/main/resources/investorMetaData/import_multiple_investor.csv"));
        String firstInvestorEmail = firstInvestorDetails.getEmail();
        String secondInvestorEmail = secondInvestorDetails.getEmail();
        String thirdInvestorEmail = thirdInvestorDetails.getEmail();
        String fourthInvestorEmail = fourthInvestorDetails.getEmail();
        importInvestorFile.updateMultipleInvestorEmail("Email", firstInvestorEmail, secondInvestorEmail, thirdInvestorEmail, fourthInvestorEmail, importCSVFilePath);
        String absoluteCSVImportFilePath = new File("tempImportMultipleFile.csv").getAbsolutePath();
        endTestLevel();

        startTestLevel("Navigate to Onboarding screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Import Investor");
        CpImportInvestors importInvestorsPage = onBoarding.clickImportInvestor();
        importInvestorsPage.uploadFile(absoluteCSVImportFilePath)
                .clickImportInvestorButton();
        endTestLevel();
    }

    public void cpImportBulkIssuanceInvestors(String importCSVFilePath, int amountOfInvestors, String lastName) {

        startTestLevel("Updating csv file with random emails and a single random last name");
        SoftAssert softAssertion = new SoftAssert();
        CSVUtils importInvestorFile = new CSVUtils(new File("src/main/resources/investorMetaData/import_bulk_issuance.csv"));
        importInvestorFile.updateCsvFileForBulkIssuance("Email", "Last Name", amountOfInvestors, importCSVFilePath, lastName);
        String absoluteCSVImportFilePath = new File("tempImportBulkIssuanceFile.csv").getAbsolutePath();
        endTestLevel();

        startTestLevel("Navigate to Onboarding screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Import Investor");
        CpImportInvestors importInvestorsPage = onBoarding.clickImportInvestor();
        importInvestorsPage.uploadFile(absoluteCSVImportFilePath)
                .clickImportAndCloseButton();
        endTestLevel();

        startTestLevel("Verify Bulk Import was succesful");
        getBrowser().refreshPage();
        onBoarding.searchInvestorByTextBox(lastName);
        onBoarding.waitForTableToCountRowsByCellDetail(amountOfInvestors, 360, "AUT519");
        endTestLevel();

        startTestLevel("Selecting investor for edit last name");
        onBoarding.clickOnInvestorByIndex(0);
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        endTestLevel();

        startTestLevel("Editing investor's last name for future tbe validation");
        investorDetailsPage.waitForEditButtonToBeVisible()
                .clickEdit()
                .typeLastName(lastName + "tbe")
                .typePhoneNumber("123456")
                .clickSaveChanges();
        endTestLevel();

        startTestLevel("Go back to Onboarding screen");
        sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Selecting an investor for adding a wallet");
        onBoarding.searchInvestorByTextBox(lastName);
        onBoarding.waitForTableToCountRowsByCellDetail(amountOfInvestors, 180, "AUT519");
        onBoarding.clickOnInvestorByIndex(5);
        endTestLevel();

        startTestLevel("Editing investor's last name for future selection");
        investorDetailsPage.waitForEditButtonToBeVisible()
                .clickEdit()
                .typeLastName(lastName + "wallet")
                .typePhoneNumber("123456")
                .clickSaveChanges();
        endTestLevel();
    }

    public void cpBulkIssuance(int amountOfInvestors, String lastName) {

        startTestLevel("Navigate to Fundraise");
        SoftAssert softAssertion = new SoftAssert();
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpFundraise fundraise = sideMenu.clickFundraise();
        endTestLevel();

        startTestLevel("Verify Investors are present on Fundraise screen");
        fundraise.searchInvestorByTextBox(lastName);
        softAssertion.assertEquals(amountOfInvestors, fundraise.countInvestorsByName("AUT519"));
        endTestLevel();

        startTestLevel("Perform a bulk issuance");
        fundraise.clickAllTickBoxes();
        fundraise.clickRunIssuanceButton();
        fundraise.clickConfirmRunButton();
        fundraise.clickOkButton();
        endTestLevel();
    }

    public void cpAddWalletToImportedInvestor() {

        startTestLevel("Add wallet");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        EthereumWallet ethereumWallet = EthereumUtils.generateRandomWallet();
        if (ethereumWallet == null) return; // should never happen due to error_an_stop within
        String walletAddress = ethereumWallet.getAddress();
        String walletName = "CP test wallet";
        info("New wallet address: " + walletAddress);
        investorDetailsPage.clickAddWallet()
                .typeName(walletName)
                .typeAddress(walletAddress)
                .clickOk();

        investorDetailsPage.walletsCard.waitForTableToContainNumberOfRows(1);

        // validate wallet name
        String actualWalletName = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Name");
        Assert.assertEquals(actualWalletName, walletName, "actual wallet name doesn't match entered value");

        // validate wallet address
        String actualWalletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        Assert.assertEquals(actualWalletAddress.toLowerCase(), walletAddress.toLowerCase(), "actual wallet address doesn't match entered value");
        endTestLevel();

        startTestLevel("Validate wallet status changed to 'Syncing'");
        getBrowser().refreshPage();
        investorDetailsPage.walletsCard.scrollTableIntoView(1);
        investorDetailsPage.walletsCard.waitForTableToContainNumberOfRows(1);
        String actualWalletStatus = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(actualWalletStatus, "syncing", "wallet status after KYC edit should be 'syncing'");
        endTestLevel();


        startTestLevel("Wait for wallet status to become 'ready'");
        int waitTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeWalletToBecomeReadyQuorumSeconds);
        int intervalTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeWalletToBecomeReadyQuorumSeconds);
        // force cron jobs to run now - to make wallet 'ready' faster
        if (!MainConfig.isProductionEnvironment()) {
            SshRemoteExecutor.executeScript("walletRegistrationBypass.sh");
        }
        WalletNotReadyTimeoutException walletNotReadyTimeoutException = new WalletNotReadyTimeoutException("Wallet was not marked as ready even after " + waitTimeSecondsWalletReady + " seconds.");
        investorDetailsPage.walletsCard.waitForEntityInTableStatusToBecome(1, "ready", waitTimeSecondsWalletReady, intervalTimeSecondsWalletReady, walletNotReadyTimeoutException);
        endTestLevel();
    }

    public void cpSignBulkIssuance(String issuerName, String randomGeneratedLastName) {

        startTestLevel("Sign the transactions");
        String bulkIssuanceDescriptionForTbe = "Issuing 18 tokens for 9 investors, address: TBE Wallet";
        String bulkIssuanceDescriptionForWallet = "Issuing 2 tokens to user AUT519 import " + randomGeneratedLastName + "wallet";
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpSignatures signatures = sideMenu.clickSignatures();
        String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerWalletAddress);
        String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPrivateKey);

        //signing bulk issuance
        signatures.waitForSignaturePageToStabilize()
                .waitForTableToContainRowsByDescriptionOnAllStatuses(bulkIssuanceDescriptionForWallet, 1)
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();

        signatures.filterByStatus("Success")
                .waitForSignaturePageToStabilize()
                .waitForTableToContainRowsByDescriptionOnAllStatuses(bulkIssuanceDescriptionForWallet, 1);
        endTestLevel();

    }

    public void cpValidateBulkIssuanceForRegularToken(String investorLastName) {
        //navigate to Fundraise screen
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickFundraise();

        //select wallet investor
        CpFundraise cpFundraise = new CpFundraise(getBrowser());
        cpFundraise.searchUniqueInvestorByTextBox(investorLastName + "wallet", NON_VALID_SEARCH);
        cpFundraise.clickOnFirstInvestor();

        //validate tokens arrived to investor
        startTestLevel("validate tokens arrived to investor");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);
        // force cron jobs to run now - to make transaction status to become 'success' faster
        if (!MainConfig.isProductionEnvironment()) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }

        // validate regular token status
        IssuanceNotSuccessTimeoutException issuanceNotSuccessTimeoutException = new IssuanceNotSuccessTimeoutException("Issuance was not marked as success even after " + waitTimeSecondsIssuanceReady + " seconds.");
        investorDetailsPage.issuancesCard.waitForEntityInTableStatusToBecome(1, "success", waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady, issuanceNotSuccessTimeoutException);
        String finalWalletIssuanceStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(finalWalletIssuanceStatus, "success", "actual status should have been 'success'");

        // validate tokens held
        int waitTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.waitTimeTokensToArriveInWalletQuorumSeconds);
        int intervalTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTokensToArriveInWalletQuorumSeconds);
        investorDetailsPage.walletsCard.waitForWalletToHoldNumberOfTokens(1, 2, waitTimeSecondsTokensToArriveInWallet, intervalTimeSecondsTokensToArriveInWallet);
        String actualTokensHeld = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Tokens Held");
        Assert.assertEquals(actualTokensHeld.trim(), 2 + "", "actual tokens held should match issuanced tokens");

        // validate Securitize total amount of tokens
        investorDetailsPage.waitForSecuritizeLogoToExist(3, 10);
        int totalTokensHeld = investorDetailsPage.getSecuritizeTotalTokenAmount();
        int expectedTotalTokensHeld = 2;
        Assert.assertEquals(totalTokensHeld, expectedTotalTokensHeld, "Total of actual tokens held should match total issuanced tokens");
        endTestLevel();
    }

    public void cpValidateBulkIssuanceForTbeToken(String investorLastName) {
        //navigate to Fundraise screen
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickFundraise();

        //select tbe investor
        CpFundraise cpFundraise = new CpFundraise(getBrowser());
        cpFundraise.searchUniqueInvestorByTextBox(investorLastName + "tbe", NON_VALID_SEARCH);
        cpFundraise.clickOnFirstInvestor();

        //validate tokens arrived to investor
        startTestLevel("validate tokens arrived to investor");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);
        // force cron jobs to run now - to make transaction status to become 'success' faster
        if (!MainConfig.isProductionEnvironment()) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }

        // validate tbe token status
        IssuanceNotSuccessTimeoutException issuanceNotSuccessTimeoutException = new IssuanceNotSuccessTimeoutException("Issuance was not marked as success even after " + waitTimeSecondsIssuanceReady + " seconds.");
        investorDetailsPage.issuancesCard.waitForEntityInTableStatusToBecome(1, "success", waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady, issuanceNotSuccessTimeoutException);
        String finalTBEStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(finalTBEStatus, "success", "actual status should have been 'success'");

        // validate tokens held
        int waitTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.waitTimeTokensToArriveInWalletQuorumSeconds);
        int intervalTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTokensToArriveInWalletQuorumSeconds);
        investorDetailsPage.waitForSecuritizeLogoToExist(waitTimeSecondsTokensToArriveInWallet, intervalTimeSecondsTokensToArriveInWallet);
        GeneralCard generalCard = new GeneralCard(getBrowser());
        String actualTokensHeld = generalCard.getTotalTbeSecurities();
        Assert.assertEquals(actualTokensHeld.trim(), 2 + "", "actual tbe tokens held should match");

        // validate Securitize total amount of tokens
        int totalTokensHeld = investorDetailsPage.getSecuritizeTotalTokenAmount();
        int expectedTotalTokensHeld = 2;
        Assert.assertEquals(totalTokensHeld, expectedTotalTokensHeld, "Total of actual tokens held should match total issuanced tokens");
        endTestLevel();
    }

    public void cpVerifyFirstRowImportInvestorInformation(String importCSVFilePath) throws FileNotFoundException, InterruptedException {

        startTestLevel("Verify Onboarding names");
        CpOnBoarding onBoarding = new CpOnBoarding(getBrowser());
        SoftAssert softAssertion = new SoftAssert();
        CSVUtils importedInvestorCSV = new CSVUtils(new File(importCSVFilePath));

        onBoarding.waitForTableToBeNotEmpty();

        String firstRowName = onBoarding.getInvestorDetailByIndex(0, "Name / Entity");
        //validate First Name
        softAssertion.assertTrue(firstRowName.contains(importedInvestorCSV.getCSVInvestorDetailByIndex(1, "First Name")), "First Name not found on exported file");
        //validate Middle Name
        softAssertion.assertTrue(firstRowName.contains(importedInvestorCSV.getCSVInvestorDetailByIndex(1, "Middle Name")), "Middle Name not found on exported file");
        //validate Last Name
        softAssertion.assertTrue(firstRowName.contains(importedInvestorCSV.getCSVInvestorDetailByIndex(1, "Last Name")), "Last Name not found on exported file");
        endTestLevel();

        startTestLevel("Verify Onboarding country codes");
        String firstRowCountry = onBoarding.getInvestorDetailByIndex(0, "Country");
        firstRowCountry = RegexWrapper.trimUSCountry(firstRowCountry);
        String firstRowCountryCode = CountryCode.findByName(firstRowCountry).get(0).name();
        //validate Country Code
        softAssertion.assertTrue(firstRowCountryCode.contains(importedInvestorCSV.getCSVInvestorDetailByIndex(1, "Country")), "Country Code not found on exported file");
        endTestLevel();

        startTestLevel("Verify Onboarding investor type");
        String firstRowInvestorType = onBoarding.getInvestorDetailByIndex(0, "Type").toLowerCase();
        firstRowInvestorType = RegexWrapper.replaceWhiteSpaceForCharacter(firstRowInvestorType, "-");
        //validate Investor Type
        softAssertion.assertTrue(firstRowInvestorType.contains(importedInvestorCSV.getCSVInvestorDetailByIndex(1, "Investor Type")), "Investor Type not found on exported file");
        endTestLevel();

        startTestLevel("Verify SecuritizeID status");
        String firstRowSecuritizeID = onBoarding.getInvestorDetailByIndex(0, "SecuritizeID").toLowerCase();
        //validate Securitize ID status
        softAssertion.assertTrue(firstRowSecuritizeID.contains(importedInvestorCSV.getCSVInvestorDetailByIndex(1, "SecuritizeID")), "SecuritizeID exists not found on exported file");
        endTestLevel();

        startTestLevel("Verify KYC Status");
        String firstRowKYCStatus = onBoarding.getInvestorDetailByIndex(0, "KYC Status");
        firstRowKYCStatus = RegexWrapper.replaceWhiteSpaceForCharacter(firstRowKYCStatus, "-");
        //validate KYC Status
        softAssertion.assertTrue(firstRowKYCStatus.contains(importedInvestorCSV.getCSVInvestorDetailByIndex(1, "KYC Status")), "KYC Status not found on exported file");
        endTestLevel();

        startTestLevel("Verify Accreditation Status");
        String firstRowAccreditationStatus = onBoarding.getInvestorDetailByIndex(0, "Accredited Status");
        //validate Accredited Status
        softAssertion.assertTrue(firstRowAccreditationStatus.contains(importedInvestorCSV.getCSVInvestorDetailByIndex(1, "Accreditation Status")), "Accreditation Status not found on exported file");
        endTestLevel();

        startTestLevel("Verify Labels");
        String firstRowLabels = onBoarding.getInvestorDetailByIndex(0, "Labels");
        firstRowLabels = RegexWrapper.blankIfNull(firstRowLabels);
        //validate Labels
        softAssertion.assertTrue(firstRowLabels.contains(importedInvestorCSV.getCSVInvestorDetailByIndex(1, "Labels")), "Labels not found on exported file");
        softAssertion.assertAll();
        endTestLevel();

    }

    public void cpVerifyImportedInvestorInformation(String importCSVFilePath, InvestorDetails investorDetails, int index_csv) {

        startTestLevel("Filtering by investor's email and going to investor details");
        waitForInvestorToBeFound(investorDetails.getEmail());
        endTestLevel();

        startTestLevel("Loading Investor Details page");
        SoftAssertions softly = new SoftAssertions();
        CSVUtils importedInvestorCSV = new CSVUtils(new File(importCSVFilePath));
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        endTestLevel();

        startTestLevel("Verify KYC Status");
        CpOnBoarding onBoarding = new CpOnBoarding(getBrowser());
        String firstRowKYCStatus = onBoarding.getInvestorDetailByIndex(0, "KYC Status");
        firstRowKYCStatus = RegexWrapper.replaceWhiteSpaceForCharacter(firstRowKYCStatus, "-");
        //validate KYC Status
        softly.assertThat(firstRowKYCStatus)
                .as("Validate KYC Status")
                .contains(importedInvestorCSV.getCSVInvestorDetailByIndex(index_csv, "KYC Status"));
        endTestLevel();

        startTestLevel("Verify Accreditation Status");
        String firstRowAccreditationStatus = onBoarding.getInvestorDetailByIndex(0, "Accredited Status");
        //validate Accredited Status
        softly.assertThat(firstRowAccreditationStatus)
                .as("Validate Accreditation Status")
                .contains(importedInvestorCSV.getCSVInvestorDetailByIndex(index_csv, "Accreditation Status"));
        endTestLevel();

        startTestLevel("Enter to investor details");
        investorDetailsPage = onBoarding.clickOnFirstInvestor();
        endTestLevel();

        startTestLevel("Verify Investor Details Name on Investor Details screen");
        softly.assertThat(investorDetailsPage.getFirstName())
                .as("Validate first name")
                .contains(importedInvestorCSV.getCSVInvestorDetailByIndex(index_csv, "First Name"));
        softly.assertThat(investorDetailsPage.getMiddleName())
                .as("Validate middle name")
                .contains(importedInvestorCSV.getCSVInvestorDetailByIndex(index_csv, "Middle Name"));
        softly.assertThat(investorDetailsPage.getLastName())
                .as("Validate last name")
                .contains(importedInvestorCSV.getCSVInvestorDetailByIndex(index_csv, "Last Name"));
        endTestLevel();

        startTestLevel("Verify Investor Investment Details on Investor Details screen");
        if (investorDetailsPage.isCurrentPledgedAmountVisible()) {
            softly.assertThat(investorDetailsPage.getCurrentPledgedAmount())
                    .as("Validate Pledged amount")
                    .contains(importedInvestorCSV.getCSVInvestorDetailByIndex(index_csv, "Pledged Amount"));
            softly.assertThat(investorDetailsPage.getCurrentPledgedAmount())
                    .as("Validate Currency")
                    .contains(importedInvestorCSV.getCSVInvestorDetailByIndex(index_csv, "Currency"));
            softly.assertThat(investorDetailsPage.getCurrentTotalFunded())
                    .as("Validate Investment amount")
                    .contains(importedInvestorCSV.getCSVInvestorDetailByIndex(index_csv, "Investment Amount"));
        }
        softly.assertAll();
        endTestLevel();

        startTestLevel("Navigate back to Onboarding screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickOnBoarding();
        endTestLevel();
    }

    private void waitForInvestorToBeFound(String investorEmail) {
        Function<String, Boolean> internalWaitForInvestorToBeFound = t -> {
            try {
                CpOnBoarding onBoarding = new CpOnBoarding(getBrowser());
                onBoarding.searchUniqueInvestorWithTimeout(investorEmail, 10);
                return true;
            } catch (TimeoutException e) {
                getBrowser().refreshPage();
                return false;
            }
        };

        String description = "waitForInvestorToBeFound: " + investorEmail;
        Browser.waitForExpressionToEqual(internalWaitForInvestorToBeFound, null, true, description, 120, 10000);
    }

    public void transferTBEToWallet(String procedureType, String firstName, String issuerName) {

        startTestLevel("Perform transfer TBE to wallet");
        performTransferTBEToWallet();
        endTestLevel();

        startTestLevel("Sign " + procedureType + " transaction");
        signSignatures(issuerName, firstName, 1);
        endTestLevel();

        startTestLevel("Accelerate transaction");
        accelerateTransaction();
        endTestLevel();

        startTestLevel("Validate TBE update after transaction");
        investorDetailsPage.waitForTokensUpdate("TBE", tokensTBE - tokensToBeProcedure);
        endTestLevel();

        startTestLevel("Validate TBE update after transaction");
        investorDetailsPage.waitForTokensUpdate("wallet", tokensWallet + tokensToBeProcedure);
        endTestLevel();
    }

    public void accelerateTransaction() {
        navigateInvestorDirectUrl(investorDirectUrl);
        // force cron jobs to run now - to make transaction status to become 'success' faster
        TestNetwork currentTestNetwork = getTestNetwork();
        if (!MainConfig.isProductionEnvironment() && (currentTestNetwork.needsBypass())) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }
    }

    public void performTransferTBEToWallet() {
        startTestLevel("Click on Transfer to wallet from TBE button");
        getBrowser().refreshPage();
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.clickTransferToWalletFromTBE()
                .setAmountToWithdraw(tokensToBeProcedure)
                .clickOk();
        endTestLevel();
    }


    public void cpMasterSecurityHolderFileValidateInvestorData(String searchText) {

        navigateToPage(msf);
        CpMasterSecurityholderFile cpMsf = new CpMasterSecurityholderFile(getBrowser());
        investorDetailsPage = cpMsf.searchUniqueInvestorByTextBox(NON_VALID_SEARCH, searchText, true);

        startTestLevel("Save Investor Details Data and navigate back");
        String firstName = investorDetailsPage.getFirstName();
        String middleName = investorDetailsPage.getMiddleName();
        String lastName = investorDetailsPage.getLastName();
        String country = investorDetailsPage.getCountry();
        String state = investorDetailsPage.getState();
        String email = investorDetailsPage.getEmail();
        String totalAmountOfTokens = String.valueOf(investorDetailsPage.getSecuritizeTotalTokenAmount());
        endTestLevel();

        startTestLevel("Navigate back and search investor again");
        browser.get().navigateBack();
        endTestLevel();

        startTestLevel("Validate MSF Investor name");
        String firstRowName = cpMsf.getInvestorDetailByIndex(0, "Record Holder");
        Assert.assertTrue(firstRowName.contains(firstName), "First Name not found on MSF table");
        Assert.assertTrue(firstRowName.contains(middleName), "Middle Name not found on MSF table");
        Assert.assertTrue(firstRowName.contains(lastName), "Last Name not found on MSF table");
        endTestLevel();

        startTestLevel("Validate MSF Country");
        String firstRowCountry = cpMsf.getInvestorDetailByIndex(0, "Country of residence");
        Assert.assertTrue(firstRowCountry.contains(country), "Country of residence not found on MSF table");
        endTestLevel();

        startTestLevel("Validate MSF US State");
        String firstRowState = cpMsf.getInvestorDetailByIndex(0, "US State");
        Assert.assertTrue(firstRowState.contains(state), "US State not found on MSF table");
        endTestLevel();

        startTestLevel("Validate MSF Email");
        String firstRowEmail = cpMsf.getInvestorDetailByIndex(0, "Email");
        Assert.assertTrue(firstRowEmail.contains(email), "Email not found on MSF table");
        endTestLevel();

        startTestLevel("Validate MSF Number of Securities");
        String firstRowSecurities = cpMsf.getInvestorDetailByIndex(0, "Number of Securities").replace(",", "");
        Assert.assertTrue(firstRowSecurities.contains(totalAmountOfTokens), "Number of securities not found on MSF table");
        endTestLevel();
    }

    public void cpMasterSecuriryholderFileSearchInvestor() {
        CpMasterSecurityholderFile cpMsf = new CpMasterSecurityholderFile(getBrowser());
        cpMsf.searchUniqueInvestorByTextBox(NON_VALID_SEARCH, firstName, false);
    }

    public void cpMasterSecuriryholderFileValidateInvestorData() {
        startTestLevel("Validate MSF Investor name");
        CpMasterSecurityholderFile cpMsf = new CpMasterSecurityholderFile(getBrowser());
        SoftAssert softAssertion = new SoftAssert();
        String firstRowName = cpMsf.getInvestorDetailByIndex(0, "Record Holder");
        softAssertion.assertTrue(firstRowName.contains(InvestorsData.getUserDetails(InvestorsProperty.investorFirstName)), "First Name not found on MSF table");
        softAssertion.assertTrue(firstRowName.contains(InvestorsData.getUserDetails(InvestorsProperty.investorMiddleName)), "Middle Name not found on MSF table");
        softAssertion.assertTrue(firstRowName.contains(InvestorsData.getUserDetails(InvestorsProperty.investorLastName)), "Last Name not found on MSF table");
        endTestLevel();

        startTestLevel("Validate MSF Country");
        String firstRowCountry = cpMsf.getInvestorDetailByIndex(0, "Country of residence");
        softAssertion.assertTrue(firstRowCountry.contains(InvestorsData.getUserDetails(InvestorsProperty.investorCountry)), "Country of residence not found on MSF table");
        endTestLevel();

        startTestLevel("Validate MSF US State");
        String firstRowState = cpMsf.getInvestorDetailByIndex(0, "US State");
        softAssertion.assertTrue(firstRowState.contains(InvestorsData.getUserDetails(InvestorsProperty.investorState)), "US State not found on MSF table");
        endTestLevel();

        startTestLevel("Validate MSF Email");
        String firstRowEmail = cpMsf.getInvestorDetailByIndex(0, "Email");
        softAssertion.assertTrue(firstRowEmail.contains(InvestorsData.getUserDetails(InvestorsProperty.investorEmail)), "Email not found on MSF table");
        endTestLevel();

        startTestLevel("Validate MSF Number of Securities");
        String firstRowSecurities = cpMsf.getInvestorDetailByIndex(0, "Number of Securities");
        softAssertion.assertTrue(firstRowSecurities.contains(InvestorsData.getUserDetails(InvestorsProperty.investorTotalAmountOfTokens)), "Number of securities not found on MSF table");
        softAssertion.assertAll();
        endTestLevel();
    }

    public void cpNavigateToSecuritiesTransactions() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickSecuritiesTransactions();
    }

    public void cpSecuritiesTransactionsSearchTransaction(String transactionEthId) {
        CpSecuritiesTransactions cpSecuritiesTransactions = new CpSecuritiesTransactions(getBrowser());
        cpSecuritiesTransactions.searchValueInvestorByTextBox(transactionEthId, NON_VALID_SEARCH);
    }

    public void cpSecuritiesTransactionsValidateRegularTransactionData(String transactionEthId) {

        startTestLevel("Navigate to Securities Transaction and search transaction");
        navigateToPage(securitiesTransactions);
        CpSecuritiesTransactions cpSecuritiesTransactions = new CpSecuritiesTransactions(getBrowser());
        cpSecuritiesTransactions.searchValueInvestorByTextBox(transactionEthId, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("get receiver updated data from investor details page and go back to Securities Transaction");
        investorDetailsPage = cpSecuritiesTransactions.getInvestorDetailByIDP("Receiver", 60);
        firstName = investorDetailsPage.getFirstName();
        middleName = investorDetailsPage.getMiddleName();
        lastName = investorDetailsPage.getLastName();
        getBrowser().navigateBack();
        cpSecuritiesTransactions.searchValueInvestorByTextBox(transactionEthId, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Validate Investor Regular Transaction ID");
        SoftAssert softAssertion = new SoftAssert();
        String regularTokenTxId = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Transaction ID");
        softAssertion.assertTrue(regularTokenTxId.contains(InvestorsData.getUserDetails(InvestorsProperty.investorTokenTxId)), "Transaction ID not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction Investor transaction type");
        String regularTokenTxType = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Type");
        softAssertion.assertTrue(regularTokenTxType.contains(InvestorsData.getUserDetails(InvestorsProperty.investorTransactionType)), "Investor transaction type not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction Investor transaction process type");
        String regularTokenTxProcessType = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Process type");
        softAssertion.assertTrue(regularTokenTxProcessType.contains(InvestorsData.getUserDetails(InvestorsProperty.investorProcessType)), "Investor transaction process type not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction Investor transaction sender");
        String regularTokenSender = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Sender");
        softAssertion.assertTrue(regularTokenSender.contains(InvestorsData.getUserDetails(InvestorsProperty.investorSender)), "Investor transaction sender not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction Receiver");
        String regularTokenReceiver = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Receiver");
        softAssertion.assertTrue(regularTokenReceiver.contains(firstName), "First Name not found on Securities Transactions table");
        softAssertion.assertTrue(regularTokenReceiver.contains(middleName), "Middle Name not found on Securities Transactions table");
        softAssertion.assertTrue(regularTokenReceiver.contains(lastName), "Last Name not found on Securities Transactions table");

        startTestLevel("Validate Securities Transaction Amount");
        String regularTokenAmount = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Amount");
        softAssertion.assertTrue(regularTokenAmount.contains(InvestorsData.getUserDetails(InvestorsProperty.investorTotalTokensHeld)), "Investor transaction amount not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction manually modified status");
        String regularTokenModifiedStatus = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Manually modified");
        softAssertion.assertTrue(regularTokenModifiedStatus.contains(InvestorsData.getUserDetails(InvestorsProperty.investorManuallyModified)), "Investor transaction amount not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Clearing text from search box");
        cpSecuritiesTransactions.clearValueFromTextBox();
        endTestLevel();
    }

    public void cpSecuritiesTransactionsValidateTBETransactionData(String transactionTBEEthId) {

        startTestLevel("Navigate to Securities Transaction and search transaction");
        navigateToPage(securitiesTransactions);
        CpSecuritiesTransactions cpSecuritiesTransactions = new CpSecuritiesTransactions(getBrowser());
        cpSecuritiesTransactions.searchValueInvestorByTextBox(transactionTBEEthId, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("get receiver updated data from investor details page and go back to Securities Transaction");
        investorDetailsPage = cpSecuritiesTransactions.getInvestorDetailByIDP("Receiver", 120);
        firstName = investorDetailsPage.getFirstName();
        middleName = investorDetailsPage.getMiddleName();
        lastName = investorDetailsPage.getLastName();
        getBrowser().navigateBack();
        cpSecuritiesTransactions.searchValueInvestorByTextBox(transactionTBEEthId, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Validate Securities Transaction Investor transaction type");
        SoftAssert softAssertion = new SoftAssert();
        String tbeTokenTxType = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Type");
        softAssertion.assertTrue(tbeTokenTxType.contains(InvestorsData.getUserDetails(InvestorsProperty.investorTransactionType)), "Investor transaction type not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction Investor transaction process type");
        String tbeTokenTxProcessType = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Process type");
        softAssertion.assertTrue(tbeTokenTxProcessType.contains(InvestorsData.getUserDetails(InvestorsProperty.investorProcessType)), "Investor transaction process type not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction Investor transaction sender");
        String tbeTokenSender = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Sender");
        softAssertion.assertTrue(tbeTokenSender.contains(InvestorsData.getUserDetails(InvestorsProperty.investorSender)), "Investor transaction sender not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction Receiver");
        String tbeTokenReceiver = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Receiver");
        softAssertion.assertTrue(tbeTokenReceiver.contains(firstName), "First Name not found on Securities Transactions table");
        softAssertion.assertTrue(tbeTokenReceiver.contains(middleName), "Middle Name not found on Securities Transactions table");
        softAssertion.assertTrue(tbeTokenReceiver.contains(lastName), "Last Name not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction Amount");
        String tbeTokenAmount = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Amount");
        softAssertion.assertTrue(tbeTokenAmount.contains(InvestorsData.getUserDetails(InvestorsProperty.investorTotalTBESecurities)), "Investor transaction amount not found on Securities Transactions table");
        endTestLevel();

        startTestLevel("Validate Securities Transaction manually modified status");
        String tbeTokenModifiedStatus = cpSecuritiesTransactions.getInvestorDetailByIndex(0, "Manually modified");
        softAssertion.assertTrue(tbeTokenModifiedStatus.contains(InvestorsData.getUserDetails(InvestorsProperty.investorManuallyModified)), "Investor transaction amount not found on Securities Transactions table");
        softAssertion.assertAll();
        endTestLevel();
    }

    public void cpDestroyTokens(String issuerName, String firstName, String tokensHeldIn, int tokenToProcedure) {

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        endTestLevel();

        startTestLevel("Perform Destroy " + tokensHeldIn + " data");
        operationalProcedures.performDestroy(tokensHeldIn, tokenToProcedure, walletAddress, investorExternalID);
        endTestLevel();

        startTestLevel("Sign the transaction");
        signSignatures(issuerName, firstName, 1);
        endTestLevel();

        startTestLevel("Accelerate transaction");
        accelerateTransaction();
        endTestLevel();

        startTestLevel("Validate tokens reflects the correct amount after destroy");
        int tokenAmount = 0;
        if (tokensHeldIn.equals("wallet")) {
            tokenAmount = tokensWallet;
        } else if (tokensHeldIn.equals("TBE")) {
            tokenAmount = tokensTBE;
        }
        investorDetailsPage.waitForTokensUpdate(tokensHeldIn, tokenAmount - tokenToProcedure);
        endTestLevel();
    }

    public void cpEditInvestorDetails(String searchText) {

        startTestLevel("Navigate to Onboarding");
        navigateToPage(onboarding);
        CpOnBoarding onBoarding = new CpOnBoarding(getBrowser());
        endTestLevel();

        startTestLevel("Filtering by investor's first name");
        onBoarding.searchUniqueInvestorByTextBox(searchText, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Enter to investor details and save the data to future validation");
        CpInvestorDetailsPage investorDetailsPage = onBoarding.clickOnFirstInvestor();
        String email = investorDetailsPage.getEmail();
        String address = investorDetailsPage.getAddress();
        String additionalInfo = investorDetailsPage.getAddressAdditionalInfo();
        String postalCode = investorDetailsPage.getPostalCode();
        String city = investorDetailsPage.getCity();
        String country = investorDetailsPage.getCountry();
        String taxCountry = investorDetailsPage.getTaxCountry();
        endTestLevel();

        startTestLevel("Checking if current investor is individual");
        String investorValue = investorDetailsPage.getCurrentInvestorType();
        if (!investorValue.equals("Individual")) {
            investorDetailsPage.clickEdit()
                    .setCurrentInvestorType("Individual")
                    .clickOK()
                    .clickSaveChanges();
        }
        endTestLevel();

        startTestLevel("Update investor type to 'Entity'");
        investorDetailsPage.clickEdit()
                .setCurrentInvestorType("Entity")
                .clickOK()
                .clickSaveChanges();
        endTestLevel();

        startTestLevel("Validate Entity details");
        Assert.assertEquals(investorDetailsPage.getEmail(), email, "incorrect email, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddress(), address, "incorrect address, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), additionalInfo, "incorrect address, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getPostalCode(), postalCode, "incorrect postal code, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCity(), city, "incorrect city, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCountry(), country, "incorrect country, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getTaxCountry(), taxCountry, "incorrect tax country, this is not the value we set!");
        endTestLevel();

        startTestLevel("Update investor type to 'Individual'");
        investorDetailsPage.clickEdit()
                .setCurrentInvestorType("Individual")
                .clickOK()
                .clickSaveChanges();
        endTestLevel();

        startTestLevel("Validate investor details");
        Assert.assertEquals(investorDetailsPage.getEmail(), email, "incorrect email, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddress(), address, "incorrect address, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), additionalInfo, "incorrect address, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getPostalCode(), postalCode, "incorrect postal code, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCity(), city, "incorrect city, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCountry(), country, "incorrect country, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getTaxCountry(), taxCountry, "incorrect tax country, this is not the value we set!");
        endTestLevel();
    }

    public void cpVerifyFundraiseScreenData(String searchText) {

        startTestLevel("Navigate to Fundraise");
        navigateToPage(fundraise);
        CpFundraise fundraise = new CpFundraise(getBrowser());
        endTestLevel();

        startTestLevel("Filtering by investor's first name");
        fundraise.searchUniqueInvestorByTextBox(searchText, NON_VALID_SEARCH);
        investorDetailsPage = fundraise.clickOnFirstInvestor();
        endTestLevel();

        startTestLevel("Save investor data to future validation");
        String firstName = investorDetailsPage.getFirstName();
        String middleName = investorDetailsPage.getMiddleName();
        String lastName = investorDetailsPage.getLastName();
        String investorType = investorDetailsPage.getInvestorType();
        String investorCurrentTotalPledged = investorDetailsPage.getCurrentPledgedAmount();
        String investorCurrentTotalFunded = investorDetailsPage.getCurrentTotalFunded();
        String investorSubscriptionAgreementStatus = investorDetailsPage.getSubscriptionAgreement();
        String investorAssignedTokens = investorDetailsPage.getAssignedTokens();
        endTestLevel();

        startTestLevel("Navigate back and filtering by investor's first name again");
        getBrowser().navigateBack();
        endTestLevel();

        startTestLevel("Verify Fundraise names");
        SoftAssert softAssertion = new SoftAssert();
        String firstRowName = fundraise.getInvestorDetailByIndex(0, "Name / Entity");
        //validate First Name
        softAssertion.assertTrue(firstRowName.contains(firstName), "First Name not found on Fundraise screen");
        //validate Middle Name
        softAssertion.assertTrue(firstRowName.contains(middleName), "Middle Name not found on Fundraise screen");
        //validate Last Name
        softAssertion.assertTrue(firstRowName.contains(lastName), "Last Name not found on Fundraise screen");
        endTestLevel();

        startTestLevel("Verify Fundraise investor status");
        String firstRowInvestorStatus = fundraise.getInvestorDetailByIndex(0, "Investor Status");
        // validate Investor Status
        // Should be added
        // softAssertion.assertTrue(firstRowInvestorStatus.contains(investorStatus, "Investor Status not found on Fundraise screen");
        endTestLevel();

        startTestLevel("Verify Fundraise investor type");
        String firstRowInvestorType = fundraise.getInvestorDetailByIndex(0, "Type")
                .toLowerCase();
        firstRowInvestorType = RegexWrapper.replaceWhiteSpaceForCharacter(firstRowInvestorType, "-");
        //validate Investor Type
        softAssertion.assertTrue(firstRowInvestorType.contains(investorType), "Investor Type not found on Fundraise screen");
        endTestLevel();

        startTestLevel("Verify Pledged amount");
        String firstRowPledgedAmount = fundraise.getInvestorDetailByIndex(0, "Pledged");
        //validate Pledged amount
        softAssertion.assertTrue(firstRowPledgedAmount.contains(investorCurrentTotalPledged), "Pledged amount not found on Fundraise screen");
        endTestLevel();

        startTestLevel("Verify Funded amount");
        String firstRowFundedAmount = fundraise.getInvestorDetailByIndex(0, "Funded");
        //validate Funded amount
        softAssertion.assertTrue(firstRowFundedAmount.contains(investorCurrentTotalFunded), "Pledged amount not found on Fundraise screen");
        endTestLevel();

        startTestLevel("Verify Subscription Agreement Status");
        String firstRowSubscriptionAgreementStatus = fundraise.getInvestorDetailByIndex(0, "SA Status");
        //validate Subscription Agreement Status
        softAssertion.assertTrue(firstRowSubscriptionAgreementStatus.contains(investorSubscriptionAgreementStatus), "Funded amount not found on Fundraise screen");
        endTestLevel();

        startTestLevel("Verify Assigned Tokens");
        String firstRowAssignedTokens = fundraise.getInvestorDetailByIndex(0, "Assigned Tokens");
        //validate Assigned Tokens
        softAssertion.assertTrue(firstRowAssignedTokens.contains(investorAssignedTokens), "Assigned tokens not found on Fundraise screen");
        endTestLevel();

        startTestLevel("Verify Round name");
        String firstRowRound = fundraise.getInvestorDetailByIndex(0, "Round");
        //validate Round name
        // Should add this
        // softAssertion.assertTrue(firstRowRound.contains(InvestorsData.getUserDetails(InvestorsProperty.investorRound)), "Round name not found on Fundraise screen");
        endTestLevel();
    }

    public void cpVerifyHoldersOfRecordScreenData(String searchText) {

        startTestLevel("Navigate to Holders");
        navigateToPage(holders);
        CpHolders holders = new CpHolders(getBrowser());
        endTestLevel();

        startTestLevel("Filtering by investor's first name");
        holders.searchUniqueInvestorByTextBox(searchText);
        endTestLevel();

        startTestLevel("Verify Holders of Record names");
        SoftAssert softAssertion = new SoftAssert();

        String firstRowName = holders.getInvestorDetailByIndex(0, "Record Holder");
        //validate First Name
        softAssertion.assertTrue(firstRowName.contains(InvestorsData.getUserDetails(InvestorsProperty.investorFirstName)), "First Name not found on Holders of Record screen");
        //validate Middle Name
        softAssertion.assertTrue(firstRowName.contains(InvestorsData.getUserDetails(InvestorsProperty.investorMiddleName)), "Middle Name not found on Holders of Record screen");
        //validate Last Name
        softAssertion.assertTrue(firstRowName.contains(InvestorsData.getUserDetails(InvestorsProperty.investorLastName)), "Last Name not found on Holders of Record screen");
        endTestLevel();

        startTestLevel("Verify Holders of Record investor country");
        String firstRowInvestorCountry = holders.getInvestorDetailByIndex(0, "Country");
        //validate Investor Country
        softAssertion.assertTrue(firstRowInvestorCountry.contains(InvestorsData.getUserDetails(InvestorsProperty.investorCountry)), "Investor Country not found on Holders of Record screen");
        endTestLevel();

        startTestLevel("Verify Holders of Record investor type");
        String firstRowInvestorType = holders.getInvestorDetailByIndex(0, "Type")
                .toLowerCase();
        firstRowInvestorType = RegexWrapper.replaceWhiteSpaceForCharacter(firstRowInvestorType, "-");
        //validate Investor Type
        softAssertion.assertTrue(firstRowInvestorType.contains(InvestorsData.getUserDetails(InvestorsProperty.investorType)), "Investor Type not found on Holders of Record screen");
        endTestLevel();

        startTestLevel("Verify Tokens");
        String firstRowTokens = holders.getInvestorDetailByIndex(0, "Tokens");
        //validate Tokens
        softAssertion.assertTrue(firstRowTokens.contains(InvestorsData.getUserDetails(InvestorsProperty.investorTotalAmountOfTokens)), "Tokens amount not found on Holders of Record screen");
        endTestLevel();

        startTestLevel("Verify Wallet Address");
        String firstRowWalletAddress = holders.getInvestorDetailByIndex(0, "Wallet Address");
        //validate Wallet Address
        softAssertion.assertTrue(firstRowWalletAddress.contains(InvestorsData.getUserDetails(InvestorsProperty.investorWalletAddress)), "Wallet Address not found on Holders of Record screen");
        endTestLevel();

        startTestLevel("Verify Wallet Status");
        String firstRowWalletStatus = holders.getInvestorDetailByIndex(0, "Wallet Status");
        //validate Wallet Status
        softAssertion.assertTrue(firstRowWalletStatus.contains(InvestorsData.getUserDetails(InvestorsProperty.investorWalletStatus)), "Wallet Status not found on Holders of Record screen");
        endTestLevel();
    }

    public void cpInternalTBETransfer(String firstName, String issuerName, int transferredAmount, String procedureType) {

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel("Internal TBE transfer procedure");
        performInternalTBETransfer(transferredAmount);
        signSignatures(issuerName, firstName, 1);
        verifyInternalTBETransfer(transferredAmount);
        endTestLevel();
    }

    public void verifyInternalTBETransfer(int transferredAmount) {
        startTestLevel("Validate TBE tokens were transferred");
        navigateInvestorDirectUrl(investorDirectUrl);
        // force cron jobs to run now - to make transaction status to become 'success' faster
        if (!MainConfig.isProductionEnvironment()) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }
        // validate TBE amount updated
        investorDetailsPage.waitForTokensUpdate("TBE", tokensTBE + transferredAmount);
        endTestLevel();
    }

    public void performInternalTBETransfer(int transferredAmount) {
        startTestLevel("Perform internal TBE transfer");
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        String reason = "Transferring " + transferredAmount + " TBE tokens for Investor: " + investorExternalID;
        operationalProcedures.chooseInternalTransferProcedure();
        operationalProcedures.addSenderId(InvestorsData.getUserDetails(InvestorsProperty.investorSenderId));
        operationalProcedures.addReceiverId(investorExternalID);
        operationalProcedures.setTokenAmount(String.valueOf(transferredAmount));
        operationalProcedures.typeReason(reason);
        operationalProcedures.clickExecute(reason);
        operationalProcedures.verifySuccessMessage();
        endTestLevel();
    }

    public void cpExportList(String investorFilterName) {
        startTestLevel("Navigate to Onboarding screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Filtering by investor's middlename");
        // first let's filter on something that will empty the table
        onBoarding.searchInvestorByTextBox(NON_VALID_SEARCH);
        onBoarding.waitForTableToBeEmpty();
        onBoarding.searchInvestorByTextBox(investorFilterName);
        onBoarding.waitForTableToBeNotEmpty();
        endTestLevel();

        startTestLevel("Download Onboarding export list");
        getBrowser().refreshPage();
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        onBoarding.clickExportList();
        // give the download several seconds to start - we don't want to switch to the downloads tab too fast
        // but we don't have an event indicating a download has started...
        getBrowser().sleep(3000);
        endTestLevel();

        startTestLevel("Open Onboarding downloaded file");
        CSVUtils onboardingExportList = null;
        try {
            String downloadedCsvContent = getLastDownloadedInvestorFileContent();
            Files.write(Paths.get("tempFile.csv"), downloadedCsvContent.getBytes());
            onboardingExportList = new CSVUtils(new File("tempFile.csv"));
        } catch (IOException e) {
            errorMessage = "Error parsing data: " + e.getMessage();
            errorAndStop(errorMessage, true);
        } catch (NullPointerException e) {
            errorMessage = "Error creating file: " + e.getMessage();
            errorAndStop(errorMessage, true);
        }
        endTestLevel();

        startTestLevel("Filter by exported investor on CSV");
        onBoarding.waitForTableToBeNotEmpty();
        onBoarding.searchInvestorByTextBox(onboardingExportList.getCSVInvestorDetailByIndex(1, "First Name"));
        endTestLevel();

        startTestLevel("Verify Onboarding names");
        SoftAssertions softAssertions = new SoftAssertions();
        String firstRowName = onBoarding.getInvestorDetailByIndex(0, "Name / Entity");
        //validate First Name
        softAssertions.assertThat(firstRowName)
                .as("Validate First name value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "First Name"));
        //validate Middle Name
        softAssertions.assertThat(firstRowName)
                .as("Validate Middle name value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Middle Name"));
        //validate Last Name
        softAssertions.assertThat(firstRowName)
                .as("Validate Last name value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Last Name"));
        endTestLevel();

        startTestLevel("Verify Onboarding country codes");
        String firstRowCountry = onBoarding.getInvestorDetailByIndex(0, "Country");
        firstRowCountry = RegexWrapper.trimUSCountry(firstRowCountry);
        String firstRowCountryCode = CountryCode.findByName(firstRowCountry)
                .get(0)
                .name();
        //validate Country Code
        softAssertions.assertThat(firstRowCountryCode)
                .as("Validate Country code value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Tax Country Code"));
        endTestLevel();

        startTestLevel("Verify Onboarding investor type");
        String firstRowInvestorType = onBoarding.getInvestorDetailByIndex(0, "Type")
                .toLowerCase();
        firstRowInvestorType = RegexWrapper.replaceWhiteSpaceForCharacter(firstRowInvestorType, "-");
        //validate Investor Type
        softAssertions.assertThat(firstRowInvestorType)
                .as("Validate Investor type")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Investor Type"));
        endTestLevel();

        startTestLevel("Verify SecuritizeID exists");
        String firstRowSecuritizeID = onBoarding.getInvestorDetailByIndex(0, "SecuritizeID")
                .toLowerCase();
        //validate Securitize ID exists
        softAssertions.assertThat(firstRowSecuritizeID)
                .as("Validate SecuritizeID value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "SecuritizeID"));
        endTestLevel();

        startTestLevel("Verify KYC Status");
        String firstRowKYCStatus = onBoarding.getInvestorDetailByIndex(0, "KYC Status");
        firstRowKYCStatus = RegexWrapper.replaceWhiteSpaceForCharacter(firstRowKYCStatus, "-");
        //validate KYC Status
        softAssertions.assertThat(firstRowKYCStatus)
                .as("Validate KYC Status value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "KYC Status"));
        endTestLevel();

        startTestLevel("Verify Accreditation Status");
        String firstRowAccreditationStatus = onBoarding.getInvestorDetailByIndex(0, "Accredited Status");
        //validate Accredited Status - with tolerance to minor changes such as "no accepted" vs "no_accepted"
        String expectedValue = onboardingExportList.getCSVInvestorDetailByIndex(1, "Accreditation Status");
        String expectedValue2 = expectedValue.replace("_", " ");
        softAssertions.assertThat(firstRowAccreditationStatus)
                .as("Validate Accreditation Status value")
                .containsAnyOf(expectedValue, expectedValue2);
        endTestLevel();

        startTestLevel("Verify Labels");
        String firstRowLabels = onBoarding.getInvestorDetailByIndex(0, "Labels");
        firstRowLabels = RegexWrapper.blankIfNull(firstRowLabels);
        //validate Labels
        softAssertions.assertThat(firstRowLabels)
                .as("Validate labels value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Labels"));
        endTestLevel();

        startTestLevel("Verify Registration Date");
        String firstRowRegistrationDate = onBoarding.getInvestorDetailByIndex(0, "Created");
        String reformattedRegistrationDate = DateTimeUtils.convertDateFormat(firstRowRegistrationDate, "MMM d, yyyy", "MM/dd/yyyy") + " (UTC)";
        //validate Registration Date
        softAssertions.assertThat(reformattedRegistrationDate)
                .as("Registration date value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Registration Date"));
        softAssertions.assertAll();
        endTestLevel();
    }

    public CpInvestorDetailsPage findInvestorForLock(String searchText, String procedureType, String tokensHeldIn) {

        startTestLevel("Navigate to investor details page");
        navigateToPage(onboarding);
        CpOnBoarding cpOnBoarding = new CpOnBoarding(getBrowser());
        investorDetailsPage = cpOnBoarding.findSpecificInvestor(searchText, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Saving External ID and investor url for future use");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        investorExternalID = investorDetailsPage.getExternalInvestorId();
        if (tokensHeldIn.equals("wallet")) {
            getBrowser().waitForPageStable(Duration.ofSeconds(2));
            walletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        }
        endTestLevel();

        tokensCanBeProcedure = investorDetailsPage.getTokensToProcedure(tokensHeldIn, procedureType);
        info("Amount of " + tokensHeldIn + " tokens can be " + procedureType + " is: " + tokensCanBeProcedure);

        startTestLevel("Saving total locked tokens");
        setTotalLockedTokens(investorDetailsPage);
        endTestLevel();

        return investorDetailsPage;
    }

    public int setTotalLockedTokens(CpInvestorDetailsPage investorDetailsPage) {
        totalLockTokens = Integer.parseInt(investorDetailsPage.getTotalLockedTokens());
        return totalLockTokens;
    }

    public CpInvestorDetailsPage findInvestorForCB(String searchText, String autName, String lockType, String tokensHeldIn) {

        startTestLevel("Navigate to investor details page");
        navigateToPage(onboarding);
        CpOnBoarding cpOnBoarding = new CpOnBoarding(getBrowser());
        investorDetailsPage = cpOnBoarding.findSpecificInvestor(searchText, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Saving investor data for future use");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        tokensTBE = Integer.parseInt(investorDetailsPage.getTotalTBESecurities());
        tokensWallet = Integer.parseInt(investorDetailsPage.getTotalTokensHeld());
        endTestLevel();

        return investorDetailsPage;
    }

    public CpInvestorDetailsPage findInvestorForDestroy(String searchText, String procedureType, String tokensHeldIn) {

        startTestLevel("Navigate to investor details page");
        navigateToPage(onboarding);
        CpOnBoarding cpOnBoarding = new CpOnBoarding(getBrowser());
        investorDetailsPage = cpOnBoarding.findSpecificInvestor(searchText, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Saving investor date for future use");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        investorExternalID = investorDetailsPage.getExternalInvestorId();
        tokensWallet = Integer.parseInt(investorDetailsPage.getTotalTokensHeld());
        tokensTBE = Integer.parseInt(investorDetailsPage.getTotalTBESecurities());
        if (tokensHeldIn.equals("wallet")) {
            walletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        }

        endTestLevel();

        tokensCanBeProcedure = investorDetailsPage.getTokensToProcedure(tokensHeldIn, procedureType);
        info("Amount of " + tokensHeldIn + " tokens can be destroy is: " + tokensCanBeProcedure);

        return investorDetailsPage;
    }

    public CpInvestorDetailsPage findInvestorForTBEToWalletTransfer(String searchText, String tokensHeldIn, int tokenToProcedure) {

        startTestLevel("Navigate to investor");
        navigateToPage(onboarding);
        CpOnBoarding cpOnBoarding = new CpOnBoarding(getBrowser());
        investorDetailsPage = cpOnBoarding.findSpecificInvestor(searchText, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Saving External ID and investor url for future use");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        investorExternalID = investorDetailsPage.getExternalInvestorId();
        if (tokensHeldIn.equals("wallet")) {
            walletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        }
        info("Chosen investor external ID is: " + investorDetailsPage.getExternalInvestorId());
        endTestLevel();

        startTestLevel("Save amount of wallet and TBE");
        tokensWallet = Integer.parseInt(investorDetailsPage.getTotalTokensHeld());
        tokensTBE = Integer.parseInt(investorDetailsPage.getTotalTBESecurities());
        endTestLevel();

        startTestLevel("Define tokens to be transfer to wallet");
        tokensToBeProcedure = tokenToProcedure;
        info("Amount of TBE securities will be transfer to wallet is: " + tokenToProcedure);
        endTestLevel();
        return investorDetailsPage;
    }

    public CpInvestorDetailsPage findInvestorForInternalTBETTransfer(String searchText, String tokensHeldIn) {

        startTestLevel("Navigate to investor details page");
        navigateToPage(onboarding);
        CpOnBoarding cpOnBoarding = new CpOnBoarding(getBrowser());
        investorDetailsPage = cpOnBoarding.findSpecificInvestor(searchText, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Saving External ID and investor url for future use");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        investorExternalID = investorDetailsPage.getExternalInvestorId();
        if (tokensHeldIn.equals("wallet")) {
            walletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        }
        endTestLevel();

        startTestLevel("Save amount of TBE");
        tokensTBE = Integer.parseInt(investorDetailsPage.getTotalTBESecurities());
        endTestLevel();

        return investorDetailsPage;
    }

    public void cpVerifyExistingInvestor(String investorFirstName) {

        startTestLevel("Navigate to Onboarding");
        navigateToPage(onboarding);
        CpOnBoarding cpOnBoarding = new CpOnBoarding(getBrowser());
        endTestLevel();

        startTestLevel("Filtering by investor's first name");
        cpOnBoarding.searchUniqueInvestorByTextBox(investorFirstName, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Enter to investor details");
        CpInvestorDetailsPage investorDetailsPage = cpOnBoarding.clickShowInvestorDetailsByName(investorFirstName);
        endTestLevel();

        startTestLevel("Validate investor details");
        Assert.assertEquals(investorDetailsPage.getFirstName(), "Auto77", "incorrect first name, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getMiddleName(), "Dont", "incorrect middle name, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getLastName(), "Touch", "incorrect last name, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getEmail(), "Auto77@securitize.io", "incorrect email, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getPhoneNumber(), "12345678901", "incorrect phone number, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getBirthday(), "Jan 16, 1949", "incorrect birthday, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getGender(), "Male", "incorrect gender, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddress(), "Address_30gsWI", "incorrect address, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), "StreetInfo.Ep27E2", "incorrect address additional info, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getPostalCode(), "78146", "incorrect postal code, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCity(), "City.UNMQ2S", "incorrect city, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCountry(), "United States of America", "incorrect country, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getState(), "New Jersey", "incorrect state, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getTaxCountry(), "United States of America", "incorrect tax country, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getTaxId(), "332678945", "incorrect tax country, this is not the value we set!");
        // Assert.assertEquals(investorDetailsPage.getComment(), "comment_Kxa8Hj967C2qs9BWFGPB", "incorrect comment, this is not the value we set!");
        endTestLevel();

        startTestLevel("Validate KYC & Accreditation");
        Assert.assertEquals(investorDetailsPage.getKYCStatus(), "Verified", "incorrect kyc status, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getLastKYCChange(), "Feb 21, 2024", "incorrect last kyc change, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getKYCExpirationDate(), "Feb 21, 2025", "incorrect kyc expiration date, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getKYCProvider(), "Internal", "incorrect kyc provider, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCurrentAccreditationStatus(), "Confirmed", "incorrect accreditation status, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getLastAccreditationChange(), "Feb 21, 2024", "incorrect last accreditation change, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getQualificationStatus(), "Confirmed", "incorrect last accreditation change, this is not the value we set!");
        Assert.assertTrue(RegexWrapper.isStringMatch(investorDetailsPage.getCurrentPledgedAmount(), "10"), "incorrect total funded, this is not the value we set!");
        Assert.assertTrue(RegexWrapper.isStringMatch(investorDetailsPage.getCurrentTotalFunded(), "10"), "incorrect total funded, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getSubscriptionAgreement(), "Confirmed", "incorrect subscription agreement, this is not the value we set!");

        // There is difference when running local or remote - remote shows "Feb 20, 2024" instead "Feb 21, 2024"
        // Remove comment when defect ISR2-2436 is fixed - Direct link - https://securitize.atlassian.net/browse/ISR2-2436
        // Assert.assertEquals(investorDetailsPage.getSignatureDate(), "Feb 21, 2024", "incorrect signature date, this is not the value we set!");
        endTestLevel();

        startTestLevel("Validate Tokens & Wallets");
        Assert.assertEquals(investorDetailsPage.getTotalTokensHeld(), "5", "incorrect total tokens held, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getTotalTBESecurities(), "6", "incorrect total TBE securities, this is not the value we set!");
        endTestLevel();

        startTestLevel("Verify wallet status is still 'ready'");
        int waitTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeWalletToBecomeReadyQuorumSeconds);
        int intervalTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeWalletToBecomeReadyQuorumSeconds);
        WalletNotReadyTimeoutException walletNotReadyTimeoutException = new WalletNotReadyTimeoutException("Wallet was not marked as ready even after " + waitTimeSecondsWalletReady + " seconds.");
        investorDetailsPage.walletsCard.waitForEntityInTableStatusToBecome(1, "ready", waitTimeSecondsWalletReady, intervalTimeSecondsWalletReady, walletNotReadyTimeoutException);
        endTestLevel();

        startTestLevel("Verify issuances amount");
        investorDetailsPage.issuancesCard.scrollToTable();
        investorDetailsPage.issuancesCard.waitForTableToContainNumberOfRows(2);
        String regularIssuanceAmount = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Issue Amount");
        String tbeIssuanceAmount = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Issue Amount");
        Assert.assertEquals(regularIssuanceAmount, "5", "incorrect amount of regular issuance, this is not the value we set!");
        Assert.assertEquals(tbeIssuanceAmount, "6", "incorrect amount of TBE issuance, this is not the value we set!");
        endTestLevel();

        startTestLevel("Verify total amount of tokens");
        int totalSecuritizeTokensHeld = investorDetailsPage.getSecuritizeTotalTokenAmount();
        int investorTotalSecuritizeTokensHeld = Integer.parseInt(regularIssuanceAmount) + Integer.parseInt(tbeIssuanceAmount);
        Assert.assertEquals(totalSecuritizeTokensHeld, investorTotalSecuritizeTokensHeld, "incorrect amount of total securitize tokens, this is not the value we set!");
        endTestLevel();
    }

    public void cpSelectToken(String tokenName) {
        info("Selecting token: " + tokenName);
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.openTokenDropdown();
        sideMenu.clickTokenName(tokenName);
    }

    public void cpSetKycAndAccreditaionToVerified() {

        startTestLevel("Set KYC & Accreditation");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        String kycComment = "comment_" + RandomGenerator.randomString(20);
        investorDetailsPage.clickEditKYC()
                .setCurrentKycStatus("Verified")
                .setCurrentAccreditationStatus("Confirmed")
                .setQualificationStatus("Confirmed")
                .typeKycComment(kycComment)
                .clickSaveChanges();
        endTestLevel();
    }

    public void cpAddSingleTbeIssuanceWithNoSignature(InvestorDetails investorDetails, String issuanceTBEReason) {

        startTestLevel("Add single TBE issuance without signing it");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        //getBrowser().refreshPage();
        startTestLevel("Add TBE issuance");
        investorDetailsPage.clickAddIssuance()
                .typeReason(issuanceTBEReason)
                .typeIssuanceAmount(1)
                .clickOk(investorDetails.getFirstName(), investorDetails.getMiddleName(), investorDetails.getLastName(), 1);
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

    public void cpVerifyExistingInvestorPrivacyControl(boolean privacyControl) {
        String investorFullName = "Investor PC Test";
        startTestLevel("Navigate to Onboarding");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Filtering by investor's first name");
        onBoarding.searchUniqueInvestorByTextBox(investorFullName, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Enter to investor details");
        CpInvestorDetailsPage investorDetailsPage = onBoarding.clickShowInvestorDetailsByName(investorFullName);
        endTestLevel();

        if (privacyControl) {
            startTestLevel("Validate investor details");
            SoftAssert softAssertion = new SoftAssert();
            softAssertion.assertEquals(investorDetailsPage.getFirstName(), "Investor", "incorrect first name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getMiddleName(), "PC", "incorrect middle name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getLastName(), "Test", "incorrect last name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getEmail(), "investorpctest+1@securitize.io", "incorrect email, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getPhoneNumber(), "********", "incorrect phone number, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getBirthday(), "********", "incorrect birthday, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getGender(), "Male", "incorrect gender, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getAddress(), "********", "incorrect address, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), "********", "incorrect address additional info, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getPostalCode(), "********", "incorrect postal code, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getCity(), "********", "incorrect city, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getCountry(), "United States of America", "incorrect country, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getState(), "Alabama", "incorrect state, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getTaxCountry(), "********", "incorrect tax country, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getComment(), "Comment me", "incorrect comment, this is not the value we set!");
            softAssertion.assertAll();
            endTestLevel();
        }

        if (!privacyControl) {
            startTestLevel("Validate investor details");
            SoftAssert softAssertion = new SoftAssert();
            softAssertion.assertEquals(investorDetailsPage.getFirstName(), "Investor", "incorrect first name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getMiddleName(), "PC", "incorrect middle name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getLastName(), "Test", "incorrect last name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getEmail(), "investorpctest+1@securitize.io", "incorrect email, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getPhoneNumber(), "0546765698", "incorrect phone number, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getBirthday(), "May 6, 1970", "incorrect birthday, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getGender(), "Male", "incorrect gender, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getAddress(), "Some Street", "incorrect address, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), "Some Info", "incorrect address additional info, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getPostalCode(), "6574654", "incorrect postal code, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getCity(), "Tel Aviv", "incorrect city, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getCountry(), "United States of America", "incorrect country, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getState(), "Alabama", "incorrect state, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getTaxCountry(), "Azerbaijan", "incorrect tax country, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getComment(), "Comment me", "incorrect comment, this is not the value we set!");
            softAssertion.assertAll();
            endTestLevel();
        }

    }

    public void privacyControlCheck(boolean privacyControl) {
        startTestLevel("Navigate to Issuer Configuration Variables");
        navigateToPage(configurationVariables);
        endTestLevel();

        startTestLevel("Search privacy control variable");
        CpIssuerConfigurationVariables issuerConfigurationVariables = new CpIssuerConfigurationVariables(getBrowser());
        issuerConfigurationVariables.searchPrivacyControlVariable();
        endTestLevel();

        startTestLevel("Set privacy control");
        issuerConfigurationVariables.setPrivacyControl(privacyControl);
        cpVerifyExistingInvestorPrivacyControl(privacyControl);
        endTestLevel();
    }

    public void cpNavigateToMarketsOverview() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickMarketsOverview();
    }

    public void cpAssertOpportunity(String opportunityName, int numberOfCards) {
        CpMarketsOverview marketsOverview = new CpMarketsOverview(getBrowser());
        marketsOverview.searchOpportunityByTextBox(opportunityName);
        marketsOverview.assertNumberOfCards(numberOfCards);
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

    public void cpValidateInvestmentData(String currencyName, InvestorDetails investorDetails) {

        startTestLevel("Setting up Date and Transaction Id");
        String todaysDate = DateTimeUtils.currentDate("MMM dd, yyyy");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        endTestLevel();

        startTestLevel("Validate data");
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

        // validate currency
        String actualCurrency = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Currency");
        Assert.assertEquals(actualCurrency, currencyName, "actual currency doesn't match entered value");

        // validate source
        String actualSource = investorDetailsPage.transactionsCard.getDetailAtIndex(1, "Source");
        Assert.assertEquals(actualSource, "manual", "source should be manual");

        startTestLevel("Change 'Subscription Agreement' Status to 'Confirmed'");
        investorDetailsPage.clickEditInvestment()
                .typePledgedAmount(investorDetails.getPledgedAmount())
                .setInvestmentSubscriptionAgreement("Confirmed")
                .setSignatureDate(todaysDate)
                .clickSaveChanges();
        endTestLevel();

    }

    public void cpSwitchToMarketsOverviewInvestorsTab() {
        CpMarketsOverview marketsOverview = new CpMarketsOverview(getBrowser());
        marketsOverview.clickInvestorsTab();
    }

    public void cpSelectOpportunity(String opportunityName) {
        CpMarketsOverview marketsOverview = new CpMarketsOverview(getBrowser());
        marketsOverview.selectOpportunity(opportunityName);
    }

    public void cpAssertInvestorData(InvestorDetails investorDetails, String opportunityName, String pledgedAmount, String fundedAmount) {

        startTestLevel("Filtering by investor's first name");
        CpMarketsOverviewInvestorsTab cpMarketsOverviewInvestorsTab = new CpMarketsOverviewInvestorsTab(getBrowser());
        cpMarketsOverviewInvestorsTab.searchUniqueInvestorByTextBox(investorDetails.getFirstName());
        endTestLevel();

        startTestLevel("Validate investor name");
        SoftAssert softAssertion = new SoftAssert();
        String investorName = cpMarketsOverviewInvestorsTab.getInvestorDetailByUniqueRow("Name/Entity");
        softAssertion.assertTrue(investorName.contains(investorDetails.getFirstName()), "Investor name not found on Markets Overview table");
        endTestLevel();

        startTestLevel("Validate investor opportunity name");
        String investorOpportunityName = cpMarketsOverviewInvestorsTab.getInvestorDetailByUniqueRow("Opportunity");
        softAssertion.assertTrue(investorOpportunityName.contains(opportunityName), "Opportunity name not found on Markets Overview table");
        endTestLevel();

        startTestLevel("Validate investor status");
        String investorStatus = cpMarketsOverviewInvestorsTab.getInvestorDetailByUniqueRow("Investor Status");
        softAssertion.assertTrue(investorStatus.contains("Ready"), "Investor Status not found on Markets Overview table");
        endTestLevel();

        startTestLevel("Validate investor type");
        String investorType = cpMarketsOverviewInvestorsTab.getInvestorDetailByUniqueRow("Type");
        softAssertion.assertTrue(investorType.contains("Individual"), "Investor Type not found on Markets Overview table");
        endTestLevel();

        startTestLevel("Validate investor pledged amount");
        String investorPledgedAmount = cpMarketsOverviewInvestorsTab.getInvestorDetailByUniqueRow("Pledged");
        softAssertion.assertTrue(investorPledgedAmount.contains(pledgedAmount), "Investor Pledged Amount not found on Markets Overview table");
        endTestLevel();

        startTestLevel("Validate investor funded amount");
        String investorFundedAmount = cpMarketsOverviewInvestorsTab.getInvestorDetailByUniqueRow("Funded");
        softAssertion.assertTrue(investorFundedAmount.contains(fundedAmount), "Investor Funded Amount not found on Markets Overview table");
        endTestLevel();

        startTestLevel("Validate investor Subscription Agreement status");
        String investorSaStatus = cpMarketsOverviewInvestorsTab.getInvestorDetailByUniqueRow("SA Status");
        softAssertion.assertTrue(investorSaStatus.contains("Co-signed"), "Investor Subscription Agreement Status not found on Markets Overview table");
        softAssertion.assertAll();
        endTestLevel();
    }

    public void updateControlBook() {
        startTestLevel("Navigate to Control book");
        navigateToPage(controlBook);
        CpControlBook cpControlBook = new CpControlBook(getBrowser());
        endTestLevel();

        startTestLevel("Update Control Book");
        cpControlBook.insertNewAuthorizedSecurities(ISSUED_TOKENS_CB);
        endTestLevel();
    }

    public void verifyControlBookUpdated() {
        startTestLevel("Navigate to Control book");
        navigateToPage(controlBook);
        CpControlBook cpControlBook = new CpControlBook(getBrowser());
        endTestLevel();

        startTestLevel("Verify if Control Book data updated after issuance");
        cpControlBook.isCBtoUpdateAfterIssuance(ISSUED_TOKENS_CB);
        endTestLevel();
    }


    public void addIssuance(String firstName, String issuerName, boolean isSecuritiesExceeded) {

        startTestLevel("Create TBE issuance");
        addTBEIssuanceCB(isSecuritiesExceeded);
        endTestLevel();

        startTestLevel("Create wallet issuance");
        addWalletIssuanceCB(isSecuritiesExceeded);
        endTestLevel();

        startTestLevel("Sign issuance if created");
        // if 'isSecuritiesExceeded = true', issuance does not suppose to be created
        if (!isSecuritiesExceeded) {
            startTestLevel("Sign both issuance");
            signSignatures(issuerName, firstName, 2);
            endTestLevel();

            startTestLevel("Validate tokens arrive to investor");
            navigateInvestorDirectUrl(investorDirectUrl);
            investorDetailsPage.waitForTokensUpdate("TBE", tokensTBE + ISSUED_TOKENS_CB);
            investorDetailsPage.waitForTokensUpdate("wallet", tokensWallet + ISSUED_TOKENS_CB);
            endTestLevel();
        }
    }

    public void addTBEIssuanceCB(boolean isSecuritiesExceeded) {
        navigateInvestorDirectUrl(investorDirectUrl);
        getBrowser().refreshPage();
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        if (isSecuritiesExceeded) {
            startTestLevel("Add TBE issuance when Authorized Securities exceeded");
            investorDetailsPage.clickAddIssuance()
                    .typeReason("CB TBE issuance")
                    .typeIssuanceAmount(ISSUED_TOKENS_CB)
                    .clickOk(investorDetailsPage.getFirstName(), investorDetailsPage.getMiddleName(), investorDetailsPage.getLastName(), ISSUED_TOKENS_CB);
            investorDetailsPage.verifyIssuanceErrorMessage();
            endTestLevel();
        } else {
            startTestLevel("Add TBE issuance");
            investorDetailsPage.clickAddIssuance()
                    .typeReason("CB TBE issuance")
                    .typeIssuanceAmount(ISSUED_TOKENS_CB)
                    .clickOk(investorDetailsPage.getFirstName(), investorDetailsPage.getMiddleName(), investorDetailsPage.getLastName(), ISSUED_TOKENS_CB);
            investorDetailsPage.verifyIssuanceSuccessMessage();
        }
    }

    public void addWalletIssuanceCB(boolean isSecuritiesExceeded) {
        if (!getBrowser().getPageTitle()
                .equals("Details")) {
            CpSideMenu sideMenu = new CpSideMenu(getBrowser());
            CpOnBoarding cpOnBoarding = sideMenu.clickOnBoarding();
            String name = investorDetailsPage.getFirstName();
            cpOnBoarding.clickShowInvestorDetailsByName(name);
        }
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        getBrowser().refreshPage();
        if (isSecuritiesExceeded) {
            investorDetailsPage.clickAddIssuance()
                    .typeReason("CB wallet issuance")
                    .typeIssuanceAmount(ISSUED_TOKENS_CB)
                    .clickOk(investorDetailsPage.getFirstName(), investorDetailsPage.getMiddleName(), investorDetailsPage.getLastName(), ISSUED_TOKENS_CB);
            investorDetailsPage.verifyIssuanceErrorMessage();
        } else {
            String walletName = "CP test wallet";
            getBrowser().waitForPageStable(Duration.ofSeconds(10));
            String walletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
            investorDetailsPage.clickAddIssuance()
                    .selectTokenWalletId(walletName, walletAddress)
                    .typeReason("CB wallet issuance")
                    .typeIssuanceAmount(ISSUED_TOKENS_CB)
                    .clickOk(investorDetailsPage.getFirstName(), investorDetailsPage.getMiddleName(), investorDetailsPage.getLastName(), ISSUED_TOKENS_CB);
            investorDetailsPage.verifyIssuanceSuccessMessage();
        }
    }

    public void cpNavigateToConfigurationFundraise() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickConfigurationFundraise();
    }

    public void cpAddRound(String roundName, String startDate, String closeDate, String issuanceDate, String minInvestmentCrypto, String minInvestmentFiat, String terms, String tokenValue) {

        startTestLevel("Open Add Round screen");
        CpConfigurationFundraise configurationFundraise = new CpConfigurationFundraise(getBrowser());
        configurationFundraise.clickAddRound();
        endTestLevel();

        startTestLevel("Fill round details");
        CpAddRound addRound = new CpAddRound(getBrowser());
        addRound.setRoundName(roundName);
        addRound.switchRoundToActive();
        addRound.setMinimumInvestmentFiat(minInvestmentFiat);
        addRound.setMinimumInvestmentCrypto(minInvestmentCrypto);
        addRound.setStartDate(startDate);
        addRound.setEndDate(closeDate);
        addRound.setIssuanceDate(issuanceDate);
        addRound.setTokenValue(tokenValue);
        addRound.setText(terms);
        addRound.clickAddRoundButton();
        endTestLevel();
    }

    public void cpValidateRound(String roundName, String statusName, String startDate, String closeDate, String issuanceDate, String minInvestmentCrypto, String minInvestmentFiat, String terms) {

        startTestLevel("Validate Round name");
        CpConfigurationFundraise configurationFundraise = new CpConfigurationFundraise(getBrowser());
        SoftAssert softAssertion = new SoftAssert();
        configurationFundraise.waitForRoundTableToBeStable();
        String actualRoundName = configurationFundraise.getRoundDetailByIndex(1, "Name");
        softAssertion.assertTrue(actualRoundName.contains(roundName), "Round name not found on Rounds table");
        endTestLevel();

        startTestLevel("Validate Status");
        String actualStatusName = configurationFundraise.getRoundDetailByIndex(1, "Status");
        softAssertion.assertTrue(actualStatusName.contains(statusName), "Status name not found on Rounds table");
        endTestLevel();

        startTestLevel("Validate Start Date");
        String actualStartDate = configurationFundraise.getRoundDetailByIndex(1, "Start date");
        softAssertion.assertTrue(actualStartDate.contains(startDate), "Start date not found on Rounds table");
        endTestLevel();

        startTestLevel("Validate Close Date");
        String actualCloseDate = configurationFundraise.getRoundDetailByIndex(1, "Close date");
        softAssertion.assertTrue(actualCloseDate.contains(closeDate), "Close date not found on Rounds table");
        endTestLevel();

        startTestLevel("Validate Issuance Date");
        String actualIssuanceDate = configurationFundraise.getRoundDetailByIndex(1, "Issuance date");
        softAssertion.assertTrue(actualIssuanceDate.contains(issuanceDate), "Issuance date not found on Rounds table");
        endTestLevel();

        startTestLevel("Validate Min. Investment for Crypto");
        String actualMinInvestmentCrypto = configurationFundraise.getRoundDetailByIndex(1, "Min. investment (USD for Crypto)");
        softAssertion.assertTrue(actualMinInvestmentCrypto.contains(minInvestmentCrypto), "Min. investment (USD for Crypto) not found on Rounds table");
        endTestLevel();

        startTestLevel("Validate Min. Investment for FIAT");
        String actualMinInvestmentFiat = configurationFundraise.getRoundDetailByIndex(1, "Min. investment (USD for FIAT)");
        softAssertion.assertTrue(actualMinInvestmentFiat.contains(minInvestmentFiat), "Min. investment (USD for FIAT) not found on Rounds table");
        endTestLevel();

        startTestLevel("Validate Investment Terms and Conditions");
        String actualTerms = configurationFundraise.getRoundDetailByIndex(1, "Investment Terms and Conditions");
        softAssertion.assertTrue(actualTerms.contains(terms), "Investment Terms and Conditions not found on Rounds table");
        endTestLevel();

        startTestLevel("Soft Assert all");
        softAssertion.assertAll();
        endTestLevel();
    }

    public void cpEditRound(String roundName, String startDate, String closeDate, String issuanceDate, String minInvestmentCrypto, String minInvestmentFiat, String tokenValue) {

        startTestLevel("Edit Round");
        CpConfigurationFundraise configurationFundraise = new CpConfigurationFundraise(getBrowser());
        configurationFundraise.clickEditRound();
        endTestLevel();

        startTestLevel("Update round details");
        CpAddRound addRound = new CpAddRound(getBrowser());
        addRound.setRoundName(roundName);
        addRound.switchRoundToActive();
        addRound.setMinimumInvestmentFiat(minInvestmentFiat);
        addRound.setMinimumInvestmentCrypto(minInvestmentCrypto);
        addRound.setStartDate(startDate);
        addRound.setEndDate(closeDate);
        addRound.setIssuanceDate(issuanceDate);
        addRound.setTokenValue(tokenValue);
        addRound.clickEditRoundButton();
        endTestLevel();
    }

    public void cpDeleteRound() {

        startTestLevel("Delete Round");
        CpConfigurationFundraise configurationFundraise = new CpConfigurationFundraise(getBrowser());
        configurationFundraise.clickDeleteRound();
        endTestLevel();

        startTestLevel("Confirm Delete Round");
        configurationFundraise.clickOkButton();
        endTestLevel();

        startTestLevel("Assert Round was deleted");
        SoftAssert softAssertion = new SoftAssert();
        configurationFundraise.waitForRoundTableToBeStable();
        int actualRoundRows = configurationFundraise.getRowCount();
        softAssertion.assertEquals(actualRoundRows, 1, "Round name was found on Rounds table");
        softAssertion.assertAll();
        endTestLevel();
    }

    public void cpRemoveAllUnwantedRounds() {

        startTestLevel("Clean up unwanted Rounds");
        CpConfigurationFundraise configurationFundraise = new CpConfigurationFundraise(getBrowser());
        configurationFundraise.removeAllNonDefaultRounds();
        endTestLevel();
    }

    public void cpActivateAffiliate() {

        startTestLevel("Activating affiliate");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.clickSetAsAffiliate()
                .addComment("comment")
                .clickOk();
        endTestLevel();
    }

    public CpSignatures cpNavigateToSignaturesScreen() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        return sideMenu.clickSignatures();
    }

    public void cpSignAffiliateTransaction(String issuerName, InvestorDetails investorDetails) {

        startTestLevel("Sign affiliate transaction");
        CpSignatures signatures = new CpSignatures(getBrowser());
        String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerAddress);
        String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPK);

        //signing affiliate transaction
        signatures.waitForSignaturePageToStabilize();
        signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();
    }

    public void cpNavigateToOnboarding() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickOnBoarding();
    }

    public void cpValidateAffiliateStatus(InvestorDetails investorDetails, String affiliateStatus) {

        startTestLevel("Search for investor and go into investor details");
        CpOnBoarding onBoarding = new CpOnBoarding(getBrowser());
        onBoarding.searchUniqueInvestorByTextBox(investorDetails.getFirstName(), NON_VALID_SEARCH);
        onBoarding.waitForTableToCountRowsByCellDetail(1, 120, "AUT563");
        onBoarding.clickOnFirstInvestor();
        endTestLevel();

        startTestLevel("Validate Affiliate status");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        SoftAssert softAssertion = new SoftAssert();
        int waitTimeSecondsAffiliateStatusReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsAffiliateStatusReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);
        investorDetailsPage.waitForInvestorDetailsPageToStabilize();
        investorDetailsPage.waitForEditButtonToBeVisible();
        investorDetailsPage.affiliateCard.waitForTableToContainAffiliateStatus(1, affiliateStatus, waitTimeSecondsAffiliateStatusReady, intervalTimeSecondsAffiliateStatusReady);
        String finalAffiliateStatus = investorDetailsPage.affiliateCard.getDetailAtIndex(1, "Status");
        String finalComment = investorDetailsPage.affiliateCard.getDetailAtIndex(1, "Comment");
        softAssertion.assertTrue(finalAffiliateStatus.contains(affiliateStatus), "Affiliate Status is incorrect");
        softAssertion.assertTrue(finalComment.contains("comment"), "Affiliate Comment is incorrect");
        softAssertion.assertAll();
        getBrowser().refreshPage();
        endTestLevel();
    }

    public void setLockProcedureInput(String lockType, String tokenHeldIn, int tokenToProcedure, boolean isPartialLock) {

        if (lockType.equals("lock")) {
            if (isPartialLock) {
                tokensToBeProcedure = tokenToProcedure;
                tokensAfterProcedure = tokensCanBeProcedure - tokensToBeProcedure;
                fullOrPartially = "partially";
            } else {
                tokensCanBeProcedure = tokensAfterProcedure;
                tokensToBeProcedure = tokensAfterProcedure;
                fullOrPartially = "full";
            }
            expectedLockTokensAfterProcedure = totalLockTokens + tokensToBeProcedure;
            info(lockType + " input are set: " + tokenHeldIn + " " + fullOrPartially + " " + lockType + ". " + lockType + " " + tokensToBeProcedure + " tokens out of " + tokensCanBeProcedure + " unlocked tokens of investor");
        } else if (lockType.equals("unlock")) {
            if (isPartialLock) {
                tokensCanBeProcedure = totalLockTokens;
                tokensToBeProcedure = tokenToProcedure;
                fullOrPartially = "partially";
            } else {
                tokensCanBeProcedure = totalLockTokens;
                tokensToBeProcedure = tokensAfterProcedure;
                fullOrPartially = "full";
            }
            expectedLockTokensAfterProcedure = totalLockTokens - tokensToBeProcedure;
            info(lockType + " input are set: " + tokenHeldIn + " " + fullOrPartially + " " + lockType + ". " + lockType + " " + tokensToBeProcedure + " tokens from " + tokensCanBeProcedure + " locked tokens of investor");
        }
        info("After procedure will be " + expectedLockTokensAfterProcedure + " locked tokens");
    }

    public void performLockTokens(String lockType, String tokenHeldIn, String issuerName, String firstName, int tokenToProcedure, boolean isPartialLock) {

        startTestLevel("Navigate to Operational Procedure");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel("Insert " + lockType + " " + tokenHeldIn + " Tokens data " + fullOrPartially + " " + lockType);
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        operationalProcedures.chooseLockTokensProcedure(lockType);
        operationalProcedures.addExternalInvestorId(investorExternalID);
        operationalProcedures.chooseTokenHeldIn(tokenHeldIn);
        // Add this validation after Story ISR2 - 1792 is Done - it shows total tokens of investor instead of available tokens for lock
        // actualTokensCanBeInProcedure = operationalProcedures.getTotalAmountOfSecuritiesCanBeProcedure();
        // Assert.assertEquals(actualTokensCanBeInProcedure, tokensCanBeProcedure - totalLockTokens, "Tokens available to lock should be " + (tokensCanBeProcedure - totalLockTokens) + " but they are " + actualTokensCanBeInProcedure);
        operationalProcedures.typeNumberOfTokenToLock(tokenToProcedure);
        if (isPartialLock && lockType.equals("lock")) {
            String reason = lockType + " " + tokenHeldIn + " tokens, " + fullOrPartially + " " + lockType;
            operationalProcedures.typeReasonLockToken(reason);
            operationalProcedures.clickExecute(reason);
            operationalProcedures.verifySuccessMessage();
            if (tokenHeldIn.equals("wallet")) {
                startTestLevel("Sign " + lockType + " " + tokenHeldIn + "tokens Procedure");
                numOfSignatures++;
                signSignatures(issuerName, firstName, numOfSignatures);
            }
            startTestLevel("Verify " + lockType + " Procedure, " + lockType + "ed tokens should be " + expectedLockTokensAfterProcedure);
            verifyLockProcedure(lockType, tokenHeldIn);
            endTestLevel();

        }
    }

    public void verifyLockProcedure(String lockType, String tokenHeldIn) {

        if (lockType.equals("lock")) {
            startTestLevel("Verify " + expectedLockTokensAfterProcedure + " tokens shown as " + lockType + " in investor details page");
            navigateInvestorDirectUrl(investorDirectUrl);
            investorDetailsPage.waitForTokensUpdate(lockType, expectedLockTokensAfterProcedure);
            setTotalLockedTokens(investorDetailsPage);
            info("Total locked tokens now is: " + expectedLockTokensAfterProcedure);
            endTestLevel();
        }
        if (lockType.equals("unlock")) {
            navigateToPage(signatures);

        }
    }

    public void navigateInvestorDirectUrl(String url) {
        if (!getBrowser().getPageTitle()
                .contains("Details".toLowerCase())) {
            if (url != null) {
                getBrowser().navigateTo(url);
                getBrowser().waitForPageStable();
                investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
            }
        }
    }

    public void navigateNewInvestorDirectUrl(String url) {
        if (!getBrowser().getPageTitle()
                .equals("Investor Details".toLowerCase())) {
            getBrowser().navigateTo(url);
            getBrowser().waitForPageStable();
            investorDetailsPageNewUI = new CpInvestorDetailsPageNewUI(getBrowser());
        }
        if (investorDetailsPageNewUI == null) {
            investorDetailsPageNewUI = new CpInvestorDetailsPageNewUI(getBrowser());
        }
    }

    public void cpLockToken(String lockType, String tokenHeldIn, String issuerName, String firstName, int tokenToProcedure, boolean isPartialLock) {
        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel(lockType + " Tokens procedure");
        setLockProcedureInput(lockType, tokenHeldIn, tokenToProcedure, isPartialLock);
        performLockTokens(lockType, tokenHeldIn, issuerName, firstName, tokenToProcedure, isPartialLock);
        endTestLevel();
    }

    public void signSignatures(String issuerName, String firstName, int numOfSignatures) {
        navigateToPage(signatures);
        CpSignatures signatures = new CpSignatures(getBrowser());
        signatures.signSignatures(issuerName, firstName, numOfSignatures);
        accelerateTransaction();
    }

    public void signIssuanceNewUI(String issuerName, String firstName) {
        startTestLevel("Sign issuance");
        signSignatures(issuerName, firstName, issuanceCounter);
        endTestLevel();
    }

    public void verifyIssuanceAndTokensAfterIssuance() {
        startTestLevel("Navigate to investor details page and token & wallets tab");
        navigateInvestorDirectUrl(investorDirectUrlNewUI);
        investorDetailsPageNewUI.clickTokensWalletTab();
        endTestLevel();

        startTestLevel("Verify issuance status is 'success' - all issuance");
        for (int i = 1; i <= issuanceCounter; i++) {
            investorDetailsPageNewUI.waitForIssuanceToBeSuccess("success", i);
        }
        endTestLevel();

        startTestLevel("Verify tokens arrive to investor");
        investorDetailsPageNewUI.waitForTokensUpdate("TBE", TBE_ISSUED_TOKENS);
        investorDetailsPageNewUI.waitForTokensUpdate("wallet", ISSUED_TOKENS);
        investorDetailsPageNewUI.waitForTokensUpdate("Securitize Logo", TBE_ISSUED_TOKENS + ISSUED_TOKENS);
        endTestLevel();
    }

    public void signIssuanceLC(String issueTo, InvestorDetails investorDetails, String issuerName, int TOKENS) {
        startTestLevel("Sign the transactions");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        String investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpSignatures signatures = sideMenu.clickSignatures();
        String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerWalletAddress);
        String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPrivateKey);

        signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();

        startTestLevel("validate tokens arrived to investor");
        navigateInvestorDirectUrl(investorDirectUrl);
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);

        // force cron jobs to run now - to make transaction status to become 'success' faster
        if (!MainConfig.isProductionEnvironment()) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }

        // validate TBE token status
        IssuanceNotSuccessTimeoutException issuanceNotSuccessTimeoutException = new IssuanceNotSuccessTimeoutException("Issuance was not marked as success even after " + waitTimeSecondsIssuanceReady + " seconds.");
        investorDetailsPage.issuancesCard.waitForEntityInTableStatusToBecome(1, "success", waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady, issuanceNotSuccessTimeoutException);
        String finalIssuanceStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(finalIssuanceStatus, "success", "actual status should have been 'success'");
        getBrowser().refreshPage();

        investorDetailsPage.waitForTokensUpdate(issueTo, TOKENS);
        endTestLevel();
    }

    public void cpNavigateToAffiliateManagement() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickAffiliateManagement();
    }

    public void cpValidateAffiliateManagement(InvestorDetails investorDetails, String affiliateStatus) {

        startTestLevel("Assert values for investor on Affiliate Management table");
        CpAffiliateManagement cpAffiliateManagement = new CpAffiliateManagement(getBrowser());
        SoftAssert softAssertion = new SoftAssert();

        cpAffiliateManagement.waitForTableToBeNotEmpty();
        cpAffiliateManagement.sortByLastChange();
        String finalInvestorName = cpAffiliateManagement.getAffiliateDetailByIndex(1, "Name");
        String finalAffiliateStatus = cpAffiliateManagement.getAffiliateDetailByIndex(1, "Affiliate");
        String finalComment = cpAffiliateManagement.getAffiliateDetailByIndex(1, "Comments");

        softAssertion.assertTrue(finalInvestorName.contains(investorDetails.getFirstName()));
        softAssertion.assertTrue(finalAffiliateStatus.contains(affiliateStatus), "Affiliate Status is incorrect");
        softAssertion.assertTrue(finalComment.contains("comment"), "Affiliate Comment is incorrect");
        softAssertion.assertAll();
        endTestLevel();
    }

    public void cpDeactivateAffiliate() {

        startTestLevel("Deactivate Affiliate status");
        CpAffiliateManagement cpAffiliateManagement = new CpAffiliateManagement(getBrowser());
        cpAffiliateManagement.clickDeactivateAffiliate();
        cpAffiliateManagement.setDeactivateComment("comment");
        cpAffiliateManagement.clickSubmitButton();
        endTestLevel();
    }

    public void cpVerifyLostSharesInvestor(InvestorDetails investorDetails) {
        startTestLevel("Search for investor and go into investor details");
        cpNavigateToOnboarding();
        CpOnBoarding onBoarding = new CpOnBoarding(getBrowser());
        onBoarding.searchUniqueInvestorByTextBox(investorDetails.getFirstName(), NON_VALID_SEARCH);
        onBoarding.waitForTableToCountRowsByCellDetail(1, 120, investorDetails.getFirstName());
        onBoarding.clickOnFirstInvestor();
        endTestLevel();

        startTestLevel("Verify the investor has wallets and tokens");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.waitForInvestorDetailsPageToStabilize();
        investorDetailsPage.waitForWalletTableToLoad();
        int numOfTokens = Integer.parseInt(investorDetailsPage.getTotalTokensHeld());
        int numOfWallets = investorDetailsPage.walletsCard.getEntityCount();
        Assert.assertEquals(numOfTokens, ISSUED_TOKENS, "The investor does not have " + ISSUED_TOKENS + " tokens");
        Assert.assertEquals(numOfWallets, NUMBER_OF_WALLETS_LOST_SHARES, "The investor does not have " + NUMBER_OF_WALLETS_LOST_SHARES + " wallets");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        endTestLevel();
    }

    public void cpExecuteLostShares(String sourceWallet, String destinationWallet, InvestorDetails investorDetails, String issuerName) {
        startTestLevel("Navigate to Operational Procedures");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickOperationalProcedures();
        endTestLevel();

        startTestLevel("Lost Shares procedure");
        cpPerformLostShares(sourceWallet, destinationWallet, investorDetails, issuerName);
        cpVerifyLostSharesProcedure(sourceWallet, destinationWallet);
        endTestLevel();
    }

    public void cpPerformLostShares(String sourceWallet, String destinationWallet, InvestorDetails investorDetails, String issuerName) {
        startTestLevel("Perform lost shares procedure");
        String reason = "Automation - Perform lost shares procedure";
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        operationalProcedures.chooseLostSharesProcedure();
        operationalProcedures.typeSourceWallet(sourceWallet);
        int securitiesInSourceWallet = operationalProcedures.getNumberOfSecuritiesInSourceWallet();
        assertThat(securitiesInSourceWallet).as("Number of securities in the source wallet should be " + ISSUED_TOKENS + " but it's " + securitiesInSourceWallet).isEqualTo(ISSUED_TOKENS);
        operationalProcedures.typeDestinationWallet(destinationWallet);
        operationalProcedures.selectDateOfRedemption();
        operationalProcedures.typeReasonLostShares(reason);
        operationalProcedures.clickExecute(reason);
        operationalProcedures.verifySuccessMessage();
        cpSignLostSharesTransactions(investorDetails, issuerName);
        endTestLevel();
    }

    public void cpSignLostSharesTransactions(InvestorDetails investorDetails, String issuerName) {
        startTestLevel("Sign the lock wallet tokens transaction");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpSignatures signatures = sideMenu.clickSignatures();
        String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerWalletAddress);
        String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPrivateKey);
        signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();
        signatures.waitForSignaturePageToStabilize();
        signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();
        endTestLevel();
    }

    public void cpVerifyLostSharesProcedure(String sourceWallet, String destinationWallet) {
        startTestLevel("Verify tokens shown at the destination wallet");
        navigateInvestorDirectUrl(investorDirectUrl);
        int waitTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.waitTimeTokensToArriveInWalletEthereumSeconds);
        int intervalTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTokensToArriveInWalletEthereumSeconds);
        int sourceWalletIndex = investorDetailsPage.getWalletRowIndex(sourceWallet);
        int destinationWalletIndex = investorDetailsPage.getWalletRowIndex(destinationWallet);
        investorDetailsPage.walletsCard.waitForWalletToHoldNumberOfTokens(sourceWalletIndex, 0, waitTimeSecondsTokensToArriveInWallet, intervalTimeSecondsTokensToArriveInWallet);
        investorDetailsPage.walletsCard.waitForWalletToHoldNumberOfTokens(destinationWalletIndex, ISSUED_TOKENS, waitTimeSecondsTokensToArriveInWallet, intervalTimeSecondsTokensToArriveInWallet);
        endTestLevel();
    }

    public void cpHoldTrading(String issuerName, String firstName, String documentID) {

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel("Hold Trading procedure: " + freezeType + " Token");
        PerformHoldTrading(freezeType, issuerName, firstName, documentID);
        endTestLevel();

        startTestLevel("Verify token is " + freezeType);
        verifyContractStatusUpdated(freezeType);
        endTestLevel();
    }

    public void verifyContractStatusUpdated(String freezeType) {
        startTestLevel("Define expected contract status");
        String expectedStatus = null;
        if (freezeType.equals("freeze")) {
            expectedStatus = "on-hold";
        } else if (freezeType.equals("un-freeze")) {
            expectedStatus = "operational";
        } else {
            errorAndStop("Freeze type is not defined", true);
        }
        info("Expected contract status is " + expectedStatus);
        endTestLevel();

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        endTestLevel();

        startTestLevel("Verify token is " + freezeType);
        operationalProcedures.chooseHoldTradingProcedure();
        operationalProcedures.getUpdatedContractStatus(expectedStatus, 600, 30000);
        endTestLevel();
    }

    public void checkContractStatus() {

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        endTestLevel();

        startTestLevel("Check contract status");
        operationalProcedures.chooseHoldTradingProcedure();
        String actual = operationalProcedures.getContractStatus();
        if (actual.equals("on-hold")) {
            freezeType = "un-freeze";
        } else if (actual.equals("operational")) {
            freezeType = "freeze";
        }
        endTestLevel();
    }

    public void PerformHoldTrading(String freezeType, String issuerName, String firstName, String documentID) {
        info("Input " + freezeType + " data ");
        String reason = freezeType + " token contracts";
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        operationalProcedures.chooseHoldTradingProcedure()
                .addDocumentId(documentID)
                .typeReason(reason)
                .executeHoldTrading()
                .confirmHoldTradingModal(issuerName);
        Assert.assertTrue(operationalProcedures.verifySuccessMessage());
        info("Procedure executed successfully and " + freezeType + " all contracts");
        signSignatures(issuerName, firstName, 1);
    }

}