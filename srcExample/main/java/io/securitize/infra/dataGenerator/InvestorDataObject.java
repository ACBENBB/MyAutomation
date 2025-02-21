package io.securitize.infra.dataGenerator;

import com.opencsv.bean.CsvBindByPosition;

public class InvestorDataObject {

    //Personal Information
    @CsvBindByPosition(position = 0)
    private String firstName = "";
    @CsvBindByPosition(position = 1)
    private String middleName = "";
    @CsvBindByPosition(position = 2)
    private String lastName = "";
    @CsvBindByPosition(position = 3)
    private String email = "";
    @CsvBindByPosition(position = 4)
    private String password = "";
    @CsvBindByPosition(position = 5)
    private String contactEmail = "";
    @CsvBindByPosition(position = 6)
    private String phonePrefix = "";
    @CsvBindByPosition(position = 7)
    private String phone = "";
    @CsvBindByPosition(position = 8)
    private String birthDay = "";
    @CsvBindByPosition(position = 9)
    private String gender = "";
    @CsvBindByPosition(position = 10)
    private String country = "";
    @CsvBindByPosition(position = 11)
    private String countryCode = "";
    @CsvBindByPosition(position = 12)
    private String state = "";
    @CsvBindByPosition(position = 13)
    private String stateCode = "";
    @CsvBindByPosition(position = 14)
    private String city = "";
    @CsvBindByPosition(position = 15)
    private String postalCode = "";
    @CsvBindByPosition(position = 16)
    private String address1 = "";

    private String address2 = "";
    @CsvBindByPosition(position = 17)
    private String taxID = "";
    @CsvBindByPosition(position = 18)
    private String taxCountry = "";
    @CsvBindByPosition(position = 19)
    private String taxCountryCode = "";
    @CsvBindByPosition(position = 20)
    private String taxID2 = "";
    @CsvBindByPosition(position = 21)
    private String taxCountry2 = "";
    @CsvBindByPosition(position = 22)
    private String taxCountryCode2 = "";
    @CsvBindByPosition(position = 23)
    private String taxID3 = "";
    @CsvBindByPosition(position = 24)
    private String taxCountry3 = "";
    @CsvBindByPosition(position = 25)
    private String taxCountryCode3 = "";
    @CsvBindByPosition(position = 26)
    private String identityDocumentNumber = "";
    @CsvBindByPosition(position = 27)
    private String countryOfBirth = "";

    @CsvBindByPosition(position = 28)
    private String countryOfBirthCode = "";

    @CsvBindByPosition(position = 29)
    private String stateOfBirth = "";
    @CsvBindByPosition(position = 30)
    private String stateOfBirthCode = "";
    @CsvBindByPosition(position = 31)
    private String cityOfBirth = "";
    @CsvBindByPosition(position = 32)
    private String LTID = "";
    private String secID = "";

