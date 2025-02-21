package io.securitize.tests.jp.abstractClass;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import dev.failsafe.RetryPolicy;
import io.securitize.infra.api.DomainApi;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.EmailWrapper;
import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.utils.SingleSignOnSony;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage2FA;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpCp;
import io.securitize.pageObjects.jp.controlPanel.*;
import io.securitize.pageObjects.jp.controlPanelPlus.*;
import io.securitize.pageObjects.jp.mauri.*;
import io.securitize.pageObjects.jp.sony.SonyDashboard;
import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.jp.testData.JpInvestorDetails;
import io.securitize.tests.jp.testData.SonyInvestorDetails;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.openqa.selenium.By;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.securitize.infra.reporting.MultiReporter.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Below need to cover in here as one test can use All.
 * = Control Panel especially when Japanese characters need to handle
 * = Control Panel Plus
 * = Investor Sites
 *    - Marui
 *    - Sony
 *    - Others shall be added as needed basis
 * = API
 *    - Domain API (probably this is the only API to use)
 * ---
 * Test examples:
 * = after investment on investor site, check the status on Control Panel and Control Panel Plus.
 *   -> Yes, there are 2 admin sites to check.
 * = The same investor opens two or more investor sites in her browser and operates almost at the same time.
 *   -> investor contents were mixed up.
 */
public abstract class AbstractJpFlow extends AbstractUiTest {

    protected RetryPolicy<Object> retryPolicy = null;
    private static final String CANCELED_IN_JAPANESE = "キャンセル済み";
    private static final String INVESTOR_NAME_LABEL = "investorName: ";
    protected static final int AT_THE_TOP_DATA_ROW_IN_TABLE = 0;
    private static final String EXPORT_LIST_AFTER_SORTING_TABLE_COLUMN = "Export list after sorting by table column: ";
    private static final String EXPECTED_DOWNLOADS_COUNT_LABEL = ", expectedDownloadsCount: ";
    private static final String UTF8_BOM = "\uFEFF";

    /**
     * While you are logged in Control Panel and perform export-list, chrome://downloads keeps downloaded files.
     * The number of files on the page increases. If the number of files is as expected, it means export-list
     * completes successfully. in this case, true is returned. otherwise, false is returned.
     */
    public boolean isExpectedNumberOfDownloadedFilesFound(int expectedDownloadsCount, int timeoutSeconds) {
        boolean result = true;
        try {
            getChromeLastDownloadedFileUrl(expectedDownloadsCount, timeoutSeconds);
        } catch (org.openqa.selenium.TimeoutException timeoutException) {
            error("Exception happened when getting chrome last download file. Exception message: " + timeoutException.getMessage());
            result = false;
        }
        info("found expected number of downloaded files ? " + result);
        getBrowser().waitForNumbersOfTabsToBe(2);
        getBrowser().closeLastTabAndSwitchToPreviousOne();
        return result;
    }

    protected <T> T retryFunctionWithRefreshPage(Supplier<T> func, boolean retry) {
        getBrowser().waitForPageStable();
        return RetryOnExceptions.retryFunction(func, ()-> {getBrowser().refreshPage(); return null;}, retry );
    }

    public MaruiApplicationPreEntryStep1AgreeTerms maruiPreEntryStart(String investorEmail, boolean retry) {
        investorSiteMaruiLogin(investorEmail, Users.getProperty(UsersProperty.marui_investorPassword), retry);

        String tokenName = getTokenNameWithAssert();
        startTestLevel("Navigate to Opportunities page on " + tokenName);
        MaruiDashboardOpportunities maruiDashboardOpportunities = retryFunctionWithRefreshPage(
                ()-> new MaruiDashboardOpportunities(getBrowser()), retry);
        maruiDashboardOpportunities.clickViewInvestmentDetailsByName(tokenName, retry);
        endTestLevel();

        startTestLevel("Proceed to Pre Entry Application");
        MaruiApplicationPreEntryStep0Start step0 = retryFunctionWithRefreshPage(()-> new MaruiApplicationPreEntryStep0Start(getBrowser()), retry);
        MaruiApplicationPreEntryStep1AgreeTerms step1 = step0.clickProceedToPreEntryApplication(retry);
        endTestLevel();
        return step1;
    }

    public MaruiApplicationPreEntryStep2Document maruiPreEntryStep1AgreeTerms(MaruiApplicationPreEntryStep1AgreeTerms step1, boolean retry) {
        startTestLevel("[step 1] agree to terms ");
        MaruiApplicationPreEntryStep2Document step2 = step1.agreeToTermsAndClickNextButton(retry);
        endTestLevel();
        return step2;
    }

    public MaruiApplicationPreEntryStep3InputAmount maruiPreEntryStep2Documents(MaruiApplicationPreEntryStep2Document step2, boolean retry) {
        startTestLevel("[step 2] documents");
        MaruiApplicationPreEntryStep3InputAmount step3 = step2.completeAllSteps(retry);
        getBrowser().switchToFirstWindow();
        endTestLevel();
        return step3;
    }

    public MaruiApplicationPreEntryStep3InputAmount maruiPreEntryStep2Documents(MaruiApplicationPreEntryStep2Document step2, int documentReadSeconds, boolean retry) {
        startTestLevel("[step 2] documents");
        step2.waitUntilAllDocumentsBecomeVisible(retry);
        step2.clickProductDescriptionDocumentLink();
        getBrowser().sleep((long)1000 * documentReadSeconds);
        step2.clickPreEntryImportantMattersDocumentLink();
        getBrowser().sleep((long)1000 * documentReadSeconds);
        MaruiApplicationPreEntryStep3InputAmount step3 = step2.clickConfirmCheckbox().clickNextStepButton();
        getBrowser().switchToFirstWindow();
        endTestLevel();
        return step3;
    }

    public MaruiApplicationPreEntryStep4Confirm maruiPreEntryStep3EnterAmount(MaruiApplicationPreEntryStep3InputAmount step3, String pledgedAmount, boolean retry) {
        startTestLevel("[step 3] enter amount");
        MaruiApplicationPreEntryStep4Confirm step4 = step3.enterAmountAndClickNextStepButton(pledgedAmount, retry);
        int tokenValue = getTokenValue();
        int amountYen = Integer.parseInt(pledgedAmount) * tokenValue;
        assertThat(step3.getAmountYenText()).isEqualTo(String.format("￥%,d", amountYen));
        endTestLevel();
        return step4;
    }

