package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.google.CpGoogleSignIn;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.Optional;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class CpLoginPage extends AbstractPage {

    private static final ExtendedBy loginWithGoogleButton = new ExtendedBy("Login with Google button", By.xpath("//button[contains(@class, 'btn-google')]"));
    private static final ExtendedBy emailTextbox = new ExtendedBy("CP login screen - Email text box", By.xpath("//*[@class = 'form-control form-group']"));
    private static final ExtendedBy passwordTextbox = new ExtendedBy("CP login screen - Email text box", By.xpath("//*[@class = 'form-control form-group password-input']"), true);
    private static final ExtendedBy signInButton = new ExtendedBy("Login screen - Sign in button", By.xpath("//button[contains(@class, 'btn btn-outline-primary btn-block mb-3')]"));
    private static final ExtendedBy signInSpinner = new ExtendedBy("Login screen - sign in spinner on the submit button", By.xpath("//span[contains(@class, 'spinner-border')]"));

    public CpLoginPage(Browser browser) {
        super(browser, loginWithGoogleButton);
    }

    public CpGoogleSignIn clickLoginWithGoogle() {
        int numberOfOpenWindows = browser.getNumberOfOpenWindows();
        browser.click(loginWithGoogleButton);
        browser.waitForNumbersOfTabsToBe(numberOfOpenWindows + 1);
        browser.switchToLatestWindow();
        return new CpGoogleSignIn(browser);
    }

    public CpLoginPage2FA loginCpUsingEmailPassword(){
        return loginCpUsingEmailPassword(true);
    }
    public CpLoginPage2FA loginCpUsingEmailPassword(boolean return2FaLoginPage) {
        return loginCpUsingEmailPassword(buildEmailAddress(), Users.getProperty(UsersProperty.automationCpPassword), return2FaLoginPage);
    }

    public void clickSignInButton() {
        browser.click(signInButton, false);
    }

    public void setUsernameAndPassword() {
        setUsernameAndPassword(null, null);
    }
    public void setUsernameAndPassword(String email, String password) {
        if (email == null && password == null) {
            email = buildEmailAddress();
            password = Users.getProperty(UsersProperty.automationCpPassword);
        }
        browser.typeTextElement(emailTextbox, email);
        browser.typeTextElement(passwordTextbox, password);
    }

    public CpLoginPage2FA loginCpUsingEmailPassword(String email, String password, boolean return2FaLoginPage) {
       browser.typeTextElement(emailTextbox, email);
       browser.typeTextElement(passwordTextbox, password);
       browser.click(signInButton, false);
       if (return2FaLoginPage) {
           return new CpLoginPage2FA(browser);
       } else {
           return null;
       }
    }

    public CpLoginPage2FA loginCpUsingSecuritizeAutomationMailUserName(){
        browser.typeTextElement(emailTextbox, "securitize_automation@securitize.io");
        browser.typeTextElement(passwordTextbox, Users.getProperty(UsersProperty.automationCpPassword));
        browser.click(signInButton, false);
        return new CpLoginPage2FA(browser);
    }

    private String buildEmailAddress() {
        String emailAddressTemplate = Users.getProperty(UsersProperty.automationCpE2eEmail);
        String currentTestName = getCurrentTestNameFromStack();
        return String.format(emailAddressTemplate, currentTestName);
    }

    private String getCurrentTestNameFromStack() {
        // search the current stackTrace for a class containing AUTXX_ string and extract it
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Optional<StackTraceElement> result = Arrays.stream(stackTrace).filter(x -> RegexWrapper.getTestNameFromClassName(x.getMethodName()) != null).findFirst();
        if (!result.isPresent()) {
            errorAndStop("Unable to extract test name like AUTXX_ from test class name, make sure you give this test class a proper name that includes the JIRA test name AUTXX_", false);
        } else {
            return RegexWrapper.getTestNameFromClassName(result.get().getMethodName());
        }
        return null; // will never run, here just to avoid code warning
    }

    public CpLoginPage2FA loginCpUsingNonCorporateEmailPassword(String nonCorporateEmail, boolean return2FaLoginPage) {
        browser.typeTextElement(emailTextbox, nonCorporateEmail);
        browser.typeTextElement(passwordTextbox, Users.getProperty(UsersProperty.automationCpPassword));
        browser.click(signInButton, false);
        if (return2FaLoginPage) {
            return new CpLoginPage2FA(browser);
        } else {
            return null;
        }
    }

    public boolean injectRecaptchaToken(String token) {
        String script =
                "function findChildWithRef(parent, refName) {\n" +
                "  if (parent.$refs && parent.$refs[refName]) {\n" +
                "    return parent;\n" +
                "  }\n" +
                "  for (const child of parent.$children) {\n" +
                "    const foundChild = findChildWithRef(child, refName);\n" +
                "    if (foundChild) return foundChild;\n" +
                "  }\n" +
                "  return null;\n" +
                "}\n" +
                "vueParentElement = document.getElementById('app').__vue__;\n" +
                "vueRecaptchaElement = findChildWithRef(vueParentElement, 'invisibleRecaptcha');\n" +
                "invisibleRecaptcha = vueRecaptchaElement.$refs['invisibleRecaptcha'];\n" +
                "invisibleRecaptcha.$emit('verify', '%s');";

        browser.executeScriptSilent(String.format(script, token));

        // wait for spinner to show and then vanish
        var signInSpinnerElement = browser.findElement(signInSpinner);
        browser.waitForElementToVanish(signInSpinnerElement, signInSpinner.getDescription());

        // return true if 2FA popup is visible (recaptcha passed) or false otherwise
        return !browser.findElementsQuick(CpLoginPage2FA.twoFaPrivateKeyTextfield, 3).isEmpty();
    }

}
