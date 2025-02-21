package io.securitize.tests.transferAgent;

import io.securitize.tests.transferAgent.abstractClass.AbstractTaxForm;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT289_TA_TaxForm_Autocomplete_Individual_NONUS extends AbstractTaxForm {

    @Test(description = "TA - Tax Form Autocomplete for Individual NON US Investor")
    public void AUT289_TA_TaxForm_Autocomplete_Individual_NONUS() {

        final String investorType = TaxFormAutoCompleteInvestorType.INDIVIDUAL_NONUS.toString();
        startTestLevel("Starting tax form submit of a non us individual investor and autocomplete data verification");
        getInvestorDataFromInvestorType(investorType);
        loginToSecIdWithInvestorType(investorType);
        navigateToTaxFormPage();
        completeTaxForm(investorType);
        validateSubmittedTaxForm(investorType);
        endTestLevel();
    }

}