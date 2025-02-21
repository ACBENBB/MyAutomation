package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.info;

public class CpControlBook extends AbstractPage {
    private static final ExtendedBy updateControlBookButton = new ExtendedBy("Update Control Book button", By.xpath("//span[@data-id='control-book-update-form-button']"));
    private static final ExtendedBy authorizerInput = new ExtendedBy("Authorizer input in update CB popup", By.xpath("//input[@name='authoriser']"));
    private static final ExtendedBy newAuthorizedSecuritiesInput = new ExtendedBy("New Authorized Securities input in update CB popup", By.xpath("//input[@name='authorizedSecurities']"));
    private static final ExtendedBy totalNumberOfAuthorizedSecurities = new ExtendedBy("Total number of Authorized Securities", By.xpath("//p[contains(text(), 'Total number of Authorized Securities')]/following-sibling::*"));
    private static final ExtendedBy description = new ExtendedBy("Description in popup", By.xpath("//input[@name='description']"));
    private static final ExtendedBy SubmitButton = new ExtendedBy("Submit button", By.xpath("//button[@type='submit']"));
    private static final ExtendedBy totalNumberOfIssuedSecurities = new ExtendedBy("Total number of Issued Securities", By.xpath("//p[contains(text(), 'Total Number of Issued Securities')]/following-sibling::*"));
    private static final ExtendedBy issuedToBlockchain = new ExtendedBy("Total number of Issued to blockchain", By.xpath("//p[contains(text(), 'Issued to Blockchain')]/following-sibling::*"));
    private static final ExtendedBy issuedToTBESecurities = new ExtendedBy("Total number of Issued to TBE", By.xpath("//p[contains(text(), 'Issued to TBE securities')]/following-sibling::*"));
    private int currentTotalNumberOfIssuedSecurities;
    private int currentTotalNumberAuthorizedSecurities;
    private int newAuthorizedSecurities;
    private int totalAuthorizedSecurities;
    private int  totalIssuedBeforeIssuance;
    private int issuedToBlockchainBeforeIssuance;
    private int issuedToTBEBeforeIssuance;

    public CpControlBook(Browser browser) {
        super(browser, updateControlBookButton);
        currentTotalNumberOfIssuedSecurities = getTotalNumberOfIssuedSecurities();
        currentTotalNumberAuthorizedSecurities = getCurrentAuthorizedSecurities();
    }

    public void insertNewAuthorizedSecurities(int numberOfTokens) {
        newAuthorizedSecurities = setNewAuthorizedSecurities(numberOfTokens);
        if (getCurrentAuthorizedSecurities() != newAuthorizedSecurities) {
            browser.click(updateControlBookButton);
            browser.waitForElementVisibility(authorizerInput);
            browser.typeTextElementCtrlA(authorizerInput, "CB AutoTest");
            browser.typeTextElementCtrlA(newAuthorizedSecuritiesInput, String.valueOf(newAuthorizedSecurities));
            browser.typeTextElementCtrlA(description, "Auto description");
            browser.clickAndWaitForElementToVanish(SubmitButton);
            browser.refreshPage();
            browser.waitForElementVisibility(updateControlBookButton);
            currentTotalNumberAuthorizedSecurities = getCurrentAuthorizedSecurities();
        }
    }

    public int getNumberOfIssuedBlockchainBeforeUpdate() {
        return Integer.parseInt(browser.getElementText(issuedToBlockchain));
    }

    public int getNumberOfIssuedTBEBeforeUpdate() {
        return Integer.parseInt(browser.getElementText(issuedToTBESecurities));
    }


    public int getCurrentAuthorizedSecurities() {
        currentTotalNumberAuthorizedSecurities = Integer.parseInt(browser.getElementText(totalNumberOfAuthorizedSecurities).replaceAll(",", ""));
        return currentTotalNumberAuthorizedSecurities;
    }

    public int setNewAuthorizedSecurities(int numberOfTokens) {
        int expectedDelta = numberOfTokens * 2;
        int delta;
        if (currentTotalNumberAuthorizedSecurities == currentTotalNumberOfIssuedSecurities) {
            newAuthorizedSecurities = currentTotalNumberAuthorizedSecurities + expectedDelta;
            return newAuthorizedSecurities;
        } else if (currentTotalNumberAuthorizedSecurities > currentTotalNumberOfIssuedSecurities) {
            delta = (currentTotalNumberAuthorizedSecurities - currentTotalNumberOfIssuedSecurities) - expectedDelta;
            newAuthorizedSecurities = currentTotalNumberAuthorizedSecurities - delta;
            return newAuthorizedSecurities;
        } else {
            delta = currentTotalNumberOfIssuedSecurities - currentTotalNumberAuthorizedSecurities;
            newAuthorizedSecurities = currentTotalNumberAuthorizedSecurities + delta + expectedDelta;
            return newAuthorizedSecurities;
        }
    }

    public int getTotalNumberOfIssuedSecurities() {
        currentTotalNumberOfIssuedSecurities = Integer.parseInt(browser.getElementText(totalNumberOfIssuedSecurities));
        return currentTotalNumberOfIssuedSecurities;
    }

    public int waitForTotalNumberOfIssuedToUpdate(int expected) {
        info("Actual total issued token now is " + browser.getElementText(totalNumberOfIssuedSecurities) + " And should be to " + expected);
        browser.waitForTextToBePresentInElementWithReloadPage(totalNumberOfIssuedSecurities, String.valueOf(expected), 600, 30, "Total number of issued securities");
        return expected;
    }

    public boolean isCBtoUpdateAfterIssuance(int issuedToken) {
        boolean isSumCorrect = false;

        totalIssuedBeforeIssuance = Integer.parseInt(browser.getElementText(totalNumberOfIssuedSecurities));
        totalAuthorizedSecurities = Integer.parseInt(browser.getElementText(totalNumberOfAuthorizedSecurities));

        if (totalIssuedBeforeIssuance != totalAuthorizedSecurities) {
            // wait all field will be updated
            totalIssuedBeforeIssuance = waitForTotalNumberOfIssuedToUpdate(issuedToken + issuedToken + totalIssuedBeforeIssuance);
        }
        issuedToBlockchainBeforeIssuance = getNumberOfIssuedBlockchainBeforeUpdate();
        issuedToTBEBeforeIssuance = getNumberOfIssuedTBEBeforeUpdate();
        // check sum of total issued: 'Blockchain + TBE = Total issued'
        if (totalIssuedBeforeIssuance == issuedToTBEBeforeIssuance + issuedToBlockchainBeforeIssuance) {
            isSumCorrect = true;
        } else {
            info("Total issued token is " + totalIssuedBeforeIssuance + " and it should be " + (issuedToBlockchainBeforeIssuance + issuedToTBEBeforeIssuance) + ": " + totalIssuedBeforeIssuance + " (Issued to blockchain) + " + issuedToTBEBeforeIssuance + " (Issued to TBE)");
        }
        return isSumCorrect;
    }
}