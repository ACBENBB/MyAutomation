package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.exceptions.StringIsNotAsExpectedException;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;

public class CpOperationalProcedures extends AbstractPage {

    private static final ExtendedBy operationalProceduresScreenTitle = new ExtendedBy("Operational Procedures Screen Title", By.xpath("//h4[contains(text(),'Operational Procedures')]"));
    private static final ExtendedBy procedureSelector = new ExtendedBy("Operational Procedure drop down", By.xpath("//select[@class='custom-select form-group']"));
    private static final ExtendedBy procedureSelectorDropDown = new ExtendedBy("Operational Procedure drop down", By.xpath("//div[@id='chooseProcedureDD']"));
    private static final ExtendedBy lockTokens = new ExtendedBy("Operational Procedure - Lock Tokens", By.xpath("//li[contains(text(),'Lock Tokens')]"));
    private static final ExtendedBy unlockTokens = new ExtendedBy("Operational Procedure - Lock Tokens", By.xpath("//li[contains(text(),'Unlock Tokens')]"));
    private static final ExtendedBy internalTBETransfer = new ExtendedBy("Operational Procedure - Internal TBE Transfer", By.xpath("//li[contains(text(),'Internal Transfer TBE')]"));
    private static final ExtendedBy walletIdField = new ExtendedBy("wallet id field", By.xpath("//input[@id='sourceWallet']"));
    private static final ExtendedBy tokensAmountField = new ExtendedBy("tokens amount field", By.xpath("//input[@id='tbeAmount']"));
    private static final ExtendedBy reasonField = new ExtendedBy("reason field", By.xpath("//input[@id='reason']"));
    private static final ExtendedBy investorIdField = new ExtendedBy("investor id field", By.xpath("//input[@id='investorId']"));
    private static final ExtendedBy senderIdField = new ExtendedBy("sender id field", By.xpath("//input[@id='senderId']"));
    private static final ExtendedBy receiverIdField = new ExtendedBy("receiver id field", By.xpath("//input[@id='receiverId']"));
    private static final ExtendedBy executeButton = new ExtendedBy("execute button", By.xpath("//button[contains(text(),'Execute')]"));
    private static final ExtendedBy confirmExecuteButton = new ExtendedBy("confirm execute button", By.xpath("//button[text()='Execute']"));
    private static final ExtendedBy tokenHeldInDropDown = new ExtendedBy("Operational Procedure - Token held in field", By.xpath("(//div[@role='button'])[2]"));
    private static final ExtendedBy tokenHeldInTBE = new ExtendedBy("Operational Procedure - Token held in field- TBE option", By.xpath("//li[@data-value='TBE']"));
    private static final ExtendedBy tokenHeldInWallet = new ExtendedBy("Operational Procedure - Token held in field - Wallet option", By.xpath("//li[contains(text(), '0x') or contains(text(), 'Wallet')]"));
    private static final ExtendedBy numberOfTotalSecurities = new ExtendedBy("Number of total Securities - Permanent field", By.xpath("(//p)[2]"));
    private static final ExtendedBy tokenAmountLockToken = new ExtendedBy("reason field", By.xpath("//input[@id='tokenAmount']"));
    private static final ExtendedBy alertProcedureMessage = new ExtendedBy("Success procedure message", By.xpath("//div[@role='alert']"));
    private static final ExtendedBy lostShares = new ExtendedBy("Operational Procedure - Lost shares", By.xpath("//li[contains(text(),'Lost shares')]"));
    private static final ExtendedBy sourceWalletField = new ExtendedBy("reason field", By.id("sourceWallet"));
    private static final ExtendedBy destinationWalletField = new ExtendedBy("reason field", By.id("destinationWallet"));


    //  private static final ExtendedBy numberOfSecuritiesField = new ExtendedBy("Number of Securities field", By.id("numberSecurities"));
    private static final ExtendedBy numberOfSecuritiesField = new ExtendedBy("Number of Securities field", By.xpath("//p[contains(text(),'Number of securities')]/following-sibling::*"));


