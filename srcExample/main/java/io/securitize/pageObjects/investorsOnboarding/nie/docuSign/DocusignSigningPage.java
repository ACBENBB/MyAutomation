package io.securitize.pageObjects.investorsOnboarding.nie.docuSign;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.warning;

public class DocusignSigningPage extends AbstractPage {

    private final ExtendedBy docuSignGotItButton = new ExtendedBy("DocuSign 'got it' button", By.xpath("//button[contains(@class, 'btn btn-primary')]"));
    private final ExtendedBy docuSignAgreeToElectronicRecords = new ExtendedBy("DocuSign 'Agree to use electronic records' button", By.id("disclosureAccepted"));
    private final ExtendedBy docuSignContinueButton = new ExtendedBy("DocuSign 'continue' button", By.id("action-bar-btn-continue"));
    private final ExtendedBy docuSignStartButton = new ExtendedBy("DocuSign 'start' button", By.id("navigate-btn"));
    private final ExtendedBy docuSignFillInButton = new ExtendedBy("DocuSign 'Fill In' button", By.xpath("//*[@id='autonav-button-wrapper']//span[text()='Fill In']"));
    private final ExtendedBy docuSignChooseButton = new ExtendedBy("DocuSign 'Choose' button", By.xpath("//*[@id='autonav-button-wrapper']//span[text()='Choose']"));
    private final ExtendedBy docuSignPerformSignButton = new ExtendedBy("DocuSign 'sign' button", By.xpath("//*[@class='tab-content-wrapper']"));
    private final ExtendedBy docuSignAdoptAndSignButton = new ExtendedBy("DocuSign 'Adopt and sign' button", By.xpath("//div[@class='footer']/button"));
    private final ExtendedBy docuSignFinishButton = new ExtendedBy("DocuSign 'Finish' button", By.xpath("//div[@id='finish-button-callout']/button"));
    private final ExtendedBy docuSignCloseButton = new ExtendedBy("DocuSign 'Close' button", By.xpath(".//div[@class='footer']//button[text()='Close']"));
    private final ExtendedBy docuSignCloseDoneSigningButton = new ExtendedBy("DocuSign 'Close' button when done signing", By.xpath("//button[contains(@class, 'btn-done-signing')]"));
    private final ExtendedBy docusignContinueButton = new ExtendedBy("Docusign Continue Final Btn ", By.xpath("//span[contains(text(), 'Continue')]"));



    public DocusignSigningPage(Browser browser) {
        super(browser);
    }


    private DocusignSigningPage docuSignClickGotIt() {
        browser.click(docuSignGotItButton);
        return this;
    }

    private DocusignSigningPage docuSignClickAgreeToUseElectronicRecords() {
        browser.click(docuSignAgreeToElectronicRecords, ExecuteBy.JAVASCRIPT,false);
        return this;
    }

    private DocusignSigningPage docuSignClickContinue() {
        browser.click(docuSignContinueButton);
        return this;
    }

    private DocusignSigningPage docuSignClickStart() {
        browser.sleep(3000); // must slow down clicks because docuSign has issues otherwise
        browser.click(docuSignStartButton, false);
        return this;
    }


    private void fillEmptyFields() {
        List<WebElement> emptyFields = browser.findElementsQuick(new ExtendedBy("", By.xpath("//input[@value='']")), 2);
        if (emptyFields.size() > 0) {
            info("Adding value to missing fields in docuSign - shouldn't be needed");
            for (WebElement currentEmptyField : emptyFields) {
                currentEmptyField.sendKeys("value");
            }
        }
    }

    private void docuSignClickAllFillIn() {
        browser.sleep(5000); // must slow down clicks because docuSign has issues otherwise

        fillEmptyFields();

        int clickCount = 1;
        List<WebElement> elements = browser.findElements(docuSignFillInButton);
        while (elements.size() > 0) {
            browser.click(docuSignFillInButton);
            browser.sleep(2000); // must slow down clicks because docuSign has issues otherwise
            info("Clicked docuSign 'fill in' button: " + clickCount + " times.");
            clickCount++;
            elements = browser.findElementsQuick(docuSignFillInButton);
        }
    }

    private void docuSignClickChoose() {
        browser.sleep(3000); // must slow down clicks because docuSign has issues otherwise
        browser.click(docuSignChooseButton);
    }

    private DocusignSigningPage docuSignClickSign() {
        browser.click(docuSignPerformSignButton, false);
        return this;
    }

    private DocusignSigningPage docuSignClickAdoptAndSign() {
        browser.click(docuSignAdoptAndSignButton);
        return this;
    }

    private void docuSignClickFinish() {
        // sometimes it takes docuSign long time to load.. we give it extra loading time if needed
        browser.waitForElementVisibility(docuSignFinishButton, 30);

        // due to docuSign bug, sometimes clicking the finish button doesn't work right away.. give it time to load the entire js event
        browser.sleep(5000);
        browser.click(docuSignFinishButton, ExecuteBy.JAVASCRIPT, true);
    }

    private void docuSignClickClose() {
        browser.click(docuSignCloseButton);
        browser.sleep(3000); // must slow down clicks because docuSign has issues otherwise

        browser.click(docuSignCloseDoneSigningButton);
        browser.sleep(3000); // must slow down clicks because docuSign has issues otherwise
    }

    public void signDocument() {
        // validate docusign successfully loaded - if not refresh the page
        try {
            browser.waitForElementVisibility(docuSignGotItButton);
        } catch (NoSuchElementException e) {
            warning("Docusign not loaded in time, trying to refresh the page", true);
            browser.refreshPage();
            browser.waitForElementVisibility(docuSignGotItButton);
        }

        docuSignClickGotIt()
                .docuSignClickAgreeToUseElectronicRecords()
                .docuSignClickContinue()
                .docuSignClickStart()
                .docuSignClickAllFillIn();

        // not always visible
        if (browser.findElementsQuick(docuSignChooseButton).size() > 0) {
            docuSignClickChoose(); // for purchaser signature
        }

        docuSignClickSign()
                .docuSignClickAdoptAndSign()
                .docuSignClickFinish();

        // not always visible
        if (browser.findElementsQuick(docuSignCloseButton).size() > 0) {
            docuSignClickClose();
        }

//        if(browser.findElementsQuick(docusignContinueButton).size() > 0) {
//            browser.click(docusignContinueButton);
//        }
    }
}