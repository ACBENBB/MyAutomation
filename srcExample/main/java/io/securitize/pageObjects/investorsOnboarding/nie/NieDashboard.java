package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.info;

public class NieDashboard extends AbstractPage {

    private static final ExtendedBy completeYourDetailsButton = new ExtendedBy("Complete your details button", By.id("complete-details-card"));
    private static final ExtendedBy verificationState = new ExtendedBy("Verification state", By.xpath("//div[contains(@class,'user-menu-nie_data')]/div[2]/span"));
    private static final ExtendedBy sideMenuOpportunities = new ExtendedBy("Side menu - opportunities", By.id("opportunities-page-nav"));
    private static final ExtendedBy sideMenuPortfolio = new ExtendedBy("Side menu - portfolio", By.xpath("//a[contains(@href, 'portfolio')]"));
    private static final ExtendedBy sideMenuDocuments = new ExtendedBy("Side menu - portfolio", By.xpath("//a[contains(@href, 'documents')]"));
    private static final ExtendedBy investorEmail = new ExtendedBy("Investor Email label", By.xpath("//div[contains(@class,'user-menu-nie_data')]/div[1]"));
    private static final ExtendedBy userMenuButton = new ExtendedBy("Top right user menu button", By.xpath("//button[contains(@class,'user-menu-nie_avatar')]"));
    private static final ExtendedBy logoutButton = new ExtendedBy("Logout button inside user menu", By.xpath("//i[contains(@class, 'icon-log-out font-24 mr-3')]/.."));
    private static final ExtendedBy goToSecuritizeIDButton = new ExtendedBy("Go to Securitize ID button", By.xpath("//span[text()='Go to Securitize iD']/.."));
    public static final ExtendedBy[] visualIgnoreList = {investorEmail};




    public NieDashboard(Browser browser) {
        super(browser, investorEmail);
    }

    public void clickCompleteYourDetails() {
        browser.click(completeYourDetailsButton, false);
    }

    public String getUserVerificationState() {
        // if needed - open the user menu so the status is visible for video recording
        if (!browser.isElementVisibleQuick(verificationState)) {
            browser.click(userMenuButton, false);
        }
        return browser.getElementText(verificationState);
    }

    public NieDashboardOpportunities switchToOpportunitiesTab() {
        browser.click(sideMenuOpportunities, false);
        return new NieDashboardOpportunities(browser);
    }

    public NieDashboardPortfolio switchToPortfolioTab() {
        browser.click(sideMenuPortfolio, false);
        return new NieDashboardPortfolio(browser);
    }

    public NieDashboardDocuments switchToDocumentsTab() {
        browser.click(sideMenuDocuments, false);
        return new NieDashboardDocuments(browser);
    }

    public void waitForUserVerificationStateToBecome(String... expectedStates) {
        if (expectedStates.length == 0) {
            throw new RuntimeException("This function must have at least one state argument");
        }

        String expectedStatesAsString = String.join(", ", expectedStates);
        info("Waiting for user verification state to become one of: " + expectedStatesAsString);
        List<String> expectedStatesAsList = Arrays.stream(expectedStates).collect(Collectors.toList());

        Function<String, Boolean> internalWaitForStatus = t -> {
            try {
                browser.refreshPage();
                new NieDashboard(browser); // waits for page to finish loading
                info("Checking user verification state...");
                String verificationState = getUserVerificationState();
                return expectedStatesAsList.stream().anyMatch(verificationState.trim()::equalsIgnoreCase);
            } catch (Exception e) {
                return false;
            }
        };
        String description = "UserVerificationStateToBecome=" + expectedStatesAsString;
        Browser.waitForExpressionToEqual(internalWaitForStatus, null, true, description, 60, 5000);
    }

    public void openUserMenu(){
        browser.waitForElementVisibility(sideMenuOpportunities);
        browser.click(userMenuButton, false);
    }

    public void clickLogout(){
        browser.click(logoutButton, false);
    }

    public void clickGoToSecuritizeID(){
        browser.click(goToSecuritizeIDButton, false);
    }

    public void performLogout() {
        openUserMenu();
        clickLogout();
    }

    public void performLogoutIncludingClearCookies() {
        openUserMenu();
        clickLogout();
        browser.clearAllCookies();
    }

    public SecuritizeIdDashboard performGoToSecuritizeID() {
        openUserMenu();
        clickGoToSecuritizeID();
        browser.switchToLatestWindow();
        return new SecuritizeIdDashboard(browser);
    }

    public String getVerificationStateText() {
        return browser.getElementText(verificationState);
    }

    public String getInvestorEmailText() {
        return browser.getElementText(investorEmail);
    }

    public ExtendedBy[] getVisualTestingIgnoreList() {
        return new ExtendedBy[]{ investorEmail };
    }
}

