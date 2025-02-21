package io.securitize.pageObjects.jp.controlPanel;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpCp;
import org.openqa.selenium.By;

public class JpCpOnBoarding extends AbstractJpCp {

    private static final ExtendedBy onBoardingPageHeader = new ExtendedBy("onBoarding page header", By.xpath("//*[text()[contains(.,'| オンボーディング ')]]"));

    public JpCpOnBoarding(Browser browser) {
        super(browser, onBoardingPageHeader);
    }

}
