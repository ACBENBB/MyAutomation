package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FormPage extends BasePage {

	@FindBy(css="#EventInfoTab")
	WebElement eventInfoTab;
	@FindBy(css="#EventNameEdit")
	WebElement eventName;
	@FindBy(css="[name='PageTitle']")
	WebElement pageTitle;
	@FindBy(css="[name='EventStartDatePicker$dateInput']")
	WebElement EventStartDate;
	@FindBy(css="[name='EventEndDatePicker$dateInput']")
	WebElement EventEndtDate;
	@FindBy(css="#SaveEventInfoButton2")
	WebElement SaveForm;


	public FormPage(WebDriver driver) {
		super(driver);
	}

	public void verifyEventTab() {
		eventInfoTab.click();
	}
	public void fillEventDetails () throws InterruptedException {
		fillText(eventName, "Test of Ben Berko");
		fillText(pageTitle, "Berko Test Title");
		fillText(EventStartDate, "12/06/2012");
		fillText(EventEndtDate, "12/09/2013");	
	}
	
	public void saveForm() {
	SaveForm.click();
	}
	
}
