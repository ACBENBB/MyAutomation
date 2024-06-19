package pageObjects;

import infra.webdriver.Browser;
import infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;

public class Sport5_MainPage extends AbstractPage {

    private static final ExtendedBy sport5MainTitle = new ExtendedBy("Sport 5 main title", By.xpath("//strong//a[@title]"));

    public Sport5_MainPage(Browser browser) {
        super(browser, sport5MainTitle);
    }


    public String getSport5Title(){
        return browser.getElementAttribute(sport5MainTitle, "title");
    }
}