    private static final ExtendedBy startDateOfRedemptionField = new ExtendedBy("Start Date Of RedemptionField field", By.id("date-picker"));
    private static final ExtendedBy destroyTokens = new ExtendedBy("Operational Procedure - Lock Tokens", By.xpath("//li[contains(text(),'Destroy')]"));
    private static final ExtendedBy destroyTBE = new ExtendedBy("Operational Procedure - Lock Tokens", By.xpath("//li[contains(text(),'Destroy TBE')]"));
    private static final ExtendedBy numberSecuritiesInput = new ExtendedBy("Number of Securities", By.xpath("//input[@id='numberSecurities']"));
    private static final ExtendedBy holdTrading = new ExtendedBy("Operational Procedure - Hold Trading", By.xpath("//li[contains(text(),'Hold Trading')]"));
    private static final ExtendedBy documentIdField = new ExtendedBy("document id field", By.xpath("//input[@id='documentId']"));
    private static final ExtendedBy holdTradingExecute = new ExtendedBy("execute button", By.xpath("//button[contains(text(),'Hold trading')]"));
    private static final ExtendedBy contractStatus = new ExtendedBy("Hold Trading - contract status", By.xpath("//div[@id='Opprocedures-frame']//div//p[text()='on-hold' or text()='operational' or text()=' ' or not(text())]"));
    private static final ExtendedBy confirmHoldTradingIssuerName = new ExtendedBy("Hold Trading - confirm execute", By.xpath("//div[@role = 'dialog']//input"));
    private static final ExtendedBy confirmExecuteHT = new ExtendedBy("Hold Trading - confirm execute", By.xpath("//div[@role = 'dialog']//button[text()='Confirm']"));
    private static final ExtendedBy investorProfile = new ExtendedBy("investor Profile - shown after insert external ID", By.xpath("(//p)[4]")); // check


    private static final String dayInDatePicker = "//*[@aria-label='%s']";

    public CpOperationalProcedures(Browser browser) {
        super(browser, operationalProceduresScreenTitle);
    }

    public CpOperationalProcedures selectProcedure(String value) {
        browser.selectElementByVisibleText(procedureSelector, value);
        return this;
    }

    public CpOperationalProcedures chooseLockTokensProcedure(String lockType) {
        browser.click(procedureSelectorDropDown);
        if (lockType.equals("lock")){
            browser.click(lockTokens);
        }
        else if (lockType.equals("unlock")){
            browser.click(unlockTokens);
        }
        return this;
    }

    public CpOperationalProcedures chooseHoldTradingProcedure() {
        browser.click(procedureSelectorDropDown);
        browser.click(holdTrading);
        return this;
    }


    public CpOperationalProcedures addDocumentId(String documentId) {
        browser.typeTextElement(documentIdField, documentId);
        return this;
    }

    public CpOperationalProcedures executeHoldTrading() {
        browser.click(holdTradingExecute);
        return this;
    }

    public void confirmHoldTradingModal(String issuerName) {
        browser.waitForElementClickable(confirmHoldTradingIssuerName);
        browser.typeTextElement(confirmHoldTradingIssuerName, issuerName);
        browser.clickAndWaitForElementToVanish(confirmExecuteHT);
    }

    public CpOperationalProcedures chooseInternalTransferProcedure() {
        browser.click(procedureSelectorDropDown);
        browser.click(internalTBETransfer);
        return this;
    }

    public CpOperationalProcedures addWalletId(String walletId) {
        browser.typeTextElement(walletIdField, walletId);
        return this;
    }

    public CpOperationalProcedures chooseUnlockTokensProcedure() {
        browser.click(procedureSelectorDropDown);
        browser.click(unlockTokens);
        return this;
    }

    public void waitForTokenProcedureShown() {
        browser.waitForElementTextNotEmpty(investorProfile);
    }

    public int getTotalAmountOfSecuritiesCanBeLock() {
        waitForTokenProcedureShown();
        return Integer.parseInt(browser.getElementText(numberOfSecuritiesField));
    }

    public CpOperationalProcedures addExternalInvestorId(String externalInvestorId) {
        browser.typeTextElement(investorIdField, externalInvestorId);
        return this;
    }

