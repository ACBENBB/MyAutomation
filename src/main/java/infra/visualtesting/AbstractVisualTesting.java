package infra.visualtesting;

import infra.webdriver.Browser;
import infra.webdriver.ExtendedBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractVisualTesting {

    protected Browser browser;

    public AbstractVisualTesting(Browser browser) {
        this.browser = browser;
    }

    protected void showRecordingMessage() {
        // add snapshot icon
        String addingElementScript = "document.body.innerHTML += \"<div id='uniqueElementForSnapshotVisualization'" +
                " style='width:100%;height: 100%;position: absolute;top: 0;left:0;z-index: 9999;'>" +
                "    <svg width='150' height='60'>" +
                "  <rect x='10' y='10' rx='20' ry='20' width='125' height='40' style='fill:white;stroke:black;stroke-width:5;opacity:1;fill-opacity: 1;'></rect>" +
                "   <circle cx='30' cy='30' r='10' stroke='black' stroke-width='4' fill='red'></circle>" +
                "   <text x='50' y='35' fill='black'>Recording!</text>" +
                "   Sorry, your browser does not support inline SVG." +
                "</svg></div>\"";
        browser.executeScript(addingElementScript);
        browser.sleep(1500);

        // remove snapshot icon
        String removeElementScript = "document.getElementById('uniqueElementForSnapshotVisualization').remove();";
        browser.executeScript(removeElementScript);
    }

    public abstract void snapshot(String name);

    public void snapshot(String name, ExtendedBy... elementsToIgnore) {
        browser.waitForPageStable(Duration.ofSeconds(5));
        Map<WebElement, String> originalValues = new HashMap<>();

        // hide elements to ignore
        for (ExtendedBy currentElementBy : elementsToIgnore) {
            WebElement element = browser.findElement(currentElementBy);
            Object[] arg = {element};
            String originalVisibilityValue = browser.executeScriptSilent("return arguments[0].style.visibility;", arg);
            originalValues.put(element, originalVisibilityValue);
            browser.executeScriptSilent("arguments[0].style.visibility = 'hidden';", arg);
        }

        this.snapshot(name);

        // restore elements to ignore
        for (WebElement element : originalValues.keySet()) {
            Object[] arg = {element};
            browser.executeScriptSilent("arguments[0].style.visibility = '" + originalValues.get(element) + "';", arg);
        }
    }

}