    public MaruiApplicationPreEntryStep5Complete maruiPreEntryStep4Confirm(MaruiApplicationPreEntryStep4Confirm step4, String pledgedAmount, boolean retry) {
        startTestLevel("[step 4] confirm");
        String tokenName = getTokenNameWithAssert();
        assertThat(step4.tokenNameText()).isEqualTo(tokenName);
        int tokenValue = getTokenValue();
        int amountYen = Integer.parseInt(pledgedAmount) * tokenValue;
        assertThat(step4.amountYenText()).isEqualTo(String.format("￥%,d", amountYen));
        assertThat(step4.amountText()).isEqualTo(pledgedAmount + " 単位");
        MaruiApplicationPreEntryStep5Complete step5 = step4.clickConfirmButton(retry);
        endTestLevel();
        return step5;
    }

    public void maruiPreEntryStep4Complete(MaruiApplicationPreEntryStep5Complete step5) {
        startTestLevel("[step 5] complete");
        step5.clickReturnToCorporateBondListButton();
        endTestLevel();
    }

    public void cpPlusLogin(String email, String password, String mfaSecret, boolean retry) {
        startTestLevel("Control Panel Plus Login");
        String cpPlusUrl = MainConfig.getCpUrl().replace("cp", "cp-plus");
        getBrowser().navigateTo(cpPlusUrl);
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        CpPlusLoginPageLanguageSet languageSetPage = retryFunctionWithRefreshPage(
                ()-> new CpPlusLoginPageLanguageSet(getBrowser()), retry);
        CpPlusLoginPage cpPlusLoginPage = languageSetPage.selectLanguageJapanese().clickLoginButton();
        cpPlusLoginPage.loginCpPlusUsingEmailPassword(email, password, mfaSecret, retry);
        endTestLevel();
    }
    public CpPlusSideMenu cpPlusLogin(String domainName, String tokenName, boolean retry) {
        String testName = getCurrentTestName();
        Matcher matcher = Pattern.compile("(AUT\\d+)_").matcher(testName);
        assertThat(matcher.find()).isTrue();
        String testId = matcher.group(1).trim();
        String email = String.format(Users.getProperty(UsersProperty.automationCpE2eEmail), testId);
        String password = Users.getProperty(UsersProperty.automationCpPassword);
        String mfaSecret = Users.getProperty(UsersProperty.automationCp2FaSecret);
        cpPlusLogin(email, password, mfaSecret, retry);
        return cpPlusSetDomainNameAndTokenName(domainName, tokenName, retry);
    }

    public CpPlusSideMenu cpPlusSetDomainNameAndTokenName(String domainName, String tokenName, boolean retry) {
        startTestLevel("Select Domain Name, Token Name; Set Language JA if not JA");
        CpPlusSideMenu sideMenu = retryFunctionWithRefreshPage(()-> new CpPlusSideMenu(getBrowser()), retry);
        if (domainName != null) {
            sideMenu.selectIssuerName(domainName, retry);
        }
        if (tokenName != null) {
            sideMenu.selectTokenName(tokenName, retry);
        }
        sideMenu.selectLanguageJapanese();
        endTestLevel();
        return sideMenu;
    }

    public CpPlusSideMenu cpPlusLoginMarui(String tokenName, boolean retry) {
        cpPlusLogin(
                Users.getProperty(UsersProperty.marui_adminEmail),
                Users.getProperty(UsersProperty.marui_adminPassword),
                Users.getProperty(UsersProperty.marui_adminMfaSecret),
                retry
        );
        String domainName = getDomainNameWithAssert();
        return cpPlusSetDomainNameAndTokenName(domainName, tokenName, retry);
    }

    public void cpPlusInvestorsPageFilterInvestor(String email, boolean retry) {
        startTestLevel("Filter investors using email address");
        CpPlusInvestorsPageFilter investorsPageFilter = retryFunctionWithRefreshPage(
                ()-> new CpPlusInvestorsPageFilter(getBrowser()), retry);
        boolean investorFound = investorsPageFilter.filterInvestorWith(email);
        assertThat(investorFound).isTrue();
        endTestLevel();
    }

    public void cpPlusOnInvestorsPageFilterInvestorAndOpenInvestorDetails(CpPlusSideMenu sideMenu, String investorEmail, boolean retry) {
        startTestLevel("Navigate to Investors page");
        CpPlusInvestorsPage investorsPage = sideMenu.clickInvestors(retry);
        endTestLevel();
        cpPlusInvestorsPageFilterInvestor(investorEmail, retry);
        startTestLevel("Click on Eye Icon");
        investorsPage.clickEyeIconWith(investorEmail, retry);
        endTestLevel();
    }

    public void cpPlusEnsureInvestorCanMakePreEntryApplication(String tokenName, String investorEmail, boolean retry) {
        CpPlusSideMenu sideMenu = cpPlusLoginMarui(tokenName, retry);
        cpPlusOnInvestorsPageFilterInvestorAndOpenInvestorDetails(sideMenu, investorEmail, retry);

        startTestLevel("Ensure investor can make for Pre-Entry Application");
        CpPlusInvestorsPageDetail investmentStatusSection = retryFunctionWithRefreshPage(
                ()-> new CpPlusInvestorsPageDetail(getBrowser()), retry);
        investmentStatusSection.moveToInvestmentStatusSection();

        String investmentStatus = investmentStatusSection.getInvestmentStatusText();
        if (investmentStatus.equals("なし") || investmentStatus.equals(CANCELED_IN_JAPANESE)) {
            info("investment status: " + investmentStatus + ". good to go pre-entry application");
        } else {
            String investmentStatusAfterCancel = investmentStatusSection.cancelInvestment(tokenName, retry);
            assertThat(investmentStatusAfterCancel).isEqualTo(CANCELED_IN_JAPANESE);
        }
        sideMenu.clickLogoutButton();
        endTestLevel();
    }

    private String expectedNumber(String number) {
        try {
            number = String.format("%,d", Integer.parseInt(number));
        } catch (NumberFormatException exception) {
           debug("number (" + number + "). null or blank is okay. exception: " + exception);
        }
        return number;
    }

    private String expectedNumber(String number, int tokenValue) {
        try {
            number = String.format("%,d", Integer.parseInt(number)*tokenValue);
        } catch (NumberFormatException exception) {
            debug("number (" + number + "). null or blank is okay. exception: " + exception);
        }
        return number;
    }

