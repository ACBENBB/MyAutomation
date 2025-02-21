package io.securitize.tests.transferAgent;

import io.securitize.tests.transferAgent.abstractClass.AbstractTaxForm;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT408_TA_TaxForm_Autocomplete_Entity_NON_US extends AbstractTaxForm {

    @Test(description = "TA - Tax Form Autocomplete for Entity NON US Investor")
    public void AUT408_TA_TaxForm_Autocomplete_Entity_NON_US() {

        final String investorType = TaxFormAutoCompleteInvestorType.ENTITY_NONUS.toString();
        startTestLevel("Starting tax form submit of a non us entity investor and autocomplete data validation");
        getInvestorDataFromInvestorType(investorType);
        loginToSecIdWithInvestorType(investorType);
        navigateToTaxFormPage();
        completeTaxForm(investorType);
        validateSubmittedTaxForm(investorType);
        endTestLevel();
    }

}