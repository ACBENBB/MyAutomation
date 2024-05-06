package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	@FindBy(css="#body_ForgotPasswordButton")
	WebElement forgotPasswordLink;
	@FindBy(css="#body_Username")
	WebElement userName;
	// Ben
	@FindBy(css="#body_Password")
	WebElement password;
	@FindBy(css="#body_SubmitButton")
	WebElement submitBtn;
	@FindBy(css="#body_ErrorMessage")
	WebElement errorMsg;
	@FindBy(css="#ePlyHeader_FirstName")
	WebElement helloShavit;

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	//Actions

	public String getActualText() {
		return helloShavit.getText(); 
	}
	
	public void forgotPasswordClick() {
		click(forgotPasswordLink);
	}

	public void login(String user, String pass) throws InterruptedException {

		fillText(userName,  user);
		fillText(password, pass);
		click(submitBtn);
		Thread.sleep(1500);
	}

	public String getErrorMsg() {
		return errorMsg.getText();
	}
}
