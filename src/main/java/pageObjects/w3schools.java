package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class w3schools extends BasePage {

    @FindBy(xpath = "//table[@id='customers']")
    WebElement customersTable;

    @FindBy(xpath = "//table[@id='customers']//tbody//tr/th")
    List<WebElement> customersHeaders;

    String tdXpathPattern = "//tbody//tr//td[%s]";

    public w3schools(WebDriver driver) {
        super(driver);
    }

    public int findColumnIndexByColumnTitle(String columnTitle) {
        for (int i = 0; i < customersHeaders.size(); i++) {
            if (customersHeaders.get(i).getText().toLowerCase().equals(columnTitle.toLowerCase())) {
                return i + 1; // +1 because we need the index to identify index with xpath
            }
        }
        System.out.println("Column not found in any column"); // in case we finish the loop and not index found by colum title
        return 0;
    }

    public int findRowIndexByText(List<WebElement> list, String searchText) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().equals(searchText)) {
                return i + 1; // +1 because we need the index to identify index with xpath
            }
        }
        System.out.println("Text not found in any row"); // in case we finish the loop and the text not found in any row
        return 0;
    }

    // another method to get table cell text by column title and not column index
    public String getTableCellText(WebElement table, String searchColumn, String searchText, int returnColumnText) {
        String tdXpath = String.format(tdXpathPattern, findColumnIndexByColumnTitle(searchColumn));
        List<WebElement> listOfTd = table.findElements(By.xpath(tdXpath));
        int rowIndex = findRowIndexByText(listOfTd, searchText);
        String returnTextPattern = "(//table[@id='customers']//tbody//tr//td[" + returnColumnText + "])[" + rowIndex + "]";
        String returnText = driver.findElement(By.xpath(returnTextPattern)).getText();
        return returnText;
    }

    public int findColumnIndexByColumnTitle(int columnTitle) {
        for (int i = 0; i < customersHeaders.size(); i++) {
            if (customersHeaders.get(i).getText().toLowerCase().equals(columnTitle)) {
                return i + 1; // +1 because we need the index to identify index with xpath
            }
        }
        System.out.println("Column not found in any column"); // in case we finish the loop and not index found by colum title
        return 0;
    }

    public String getTableCellText(WebElement table, int searchColumn, String searchText, int returnColumnText) {
        String tdXpath = String.format(tdXpathPattern, searchColumn);
        List<WebElement> listOfTd = table.findElements(By.xpath(tdXpath));
        int rowIndex = findRowIndexByText(listOfTd, searchText);
        String returnTextPattern = String.format("(//tbody//tr//td[%d])[%d]", returnColumnText, rowIndex);
        String returnText = table.findElement(By.xpath(returnTextPattern)).getText();
        return returnText;
    }

    public boolean verifyTableCellText(WebElement table, int searchColumn, String searchText, int returnColumnText, String expectedText) throws Exception {
        String actual = getTableCellText(table, searchColumn, searchText, returnColumnText).toLowerCase();
        return actual.equals(expectedText.toLowerCase());
    }

    public String getTableCellTextByXpath(WebElement table, int searchColumn, String searchText, int returnColumnText) throws Exception {
        try {
            String tdXpath = String.format(tdXpathPattern, searchColumn) + "[contains(text(),'" + searchText + "')]";
            List<WebElement> listOfTd = table.findElements(By.xpath(tdXpath + "/ancestor::tr//td"));
            WebElement expectedCell = listOfTd.get(returnColumnText - 1);
            // Another way:
            // WebElement expectedCell = driver.findElement(By.xpath(tdXpath+"/ancestor::tr//td["+returnColumnText+"]"));
            String returnedText = expectedCell.getText();
            return returnedText;
        } catch (Exception e) {
            throw new Exception("Element not found for XPath: ", e);
        }
    }


}
