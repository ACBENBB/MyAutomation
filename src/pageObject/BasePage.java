package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BasePage {
	WebDriver driver;
	JavascriptExecutor js;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		js=(JavascriptExecutor)driver;
	}

	public void click(WebElement el) {
		highlightElement(el,"yellow");
		el.click();
	}

	public void fillText(WebElement el, String text) {
		highlightElement(el, "orange");
		el.clear();
		el.sendKeys(text);
	}

	public void setSelect(WebElement el, String value) {
		Select select = new Select(el);
		select.selectByValue(value);
	}

	// call this method with your element and a color you like (red, green, orange, blue etc...)

	protected void highlightElement(WebElement element, String color) {
		//keep the old style to change it back
		String originalStyle = element.getAttribute("style");
		String newStyle = "border: 4px solid " + color + ";" + originalStyle;
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Change the style 
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style','" + newStyle + "');},0);", element);
	}
}
