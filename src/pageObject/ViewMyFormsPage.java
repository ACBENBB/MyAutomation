package pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewMyFormsPage extends BasePage{

	int size;
	@FindBy(css="#ePlyHeader_SearchLink")
	WebElement searchAll;
	@FindBy(css="#ePlyHeader_accountLi")
	WebElement accounts;
	@FindBy(css=".rgMasterTable>tbody>tr")
	List<WebElement> listForms;
	@FindBy(css="#ePlyHeader_FirstName")
	WebElement helloShavit;
	String helloShavitText;

	public ViewMyFormsPage(WebDriver driver) {
		super(driver);
	}
	//Actions
	public void clickOnSearchAll() {
		click(searchAll);
	}
	public void clickOnAccounts() {
		click(accounts);
	}
	public void enterAccountPage() {
		accounts.click();
	}

	public void showAllForms() {
		driver.findElement(By.cssSelector("#DateFilter [value='All']")).click();
	}

	public void listFormsSize() throws InterruptedException{
		Thread.sleep(3000);
		listForms = driver.findElements(By.cssSelector(".rgMasterTable>tbody>tr"));
		System.out.println("There are "+listForms.size()+" forms");
	}

	public WebElement returnEl(int i) throws InterruptedException {
		Thread.sleep(3000);
		listForms = driver.findElements(By.cssSelector(".rgMasterTable>tbody>tr"));
		WebElement el = listForms.get(i);
		return el;
	}

	public void clickOnForm(int i) throws InterruptedException {
		Thread.sleep(3000);
		listForms = driver.findElements(By.cssSelector(".fa.fa-cogs"));
		listForms.get(i).click();
			}
	
	public void enterForm(int i) {
		listForms.get(i).click();
	}
	
	
}

