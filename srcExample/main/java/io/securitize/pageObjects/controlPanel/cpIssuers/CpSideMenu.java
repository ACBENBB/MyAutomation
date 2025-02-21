package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.controlPanel.CpAuditLog;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.DistributionsListPage;
import io.securitize.pageObjects.controlPanel.cpMarkets.CpAccreditation;
import io.securitize.pageObjects.controlPanel.cpMarkets.CpMarkets;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static io.securitize.infra.reporting.MultiReporter.error;
import static io.securitize.infra.reporting.MultiReporter.info;

public class CpSideMenu extends AbstractPage {

    private static final ExtendedBy signInButton = new ExtendedBy("Sign in button", By.xpath("//span[text()='Sign in']"));
    private static final ExtendedBy logoutButton = new ExtendedBy("Side Menu Model - logout button", By.xpath("//*[@class = 'ion ion-ios-log-out navbar-icon align-middle']"));
    private static final ExtendedBy sideNavigationContainer = new ExtendedBy("onboarding button", By.id("side-nav-container"));
    private static final ExtendedBy onboardingButton = new ExtendedBy("onboarding button", By.xpath("//*[@id='side-nav-container']//a[contains(@href, 'onboarding')]/div"));
    private static final ExtendedBy fundraiseButton = new ExtendedBy("fundraise button", By.xpath("//*[@id='side-nav-container']//a[contains(@href, 'fundraise')]/div"));
    private static final ExtendedBy holdersButton = new ExtendedBy("holders button", By.xpath("//*[@id='side-nav-container']//a[contains(@href, 'holders')]/div"));
    private static final ExtendedBy signaturesButton = new ExtendedBy("signatures button", By.xpath("//*[@id='side-nav-container']//a[contains(@href, 'signatures')]/div"));
    private static final ExtendedBy distributionsBtn = new ExtendedBy("Distributions button", By.xpath("//*[@id='side-nav-container']//a[contains(@href, 'distributions')]/div"));
    private static final ExtendedBy securitizeIdButton = new ExtendedBy("securitizeId button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Securitize ID')]"));
    private static final ExtendedBy investorsButton = new ExtendedBy("investors button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Investors')]"));
    private static final ExtendedBy issuersButton = new ExtendedBy("investors button", By.xpath("//*[@id='side-nav-container']//a[contains(@href, 'issuer-list')]/div"));
    private static final ExtendedBy marketsButton = new ExtendedBy("markets button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Markets')]"));
    private static final ExtendedBy auditLogButton = new ExtendedBy("audit log button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Audit Log')]"));
    private static final ExtendedBy marketsInvestorButton = new ExtendedBy("markets investor button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Markets')]//../..//div[contains(text(), 'Investors')]"));
    private static final ExtendedBy transferAgentButton = new ExtendedBy("transfer agent button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Transfer Agent')]"));
    private static final ExtendedBy msfButton = new ExtendedBy("master securityholder file button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Transfer Agent')]//../..//div[contains(text(), 'Master Securityholder File (MSF)')]"));
    private static final ExtendedBy securitiesTransactionsButton = new ExtendedBy("securities transactions button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Transfer Agent')]//../..//div[contains(text(), 'Securities Transactions')]"));
    private static final ExtendedBy operationalProceduresButton = new ExtendedBy("operational procedures button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Transfer Agent')]//../..//div[contains(text(), 'Operational Procedures')]"));
    private static final ExtendedBy tokenDropdownButton = new ExtendedBy("Token Selector Button", By.xpath("//div[@class='dropdown btn-group b-dropdown token-selector-dropdown']//button[@class='btn dropdown-toggle btn-link dropdown-toggle-no-caret']"));
    private static final ExtendedBy issuerConfigurationVariablesButton = new ExtendedBy("Issuer Configuration Variables button", By.xpath("//*[@id='side-nav-container']//div[contains(text(), 'Issuer Configuration Variables')]"));
    private static final ExtendedBy advancedButton = new ExtendedBy("Advanced button", By.xpath("//*[@id='side-nav-container']//div[contains(text(), 'Advanced')]"));
    private static final ExtendedBy marketsAccreditationBtn = new ExtendedBy("Markets Accreditation Button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Accreditation')]"));
    private static final ExtendedBy controlBookButton = new ExtendedBy("control book button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Transfer Agent')]//../..//div[contains(text(), 'Control Book')]"));

    private static final ExtendedBy marketsOverviewButton = new ExtendedBy("markets overview button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Markets')]//../..//div[contains(text(), 'Overview')]"));
    private static final ExtendedBy configurationButton = new ExtendedBy("configuration button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Configuration')]"));
    private static final ExtendedBy configurationFundraiseButton = new ExtendedBy("configuration fundraise button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Configuration')]//../..//div[contains(text(), 'Fundraise')]"));
    private static final ExtendedBy affiliateManagementButton = new ExtendedBy("affiliate management button", By.xpath("//*[@id='side-nav-container']//a//div[contains(text(), 'Transfer Agent')]//../..//div[contains(text(), 'Affiliate Management')]"));

    public CpSideMenu(Browser browser) {
        super(browser, sideNavigationContainer);
    }

    public void logoutFromCp() {
        browser.click(logoutButton);
        if (!browser.isElementVisible(signInButton)) {
            browser.refreshPage();
        }
    }

    public void navigateToPageInSideMenu(String pageName) {
        pageName = pageName.toLowerCase();
        String pageTitle = browser.getPageTitle().toLowerCase();
        if (!pageTitle.contains(pageName)) {
            switch (pageName) {
                case "onboarding":
                    clickOnBoarding();
                    break;
                case "operational procedures":
                    clickOperationalProcedures();
                    break;
                case "holders":
                    clickHolders();
                    break;
                case "control book":
                    clickControlBook();
                    break;
                case "signatures":
                    clickSignatures();
                    break;
                case "fundraise":
                    clickFundraise();
                    break;
                case "distribution":
                    clickDistributions();
                    break;
                case "securities transactions":
                    clickSecuritiesTransactions();
                    break;
                case "msf":
                    clickMasterSecuriryholderFile();
                    break;
                case "configuration variables":
                    clickIssuerConfigurationVariables();
                    break;
                default:
                    error("No page title detected");
                    break;
            }
            browser.waitForPageStable(Duration.ofSeconds(5));
        }

        String finalPageName = pageName;
        Browser.waitForExpressionToEqual(q -> browser.getPageTitle().toLowerCase().contains(finalPageName),
                null,
                true,
                "wait for page title to contain: " + finalPageName,
                120,
                5000);
    }

    public CpOnBoarding clickOnBoarding() {
        info("switching to the 'onboarding' window");
        browser.scrollIntoElement(onboardingButton); // otherwise in some cases the button isn't visible and can't be clicked
        browser.click(onboardingButton);
        return new CpOnBoarding(browser);
    }

    public CpSignatures clickSignatures() {
        info("switching to the 'signatures' window");
        browser.scrollIntoElement(signaturesButton);
        browser.click(signaturesButton);
        return new CpSignatures(browser);
    }

    public DistributionsListPage clickDistributions() {
        info("switching to the 'Distributions' window");
        browser.click(distributionsBtn);
        return new DistributionsListPage(browser);
    }

    public CpSideMenu clickSecuritizeId() {
        info("Opening the'SecuritizeId' sub-menu");
        browser.click(securitizeIdButton);
        return this;
    }

    public CpAuditLog clickAuditLog() {
        info("switching to the 'Audit log' window");
        browser.click(auditLogButton);
        return new CpAuditLog(browser);
    }

    public CpInvestorsList clickInvestors() {
        info("switching to the 'investors' window");
        browser.click(investorsButton);
        return new CpInvestorsList(browser);
    }

    public CpSelectIssuer clickIssuers() {
        info("switching to the 'issuers' window");
        browser.click(issuersButton);
        return new CpSelectIssuer(browser);
    }

    public CpMarketsOverview clickMarketsOverview() {
        info("switching to the to 'Markets - Overview' screen");
        browser.scrollIntoElement(marketsButton);
        browser.click(marketsButton);
        browser.scrollIntoElement(marketsOverviewButton);
        browser.click(marketsOverviewButton);
        return new CpMarketsOverview(browser);
    }

    public CpMarkets clickMarketsInvestors() {
        info("switching to the 'markets-investor' window");
        browser.click(marketsButton);
        browser.click(marketsInvestorButton);
        return new CpMarkets(browser);
    }

    public CpAccreditation clickMarketsAccreditation() {
        info("switching to the to 'Markets - Accreditation' screen");
        browser.click(marketsButton);
        browser.click(marketsAccreditationBtn);
        return new CpAccreditation(browser);
    }

    public CpMasterSecurityholderFile clickMasterSecuriryholderFile() {
        info("switching to the to 'Transfer Agent - MSF' screen");
        browser.click(transferAgentButton);
        browser.click(msfButton);
        return new CpMasterSecurityholderFile(browser);
    }

    public CpSecuritiesTransactions clickSecuritiesTransactions() {
        info("switching to the to 'Transfer Agent - Securities Transactions' screen");
        browser.click(transferAgentButton);
        browser.click(securitiesTransactionsButton);
        return new CpSecuritiesTransactions(browser);
    }

    public void clickOperationalProcedures() {
        if (!browser.getPageTitle().equals("Operational Procedures")) {
            info("switching to the to 'Transfer Agent - Operational Procedures' screen");
            if (browser.isElementVisible(operationalProceduresButton)) {
                browser.click(operationalProceduresButton);
            } else {
                browser.scrollIntoElement(transferAgentButton);
                browser.click(transferAgentButton);
                browser.click(operationalProceduresButton);
            }
        }
    }

    public CpFundraise clickFundraise() {
        info("switching to the 'fundraise' window");
        browser.click(fundraiseButton);
        return new CpFundraise(browser);
    }

    public CpHolders clickHolders() {
        info("switching to the 'holders' window");
        browser.click(holdersButton);
        return new CpHolders(browser);
    }

    public void openTokenDropdown() {
        WebElement tokenDropdown = browser.findElement(tokenDropdownButton);
        browser.click(tokenDropdown, "opening token dropdown", ExecuteBy.JAVASCRIPT, false);
    }

    public void clickTokenName(String tokenName) {
        ExtendedBy tokenSelector = new ExtendedBy("Token Selector", By.xpath("//span[contains(text(), '" + tokenName + "')]"));
        browser.waitForElementVisibility(tokenSelector);
        browser.click(tokenSelector);
    }

    public void clickAdvanced() {
        info("switching to the 'Advanced' window");
        browser.scrollWaitClick(advancedButton);
    }

    public void clickIssuerConfigurationVariables() {
        info("switching to the 'Issuer Configuration Variables' window");
        clickAdvanced();
        browser.scrollToElement(browser.findElement(issuerConfigurationVariablesButton), true);
        browser.click(issuerConfigurationVariablesButton);
        browser.waitForPageStable();
    }

    public CpControlBook clickControlBook() {
        info("switching to the to 'Transfer Agent - Control Book' screen");
        browser.scrollWaitClick(transferAgentButton);
        browser.scrollWaitClick(controlBookButton);
        return new CpControlBook(browser);
    }

    public CpConfigurationFundraise clickConfigurationFundraise() {
        info("switching to the to 'Configuration - Fundraise' screen");
        browser.click(configurationButton);
        browser.click(configurationFundraiseButton);
        return new CpConfigurationFundraise(browser);
    }

    public CpAffiliateManagement clickAffiliateManagement() {
        info("switching to the to 'Transfer Agent - Affiliate Management' screen");
        browser.click(transferAgentButton);
        browser.click(affiliateManagementButton);
        return new CpAffiliateManagement(browser);
    }

}