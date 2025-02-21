package io.securitize.pageObjects.jp.controlPanel;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpCp;
import org.openqa.selenium.By;

public class JpCpFundraise extends AbstractJpCp {

    private static final ExtendedBy fundraisePageHeader = new ExtendedBy("Fundraise page header", By.xpath("//*[text()[contains(.,'| 資金調達ラウンド ')]]"));

    public JpCpFundraise(Browser browser) {
        super(browser, fundraisePageHeader);
    }
}