    //CP Information
    @CsvBindByPosition(position = 33)
    private String secIDType = null; //Accepts Individual/Entity
    @CsvBindByPosition(position = 34)
    private String verificationStatus = null; //Accepts YES/NO
    @CsvBindByPosition(position = 35)
    private String taxForm = "";
    @CsvBindByPosition(position = 36)
    private String legalSigner = "";
    @CsvBindByPosition(position = 37)
    private String isSuitabilityApproved = null; //Accepts YES/NO
    @CsvBindByPosition(position = 38)
    private String isCashAccountApproved = null; //Accepts YES/NO
    private String cashAccount = "";
    @CsvBindByPosition(position = 39)
    private String isAccreditationApproved = null; //Accepts YES/NO
    @CsvBindByPosition(position = 40)
    private String issuersID = "";
    @CsvBindByPosition(position = 41)
    private String tokensID = "";
    @CsvBindByPosition(position = 42)
    private String distributionsID = "";
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getState() {
        return state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getCountryOfBirthCode() {
        return countryOfBirthCode;
    }

    public String getStateOfBirth() {
        return stateOfBirth;
    }

    public String getStateOfBirthCode() {
        return stateOfBirthCode;
    }

    public void setCountryOfBirthCode(String countryOfBirthCode) {
        this.countryOfBirthCode = countryOfBirthCode;
    }

    public void setStateOfBirth(String stateOfBirth) {
        this.stateOfBirth = stateOfBirth;
    }

    public void setStateOfBirthCode(String stateOfBirthCode) {
        this.stateOfBirthCode = stateOfBirthCode;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getTaxCountry() {
        return taxCountry;
    }

    public void setTaxCountry(String taxCountry) {
        this.taxCountry = taxCountry;
    }

    public String getTaxID2() {
        return taxID2;
    }

    public void setTaxID2(String taxID2) {
        this.taxID2 = taxID2;
    }

    public String getTaxCountry2() {
        return taxCountry2;
    }

    public void setTaxCountry2(String taxCountry2) {
        this.taxCountry2 = taxCountry2;
    }

    public String getTaxID3() {
        return taxID3;
    }

    public void setTaxID3(String taxID3) {
        this.taxID3 = taxID3;
    }

    public String getTaxCountry3() {
        return taxCountry3;
    }

    public void setTaxCountry3(String taxCountry3) {
        this.taxCountry3 = taxCountry3;
    }

    public String getIdentityDocumentNumber() {
        return identityDocumentNumber;
    }

    public void setIdentityDocumentNumber(String identityDocumentNumber) {
        this.identityDocumentNumber = identityDocumentNumber;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public String getLTID() {
        return LTID;
    }

    public void setLTID(String LTID) {
        this.LTID = LTID;
    }

    public String getSecID() {
        return secID;
    }

    public void setSecID(String secID) {
        this.secID = secID;
    }

    public String getSecIDType() {
        return secIDType;
    }

    public void setSecIDType(String secIDType) {
        this.secIDType = secIDType;
    }

    public String[] getVerificationStatus() {
        String[] status = new String[]{verificationStatus};
        return status;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getTaxForm() {
        return taxForm;
    }

    public void setTaxForm(String taxForm) {
        this.taxForm = taxForm;
    }

    public String getLegalSigner() {
        return legalSigner;
    }

    public void setLegalSigner(String legalSigner) {
        this.legalSigner = legalSigner;
    }

    public String getIsSuitabilityApproved() {
        return isSuitabilityApproved;
    }

    public void setIsSuitabilityApproved(String isSuitabilityApproved) {
        this.isSuitabilityApproved = isSuitabilityApproved;
    }

    public String getIsCashAccountApproved() {
        return isCashAccountApproved;
    }

    public void setIsCashAccountApproved(String isCashAccountApproved) {
        this.isCashAccountApproved = isCashAccountApproved;
    }

    public String getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(String cashAccount) {
        this.cashAccount = cashAccount;
    }

    public String getIsAccreditationApproved() {
        return isAccreditationApproved;
    }

    public void setIsAccreditationApproved(String isAccreditationApproved) {
        this.isAccreditationApproved = isAccreditationApproved;
    }

    public String getIssuersID() {
        return issuersID;
    }

    public void setIssuersID(String issuersID) {
        this.issuersID = issuersID;
    }

    public String getTokensID() {
        return tokensID;
    }

    public void setTokensID(String tokensID) {
        this.tokensID = tokensID;
    }

    public String getDistributionsID() {
        return distributionsID;
    }

    public void setDistributionsID(String distributionsID) {
        this.distributionsID = distributionsID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTaxCountryCode() {
        return taxCountryCode;
    }

    public void setTaxCountryCode(String taxCountryCode) {
        this.taxCountryCode = taxCountryCode;
    }

    public String getTaxCountryCode2() {
        return taxCountryCode2;
    }

    public void setTaxCountryCode2(String taxCountryCode2) {
        this.taxCountryCode2 = taxCountryCode2;
    }

    public String getTaxCountryCode3() {
        return taxCountryCode3;
    }

    public void setTaxCountryCode3(String taxCountryCode3) {
        this.taxCountryCode3 = taxCountryCode3;
    }

    @Override
    public String toString() {
        return "InvestorDataObject{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", phonePrefix='" + phonePrefix + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", address='" + address1 + '\'' +
                ", taxID='" + taxID + '\'' +
                ", taxCountry='" + taxCountry + '\'' +
                ", taxCountryCode='" + taxCountryCode + '\'' +
                ", taxID2='" + taxID2 + '\'' +
                ", taxCountry2='" + taxCountry2 + '\'' +
                ", taxCountryCode2='" + taxCountryCode2 + '\'' +
                ", taxID3='" + taxID3 + '\'' +
                ", taxCountry3='" + taxCountry3 + '\'' +
                ", taxCountryCode3='" + taxCountryCode3 + '\'' +
                ", identityDocumentNumber='" + identityDocumentNumber + '\'' +
                ", countryOfBirth='" + countryOfBirth + '\'' +
                ", countryOfBirthCode='" + countryOfBirthCode + '\'' +
                ", cityOfBirth='" + cityOfBirth + '\'' +
                ", LTID='" + LTID + '\'' +
                ", secID='" + secID + '\'' +
                ", secIDType='" + secIDType + '\'' +
                ", verificationStatus='" + verificationStatus + '\'' +
                ", taxForm='" + taxForm + '\'' +
                ", legalSigner='" + legalSigner + '\'' +
                ", isSuitabilityApproved='" + isSuitabilityApproved + '\'' +
                ", isCashAccountApproved='" + isCashAccountApproved + '\'' +
                ", cashAccount='" + cashAccount + '\'' +
                ", isAccreditationApproved='" + isAccreditationApproved + '\'' +
                ", issuersID='" + issuersID + '\'' +
                ", tokensID='" + tokensID + '\'' +
                ", distributionsID='" + distributionsID + '\'' +
                '}';
    }
}
