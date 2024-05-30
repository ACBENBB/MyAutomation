package pageObjects;

import infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DemoGuru99 extends BasePage {

    @FindBy(xpath = "//div[@id='navbar-brand-centered']")
    WebElement upperMenu;

    private static final ExtendedBy updateControlBookButton = new ExtendedBy("Update Control Book button", By.xpath("//span[@data-id='control-book-update-form-button']"));

    String headerPattern = "//div[@id='navbar-brand-centered']//a[contains(text(),'%s')]";

    public DemoGuru99(WebDriver driver) {
        super(driver);
    }

    public void clickUpperMenu(String menuPath) {
        String[] menus = menuPath.split(";");
        clickUpperMenu(menus[0], menus[1]);
    }

    public void clickUpperMenu(String mainManu, String subMenu) {
        String mainMenuXpath = String.format(headerPattern, mainManu);
        click(driver.findElement(By.xpath(mainMenuXpath)));
        if (subMenu != null || !subMenu.equals("")){
                String menuXpath = String.format(headerPattern, subMenu);
                click(driver.findElement(By.xpath(menuXpath)));
        }
    }
}
