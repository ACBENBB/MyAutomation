package io.securitize.tests.controlPanel;

import io.securitize.pageObjects.controlPanel.CpAuditLog;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSideMenu;
import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT472_ISR_Login_And_Audit_Test extends AbstractUiTest {

    @Test(description = "AUT472 - Control panel - Login and validate the login event shows in the Audit page")
    public void AUT472_ISR_Login_And_Audit_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = AbstractCpInvestorRegistrationFlow.ISR_TestScenario.AUT472_ISR_Login_And_Audit;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel");
        getBrowser().navigateTo(td.url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        Date loginTime = new Date();
        loginPage
                .loginCpUsingEmailPassword()
                .obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
        endTestLevel();

        startTestLevel("BUG WORKAROUND: refresh page to create a full audit log event");
        getBrowser().refreshPage(true);
        endTestLevel();

        startTestLevel("Go to the Audit screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpAuditLog auditLog = sideMenu.clickAuditLog();
        endTestLevel();

        startTestLevel("Filter audit table for events of current user only");
        auditLog.waitForTableToBeNotEmpty();
        auditLog.searchByText("ggggggg");
        auditLog.waitForTableToBeEmpty();
        auditLog.searchByText(td.userToSearch);
        auditLog.waitForTableToBeNotEmpty();
        endTestLevel();

        startTestLevel("Get first row and make sure its date/time is recent and the operator matches");
        SoftAssertions softAssertions = new SoftAssertions();
        String firstRowOperator = auditLog.getTableData(1, "Operator");
        softAssertions.assertThat(firstRowOperator).as("First row operator").isEqualTo(td.userToSearch);
        String firstRowDateTime = auditLog.getTableData(1, "Date & Time");
        endTestLevel();

        startTestLevel("Compare between login time and actual time");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        try {
            Date actualDate = dateFormat.parse(firstRowDateTime);

            // Extract minutes and seconds from actual date
            long actualMinutes = actualDate.toInstant().getEpochSecond() % 3600 / 60;
            long actualSeconds = actualDate.toInstant().getEpochSecond() % 60;

            // Extract minutes and seconds from new date
            long loginTimeMinutes = loginTime.toInstant().getEpochSecond() % 3600 / 60;
            long loginTimeSeconds = loginTime.toInstant().getEpochSecond() % 60;

            // Calculate the difference in seconds between actual and new dates
            long dateTimeDiffInSeconds = Math.abs((actualMinutes * 60 + actualSeconds) - (loginTimeMinutes * 60 + loginTimeSeconds));

            //  long dateTimeDiffInSeconds = Math.abs(loginTime.getTime() - newDate.getTime()) / 1000;
            softAssertions.assertThat(dateTimeDiffInSeconds).as("Date & Time value difference in seconds").isLessThan(td.dateTimeDifferanceToleranceSeconds);
            info("Actual time is: " + actualDate);
            info("Login time is: " + loginTime);
            info("The difference in second is: "+actualMinutes * 60 + actualSeconds+" - "+loginTimeMinutes * 60 + loginTimeSeconds+" = "+dateTimeDiffInSeconds);
            softAssertions.assertAll();

        } catch (ParseException e) {
            String errorMessage = "Error parsing date: " + e.getMessage();
            errorAndStop(errorMessage, true);
        }
        endTestLevel();
    }

    private ISR_TestData createTestDataObject(AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario) {
        return new ISR_TestData(testScenario);
    }
}