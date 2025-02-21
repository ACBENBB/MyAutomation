package io.securitize.tests.controlPanel.abstractClass;

import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import org.testng.asserts.SoftAssert;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public abstract class AbstractCpIssuerConfiguration extends AbstractCpTokens {

    private final String configurationVariables = "configuration variables";
    private final String NON_VALID_SEARCH = "ThisInvestorShouldNotExist";

    public void cpAddRound(String roundName, String startDate, String closeDate, String issuanceDate, String minInvestmentCrypto, String minInvestmentFiat, String terms, String tokenValue) {

        startTestLevel("Open Add Round screen");
        CpConfigurationFundraise configurationFundraise = new CpConfigurationFundraise(getBrowser());
        configurationFundraise.clickAddRound();
        endTestLevel();

        startTestLevel("Fill round details");
        CpAddRound addRound = new CpAddRound(getBrowser());
        addRound.setRoundName(roundName);
        addRound.switchRoundToActive();
        addRound.setMinimumInvestmentFiat(minInvestmentFiat);
        addRound.setMinimumInvestmentCrypto(minInvestmentCrypto);
        addRound.setStartDate(startDate);
        addRound.setEndDate(closeDate);
        addRound.setIssuanceDate(issuanceDate);
        addRound.setTokenValue(tokenValue);
        addRound.setText(terms);
        addRound.clickAddRoundButton();
        endTestLevel();
    }

    public void cpEditRound(String roundName, String startDate, String closeDate, String issuanceDate, String minInvestmentCrypto, String minInvestmentFiat, String tokenValue) {

        startTestLevel("Edit Round");
        CpConfigurationFundraise configurationFundraise = new CpConfigurationFundraise(getBrowser());
        configurationFundraise.clickEditRound();
        endTestLevel();

        startTestLevel("Update round details");
        CpAddRound addRound = new CpAddRound(getBrowser());
        addRound.setRoundName(roundName);
        addRound.switchRoundToActive();
        addRound.setMinimumInvestmentFiat(minInvestmentFiat);
        addRound.setMinimumInvestmentCrypto(minInvestmentCrypto);
        addRound.setStartDate(startDate);
        addRound.setEndDate(closeDate);
        addRound.setIssuanceDate(issuanceDate);
        addRound.setTokenValue(tokenValue);
        addRound.clickEditRoundButton();
        endTestLevel();
    }

    public void cpDeleteRound() {

        startTestLevel("Delete Round");
        CpConfigurationFundraise configurationFundraise = new CpConfigurationFundraise(getBrowser());
        configurationFundraise.clickDeleteRound();
        endTestLevel();

        startTestLevel("Confirm Delete Round");
        configurationFundraise.clickOkButton();
        endTestLevel();

        startTestLevel("Assert Round was deleted");
        SoftAssert softAssertion = new SoftAssert();
        configurationFundraise.waitForRoundTableToBeStable();
        int actualRoundRows = configurationFundraise.getRowCount();
        softAssertion.assertEquals(actualRoundRows, 1, "Round name was found on Rounds table");
        softAssertion.assertAll();
        endTestLevel();
    }

    public void cpRemoveAllUnwantedRounds() {

        startTestLevel("Clean up unwanted Rounds");
        CpConfigurationFundraise configurationFundraise = new CpConfigurationFundraise(getBrowser());
        configurationFundraise.removeAllNonDefaultRounds();
        endTestLevel();
    }

    public void cpVerifyExistingInvestorPrivacyControl(boolean privacyControl) {
        String investorFullName = "Investor PC Test";
        startTestLevel("Navigate to Onboarding");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Filtering by investor's first name");
        onBoarding.searchUniqueInvestorByTextBox(investorFullName, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Enter to investor details");
        CpInvestorDetailsPage investorDetailsPage = onBoarding.clickShowInvestorDetailsByName(investorFullName);
        endTestLevel();

        if (privacyControl) {
            startTestLevel("Validate investor details");
            SoftAssert softAssertion = new SoftAssert();
            softAssertion.assertEquals(investorDetailsPage.getFirstName(), "Investor", "incorrect first name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getMiddleName(), "PC", "incorrect middle name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getLastName(), "Test", "incorrect last name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getEmail(), "investorpctest+1@securitize.io", "incorrect email, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getPhoneNumber(), "********", "incorrect phone number, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getBirthday(), "********", "incorrect birthday, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getGender(), "Male", "incorrect gender, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getAddress(), "********", "incorrect address, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), "********", "incorrect address additional info, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getPostalCode(), "********", "incorrect postal code, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getCity(), "********", "incorrect city, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getCountry(), "United States of America", "incorrect country, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getState(), "Alabama", "incorrect state, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getTaxCountry(), "********", "incorrect tax country, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getComment(), "Comment me", "incorrect comment, this is not the value we set!");
            softAssertion.assertAll();
            endTestLevel();
        }

        if (!privacyControl) {
            startTestLevel("Validate investor details");
            SoftAssert softAssertion = new SoftAssert();
            softAssertion.assertEquals(investorDetailsPage.getFirstName(), "Investor", "incorrect first name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getMiddleName(), "PC", "incorrect middle name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getLastName(), "Test", "incorrect last name, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getEmail(), "investorpctest+1@securitize.io", "incorrect email, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getPhoneNumber(), "0546765698", "incorrect phone number, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getBirthday(), "May 6, 1970", "incorrect birthday, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getGender(), "Male", "incorrect gender, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getAddress(), "Some Street", "incorrect address, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), "Some Info", "incorrect address additional info, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getPostalCode(), "6574654", "incorrect postal code, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getCity(), "Tel Aviv", "incorrect city, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getCountry(), "United States of America", "incorrect country, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getState(), "Alabama", "incorrect state, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getTaxCountry(), "Azerbaijan", "incorrect tax country, this is not the value we set!");
            softAssertion.assertEquals(investorDetailsPage.getComment(), "Comment me", "incorrect comment, this is not the value we set!");
            softAssertion.assertAll();
            endTestLevel();
        }

    }

    public void privacyControlCheck(boolean privacyControl) {
        startTestLevel("Navigate to Issuer Configuration Variables");
        navigateToPage(configurationVariables);
        endTestLevel();

        startTestLevel("Search privacy control variable");
        CpIssuerConfigurationVariables issuerConfigurationVariables = new CpIssuerConfigurationVariables(getBrowser());
        issuerConfigurationVariables.searchPrivacyControlVariable();
        endTestLevel();

        startTestLevel("Set privacy control");
        issuerConfigurationVariables.setPrivacyControl(privacyControl);
        cpVerifyExistingInvestorPrivacyControl(privacyControl);
        endTestLevel();
    }
}
