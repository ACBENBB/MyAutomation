package io.securitize.tests.transferAgent.pojo;

public class w9TaxForm {

    private String businessName;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String exemptFATCAReportingCodes;
    private String exemptPayeeCodes;
    private String formClassification;
    private String formInvestorName;
    private String formType;
    private String postalCode;
    private String region;
    private String taxPayerIdNumber;
    private String taxPayerIdType;
    private String taxCountry;

    public w9TaxForm(String taxformType) {
        this.formType = "W-9";
        this.country = "US";
        this.taxCountry = "US";
        if (taxformType.equalsIgnoreCase("Entity")) {
            this.formClassification = "SINGLE";
        } else {
            this.formClassification = "INDIVIDUAL";
        }
        this.formInvestorName = "Test";
        if (taxformType.equalsIgnoreCase("Entity")) {
            this.businessName = "Securitize, Inc";
        }
        this.address1 = "Street Test 14th";
        this.address2 = "";
        this.postalCode = "80014";
        this.region = "CO";
        this.city = "Denver";
        this.taxPayerIdType = "SSN";
        this.taxPayerIdNumber = String.format("3354");
        this.exemptFATCAReportingCodes = null;
        this.exemptPayeeCodes = null;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public String getBusinessName() {
        return businessName;
    }
    public String getTaxCountry() {
        return taxCountry;
    }

    public void setTaxCountry(String taxCountry) {
        this.taxCountry = taxCountry;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExemptFATCAReportingCodes() {
        return exemptFATCAReportingCodes;
    }

    public void setExemptFATCAReportingCodes(String exemptFATCAReportingCodes) {
        this.exemptFATCAReportingCodes = exemptFATCAReportingCodes;
    }

    public String getExemptPayeeCodes() {
        return exemptPayeeCodes;
    }

    public void setExemptPayeeCodes(String exemptPayeeCodes) {
        this.exemptPayeeCodes = exemptPayeeCodes;
    }

    public String getFormClassification() {
        return formClassification;
    }

    public void setFormClassification(String formClassification) {
        this.formClassification = formClassification;
    }

    public String getFormInvestorName() {
        return formInvestorName;
    }

    public void setFormInvestorName(String formInvestorName) {
        this.formInvestorName = formInvestorName;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTaxPayerIdNumber() {
        return taxPayerIdNumber;
    }

    public void setTaxPayerIdNumber(String taxPayerIdNumber) {
        this.taxPayerIdNumber = taxPayerIdNumber;
    }

    public String getTaxPayerIdType() {
        return taxPayerIdType;
    }

    public void setTaxPayerIdType(String taxPayerIdType) {
        this.taxPayerIdType = taxPayerIdType;
    }
}