    public CpOperationalProcedures chooseTokenHeldIn(String value) {
        browser.waitForPageStable(Duration.ofSeconds(5));
        browser.click(tokenHeldInDropDown);
        if (value.equals("TBE")) {
            browser.click(tokenHeldInTBE);
        } else if (value.equals("wallet")) {
            browser.click(tokenHeldInWallet);
        } else {
            errorAndStop("Couldn't detect token type", true);
        }
        return this;
    }

    public CpOperationalProcedures checkTokensAmountNotEmpty() {
        browser.waitForElementTextNotEmpty(tokensAmountField);
        return this;
    }

    public CpOperationalProcedures typeReason(String reason) {
        browser.typeTextElement(reasonField, reason);
        browser.waitForPageStable();
        return this;
    }

    public CpOperationalProcedures addSenderId(String senderInvestorId) {
        browser.typeTextElement(senderIdField, senderInvestorId);
        browser.waitForPageStable();
        return this;
    }

    public CpOperationalProcedures addReceiverId(String receiverInvestorId) {
        browser.typeTextElement(receiverIdField, receiverInvestorId);
        browser.waitForPageStable();
        return this;
    }

    public CpOperationalProcedures setTokenAmount(String tokenAmount) {
        browser.typeTextElement(tokensAmountField, tokenAmount);
        browser.waitForPageStable();
        return this;
    }

    public void clickExecute(String reason) {
        String actualReason = browser.getElementText(reasonField);
        if (actualReason == null || actualReason.equals("")) {
            typeReason(reason);
        }
        browser.click(executeButton);
    }

    public int getTotalAmountOfSecuritiesCanBeProcedure() {

        Function<Integer, Integer> internalWaitInvestorDetailsPage = t -> {
            try {
                String textFromElement;
                textFromElement = browser.getElementText(numberOfTotalSecurities);
                return Integer.parseInt(textFromElement);
            } catch (Exception e) {
                return null;
            }
        };
        String description = "Wait for token can be procedure to be a number";
        return Browser.waitForExpressionNotNull(internalWaitInvestorDetailsPage, null, description, 600, 1000);
    }

    public boolean verifyTokenCanBeLockEqualTotalToken(int tokenCanBeLock) {
        browser.waitForElementTextNotEmpty(numberOfTotalSecurities);
        int totalSecuritiesInProcedure = getTotalAmountOfSecuritiesCanBeProcedure();
        return tokenCanBeLock == totalSecuritiesInProcedure;
    }

    public CpOperationalProcedures typeReasonLockToken(String reason) {
        browser.typeTextElement(reasonField, reason);
        browser.waitForElementVisibility(numberOfTotalSecurities);
        return this;
    }

    public CpOperationalProcedures typeNumberOfTokenToLock(int tokensToLock) {
        var elements = browser.findElementsQuick(tokenAmountLockToken, 5);
        if (!elements.isEmpty()) {
            browser.typeTextElementCtrlA(tokenAmountLockToken, String.valueOf(tokensToLock));
        }
        return this;
    }

    public CpOperationalProcedures typeNumberOfTokenToDestroy(int tokensToDestroy) {
        browser.typeTextElementCtrlA(numberSecuritiesInput, String.valueOf(tokensToDestroy));
        return this;
    }

    public boolean verifySuccessMessage() {
        return browser.verifyAlertMessage(alertProcedureMessage, "Success", true);

    }

    public boolean isAlertMessageCorrect(ExtendedBy extendedBy, String message) {
        boolean isMessageDisplay = false;
        boolean isMessageCorrect = false;
        String actualMessage;
        isMessageDisplay = browser.isElementVisible(alertProcedureMessage);
        if (isMessageDisplay) {
            info("Alert message after action is: " + browser.getElementText(alertProcedureMessage));
            actualMessage = browser.getElementText(alertProcedureMessage);
            if (message.contains(actualMessage)) {
                isMessageCorrect = true;
            } else {
                info("Alert message not as expected, should be '" + message + "' but it's '" + actualMessage);
            }
        }
        return isMessageCorrect;
    }