    /**
     * [CP+] check values at investment section (at the bottom) on investor details page
     * @param tokenName expected token name
     * @param investorEmail investor email
     * @param status expected investment status
     * @param tokensHeldWallet expected number of tokens held in wallet
     * @param tokensHeldTreasure expected number of tokens help in treasure
     * @param tokensForPledgedAmount expected number of tokens for pledged amount.
     *                               pledged amount is calculated with this number to compare value on UI
     * @param tokensForConfirmedPledgedAmount expected number of tokens for confirmed pledged amount.
     *                               confirmed pledged amount is calculated with this number to compare value on UI.
     * @param tokensForTotalFunded expected number of tokens for total funded.
     *                             total funded is calculated with this number to compare value on UI.
     */
    public void cpPlusInvestmentValueCheck(String tokenName, String investorEmail, String status, String tokensHeldWallet,
                                           String tokensHeldTreasure, String tokensForPledgedAmount,
                                           String tokensForConfirmedPledgedAmount, String tokensForTotalFunded, String currencySymbol, boolean retry) {

        CpPlusSideMenu sideMenu = cpPlusLoginMarui(tokenName, retry);
        cpPlusOnInvestorsPageFilterInvestorAndOpenInvestorDetails(sideMenu, investorEmail, retry);

        startTestLevel("Check Investment status and values");
        CpPlusInvestorsPageDetail investmentStatusSection = retryFunctionWithRefreshPage(
                ()-> new CpPlusInvestorsPageDetail(getBrowser()), retry);
        investmentStatusSection.moveToInvestmentStatusSection();

        // create expected values
        int tokenValue = getTokenValue(tokenName);
        String expectedTokenHeldWallet = expectedNumber(tokensHeldWallet);
        String expectedTokenHeldTreasure = expectedNumber(tokensHeldTreasure);
        String expectedPledgedAmount = expectedNumber(tokensForPledgedAmount, tokenValue);
        String expectedConfirmedPledgedAmount = expectedNumber(tokensForConfirmedPledgedAmount, tokenValue);
        String expectedTotalFunded = expectedNumber(tokensForTotalFunded, tokenValue);
        if (expectedPledgedAmount != null) {
            expectedPledgedAmount = currencySymbol + expectedPledgedAmount;
        }
        if (expectedConfirmedPledgedAmount != null) {
            expectedConfirmedPledgedAmount = currencySymbol + expectedConfirmedPledgedAmount;
        }
        if (expectedTotalFunded.equals("0")) {
            expectedTotalFunded = (status.equals("なし")) ? "" : "-" ;
        } else {
            expectedTotalFunded = currencySymbol + expectedTotalFunded;
        }
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(investmentStatusSection.getInvestmentStatusText()).isEqualTo(status);
        softAssertions.assertThat(investmentStatusSection.getTokenNameText()).isEqualTo(tokenName);
        softAssertions.assertThat(investmentStatusSection.getTokenHeldWalletText()).isEqualTo(expectedTokenHeldWallet);
        softAssertions.assertThat(investmentStatusSection.getTokenHeldTreasureText()).isEqualTo(expectedTokenHeldTreasure);
        softAssertions.assertThat(investmentStatusSection.getPledgedAmountText()).isEqualTo(expectedPledgedAmount);
        softAssertions.assertThat(investmentStatusSection.getConformedPledgedAmountText()).isEqualTo(expectedConfirmedPledgedAmount);
        softAssertions.assertThat(investmentStatusSection.getTotalFundedText()).isEqualTo(expectedTotalFunded);
        softAssertions.assertAll();
        endTestLevel();
    }

    public void cpPlusInvestmentValueCheckAfterPreEntry(String tokenName, String investorEmail, String numberOfTokensForPledgedAmount, String currencySymbol, boolean retry) {
        String expectedStatus = "申込内容確認中";
        String expectedNumberOfTokensHeldWallet = "0";
        String expectedNumberOfTokensHeldTreasure = "0";
        String expectedNumberOfTokensForConfirmedPledgedAmount = null;
        String expectedNumberOfTokensForTotalFunded = "0";

        cpPlusInvestmentValueCheck(tokenName, investorEmail, expectedStatus, expectedNumberOfTokensHeldWallet,
                expectedNumberOfTokensHeldTreasure, numberOfTokensForPledgedAmount,
                expectedNumberOfTokensForConfirmedPledgedAmount, expectedNumberOfTokensForTotalFunded, currencySymbol, retry);
    }

    public void emailContentCheckAfterPreEntry(String tokenName, String investorEmail, String familyName, String givenName,
                                               String numberOfTokensForPledgedAmount) {
        startTestLevel("Check Email Content After PreEntry");
        String regex = "(.*抽選のお申込みを受付けました.*)";
        String emailContent = "";
        try {
            emailContent = EmailWrapper.waitAndGetEmailContentByRecipientAddressAndRegex(investorEmail, regex);
        } catch (Exception exception) {
            error("exception while getting email about pre-entry. " + exception);
        }
        assertThat(emailContent).contains(familyName + "\u3000" + givenName + "様");
        assertThat(emailContent).contains(tokenName);
        assertThat(emailContent).contains("以下の内容で、抽選のお申込みを受付けました");
        assertThat(emailContent).contains("投資家ID：" + getExternalId(investorEmail));
        assertThat(emailContent).contains("申込数量：" + numberOfTokensForPledgedAmount);
        int tokenValue = getTokenValue();
        String expectedPledgedAmount = expectedNumber(numberOfTokensForPledgedAmount, tokenValue);
        assertThat(emailContent).contains("申込金額：" + expectedPledgedAmount);
        endTestLevel();
    }

    public void emailContentCheckAfterCancelPreEntry(String tokenName, String investorEmail, String familyName, String givenName) {
        startTestLevel("Check Email Content After Cancel PreEntry");
        String regex = "(.*先日お申込みいただきました.*)";
        String emailContent = "";
        try {
            emailContent = EmailWrapper.waitAndGetEmailContentByRecipientAddressAndRegex(investorEmail, regex);
        } catch (Exception exception) {
            error("exception while getting email about cancel pre-entry. " + exception);
        }
        assertThat(emailContent).contains(familyName + "\u3000" + givenName + "様");
        assertThat(emailContent).contains(tokenName);
        assertThat(emailContent).contains("キャンセルが完了しました");
        endTestLevel();
    }

    public void cpPlusCancelInvestment(String investorEmail, boolean retry) {
        startTestLevel("Control Panel Plus SideMenu");
        CpPlusSideMenu sideMenu = retryFunctionWithRefreshPage(()-> new CpPlusSideMenu(getBrowser()), retry);
        endTestLevel();

        cpPlusOnInvestorsPageFilterInvestorAndOpenInvestorDetails(sideMenu, investorEmail, retry);

        startTestLevel("Cancel Investment");
        String tokenName = getTokenNameWithAssert();
        CpPlusInvestorsPageDetail investmentStatusSection = retryFunctionWithRefreshPage(
                ()-> new CpPlusInvestorsPageDetail(getBrowser()), retry);
        investmentStatusSection.moveToInvestmentStatusSection();
        String investmentStatusAfterCancel = investmentStatusSection.cancelInvestment(tokenName, retry);
        assertThat(investmentStatusAfterCancel).isEqualTo(CANCELED_IN_JAPANESE);
        endTestLevel();
    }

