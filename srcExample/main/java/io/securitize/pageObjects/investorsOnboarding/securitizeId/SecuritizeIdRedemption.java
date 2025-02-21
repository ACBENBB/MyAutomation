package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

import java.util.List;


public class SecuritizeIdRedemption extends AbstractPage {

    //NAV BAR AT THE LEFT ELEMENTS
    private static final ExtendedBy securitizeIdLogo = new ExtendedBy("Securitize Id Logo", By.cssSelector("img[alt='Securitize iD']"));

    private static final ExtendedBy holdingsButton = new ExtendedBy("Holdings Button", By.id("home-page-nav-holdings"));

    private static final ExtendedBy primaryOfferingsButton = new ExtendedBy("Primary Offerings Button", By.id("home-page-nav-market"));

    private static final ExtendedBy tradingButton = new ExtendedBy("Trading Button", By.id("home-page-nav-secondary-market"));

    private static final ExtendedBy walletsButton = new ExtendedBy("Wallets Button", By.id("home-page-nav-wallets"));

    //NAV AREA AT THE TOP

    private static final ExtendedBy backNavigationButton = new ExtendedBy("Back Navigation Button", By.xpath("//*[contains(text(), 'Go back to Portfolio')]"));

    private static final ExtendedBy userMenuDataText = new ExtendedBy("User Menu Data Text", By.className("user-menu_data"));

    private static final ExtendedBy userMenuButton = new ExtendedBy("User Menu Button", By.xpath("//button[contains(@class, 'user-menu_avatar')]"));

    //REDEMPTION PAGE ELEMENTS
    private static final ExtendedBy redemptionTitleText = new ExtendedBy("Redemption Title Text", By.xpath("//span[contains(@class, 'redemption-title-text') and contains(@class, 'wh-font-medium')]"));

    private static final ExtendedBy redemptionStatusChip = new ExtendedBy("Redemption Status Chip", By.xpath("//div[contains(@class, 'redemption-status')]/div/span"));

    private static final ExtendedBy redemptionDescriptionText1 = new ExtendedBy("Redemption Description Text1", By.xpath("(//span[contains(@class, 'wh-text-h4') and contains(@class, 'wh-text-none')])[1]"));

    private static final ExtendedBy redemptionDescriptionText2 = new ExtendedBy("Redemption Description Text2", By.xpath("(//span[contains(@class, 'wh-text-h4') and contains(@class, 'wh-text-none')])[2]"));

    private static final ExtendedBy endDateIcon = new ExtendedBy("End Date Icon", By.xpath("(//div[@class='redemption-details-item']/i)[1]"));

    private static final ExtendedBy endDateTitle = new ExtendedBy("End Date Title", By.xpath("(//div[@class='redemption-details-item-content']//span[1])[1]"));

    private static final ExtendedBy endDateValue = new ExtendedBy("End Date Value", By.xpath("(//div[@class='redemption-details-item-content']//span[2])[1]"));

    private static final ExtendedBy payoutPerTokenIcon = new ExtendedBy("Payout Per Token Icon", By.xpath("(//div[@class='redemption-details-item']/i)[2]"));

    private static final ExtendedBy payoutPerTokenTitle = new ExtendedBy("Payout Per Token Title", By.xpath("(//div[@class='redemption-details-item-content']//span[1])[2]"));

    private static final ExtendedBy payoutPerTokenValue = new ExtendedBy("Payout Per Token Value", By.xpath("(//div[@class='redemption-details-item-content']//span[2])[2]"));

    private static final ExtendedBy redemptionCapIcon = new ExtendedBy("Redemption Cap Icon", By.xpath("(//div[@class='redemption-details-item']/i)[3]"));

    private static final ExtendedBy redemptionCapTitle = new ExtendedBy("Redemption Cap Title", By.xpath("(//div[@class='redemption-details-item-content']//span[1])[3]"));

    private static final ExtendedBy redemptionCapValue = new ExtendedBy("Redemption Cap Value", By.xpath("(//div[@class='redemption-details-item-content']//span[2])[3]"));

