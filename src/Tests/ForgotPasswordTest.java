package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.ForgotPasswordPage;
import pageObject.LoginPage;

public class ForgotPasswordTest extends BaseTest{

	@Test (description = "forgotPassword + wrong userName + test correct errorMessage")
	public void forgotPasswordErrorMessage() throws InterruptedException {
		LoginPage signin = new LoginPage(driver);
		signin.forgotPasswordClick();
		ForgotPasswordPage forgotPass = new ForgotPasswordPage(driver);
		forgotPass.forgotPassword("Ben@Ben.ben");
		String expected = "Instructions on how to reset your password have "
				+ "been sent to the email address registered with the username provided.";
		String actual = forgotPass.getMessage();
		Assert.assertEquals(actual, expected);
	}

	/* @Test (description = "forgotPassword + correct userName + test reset password")
	public void forgotPasswordCorrecetUserName() throws InterruptedException {
		LoginPage signin = new LoginPage(driver);
		signin.forgotPasswordClick();
		ForgotPasswordPage forgotPass = new ForgotPasswordPage(driver);
		forgotPass.forgotPassword("shavit1986@gmail.com");

		//Here I put a verification after success 'sumbit' for correct userName 

		String expected = "Instructions on how to reset your password have "
			+ "been sent to the email address registered with the username provided";
		String actual = forgotPass.getMessage();
			Assert.assertEquals(actual, expected);
	} */
}