    public CpOperationalProcedures chooseLostSharesProcedure() {
        browser.click(procedureSelectorDropDown);
        browser.click(lostShares);
        return this;
    }

    public CpOperationalProcedures typeSourceWallet(String sourceWallet) {
        browser.typeTextElement(sourceWalletField, sourceWallet);
        return this;
    }

    public CpOperationalProcedures typeDestinationWallet(String destinationWallet) {
        browser.typeTextElement(destinationWalletField, destinationWallet);
        return this;
    }

    public static ExtendedBy getDayInDatePicker(String date) {
        String dayXpath = String.format(dayInDatePicker, date);
        return new ExtendedBy("dayInDatePicker", By.xpath(dayXpath));
    }

    public CpOperationalProcedures selectDateOfRedemption() {
        String today = DateTimeUtils.currentDate("MMM d, yyyy");
        browser.click(startDateOfRedemptionField);
        browser.click(getDayInDatePicker(today));
        return this;
    }

    public CpOperationalProcedures typeReasonLostShares(String reason) {
        browser.typeTextElement(reasonField, reason);
        return this;
    }

    public int getNumberOfSecuritiesInSourceWallet() {
        browser.waitForElementTextNotEmpty(numberOfSecuritiesField);
        browser.waitForElementTextValueNotZero(numberOfSecuritiesField);
        return Integer.parseInt(browser.getElementText(numberOfSecuritiesField));
    }

    public CpOperationalProcedures chooseDestroyRegularTokenProcedure() {
        browser.click(procedureSelectorDropDown);
        browser.click(destroyTokens);
        return this;
    }

    public CpOperationalProcedures chooseDestroyTBETokenProcedure() {
        browser.click(procedureSelectorDropDown);
        browser.click(destroyTBE);
        return this;
    }

    public String getContractStatus() {
        Function<String, String> waitContractStatusNotBeNull = t -> {
            try {
                List<WebElement> elements;
                elements = browser.findElements(contractStatus);
                String status = elements.get(0).getText();
                return status;
            } catch (Exception e) {
                return null;
            }
        };
        String description = "Wait for contract status not to be null";
        return Browser.waitForExpressionNotNull(waitContractStatusNotBeNull, null, description, 600, 1000);
    }

    public void getUpdatedContractStatus(String expectedStatus, int timeoutSeconds, int checkIntervalMilliseconds) {
        final String[] actualStatus = new String[1];
        Function<String, String> internalWaitForExpectedStatus = t -> {
            actualStatus[0] = getContractStatus();
            try {
                if (!actualStatus[0].equals(expectedStatus)) {
                    info("Actual status is not as expected. It is " + actualStatus[0]);
                    browser.refreshPage();
                    chooseHoldTradingProcedure();
                } else {
                    throw new RuntimeException("Can't find userId details (returned null) in the response");
                }
            } catch (Exception e) {
            }
            return actualStatus[0];
        };
        String errorMessage = "Actual contract status after " + timeoutSeconds + " should be " + expectedStatus + " but it is " + actualStatus[0];
        StringIsNotAsExpectedException contractStatusNotChanged = new StringIsNotAsExpectedException(errorMessage, actualStatus[0], expectedStatus);
        Browser.waitForExpressionToEqual(internalWaitForExpectedStatus, null, expectedStatus, errorMessage, timeoutSeconds, checkIntervalMilliseconds, contractStatusNotChanged);
    }

    public void performDestroy(String tokensHeldIn, int tokenToProcedure, String walletAddress, String investorExternalID) {
        String reason = "Destroying " + tokenToProcedure + " " + tokensHeldIn + " tokens";
        if (tokensHeldIn.equals("wallet")) {
            chooseDestroyRegularTokenProcedure();
            addWalletId(walletAddress);
        } else if (tokensHeldIn.equals("TBE")) {
            chooseDestroyTBETokenProcedure();
            addExternalInvestorId(investorExternalID);
        }
        typeReason(reason);
        typeNumberOfTokenToDestroy(tokenToProcedure);
        clickExecute(reason);
        verifySuccessMessage();
    }

}
