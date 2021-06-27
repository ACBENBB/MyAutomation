package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.LoginPage;

public class LoginTest extends BaseTest {

	@Test (description = "test incorrect login info")
	public void loginFailure() throws InterruptedException {
		LoginPage signin = new LoginPage(driver);
		signin.login("shavit1986@gmail.com", "sh");
		//Assertions
		String expected = "Sorry, either your username or password was incorrect.";
		String actual = signin.getErrorMsg();
		Assert.assertEquals(actual, expected);	
	}

	@Test(description = "Test correct login info")
	public void loginSucces() throws InterruptedException {
		LoginPage signin = new LoginPage(driver);
		//ViewMyFormsPage view = new ViewMyFormsPage(driver);
		signin.login("shavit1986@gmail.com", "shavitdemo123");
		//Assertions
		String expected = "Hello, Shavit!";
		String actual = signin.getActualText();
		Assert.assertEquals(actual, expected);
	}
}
