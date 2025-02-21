package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.SynapseTCAgreementPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountPopUps.SecuritizeIdAccountTypePopUp;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountPopUps.SecuritizeIdAddEntityAccountPopUp;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket.TradingSecondaryMarketsDashboardAssetsCatalog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.info;


public class SecuritizeIdDashboard extends AbstractPage<SecuritizeIdDashboard> {

    private static final ExtendedBy verificationState = new ExtendedBy("Verification state", By.xpath("//span[contains(@class, 'user-menu_menu__status')]"));
    //KYC
    private static final ExtendedBy sidHomeLink = new ExtendedBy("Securitize Id - My Account - Left side bar - Link home icon ", By.id("link-home"));
    public static final ExtendedBy completeInvestorDetailsButton = new ExtendedBy("Securitize Id - My account - Complete investor details button", By.id("complete-details-card"));
    //Left side menu
    private static final ExtendedBy sidePrimaryOfferings = new ExtendedBy("Primary Offering side Menu", By.id("home-page-nav-market"));
    private static final ExtendedBy sideTrading = new ExtendedBy("Trading side Menu", By.id("home-page-nav-secondary-market"));
    private static final ExtendedBy sideWallets = new ExtendedBy("Wallets side Menu", By.id("home-page-nav-wallets"));
    //Top Right user menu data
    private static final ExtendedBy userMenuData = new ExtendedBy("Top right User menu data", By.xpath("//div[@class = 'sec-id-user-menu_data']/div"));
    //Top Right menu
    private static final ExtendedBy userMenuButton = new ExtendedBy("Top right User menu button", By.xpath("//*[contains (@class, 'user-menu_avatar')]|//button[contains (@class, 'ìcon-profile')]"));
    private static final ExtendedBy settingsSubMenu = new ExtendedBy("Top right Settings submenu button", By.xpath("//span[text() = 'Settings']"));
    private static final ExtendedBy profileButton = new ExtendedBy("Profile button inside user menu", By.xpath("//span[text()='Account']"));
    private static final ExtendedBy authorizedAccountButton = new ExtendedBy("Authorized account button inside user menu", By.xpath("//button[@class = 'sec-id-user-menu_menu__item user-investor-item h-100 dropdown-item']"));
    private static final String accountItemButtonXpath = "//span[@class='text-truncate' and text() = '%s']";

    private static final ExtendedBy switchAccountButton = new ExtendedBy("Switch account button inside user menu", By.xpath("//div[text() = 'Switch Account']"));
    private static final ExtendedBy createAccountButton = new ExtendedBy("Create account button inside user menu", By.xpath("//div[text() = 'Create Account']"));


    private static final ExtendedBy addEntityButton = new ExtendedBy("Add entity button inside user menu", By.xpath("//*[text() = 'Create Entity Account'] | //*[text() = 'Add entity'] "));

    private static final ExtendedBy logoutButton = new ExtendedBy("Logout button inside user menu", By.xpath("//span[text() = 'Log out']/.."));
    //2FA
    public static final ExtendedBy twoFaLater = new ExtendedBy("Securitize Id - 2FA pop up - Do it Later button", By.id("twofa-later"));
    //Wallets
    private static final ExtendedBy walletDetailsLabel = new ExtendedBy("Wallet details label", By.xpath("//form[@class = 'editable-wallet-title']//span"));
    private static final ExtendedBy cashAccountCard = new ExtendedBy("Cash Account card", By.className("caccount-wrapper"));
    private static final ExtendedBy createAnAccountButton = new ExtendedBy("Create an Account button", By.id("btn-create-cash-account"));
    private static final ExtendedBy cashAccountBalance = new ExtendedBy(" Cash Account Balance", By.id("cash-account-balance-desktop"));
    private static final ExtendedBy manageFundsButton = new ExtendedBy("Manage Funds button", By.id("btn-cash-account-add-funds"));
    private static final ExtendedBy tokenButton = new ExtendedBy("Token button", By.xpath("//*[contains(text(), 'AUT')]"));
    private static final ExtendedBy pageTitle = new ExtendedBy("Page title", By.className("profile-title"));
    private static final ExtendedBy tradingSoonAvailableText = new ExtendedBy("Trading soon available", By.xpath("//*[contains(text(),'Trading soon available')]"));


    public SecuritizeIdDashboard(Browser browser) {
        super(browser, userMenuButton);
    }

    public SecuritizeIdDashboard clickHomeLink() {
        browser.click(sidHomeLink, false);
        return this;
    }

    public void primaryOfferingClick() {
        browser.click(sidePrimaryOfferings);
    }

    public void clickTrading() {
        browser.click(sideTrading, false);
    }

    public TradingSecondaryMarketsDashboardAssetsCatalog clickTradingAssetsCatalog() {
        clickTrading();
        return new TradingSecondaryMarketsDashboardAssetsCatalog(browser);
    }

    public SecuritizeIdProfile clickAccount() {
        openUserMenu();
        browser.click(profileButton, false);
        return new SecuritizeIdProfile(browser);
    }

    public SecuritizeIdPortfolio clickAuthorizedAccount() {
        openUserMenu();
        browser.click(authorizedAccountButton, false);
        return new SecuritizeIdPortfolio(browser);
    }