    private static final ExtendedBy redemptionAddressTitle = new ExtendedBy("Redemption Address Title", By.xpath("//span[contains(text(), 'Send the tokens you want to redeem to this wallet')]"));

    private static final ExtendedBy redemptionAddressData = new ExtendedBy("Redemption Address Data", By.xpath("//div[@class='redemption-address-section']/button/span/div/span/div[1]"));

    private static final ExtendedBy instructionsInfoText = new ExtendedBy("Redemption Instructions Info Text", By.xpath("//span[contains(text(), 'How to participate in the redemption')]"));

    private static final ExtendedBy redemptionStepOneNumber = new ExtendedBy("Redemption Step One Number", By.xpath("(//div[contains(@class, 'redemption-step')][1]//div[1]/span)"));

    private static final ExtendedBy redemptionStepOneTitle = new ExtendedBy("Redemption Step One Title", By.xpath("//span[contains(text(), 'Connect to your') and contains(text(), ' wallet')]"));

    private static final ExtendedBy redemptionStepOneDescription = new ExtendedBy("Redemption Step One Description", By.xpath("//span[contains(text(), 'In order to withdraw digital assets, you need to make sure you have') and contains(text(), ' wallet that can store your assets.')]"));

    private static final ExtendedBy redemptionStepOneButton = new ExtendedBy("Redemption Step One Button", By.xpath("//span[contains(text(), 'Connect wallet')]"));

    private static final ExtendedBy redemptionStepTwoNumber = new ExtendedBy("Redemption Step Two Number", By.xpath("(//div[contains(@class, 'redemption-step')][2]//div[1]/span)"));

    private static final ExtendedBy redemptionStepTwoTitle = new ExtendedBy("Redemption Step Two Title", By.xpath("//span[contains(text(), 'Open a Cash Account')]"));

    private static final ExtendedBy redemptionStepTwoDescription = new ExtendedBy("Redemption Two One Description", By.xpath("//span[contains(text(), 'You will receive the money into your Cash Account, so make sure you have it opened. From The Cash Balance you can see the payment of your redemption.')]"));

    private static final ExtendedBy redemptionStepTwoButton = new ExtendedBy("Redemption Step Two Button", By.xpath("//span[contains(text(), 'Create an account') or contains(text(), 'Created Account')]"));

    private static final ExtendedBy redemptionStepThreeNumber = new ExtendedBy("Redemption Step Three Number", By.xpath("(//div[contains(@class, 'redemption-step')][3]//div)"));

    private static final ExtendedBy redemptionStepThreeTitle = new ExtendedBy("Redemption Step Three Title", By.xpath("//span[contains(text(), 'Send the tokens from your account to your') and contains(text(), ' wallet')]"));

    private static final ExtendedBy redemptionStepThreeDescription = new ExtendedBy("Redemption Step Three Description", By.xpath("//span[contains(text(), 'Once the tokens are in your wallet, send them to the Redemption Wallet.')]"));

    private static final ExtendedBy redemptionStepThreeValue = new ExtendedBy("Redemption Step Three Value", By.xpath("(//div[contains(@class, 'redemption-step')][3]//div/div/button/span/div/span/div[1])"));

    public SecuritizeIdRedemption(Browser browser) {
        super(browser, redemptionTitleText);
    }

    public ExtendedBy getVisibleSecuritizeIdLogo() {
        return securitizeIdLogo;
    }

    public ExtendedBy getVisibleHoldingsButton() {
        return holdingsButton;
    }

    public ExtendedBy getVisiblePrimaryOfferingsButton() {
        return primaryOfferingsButton;
    }

    public ExtendedBy getVisibleTradingButton() {
        return tradingButton;
    }

    public ExtendedBy getVisibleWalletsButton() {
        return walletsButton;
    }

    public ExtendedBy getVisibleBackNavigationButton() {
        return backNavigationButton;
    }

    public ExtendedBy getVisibleUserMenuDataText() {
        return userMenuDataText;
    }

