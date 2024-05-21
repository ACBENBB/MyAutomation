package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchAllPage extends BasePage{

	@FindBy(css="#RadFromDatePicker_dateInput")
	WebElement fromDate; 
	@FindBy(css="#RadToDatePicker_dateInput")
	WebElement toDate;
	@FindBy(css="#Email")
	WebElement email;
	@FindBy(css="#SearchButton>span")
	WebElement searchButton;
	@FindBy(css="#ralpLoading>img")
	WebElement serachSpinner;

	public SearchAllPage(WebDriver driver) {
		super(driver);
	}

	public void fillSearchAll(String fromdate, String todate, String mail) throws InterruptedException {
		fillText(fromDate, fromdate);
		fillText(toDate, todate);
		fillText(email, mail);
		click(searchButton);
		Thread.sleep(2000);
	}

	public boolean SpinnerDisplay() {
		if (serachSpinner.isDisplayed()) 
		{
			return true;
		}
		else return false;
	}


}