    public SecuritizeIdPortfolio clickSwitchAccountItem(String accountName) {
        ExtendedBy accountItemButton = new ExtendedBy("Account item button inside switch account options in user menu", By.xpath(String.format(accountItemButtonXpath, accountName)));
        browser.click(accountItemButton, false);
        return new SecuritizeIdPortfolio(browser);
    }

    public SecuritizeIdDashboard clickSwitchAccountButton() {
        openUserMenu();
        browser.click(switchAccountButton, false);
        return this;
    }


    public SecuritizeIdAccountTypePopUp clickCreateAccountButton() {
        openUserMenu();
        browser.click(createAccountButton, false);
        return new SecuritizeIdAccountTypePopUp(browser);
    }

    public SecuritizeIdAddEntityAccountPopUp clickAddEntity() {
        openUserMenu();
        browser.click(addEntityButton, false);
        return new SecuritizeIdAddEntityAccountPopUp(browser);
    }

    public void clickCompleteYourDetails() {
        browser.click(completeInvestorDetailsButton, false);
    }

    public SecuritizeIdDashboard clickSkipTwoFactor() {
        browser.waitForElementVisibility(twoFaLater);
        browser.click(twoFaLater);
        return this;
    }

    public boolean isTwoFactorModalVisible() {
        browser.waitForPageStable();
        return browser.findElement(twoFaLater).isDisplayed();
    }

    public SecuritizeIdAddWallet walletMenuClick() {
        browser.click(sideWallets, false);
        return new SecuritizeIdAddWallet(browser);
    }

    // TODO we should model a "TopRightUserMenu" class to return in this action.
    public void openUserMenu() {
        browser.click(userMenuButton, false);
    }

    public void openSettingsSubMenu() {
        browser.click(settingsSubMenu, false);
    }

    public void clickLogout() {
        browser.click(logoutButton, false);
    }

    public SecuritizeIdSettings goToSettingPage() {
        openUserMenu();
        openSettingsSubMenu();
        return new SecuritizeIdSettings(browser);
    }

    public void performLogout() {
        openUserMenu();
        clickLogout();
        new SecuritizeIdInvestorLoginScreen(browser);
    }

    public void performLogoutIncludingClearCookies() {
        openUserMenu();
        clickLogout();
        browser.clearAllCookies();
        new SecuritizeIdInvestorLoginScreen(browser);
    }

    public String getWalletDetailsLabel() {
        browser.waitForElementVisibility(walletDetailsLabel, 60);
        return browser.getElementText(walletDetailsLabel);
    }

    public String getUserMenuData() {
        return browser.getElementText(userMenuData);
    }

    public String getUserVerificationState() {
        // if needed - open the user menu so the status is visible for video recording
        if (!browser.isElementVisibleQuick(verificationState)) {
            browser.click(userMenuButton, false);
        }
        return browser.getElementText(verificationState);
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
                new SecuritizeIdDashboard(browser); // waits for page to finish loading
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

    public WebElement getActiveCreateAnAccountButton() {
        browser.waitForElementVisibility(cashAccountCard);

        return browser.findElements(createAnAccountButton).stream()
                .filter(WebElement::isDisplayed)
                .findFirst().orElse(null);
    }

    public SynapseTCAgreementPage clickCreateAnAccountButton() {
        browser.click(getActiveCreateAnAccountButton(), createAnAccountButton.getDescription(), false);
        return new SynapseTCAgreementPage(browser);
    }

    public String getCreateAccountButtonText() {
        return getActiveCreateAnAccountButton().getText().trim();
    }

    public void refreshPage() {
        browser.waitForPageStable();
        browser.refreshPage();
    }

    public WebElement getActiveCashAccountBalanceElement() {
        browser.waitForElementVisibility(cashAccountCard);

        return browser.findElements(cashAccountBalance).stream()
                .filter(WebElement::isDisplayed)
                .findFirst().orElse(null);
    }

    public String getCashAccountBalance() {
        return getActiveCashAccountBalanceElement().getText();
    }

    public WebElement getActiveAddFundsButton() {
        browser.waitForElementVisibility(cashAccountCard);

        return browser.findElements(manageFundsButton).stream()
                .filter(WebElement::isDisplayed)
                .findFirst().orElse(null);
    }

    public SecuritizeIdCashAccountPage clickManageFundsButton() {
        browser.click(getActiveAddFundsButton(), manageFundsButton.getDescription(), false);
        return new SecuritizeIdCashAccountPage(browser);
    }

    public String getAddFundsButtonText() {
        return getActiveAddFundsButton().getText();
    }

    public SecuritizeIdCashAccountPage clickCashAccountCard() {
        //Used findElement because browser.click(cashAccountCard) was throwing a timeOut exception
        browser.findElement(cashAccountCard).click();
        return new SecuritizeIdCashAccountPage(browser);
    }

    public void goToAssetDetails(String tokenName) {
        browser.click(tokenButton);
        browser.waitForPageStable();
    }

    public void goToPortfolioPage() {
        browser.findElement(new ExtendedBy("Portfolio", By.xpath("//*[contains(text(), 'Portfolio')]"))).click();
        browser.waitForPageStable();
    }

    public boolean isPageLoaded() {
        browser.waitForPageStable();
        return browser.findElement(pageTitle).isDisplayed();

    }

    public boolean isTradingSoonAvailableTextDisplayed() {
        return browser.waitForTextInElement(tradingSoonAvailableText, "Trading soon available");
    }

}