    public ExtendedBy getVisibleUserMenuButton() {
        return userMenuButton;
    }

    public ExtendedBy getVisibleRedemptionTitleText() {
        return redemptionTitleText;
    }

    public ExtendedBy getVisibleRedemptionStatusChip() {
        return redemptionStatusChip;
    }

    public ExtendedBy getVisibleRedemptionDescriptionText1() {
        return redemptionDescriptionText1;
    }

    public ExtendedBy getVisibleRedemptionDescriptionText2() {
        return redemptionDescriptionText2;
    }

    public ExtendedBy getVisibleEndDateIcon() {
        return endDateIcon;
    }

    public ExtendedBy getVisibleEndDateTitle() {
        return endDateTitle;
    }

    public ExtendedBy getVisibleEndDateValue() {
        return endDateValue;
    }

    public ExtendedBy getVisiblePayoutPerTokenIcon() {
        return payoutPerTokenIcon;
    }

    public ExtendedBy getVisiblePayoutPerTokenTitle() {
        return payoutPerTokenTitle;
    }

    public ExtendedBy getVisiblePayoutPerTokenValue() {
        return payoutPerTokenValue;
    }

    public ExtendedBy getVisibleRedemptionCapIcon() {
        return redemptionCapIcon;
    }

    public ExtendedBy getVisibleRedemptionCapTitle() {
        return redemptionCapTitle;
    }

    public ExtendedBy getVisibleRedemptionCapValue() {
        return redemptionCapValue;
    }

    public ExtendedBy getVisibleRedemptionAddressTitle() {
        return redemptionAddressTitle;
    }

    public ExtendedBy getVisibleRedemptionAddressData() {
        return redemptionAddressData;
    }

    public ExtendedBy getVisibleInstructionsInfoText() {
        return instructionsInfoText;
    }

    public ExtendedBy getVisibleRedemptionStepOneNumber() {
        return redemptionStepOneNumber;
    }

    public ExtendedBy getVisibleRedemptionStepOneTitle() {
        return redemptionStepOneTitle;
    }

    public ExtendedBy getVisibleRedemptionStepOneDescription() {
        return redemptionStepOneDescription;
    }

    public ExtendedBy getVisibleRedemptionStepOneButton() {
        return redemptionStepOneButton;
    }

    public ExtendedBy getVisibleRedemptionStepTwoNumber() {
        return redemptionStepTwoNumber;
    }

    public ExtendedBy getVisibleRedemptionStepTwoTitle() {
        return redemptionStepTwoTitle;
    }

    public ExtendedBy getVisibleRedemptionStepTwoDescription() {
        return redemptionStepTwoDescription;
    }

    public ExtendedBy getVisibleRedemptionStepTwoButton() {
        return redemptionStepTwoButton;
    }

    public ExtendedBy getVisibleRedemptionStepThreeNumber() {
        return redemptionStepThreeNumber;
    }

    public ExtendedBy getVisibleRedemptionStepThreeTitle() {
        return redemptionStepThreeTitle;
    }

    public ExtendedBy getVisibleRedemptionStepThreeDescription() {
        return redemptionStepThreeDescription;
    }

    public ExtendedBy getVisibleRedemptionStepThreeValue() {
        return redemptionStepThreeValue;
    }

    public void checkVisibilityOfAllElements() {
        browser.waitForPageStable();
        final List<ExtendedBy> elem = visibleElements();
        for (ExtendedBy el : elem) {
            try {
                browser.waitForElementVisibility(el);
            } catch (Exception e) {
              browser.scrollDownToPageBottom();
              browser.waitForElementVisibility(el);

            }
        }
    }

    public String getRedemptionEndDate() {
        return browser.getElementText(endDateValue);
    }

    public String getPayoutPerToken() {
        return browser.getElementText(payoutPerTokenValue).replaceAll("\\$", "");
    }

    public String getRedemptionWallet() {
        return browser.getElementText(redemptionAddressData);
    }

    public String getRedemptionStatusChipText() {
        return browser.findElement(redemptionStatusChip).getText();
    }

}