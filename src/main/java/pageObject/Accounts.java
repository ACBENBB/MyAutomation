package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Accounts extends BasePage {

	@FindBy(css="#Website")
	WebElement website;
	@FindBy(css="#Address")
	WebElement streetAdress; 
	@FindBy(css="#Province")
	WebElement province;
	@FindBy(css="#Country")
	WebElement country;

	public Accounts(WebDriver driver) {
		super(driver);
	}

	public void fillWebsite(String websiteS) {
		fillText(website, websiteS);
	}
	public void fillstreetAdress(String streetAdressS) {
		fillText(streetAdress, streetAdressS);
	}
	public void fillprovince(String provinceS) {
		setSelect(province, provinceS);	
	}
	public void fillcountry(String countryS) {
		setSelect(country, countryS);	
	}
}
