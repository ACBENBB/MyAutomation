package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class CpPlusLotteriesPageCreateCondition extends AbstractJpPage {

    private static final ExtendedBy conditionNameInput = new ExtendedBy("Condition Name Input", By.xpath("//*[@name='name']"));
    private static final ExtendedBy totalIssuedInput = new ExtendedBy("Total Issued Input", By.xpath("//*[@name='totalIssued']"));
    private static final ExtendedBy unitPriceText = new ExtendedBy("Unit Price Text", By.xpath("//*[@class='ml-3 col' and contains(., '単価')]/div[2]"));
    private static final ExtendedBy totalIssuanceAmount = new ExtendedBy("Total Issuance Amount", By.xpath("//*[@class='ml-3 col' and contains(., '総発行額')]/div[2]"));
    private static final ExtendedBy totalPledgedAmount = new ExtendedBy("Total Pledged Amount", By.xpath("//*[@class='ml-3 col' and contains(., '総申込額')]/div[2]"));
    private static final ExtendedBy lotterySystemCondition = new ExtendedBy("Lottery System Condition", By.xpath("//input[@type='radio' and @value='SpecifiedCondition']"));
    private static final ExtendedBy lotterySystemAllocation = new ExtendedBy("Lottery System Allocation", By.xpath("//input[@type='radio' and @value='ExternalAllocation']"));
    private static final ExtendedBy createButton = new ExtendedBy("Create Button", By.xpath("//*[@class='modal-content' and contains(.,'条件作成')]//*[text() = '作成']"));

    public CpPlusLotteriesPageCreateCondition(Browser browser) {
        super(browser, conditionNameInput);
    }

    public CpPlusLotteriesPageCreateCondition enterConditionName(String conditionName) {
        browser.typeTextElement(conditionNameInput, conditionName);
        return this;
    }

    public CpPlusLotteriesPageCreateCondition enterTotalIssued(String totalIssued) {
        browser.typeTextElement(totalIssuedInput, totalIssued);
        return this;
    }

    public String getUnitPriceText() {
        return browser.getElementText(unitPriceText);
    }

    public String getTotalIssuanceAmount() {
        return browser.getElementText(totalIssuanceAmount);
    }

    public String getTotalPledgedAmount() {
        return browser.getElementText(totalPledgedAmount);
    }

    public CpPlusLotteriesPageCreateCondition clickLotterySystemCondition() {
        browser.click(lotterySystemCondition);
        return this;
    }

    public CpPlusLotteriesPageCreateCondition clickLotterySystemAllocation() {
        browser.click(lotterySystemAllocation);
        return this;
    }

    public CpPlusLotteriesPageCreateCondition clickCreateButton() {
        browser.clickAndWaitForElementToVanish(createButton);
        return this;
    }
}
