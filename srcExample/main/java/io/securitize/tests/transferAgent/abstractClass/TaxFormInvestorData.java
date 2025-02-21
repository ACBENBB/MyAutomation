package io.securitize.tests.transferAgent.abstractClass;

public class TaxFormInvestorData {

        String investorType;
        String firstName;
        String midName;
        String lastName;
        String streetName;
        String country;
        String city;
        String postalCode;
        String stateRegion;
        String stateRegionShort;
        String stateRegionNonUs;
        String federalTaxClass;
        String entityFederalTaxClass;
        String taxIdentifierType;
        String taxPayerId;
        String ssn;
        String entityName;
        String entityBusinessName;
        String entityDba;
        String dobYear;
        String dobMonth;
        String dobDay;

        public TaxFormInvestorData() {
                this.taxIdentifierType = "Social Security Number (SSN)";
                this.stateRegionShort = "CO";
                this.entityName = "Entity Test Name";
                this.federalTaxClass = "Individual";
                this.entityFederalTaxClass = "Sole Proprietor";
                this.stateRegionNonUs = "Santa Fe";
        }

}