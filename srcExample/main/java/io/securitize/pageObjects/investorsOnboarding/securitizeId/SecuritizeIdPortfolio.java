package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;


public class SecuritizeIdPortfolio extends AbstractPage<SecuritizeIdPortfolio> {

    private static final ExtendedBy portfolioPageTitle = new ExtendedBy("Securitize Id - Portfolio Page Title", By.xpath("//h1[contains(text(), 'Portfolio')]"));
    private static final ExtendedBy portfolioTotalAmountField = new ExtendedBy("Securitize Id - Holdings - Portfolio Total Amount Field", By.id("portfolio-total-amount"));
    private static final ExtendedBy promotionCard = new ExtendedBy("Securitize Id - Holdings - Promotion Card", By.xpath("//div[contains(@class , 'header-card-promotion ')]"));
    private static final ExtendedBy tokenName = new ExtendedBy("Securitize Id - Holdings - token name", By.xpath("//div[contains(@class, 'issuer-investment-card__')  and contains(@class, '-item__title')]//div[text()!='']"));
    private static final ExtendedBy tokenTotalUnits = new ExtendedBy("Securitize Id - Holdings - token total units", By.xpath("(//div[contains(@id, '-total-units-value')])[2]"));
    private static final ExtendedBy tokenTotalValue = new ExtendedBy("Securitize Id - Holdings - token total value", By.xpath("//div[contains(@id, '-holding-value')]"));
    private static final ExtendedBy cashAccountCardBalance = new ExtendedBy("Securitize Id - Holdings - Balance Desktop", By.xpath("(//div[contains(@id, 'cash-account-balance')])[2]"));
    private static final ExtendedBy onBoardingCard = new ExtendedBy("Securitize Id - Holdings - Onboarding Card", By.xpath("//*[@class='on-boarding']"));
    private static final ExtendedBy identityDetailsCard = new ExtendedBy("Securitize Id - Holdings - Identity Details Card", By.xpath("//h6[text() = 'Verify Entity Information']/../p | //h6[text() = 'Verify Your Identity']/../p"));
    private static final ExtendedBy investmentProfileCard = new ExtendedBy("Securitize Id - Holdings - Investment Profile Card", By.xpath("//h6[contains(text(), 'Complete Investment Profile')]/../p[contains(@class,'set-card_data__status set-card_data__status')]"));
    private static final ExtendedBy accreditationCard = new ExtendedBy("Securitize Id - Holdings - Accreditation Card", By.xpath("//h6[contains(text(), 'accredited') or contains(text(), 'Accreditation')]/../p[contains(@class,'set-card_data__status')]"));
    public static final ExtendedBy[] visualIgnoreList = {promotionCard};

    private static final ExtendedBy tokenTotalValueByNameInternalField = new ExtendedBy("Total tokens internal field", By.xpath(".//span[contains(@class, 'total-units')]"));
    private static final ExtendedBy verifyYourself = new ExtendedBy("Verify yourself button", By.xpath("//*[text() = 'verify yourself']"));

    private static final String TOKEN_PENDING_ISSUANCE = "//*[text() = 'Pending issuance']/../../preceding-sibling::div//div[text() = '%s']";
    private static final String TOKEN_TOTAL_VALUE_BY_NAME_TEMPLATE = "//div[contains(@id, '%s-total-units-value')]";
    private static final String TOKEN_TOTAL_HOLDING_VALUE_BY_NAME_TEMPLATE = "//div[contains(@id, '%s-holding-value')]";

    public SecuritizeIdPortfolio(Browser browser) {
        super(browser, portfolioPageTitle);
    }

    public int getNumberOfVisibleTokens() {
        return browser.findElements(tokenName).size();
    }

    public String getTokenNameByIndex(int index) {
        List<WebElement> elements = browser.findElements(tokenName);
        return browser.getElementText(elements.get(index), tokenName.getDescription() + " index: " + index);
    }

    public int getTokenTotalUnitsByTokenIndex(int index) {
        List<WebElement> elements = browser.findElements(tokenTotalUnits);
        String valueAsString = browser.getElementText(elements.get(index), tokenTotalUnits.getDescription() + " index: " + index);
        return Integer.parseInt(valueAsString);
    }

    public int getTokenTotalUnitsByTokenName(String tokenName) {
        // There are 3 possible field that show the total token held. Only one of them is visible according to the current
        // browser screen resolution. We need to find the visible one
        ExtendedBy locator = new ExtendedBy(tokenName + " total units field", By.xpath(String.format(TOKEN_TOTAL_VALUE_BY_NAME_TEMPLATE, tokenName)));
        Optional<WebElement> element = browser.findFirstVisibleElementQuick(locator, 5);
        if (element.isPresent()) {
            List<WebElement> childElements = browser.findElementsInElementQuick(element.get(), tokenTotalValueByNameInternalField, 5);
            String rawValue;
            // sometimes the value hides in an internal span element
            if (childElements.isEmpty()) {
                rawValue = element.get().getText();
            } else {
                rawValue = childElements.get(0).getText();
            }
            return RegexWrapper.stringToInteger(rawValue);
        } else {
            errorAndStop("Can't find total units field for token " + tokenName, true);
            return -1;
        }
    }


    public int getTokenTotalValueByTokenName(String tokenName) {
        // There are 3 possible field that show the total token held. Only one of them is visible according to the current
        // browser screen resolution. We need to find the visible one
        ExtendedBy locator = new ExtendedBy(tokenName + " total value", By.xpath(String.format(TOKEN_TOTAL_HOLDING_VALUE_BY_NAME_TEMPLATE, tokenName)));
        Optional<WebElement> element = browser.findFirstVisibleElementQuick(locator, 5);
        if (element.isPresent()) {
            return RegexWrapper.stringToInteger(element.get().getText());
        } else {
            errorAndStop("Can't find total value field for token " + tokenName, true);
            return -1;
        }
    }


    public double getTokenTotalValueByTokenIndex(int index) {
        List<WebElement> elements = browser.findElements(tokenTotalValue);
        String valueAsString = browser.getElementText(elements.get(index), tokenTotalValue.getDescription() + " index: " + index)
                .replaceAll("\\$", "").replaceAll(",", "");
        return Double.parseDouble(valueAsString);
    }

    public double getPortfolioTotalAmountField() {
        String rawValue = browser.findElement(portfolioTotalAmountField).getText();
        return RegexWrapper.stringToDouble(rawValue);
    }

    public double getCashAccountBalance() {
        return Double.parseDouble(browser.findElement(cashAccountCardBalance).getText()
                .replaceAll("\\$", "").replaceAll(",", ""));
    }

    public void clickAsset() {
        browser.click(tokenName, false);
    }

    public boolean verifyOnboardingCard(String text) {
        return browser.getElementText(onBoardingCard).startsWith(text);
    }

    public boolean isOnBoardingCardPresentOrVisible() {
        browser.waitForPageStable(Duration.ofSeconds(3));
        return browser.isElementPresent(onBoardingCard, 3);
    }

    public String getIdentityDetailsCardText() {
        return browser.getElementText(identityDetailsCard);
    }

    public String getInvestmentProfileCardText() {
        return browser.getElementText(investmentProfileCard);
    }

    public String getAccreditationCardText() {
        return browser.getElementText(accreditationCard);
    }

    public void clickVerifyYourself() {
        browser.click(verifyYourself);
    }

    public boolean isTokenPendingIssuance(String tokenName) {
        ExtendedBy locator = new ExtendedBy(tokenName + " pending issuance", By.xpath(String.format(TOKEN_PENDING_ISSUANCE, tokenName)));
        return browser.isElementVisible(locator);
    }
}