    public void cpPlusLogout(boolean retry) {
        startTestLevel("Control Panel Plus Logout");
        CpPlusSideMenu sideMenu = retryFunctionWithRefreshPage(()-> new CpPlusSideMenu(getBrowser()), retry);
        sideMenu.clickLogoutButton();
        endTestLevel();
    }

    public void cpPlusCancelInvestmentAndLogout(String investorEmail, boolean retry) {
        cpPlusCancelInvestment(investorEmail, retry);
        cpPlusLogout(retry);
    }

    public CpPlusBankDepositFilePage cpPlusCreateBankDepositFile(String tokenName, String payYear, String payMonth, boolean isInterestPay, boolean isRedemptionPay, int row, boolean retry) {
        startTestLevel("Create Bank Deposit File");
        CpPlusSideMenu sideMenu = retryFunctionWithRefreshPage(()-> new CpPlusSideMenu(getBrowser()), retry);
        CpPlusBankDepositFilePage bankDepositFilePage = sideMenu.clickBankDepositFileMenu(retry);
        // delete bank deposit file if exists
        if (bankDepositFilePage.isNoDataTextFound()) {
            info("bank deposit file does not exist");
        } else {
            int numberOfBankDepositFiles = bankDepositFilePage.numberOfRowsInTable();
            assertThat(numberOfBankDepositFiles).isEqualTo(1);
            assertThat(bankDepositFilePage.isBankDepositFileFound(payYear)).isTrue();
            assertThat(bankDepositFilePage.isBankDepositFileFound(payMonth)).isTrue();
            info("delete bank deposit file " + payYear + "/" + payMonth);
            bankDepositFilePage.deleteAt(row);
        }
        RetryOnExceptions.retryFunction(
                ()-> {bankDepositFilePage.createBankDepositFile(payYear, payMonth, isInterestPay, isRedemptionPay); return null;},
                ()-> {getBrowser().refreshPage(); sideMenu.selectTokenName(tokenName, retry); return null;},
                retry
        );
        getBrowser().waitForPageStable();
        String status = bankDepositFilePage.getStatus(row);
        assertThat(status).isEqualTo("作成完了");
        endTestLevel();
        return bankDepositFilePage;
    }

    public CpPlusCorporateBondLedgerPage cpPlusCreateCorporateBondLedgerPage(String tokenName, int row, boolean retry) {
        startTestLevel("Create Corporate Bond Ledger");
        CpPlusSideMenu sideMenu = retryFunctionWithRefreshPage(()-> new CpPlusSideMenu(getBrowser()), retry);
        CpPlusCorporateBondLedgerPage corporateBondLedgerPage = sideMenu.clickCorporateBondLedgersMenu(retry);
        corporateBondLedgerPage.deleteAllCorporateBondLedgers(corporateBondLedgerPage, retry);
        RetryOnExceptions.retryFunction(
                ()-> {corporateBondLedgerPage.createCorporateLedger(); return null;},
                ()-> {getBrowser().refreshPage(); sideMenu.selectTokenName(tokenName, retry); return null;},
                retry
        );
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        int numberOfCorporateBondLedgers = corporateBondLedgerPage.numberOfRowsInTable();
        assertThat(numberOfCorporateBondLedgers).isEqualTo(1);
        String status = corporateBondLedgerPage.getStatus(row);
        assertThat(status).isEqualTo("作成完了");
        endTestLevel();
        return corporateBondLedgerPage;
    }

    public String cpPlusDownloadCorporateBondLedger(CpPlusCorporateBondLedgerPage corporateBondLedgerPage, int row) {
        startTestLevel("Download Ledger");
        corporateBondLedgerPage.clickDownloadButton(row);
        String downloadedFileUrl = getChromeLastDownloadedFileUrl();
        String fileContent = urlGetRequest(downloadedFileUrl);
        debug(fileContent);
        getBrowser().closeLastTabAndSwitchToPreviousOne();
        endTestLevel();
        return fileContent;
    }

