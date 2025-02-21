package io.securitize.tests.transferAgent;

import io.securitize.tests.transferAgent.abstractClass.AbstractTaxForm;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT288_TA_TaxForm_Autocomplete_Entity_US extends AbstractTaxForm {

    @Test(description = "TA - Tax Form Autocomplete for Entity US Investor")
    public void AUT288_TA_TaxForm_Autocomplete_Entity_US() {

        final String investorType = TaxFormAutoCompleteInvestorType.ENTITY_US.toString();
        startTestLevel("Starting tax form submit of a us entity investor and autocomplete data verification");
        getInvestorDataFromInvestorType(investorType);
        loginToSecIdWithInvestorType(investorType);
        navigateToTaxFormPage();
        completeTaxForm(investorType);
        validateSubmittedTaxForm(investorType);
        endTestLevel();
    }

}