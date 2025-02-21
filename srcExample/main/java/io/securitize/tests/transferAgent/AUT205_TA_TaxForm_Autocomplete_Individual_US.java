package io.securitize.tests.transferAgent;

import io.securitize.infra.utils.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractTaxForm;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT205_TA_TaxForm_Autocomplete_Individual_US extends AbstractTaxForm {

    @BypassRecaptcha(environments = "production")
    @Test(description = "TA - Tax Form Autocomplete for Individual US Investor")
    public void AUT205_TA_TaxForm_Autocomplete_Individual_US() {

        final String investorType = TaxFormAutoCompleteInvestorType.INDIVIDUAL_US.toString();
        startTestLevel("Starting submitting individual us investor taxform and validating fields autocomplete");
        getInvestorDataFromInvestorType(investorType);
        loginToSecIdWithInvestorType(investorType);
        navigateToTaxFormPage();
        completeTaxForm(investorType);
        validateSubmittedTaxForm(investorType);
        endTestLevel();
    }

}