    private static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            s = s.substring(1);
        }
        return s;
    }

    // [note] corporate bond ledger file has BOM. BOM needs to remove.
    public List<String> getInvestorNamesFromCorporateBondLedger(String ledgerContent) {
        List<String> investorsInLedger = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new StringReader(ledgerContent))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] array = line.split(",");
                String name = removeUTF8BOM(array[0]);
                if (!name.equals("氏名")) {
                    debug(name);
                    investorsInLedger.add(name);
                }
            }
        } catch (IOException exception) {
            errorAndStop("IO exception happened when getting investor names from ledger",false);
        }
        return investorsInLedger;
    }

    public void cpPlusBankDepositFileDownloadTheLatest(CpPlusBankDepositFilePage bankDepositFilePage, int row) {
        startTestLevel("Download Bank Deposit File");
        bankDepositFilePage.clickDownloadButton(row);
        getLastDownloadedDistributionFileContent();
        getBrowser().closeLastTabAndSwitchToPreviousOne();
        endTestLevel();
    }

    public void cpLogin(String adminEmail, String adminPassword, String twoFaSecret) {
        startTestLevel("CP Login");
        String url = MainConfig.getProperty(MainConfigProperty.cpUrl);
        getBrowser().navigateTo(url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());

        CpLoginPage2FA loginPage2Fa;
        if (adminEmail == null && adminPassword == null) {
            loginPage2Fa = loginPage.loginCpUsingEmailPassword();
        } else {
            loginPage2Fa = loginPage.loginCpUsingEmailPassword(adminEmail, adminPassword, true);
        }

        int seconds = LocalTime.now().getSecond() % 30;
        if (seconds > 21) {
            getBrowser().sleep((30 - seconds + 1) * (long)1000);
        }

        if (twoFaSecret == null) {
            loginPage2Fa.obtainPrivateKey()
                        .generate2FACode()
                        .setTwoFaCodeInUIBasicOperator();
        } else {
            loginPage2Fa.obtainPrivateKey()
                        .generate2FACode(twoFaSecret)
                        .setTwoFaCodeInUIBasicOperator();
        }
        endTestLevel();
    }

    public void cpLogin() {
        cpLogin(null, null, null);
    }

    public JpCpSideMenu selectTokenName(String tokenName) {
        startTestLevel("Select Token Name: " + tokenName);
        JpCpSideMenu sideMenu = new JpCpSideMenu(getBrowser());
        sideMenu.openTokenDropdown();
        sideMenu.clickTokenName(tokenName);
        endTestLevel();
        return sideMenu;
    }

    public void selectTokenName(JpCpSideMenu sideMenu, String tokenName) {
        startTestLevel("Select Token Name: " + tokenName);
        sideMenu.openTokenDropdown();
        sideMenu.clickTokenName(tokenName);
        endTestLevel();
    }

    public void selectIssuer(String domainName) {
        startTestLevel("Select Issuer: " + domainName);
        JpCpSelectIssuer selectIssuer = new JpCpSelectIssuer(getBrowser());
        selectIssuer.setLanguageJapanese()
                .waitForIssuerPageToLoad()
                .clickViewIssuerByName(domainName);
        endTestLevel();
    }

    public void selectIssuer(String domainName, boolean retry) {
        retryFunctionWithRefreshPage(()-> {selectIssuer(domainName); return null;}, retry);
    }

    public JpCpSideMenu cpLoginMarui(String tokenName, boolean retry) {
        String admin1Email = Users.getProperty(UsersProperty.marui_adminEmail);
        String admin1Password = Users.getProperty(UsersProperty.marui_adminPassword);
        String admin1MfaSecret = Users.getProperty(UsersProperty.marui_adminMfaSecret);
        cpLogin(admin1Email, admin1Password, admin1MfaSecret);

        String domainName = getDomainNameWithAssert();
        selectIssuer(domainName, retry) ;
        return selectTokenName(tokenName);
    }

    public JpCpSideMenu cpLoginSony() {
        String adminEmail = Users.getProperty(UsersProperty.sonyAdminEmail);
        String adminPassword = Users.getProperty(UsersProperty.sonyAdminPassword);
        String adminMfaSecret = Users.getProperty(UsersProperty.sonyAdminMfaSecret);
        cpLogin(adminEmail, adminPassword, adminMfaSecret);
        startTestLevel("CP Side Menu");
        JpCpSideMenu sideMenu = new JpCpSideMenu(getBrowser());
        endTestLevel();
        return sideMenu;
    }

    public JpCpSideMenu cpLoginSony(String tokenName) {
        cpLogin();
        selectIssuer("ソニー銀行");
        return selectTokenName(tokenName);
    }

    /**
     * note: this method shall be called after the method above "cpLoginMarui()"
     * then, on controlPanel, search another admin, click edit on the admin
     * when edit is clicked, edit dialog box should appear. a problem was that edit dialog
     * did not appear under a certain case -- edit function was broken
     */
    public void cpPanelAdministrationPageClickEditOnAdministrator(String adminEmail2) {
        startTestLevel("On Panel Administration page, Search administrator " + adminEmail2);
        String url = MainConfig.getProperty(MainConfigProperty.cpUrl);
        getBrowser().navigateTo(url + "operators");
        JpCpPanelAdministration panelAdministration = new JpCpPanelAdministration(getBrowser());
        assertThat(panelAdministration.adminStatusText(adminEmail2)).isEqualTo("アクティブ");
        panelAdministration.clickEditButtonOfEmail(adminEmail2);
        endTestLevel();
    }

    public JpCpOnBoarding cpNavigateToOnboardingPage() {
        startTestLevel("Navigate to Onboarding page");
        JpCpOnBoarding onBoarding = new JpCpSideMenu(getBrowser()).clickOnBoarding();
        endTestLevel();
        return onBoarding;
    }

    public JpCpFundraise cpNavigateToFundraisePage() {
        startTestLevel("Navigate to Fundraise page");
        JpCpFundraise fundraise = new JpCpSideMenu(getBrowser()).clickFundraise();
        endTestLevel();
        return fundraise;
    }

    public JpCpHolders cpNavigateToHoldersPage() {
        startTestLevel("Navigate to Holders page");
        JpCpHolders holders = new JpCpSideMenu(getBrowser()).clickHolders();
        endTestLevel();
        return holders;
    }

    public String cpPageSearchAndGetInvestorName(AbstractJpCp cpPage, String investorEmail, String tableHeader) {
        startTestLevel("Search investor using " + investorEmail + " and get investor name");
        if (cpPage.enterUniqueSearchText(investorEmail) == null) {
            info("investor not found");
            return null;
        }
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        String investorName = cpPage.getInvestorDataByTableHeaderAndRowNumber(tableHeader, AT_THE_TOP_DATA_ROW_IN_TABLE);
        debug(INVESTOR_NAME_LABEL + investorName);
        endTestLevel();
        return investorName;
    }

    public String cpOnboardingPageSearchAndGetInvestorName(String investorEmail) {
        return cpPageSearchAndGetInvestorName(cpNavigateToOnboardingPage(), investorEmail, "投資家");
    }

    public String cpFundraisePageSearchAndGetInvestorName(String investorEmail) {
        return cpPageSearchAndGetInvestorName(cpNavigateToFundraisePage(), investorEmail, "投資家");
    }

    public String cpHoldersPageSearchAndGetInvestorName(String investorEmail) {
        return cpPageSearchAndGetInvestorName(cpNavigateToHoldersPage(), investorEmail, "保有者");
    }

    public JpCpInvestorsDetails cpOpenInvestorDetailsPage(String investorEmail) {
        JpCpOnBoarding onBoarding = cpNavigateToOnboardingPage();
        String investorName = cpPageSearchAndGetInvestorName(onBoarding, investorEmail, "投資家");
        startTestLevel("Click Eye Icon");
        JpCpInvestorsDetails investorDetails = onBoarding.clickEyeIconOf(investorName);
        endTestLevel();
        return investorDetails;
    }

    public void cpOpenInvestorDetailsPageAndClickInvestmentLink(String investorEmail) {
        JpCpInvestorsDetails investorDetails = cpOpenInvestorDetailsPage(investorEmail);
        startTestLevel("Click Investment Link");
        investorDetails.clickInvestmentLink();
        endTestLevel();
    }

    public String getExpectedCountryInJapanese(String countryCode) {
        String countryName;
        switch(countryCode) {
            case "US":
                countryName = "アメリカ合衆国";
                break;
            case "HK":
                countryName = "香港";
                break;
            case "JP":
                countryName = "日本";
                break;
            default:
                countryName = "UnknownCountryCode: " + countryCode;
        }
        return countryName;
    }

    public void cpOnboardingPageCheckTableValues(String investorEmail, String expectedInvestorName, String expectedCountry) {
        String investorName = cpOnboardingPageSearchAndGetInvestorName(investorEmail);
        startTestLevel("check table values on OnBoarding page about " + investorEmail);
        SoftAssertions softAssertions = new SoftAssertions();
        JpCpOnBoarding onBoarding = new JpCpOnBoarding(getBrowser());
        softAssertions.assertThat(investorName).isEqualTo(expectedInvestorName);
        softAssertions.assertThat(onBoarding.getTableDataAbout("居住国", investorName)).isEqualTo(expectedCountry);
        softAssertions.assertThat(onBoarding.getTableDataAbout("タイプ", investorName)).isEqualTo("個人");
        softAssertions.assertThat(onBoarding.getTableDataAbout("登録元", investorName)).isEqualTo("内部");
        softAssertions.assertThat(onBoarding.getTableDataAbout("SecuritizeID", investorName)).isEqualTo("なし");
        softAssertions.assertThat(onBoarding.getTableDataAbout("KYC\nステータス", investorName)).isEqualTo("なし");
        softAssertions.assertThat(onBoarding.getTableDataAbout("適格性\nステータス", investorName)).isEqualTo("なし");
        softAssertions.assertThat(onBoarding.getTableDataAbout("ラベル", investorName)).isNull();
        softAssertions.assertAll();
        endTestLevel();
    }

    public void cpFundraisePageCheckTableValues(String investorEmail, String investorStatus, String pledged, String funded, String saStatus, String token) {
        String investorName = cpFundraisePageSearchAndGetInvestorName(investorEmail);
        startTestLevel("check table values on Fundraise page about " + investorEmail);
        SoftAssertions softAssertions = new SoftAssertions();
        JpCpFundraise fundraise = new JpCpFundraise(getBrowser());
        softAssertions.assertThat(fundraise.getTableDataAbout("KYC\nステータス", investorName)).isEqualTo(investorStatus);
        softAssertions.assertThat(fundraise.getTableDataAbout("投資家タイプ", investorName)).isEqualTo("個人");
        softAssertions.assertThat(fundraise.getTableDataAbout("申込み額", investorName)).contains("¥\n" + pledged);
        String expectedFunded = funded.isEmpty() ? "¥" : "¥\n" + funded;
        softAssertions.assertThat(fundraise.getTableDataAbout("入金額", investorName)).contains(expectedFunded);
        softAssertions.assertThat(fundraise.getTableDataAbout("契約\nステータス", investorName)).isEqualTo(saStatus);
        softAssertions.assertThat(fundraise.getTableDataAbout("Assigned Tokens", investorName)).isEqualTo(token);
        softAssertions.assertThat(fundraise.getTableDataAbout("ラウンド", investorName)).isEqualTo("Default name");
        softAssertions.assertThat(fundraise.getTableDataAbout("ラベル", investorName)).isNull();
        softAssertions.assertAll();
        endTestLevel();
    }

    public void cpFundraisePageCheckTableValuesAfterPreEntry(String investorEmail, String pledgedAmount) {
        String expectedInvestorStatus = "KYC待ち";
        int tokenValue = getTokenValue();
        String expectedPledged = String.valueOf(Integer.parseInt(pledgedAmount) * tokenValue);
        String expectedFunded = "";
        String expectedSaStatus = "リクエスト済";
        String expectedAssignedTokens = "0";
        cpFundraisePageCheckTableValues(investorEmail, expectedInvestorStatus, expectedPledged, expectedFunded, expectedSaStatus, expectedAssignedTokens);
    }

    public void cpFundraisePageCheckTableValuesAfterCancelPreEntry(String investorEmail, String pledgedAmount) {
        String expectedInvestorStatus = "KYC待ち";
        int tokenValue = getTokenValue();
        String expectedPledged = String.valueOf(Integer.parseInt(pledgedAmount) * tokenValue);
        String expectedFunded = "";
        String expectedSaStatus = "不合格";
        String expectedAssignedTokens = "0";
        cpFundraisePageCheckTableValues(investorEmail, expectedInvestorStatus, expectedPledged, expectedFunded, expectedSaStatus, expectedAssignedTokens);
    }

    public void cpHoldersPageCheckTableValues(String investorEmail, String token, String walletAddress) {
        String investorName = cpHoldersPageSearchAndGetInvestorName(investorEmail);
        startTestLevel("check table values on Holders page about " + investorEmail);
        SoftAssertions softAssertions = new SoftAssertions();
        JpCpHolders holders = new JpCpHolders(getBrowser());
        softAssertions.assertThat(holders.getTableDataAbout("居住国", investorName)).isEqualTo("日本");
        softAssertions.assertThat(holders.getTableDataAbout("投資家\nタイプ", investorName)).isEqualTo("個人");
        softAssertions.assertThat(holders.getTableDataAbout("トークン\n保有量", investorName)).isEqualTo(token);
        softAssertions.assertThat(holders.getTableDataAbout("パーセント", investorName)).isNotEmpty();
        // percent check -- exact value is not checked
        String percentString = holders.getTableDataAbout("パーセント", investorName).replace("%", "").trim();
        double percentageDouble;
        try {
            percentageDouble = Double.parseDouble(percentString);
            debug("percentage string: " + percentString + " float: " + percentageDouble);
            softAssertions.assertThat(percentageDouble).isGreaterThanOrEqualTo(0.0);
            softAssertions.assertThat(percentageDouble).isLessThanOrEqualTo(100.0);
        } catch (NumberFormatException numberFormatException) {
            error("percent value : " + percentString + ". exception: " + numberFormatException);
        }
        softAssertions.assertThat(holders.getTableDataAbout("ウォレットアドレス", investorName)).isEqualTo(walletAddress);
        softAssertions.assertThat(holders.getTableDataAbout("ウォレット\n登録ステータス", investorName)).isNull();
        softAssertions.assertThat(holders.getTableDataAbout("ラベル", investorName)).isNull();
        softAssertions.assertAll();
        endTestLevel();
    }

    public void cpInvestorDetailsCheckInvestmentSectionValues(String tokenName, String status,
                String investmentStatus, String pledged, String initiator, String funded, String subscriptionStatus) {
        startTestLevel("check Investor Details Investment Section");
        JpCpInvestorsDetails investorsDetails = new JpCpInvestorsDetails(getBrowser());
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(investorsDetails.getTokenNameSelectText()).isEqualTo(tokenName);
        softAssertions.assertThat(investorsDetails.getRoundNameSelect()).isEqualTo("Default name");
        softAssertions.assertThat(investorsDetails.getStatusText()).isEqualTo(status);
        softAssertions.assertThat(investorsDetails.getInvestmentStatusText()).isEqualTo(investmentStatus);
        softAssertions.assertThat(investorsDetails.getPledgedAmountText()).contains(pledged);
        softAssertions.assertThat(investorsDetails.getTotalFundedText()).contains(funded);
        softAssertions.assertThat(investorsDetails.getSubscriptionStatusText()).contains(subscriptionStatus);
        softAssertions.assertAll();
        endTestLevel();
    }

    public void cpInvestorDetailsCheckInvestmentSectionValuesAfterPreEntry(String tokenName) {
        String expectedStatus = "募集中";
        String expectedInvestmentStatus = "保留中";
        String expectedAmount = "";
        String expectedInitiator = "api";
        String expectedSubscriptionStatus = "リクエスト済";
        cpInvestorDetailsCheckInvestmentSectionValues(tokenName, expectedStatus, expectedInvestmentStatus,
                expectedAmount, expectedInitiator, expectedAmount, expectedSubscriptionStatus);
    }

    public void cpInvestorDetailsCheckGeneralInformationValues(String investorEmail, String firstName, String lastName, String country) {
        startTestLevel("check Investor Details General Information Section");
        JpCpInvestorsDetails investorsDetailsPage = cpOpenInvestorDetailsPage(investorEmail);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(investorsDetailsPage.getFirstNameField()).isEqualTo(firstName);
        softAssertions.assertThat(investorsDetailsPage.getLastNameField()).isEqualTo(lastName);
        softAssertions.assertThat(investorsDetailsPage.getMiddleNameField()).isEqualTo("-");
        softAssertions.assertThat(investorsDetailsPage.getEmailField()).isEqualTo(investorEmail);
        softAssertions.assertThat(investorsDetailsPage.getCountryField()).isEqualTo(country);
        // note -- "Japan" is displayed for automation admin, "日本" is displayed for sony admin
        if (country.equals("United States of America")) {
            softAssertions.assertThat(investorsDetailsPage.getStateField()).isEqualTo("Alabama");
        }
        softAssertions.assertAll();
        endTestLevel();
    }

    public void cpLogout() {
        startTestLevel("logout");
        JpCpSideMenu sideMenu = new JpCpSideMenu(getBrowser());
        sideMenu.logout();
        getBrowser().clearAllCookies();
        endTestLevel();
    }

    public void cpLogout(JpCpSideMenu sideMenu) {
        startTestLevel("logout");
        sideMenu.logout();
        getBrowser().clearAllCookies();
        endTestLevel();
    }

    public void cpInvestorDetailsCheckInvestmentSectionValuesAfterCancelPreEntry(String tokenName) {
        String expectedStatus = "募集中";
        String expectedInvestmentStatus = "保留中";
        String expectedAmount = "";
        String expectedInitiator = "api";
        String expectedSubscriptionStatus = "拒否";
        cpInvestorDetailsCheckInvestmentSectionValues(tokenName, expectedStatus, expectedInvestmentStatus,
                expectedAmount, expectedInitiator, expectedAmount, expectedSubscriptionStatus);
    }

    private boolean exportList(AbstractJpCp cpPage, String tableHeaderColumnName, int expectedDownloadsCount, int downloadTimeoutSeconds) {
        startTestLevel(EXPORT_LIST_AFTER_SORTING_TABLE_COLUMN + tableHeaderColumnName + EXPECTED_DOWNLOADS_COUNT_LABEL + expectedDownloadsCount);
        if (tableHeaderColumnName.isEmpty()) {
            cpPage.clickExportButton();
        } else {
            cpPage.clickTableHeader(tableHeaderColumnName)
                    .clickExportButton();
        }
        boolean result = isExpectedNumberOfDownloadedFilesFound(expectedDownloadsCount, downloadTimeoutSeconds);
        if (result) {
            info("Number of download files is as expected.");
        } else {
            error("Number of download files is not as expected.");
        }
        endTestLevel();
        return result;
    }

    public boolean onBoardingExportList(String tableHeaderColumnName, int expectedDownloadsCount, int downloadTimeoutSeconds) {
        return exportList(cpNavigateToOnboardingPage(), tableHeaderColumnName, expectedDownloadsCount, downloadTimeoutSeconds);
    }

    public boolean fundraiseExportList(String tableHeaderColumnName, int expectedDownloadsCount, int downloadTimeoutSeconds) {
        return exportList(cpNavigateToFundraisePage(), tableHeaderColumnName, expectedDownloadsCount, downloadTimeoutSeconds);
    }

    public boolean holdersExportList(String tableHeaderColumnName, int expectedDownloadsCount, int downloadTimeoutSeconds) {
        return exportList(cpNavigateToHoldersPage(), tableHeaderColumnName, expectedDownloadsCount, downloadTimeoutSeconds);
    }

    public DomainApi domainApi(String domainId) {
        return new DomainApi(
                Users.getProperty(UsersProperty.jp_domainApiBaseUrl),
                domainId,
                Users.getProperty(UsersProperty.jp_domainApiKey)
        );
    }

    public DomainApi domainApi() {
        return domainApi(Users.getProperty(UsersProperty.marui_domainId));
    }

    public String getTokenName() {
        JSONObject jsonObject = domainApi().getToken(Users.getProperty(UsersProperty.marui_tokenId));
        return jsonObject.optString("tokenName");
    }

    public int getTokenValue() {
        JSONObject jsonObject = domainApi().getToken(Users.getProperty(UsersProperty.marui_tokenId));
        return jsonObject.optInt("tokenValue");
    }

    public int getTokenValue(String tokenName) {
        String tokenId = getTokenIdWithAssert(tokenName);
        JSONObject jsonObject = domainApi().getToken(tokenId);
        return jsonObject.optInt("tokenValue");
    }

    public String getTokenNameWithAssert() {
        String tokenName = getTokenName();
        assertThat(tokenName.isBlank()).isFalse();
        return tokenName;
    }

    public String getDomainNameWithAssert() {
        List<String> domainNameList = getDomainName();
        assertThat(domainNameList.size() == 1).isTrue();
        return domainNameList.get(0);
    }

    public List<String> getDomainName() {
        return domainApi().getDomainName();
    }

    public String getTokenIdWithAssert(String tokenName) {
        List<String> tokenIdList = getTokenId(tokenName);
        assertThat(tokenIdList.size() == 1).isTrue();
        return tokenIdList.get(0);
    }

    public List<String> getTokenId(String tokenName) {
        return domainApi().getTokenId(tokenName);
    }

    public String getExternalId(String investorEmail) {
        List<String> externalIdList = domainApi().getInvestorExternalId(investorEmail);
        assertThat(externalIdList.size()).isEqualTo(1);
        return externalIdList.get(0);
    }

    public List<String> getHolderNames(String tokenId) {
        return domainApi().getHolderNameList(tokenId);
    }

    public boolean sonyInvestorIsFound(String branchCode, String accountNumber) {
        DomainApi sonyDomainApi =  domainApi(Users.getProperty(UsersProperty.sonyDomainId));
        String sonyInvestorEmailAddress = branchCode + accountNumber + "@s.s.s.sso";
        return sonyDomainApi.investorIsFound(sonyInvestorEmailAddress);
    }

    /**
     * get an unused bank account number in the specified branch code
     * investor email is based on bank branch code and account number.
     * email address is used to check whether investor exists or not
     * account number starts from timestamp yyMMdd0 (the last is zero)
     * if exists, the number is incremented.
     */
    public String sonyGetUnusedBankAccountNumberIn(String branchCode) {
        int searchFrom = Integer.parseInt(DateTimeUtils.currentDateFormat("yyMMdd"))*10;
        int searchTo = 9999999;
        String accountNumberString;
        for(int accountNumber=searchFrom; accountNumber<=searchTo; accountNumber++) {
            accountNumberString = String.format("%07d",accountNumber);
            if (!sonyInvestorIsFound(branchCode, accountNumberString)) {
                debug("unused account number: " + accountNumberString);
                return accountNumberString;
            }
        }
        errorAndStop("try another branch code. all account numbers are used in branch: " + branchCode, false);
        return null;
    }

    public void navigateToMaruiInvestorSite() {
        String url = Users.getProperty(UsersProperty.marui_investorSiteBaseUrl);
        getBrowser().navigateTo(url + "/login");
    }

    public MaruiLoginPage openMaruiInvestorLoginPage(boolean performRetry) {
        return RetryOnExceptions.retryFunction(
            ()-> {navigateToMaruiInvestorSite(); return new MaruiLoginPage(getBrowser());},
            ()-> {getBrowser().refreshPage(); return null;}, performRetry);
    }

    public MaruiLoginPage investorSiteMaruiLogin(String email, String password, boolean retry) {
        startTestLevel("Marui Investor Login " + email + " " + password);
        MaruiLoginPage maruiLoginPage = openMaruiInvestorLoginPage(retry).login(email, password, retry);
        endTestLevel();
        return maruiLoginPage;
    }

    public void investorSiteMaruiLogout(boolean retry) {
        startTestLevel("Marui Investor Logout");
        MaruiDashboard maruiDashboard = new MaruiDashboard(getBrowser());
        maruiDashboard.performLogout(retry).waitUntilSideMenuOpportunitiesBecomeVisible(retry);
        endTestLevel();
    }

    public SonyDashboard loginSonyInvestorPage(String bankBranchCode, String bankAccountNumber) {
        String url = SingleSignOnSony.getUrl(bankBranchCode, bankAccountNumber);
        getBrowser().navigateTo(url);
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        return new SonyDashboard(getBrowser());
    }

    public SonyDashboard loginSonyInvestorPage(String bankBranchCode, String bankAccountNumber, boolean retry) {
        return RetryOnExceptions.retryFunction(
            () -> {loginSonyInvestorPage(bankBranchCode, bankAccountNumber); return new SonyDashboard(getBrowser());},
            () -> {getBrowser().refreshPage(); return null; },
            retry);
    }

    public void navigateSonyInvestorDashboardPagesAndLogout(boolean retry) {
        startTestLevel("navigate tabs, and then perform logout");
        SonyDashboard sonyDashboard = retryFunctionWithRefreshPage(()-> new SonyDashboard(getBrowser()), retry);
        sonyDashboard.switchToPortfolioTab();
        sonyDashboard.switchToDocumentsTab();
        sonyDashboard.switchToFaqTab();
        sonyDashboard.switchToOpportunitiesTab();
        sonyDashboard.performLogout();
        endTestLevel();
    }

    protected void startSingleSignOnWithPost(SonyInvestorDetails investorDetails) {
        String baseUrl = Users.getProperty(UsersProperty.sonyInvestorSiteBaseUrl);
        getBrowser().navigateTo(baseUrl);
        String script = SingleSignOnSony.getJavascriptCodeForSso(investorDetails);
        getBrowser().executeScript(script);
        getBrowser().waitForPageStable();

        ExtendedBy connectionInterruptedText = new ExtendedBy("Connection Interrupted Text", By.xpath("//*[text()[contains(.,'Your connection was interrupted')]]"));
        if (getBrowser().isElementVisibleQuick(connectionInterruptedText, 5)) {
            warning("connection interrupted");
            getBrowser().refreshPage();
        }
    }

    /**
     * This method converts csv data to JpInvestorDetails object. It returns the one with investorIndex
     * specified. getter shall be used to get value in each parameterized test. investorIndex is the
     * parameter.
     */
    public JpInvestorDetails getJpInvestorDetailsWithIndexFromCsvFile(String investorsCsvFile, String investorIndex) {
        startTestLevel("Get Investor Details");
        Path path = Paths.get(investorsCsvFile);
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(JpInvestorDetails.class).withHeader();
        JpInvestorDetails jpInvestorDetails = null;
        try (MappingIterator<JpInvestorDetails> objectMappingIterator =
                     csvMapper.readerFor(JpInvestorDetails.class).with(csvSchema).readValues(path.toFile())) {
            while (objectMappingIterator.hasNext()) {
                jpInvestorDetails = objectMappingIterator.next();
                if (jpInvestorDetails.getIndex().equalsIgnoreCase(investorIndex)) {
                    info("found investorDetails with index " + investorIndex);
                    break;
                }
            }
        } catch (IOException ioException) {
            error("exception in reading file: " + ioException);
        }
        if (jpInvestorDetails == null) {
            errorAndStop("not found investorDetails with index " + investorIndex, false);
        }
        endTestLevel();
        return jpInvestorDetails;
    }

    /**
     * This method converts csv data to JpInvestorDetails object. It returns the list JpInvestorDetails object.
     */
    public List<JpInvestorDetails> getJpInvestorDetailsWithIndexFromCsvFile(String investorsCsvFile) {
        Path path = Paths.get(investorsCsvFile);
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(JpInvestorDetails.class).withHeader();
        List<JpInvestorDetails> jpInvestorDetailsList = new ArrayList<>();
        try (MappingIterator<JpInvestorDetails> objectMappingIterator =
                     csvMapper.readerFor(JpInvestorDetails.class).with(csvSchema).readValues(path.toFile())) {
            while (objectMappingIterator.hasNext()) {
                jpInvestorDetailsList.add(objectMappingIterator.next());
            }
        } catch (IOException ioException) {
            error("exception in reading file: " + ioException);
        }
        if (jpInvestorDetailsList.isEmpty()) {
            errorAndStop("no investorDetails", false);
        }
        return jpInvestorDetailsList;
    }
}
