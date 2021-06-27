package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgotPasswordPage extends BasePage {
	@FindBy(css="#body_Username")
	WebElement userName; 
	@FindBy(css="#body_SubmitButton")
	WebElement submit; 
	@FindBy(css="#body_CancelButton")
	WebElement cancel; 
	@FindBy(css="#body_Message")
	WebElement message; 


	public ForgotPasswordPage(WebDriver driver) {
		super(driver);
	}
	
	//Actions
	public void forgotPassword(String user) {
		fillText(userName, user);
		click(submit);
	}
	
	public String getMessage() {
		return message.getText();
	}



}
