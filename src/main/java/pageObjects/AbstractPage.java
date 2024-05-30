package pageObjects;

import infra.exceptions.PageNotLoadedException;
import infra.webdriver.Browser;
import infra.webdriver.ExtendedBy;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractPage <R>  {
    protected Browser browser;
    private static final Logger logger = LoggerFactory.getLogger(Browser.class);

    public AbstractPage(Browser browser, ExtendedBy... elements) {
        this.browser = browser;
        verifyOnPage(elements);
    }

    private void verifyOnPage(ExtendedBy[] elements) { // Verifying that the desired page has finished loading
        logger.debug("Verifying we are on the correct page by elements...");

        for (ExtendedBy currentBy : elements) {
            try {
                browser.waitForElementVisibility(currentBy);
            } catch (TimeoutException e) {
                throw new PageNotLoadedException(this.getClass().getSimpleName(), currentBy);
            }
        }
        logger.debug("Validated we are on the correct page by elements!");
    }
}
