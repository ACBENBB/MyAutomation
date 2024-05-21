package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.LoginPage;
import pageObject.SearchAllPage;
import pageObject.ViewMyFormsPage;

public class ShowAllFormsTest extends Tests.BaseTest {

	@Test(description = "Search all forms")
	public void searchAllForms() throws InterruptedException {
		LoginPage login = new LoginPage(driver);
		login.login("shavit1986@gmail.com", "shavitdemo123");
		ViewMyFormsPage viewMyForms = new ViewMyFormsPage(driver);
		viewMyForms.clickOnSearchAll();
		SearchAllPage searchall = new SearchAllPage(driver);
		searchall.fillSearchAll("7/12/2016", "7/12/2017", "shavit1986@gmail.com");
		boolean expected = true;
		boolean actual = searchall.SpinnerDisplay();
		Assert.assertEquals(actual, expected);
	}
		
/*	@Test(description = "Edit details of account")
	public void fillAccount() throws InterruptedException{
		LoginPage login = new LoginPage(driver);
		login.login("shavit1986@gmail.com", "shavitdemo123");
		ViewMyFormsPage view = new ViewMyFormsPage(driver);
		view.enterAccountPage();
		Accounts account = new Accounts(driver);
		Thread.sleep(1500);
		account.fillWebsite("www.victoriaTest.com");
		account.fillstreetAdress("streetAdressS");
		account.fillprovince("AB");
		account.fillcountry("CA");
	}

	@Test(description = "test if all forms are showed")
	public void showAllForms() throws InterruptedException {
		LoginPage login = new LoginPage(driver);
		login.login("shavit1986@gmail.com", "shavitdemo123");
		ViewMyFormsPage view = new ViewMyFormsPage(driver);
		view.showAllForms();
		view.listFormsSize();
	}
	
	@Test(description = "Fill event details")
	public void fillFormDetails() throws InterruptedException {
		LoginPage login = new LoginPage(driver);
		login.login("shavit1986@gmail.com", "shavitdemo123");
		ViewMyFormsPage view = new ViewMyFormsPage(driver);
		view.showAllForms();
		view.clickOnForm(0);
		FormPage formPage = new FormPage(driver);
		formPage.fillEventDetails();
		formPage.saveForm();
	}*/

}
