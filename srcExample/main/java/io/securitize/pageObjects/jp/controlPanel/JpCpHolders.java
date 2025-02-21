package io.securitize.pageObjects.jp.controlPanel;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpCp;
import org.openqa.selenium.By;

public class JpCpHolders extends AbstractJpCp {

    private static final ExtendedBy onFundraisePageHeader = new ExtendedBy("Holder page header", By.xpath("//*[text()[contains(.,'| 保有者 ')]]"));

    public JpCpHolders(Browser browser) {
        super(browser, onFundraisePageHeader);
    }
}
