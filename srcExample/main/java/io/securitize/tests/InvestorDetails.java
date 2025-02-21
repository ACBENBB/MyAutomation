package io.securitize.tests;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.RandomGenerator;
import io.securitize.infra.utils.RegexWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.setTestDetails;

public class InvestorDetails {

    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthDate;
    private String sumSubBirthDate;
    private String prefixPhone;
    private String phoneNumber;
    private String documentNumber;
    private String address;
    private String streetName;
    private String streetNumber;
    private String streetAdditionalInfo;
    private String country;
    private String city;
    private String postalCode;
    private String state;
    private String countryOfBirth;
    private String cityOfBirth;
    private String countryOfTax;
    private String taxId;
    private String walletName;
    private String entityName;
    private String comment;
    private String gender;
    private String investorType;
    private String pledgedAmount;
    private String custodian;
    private String sourceOfFunds;
    private String lineOfBusiness;
    private String legalSigner;
    private String entityDba;
    private String entityType;
    private String entityIdNumber;

    Map<String, String> states = new HashMap<String, String>() {{
        put("Argentina", "AR");
        put("Alabama", "AL");
        put("Alaska", "AK");
        put("Alberta", "AB");
        put("American Samoa", "AS");
        put("Arizona", "AZ");
        put("Arkansas", "AR");
        put("Armed Forces (AE)", "AE");
        put("Armed Forces Americas", "AA");
        put("Armed Forces Pacific", "AP");
        put("British Columbia", "BC");
        put("California", "CA");
        put("Colorado", "CO");
        put("Connecticut", "CT");
        put("Delaware", "DE");
        put("District Of Columbia", "DC");
        put("Florida", "FL");
        put("Georgia", "GA");
        put("Guam", "GU");
        put("Hawaii", "HI");
        put("Idaho", "ID");
        put("Illinois", "IL");
        put("Indiana", "IN");
        put("Iowa", "IA");
        put("Kansas", "KS");
        put("Kentucky", "KY");
        put("Louisiana", "LA");
        put("Maine", "ME");
        put("Manitoba", "MB");
        put("Maryland", "MD");
        put("Massachusetts", "MA");
        put("Michigan", "MI");
        put("Minnesota", "MN");
        put("Mississippi", "MS");
        put("Missouri", "MO");
        put("Montana", "MT");
        put("Nebraska", "NE");
        put("Nevada", "NV");
        put("New Brunswick", "NB");
        put("New Hampshire", "NH");
        put("New Jersey", "NJ");
        put("New Mexico", "NM");
        put("New York", "NY");
        put("Newfoundland", "NF");
        put("North Carolina", "NC");
        put("North Dakota", "ND");
        put("Northwest Territories", "NT");
        put("Nova Scotia", "NS");
        put("Nunavut", "NU");
        put("Ohio", "OH");
        put("Oklahoma", "OK");
        put("Ontario", "ON");
        put("Oregon", "OR");
        put("Pennsylvania", "PA");
        put("Prince Edward Island", "PE");
        put("Puerto Rico", "PR");
        put("Quebec", "QC");
        put("Rhode Island", "RI");
        put("Saskatchewan", "SK");
        put("South Carolina", "SC");
        put("South Dakota", "SD");
        put("Tennessee", "TN");
        put("Texas", "TX");
        put("Utah", "UT");
        put("Vermont", "VT");
        put("Virgin Islands", "VI");
        put("Virginia", "VA");
        put("Washington", "WA");
        put("West Virginia", "WV");
        put("Wisconsin", "WI");
        put("Wyoming", "WY");
        put("Yukon Territory", "YT");
    }};

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setSumSubBirthDate(String sumSubBirthDate) {
        this.sumSubBirthDate = sumSubBirthDate;
    }

    public void setEntityDba(String entityDba) {
        this.entityDba = entityDba;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setStreetAdditionalInfo(String streetAdditionalInfo) {
        this.streetAdditionalInfo = streetAdditionalInfo;
    }

    public void setEntityIdNumber(String entityIdNumber) {
        this.entityIdNumber = entityIdNumber;
    }

    public void setLegalSigner(String legalSigner) {
        this.legalSigner = legalSigner;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setInvestorType(String investorType) {
        this.investorType = investorType;
    }

    public void setPledgedAmount(String pledgedAmount) {
        this.pledgedAmount = pledgedAmount;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public void setSourceOfFunds(String sourceOfFunds) {
        this.sourceOfFunds = sourceOfFunds;
    }

    public void setLineOfBusiness(String lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    private static String getJiraTestName() {
        // search the current stackTrace for a class containing AUTXX_ string and extract it
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Optional<StackTraceElement> result = Arrays.stream(stackTrace).filter(x -> RegexWrapper.getTestNameFromClassName(x.getClassName()) != null).findFirst();
        if (!result.isPresent()) {
            errorAndStop("Unable to extract test name like AUTXX_ from test class name, make sure you give this test class a proper name that includes the JIRA test name AUTXX_", false);
        } else {
            return RegexWrapper.getTestNameFromClassName(result.get().getClassName());
        }
        return null; // will never run, here just to avoid code warning
    }

    public String getLegalSigner() {
        return legalSigner;
    }

    public String getEntityDba() {
        return entityDba;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEntityIdNumber() {
        return entityIdNumber;
    }

    public static InvestorDetails generateRandomUSInvestor() {
        return generateRandomUSInvestor(".");
    }

    public static InvestorDetails generateRandomUSInvestor(boolean isApiTest, String apiTestName) {
        return generateRandomUSInvestor(".", isApiTest, apiTestName);
    }

    public static InvestorDetails generateRandomUSInvestor(String separator) {
        return generateRandomUSInvestor(separator, false, null);
    }

    public static InvestorDetails generateRandomUSInvestor(String separator, boolean isApiTest, String apiTestName) {
        InvestorDetails investorDetails = new InvestorDetails();

        String testName;
        if (isApiTest) {
            testName = apiTestName;
        } else {
            testName = getJiraTestName();
        }

        investorDetails.email = RandomGenerator.randomEmail(6).replace("@", "_" + testName + "@");
        setTestDetails(investorDetails.getEmail());

        investorDetails.password = Users.getProperty(UsersProperty.defaultPasswordComplex);

        setValidatedUIFirstNameAndLastName(separator, investorDetails);

        investorDetails.middleName = "middlename" + separator + testName + separator + RandomGenerator.randomString(6);
        investorDetails.birthDate = RandomGenerator.randomDate("MM/dd/yyyy");
        investorDetails.sumSubBirthDate = DateTimeUtils.convertDateFormat(investorDetails.birthDate, "MM/dd/yyyy", "yyyy-MM-dd");
        investorDetails.phoneNumber = "2015550124";
        investorDetails.documentNumber = RandomGenerator.randomNumber(10);
        investorDetails.address = "Address_" + RandomGenerator.randomString(6);
        investorDetails.streetName = "Street" + separator + RandomGenerator.randomString(6);
        investorDetails.streetNumber = RandomGenerator.randomNumber(10);
        investorDetails.streetAdditionalInfo = "StreetInfo." + RandomGenerator.randomString(6);
        investorDetails.country = "United States of America";
        investorDetails.city = "City" + separator + RandomGenerator.randomString(6);
        investorDetails.postalCode = RandomGenerator.randomNumber(5);
        investorDetails.state = "New York";
        investorDetails.countryOfBirth = "Israel";
        investorDetails.cityOfBirth = "Tel Aviv";
        investorDetails.countryOfTax = "United States of America";
        investorDetails.taxId = "222222222"; // Synapse requires this value to create User with Send and Receive status
        investorDetails.walletName = "metamask";
        investorDetails.entityName = "entity-" + RandomGenerator.randomString(10);
        investorDetails.comment = "comment_" + RandomGenerator.randomString(20);
        investorDetails.gender = "Male";
        investorDetails.investorType = "Individual";
        investorDetails.pledgedAmount = "10";
        investorDetails.custodian = "Automation Custodian";
        investorDetails.sourceOfFunds = "Sales";
        investorDetails.lineOfBusiness = "Insurance";

        return investorDetails;
    }

    @NotNull
    private static String getSeperatorReplacementByEnv() {
        String currentEnv = MainConfig.getProperty(MainConfigProperty.environment);
        String seperatorReplacement = (!currentEnv.equalsIgnoreCase("dev")) ? "" : " ";
        return seperatorReplacement;
    }

    public static InvestorDetails generateRandomNonUSInvestor() {
        return generateRandomNonUSInvestor(".");
    }

    public static InvestorDetails generateRandomNonUSInvestor(String separator) {
        return generateRandomNonUSInvestor(separator, false, null);
    }

    public static InvestorDetails generateRandomNonUSInvestor(String separator, boolean isApiTest, String apiTestName) {
        InvestorDetails investorDetails = new InvestorDetails();

        String testName;
        if (isApiTest) {
            testName = apiTestName;
        } else {
            testName = getJiraTestName();
        }

        investorDetails.email = RandomGenerator.randomEmail(6).replace("@", "_" + testName + "@");
        setTestDetails(investorDetails.getEmail());

        investorDetails.password = Users.getProperty(UsersProperty.defaultPasswordComplex);

        setValidatedUIFirstNameAndLastName(separator, investorDetails);

        investorDetails.middleName = "middlename" + separator + testName + separator + RandomGenerator.randomString(6);
        investorDetails.birthDate = RandomGenerator.randomDate("MM/dd/yyyy");
        investorDetails.sumSubBirthDate = DateTimeUtils.convertDateFormat(investorDetails.birthDate, "MM/dd/yyyy", "yyyy-MM-dd");
        investorDetails.phoneNumber = "01112345678";
        investorDetails.prefixPhone = "+972";
        investorDetails.documentNumber = RandomGenerator.randomNumber(10);
        investorDetails.address = "Address_" + RandomGenerator.randomString(6);
        investorDetails.streetName = "Street" + separator + RandomGenerator.randomString(6);
        investorDetails.streetNumber = RandomGenerator.randomNumber(10);
        investorDetails.streetAdditionalInfo = "StreetInfo." + RandomGenerator.randomString(6);
        investorDetails.country = "Argentina";
        investorDetails.city = "Rosario" + separator + RandomGenerator.randomString(6);
        investorDetails.postalCode = RandomGenerator.randomNumber(5);
        investorDetails.state = "Santa Fe";
        investorDetails.countryOfBirth = "Argentina";
        investorDetails.cityOfBirth = "Rosario";
        investorDetails.countryOfTax = "Argentina";
        investorDetails.taxId = RandomGenerator.randomNumber(9);
        investorDetails.walletName = "metamask";
        investorDetails.entityName = "entity-" + RandomGenerator.randomString(10);
        investorDetails.comment = "comment_" + RandomGenerator.randomString(20);
        investorDetails.gender = "Male";
        investorDetails.investorType = "Individual";
        investorDetails.pledgedAmount = "10";
        investorDetails.custodian = "Automation Custodian";

        return investorDetails;
    }

    private static void setValidatedUIFirstNameAndLastName(String separator, InvestorDetails investorDetails) {
        // due to UI validations modified 'firstName' & 'lastname':
        // 1.commented testName segment in 'firstName, as numbers are not allowed
        // 2. created method randomStringContainingOnlyLetters, as numbers are not allowed
        // 3. replace the dot (.) original separator to hyphen ("-"), as special characters are not allowed
        String seperatorReplace = getSeperatorReplacementByEnv();

        investorDetails.firstName = "firstname" +  separator.replace(separator,seperatorReplace) /* + testName + separator */ + RandomGenerator.randomStringContainingOnlyLetters(6);
        investorDetails.lastName = "lastname" + separator.replace(separator,seperatorReplace) + RandomGenerator.randomStringContainingOnlyLetters(6);
    }

    public static InvestorDetails generateRandomEntityInvestor() {
        return generateRandomEntityInvestor(".");
    }

    public static InvestorDetails generateRandomEntityInvestor(String separator) {
        return generateRandomEntityInvestor(separator, false, null);
    }

    public static InvestorDetails generateRandomEntityInvestor(String separator, boolean isApiTest, String apiTestName) {
        InvestorDetails investorDetails = new InvestorDetails();

        String testName;
        if (isApiTest) {
            testName = apiTestName;
        } else {
            testName = getJiraTestName();
        }

        investorDetails.email = RandomGenerator.randomEmail(6).replace("@", "_" + testName + "@");
        setTestDetails(investorDetails.getEmail());

        investorDetails.password = Users.getProperty(UsersProperty.defaultPasswordComplex);

        setValidatedUIFirstNameAndLastName(separator, investorDetails);

        investorDetails.middleName = "middlename" + separator + testName + separator + RandomGenerator.randomString(6);
        investorDetails.birthDate = RandomGenerator.randomDate("MM/dd/yyyy");
        investorDetails.sumSubBirthDate = DateTimeUtils.convertDateFormat(investorDetails.birthDate, "MM/dd/yyyy", "yyyy-MM-dd");
        investorDetails.phoneNumber = "12345678901";
        investorDetails.documentNumber = RandomGenerator.randomNumber(10);
        investorDetails.address = "Address_" + RandomGenerator.randomString(6);
        investorDetails.streetName = "Street" + separator + RandomGenerator.randomString(6);
        investorDetails.streetNumber = RandomGenerator.randomNumber(10);
        investorDetails.streetAdditionalInfo = "StreetInfo." + RandomGenerator.randomString(6);
        investorDetails.country = "United States of America";
        investorDetails.city = "City" + separator + RandomGenerator.randomString(6);
        investorDetails.postalCode = RandomGenerator.randomNumber(5);
        investorDetails.state = "New York";
        investorDetails.countryOfBirth = "Israel";
        investorDetails.cityOfBirth = "Tel Aviv";
        investorDetails.countryOfTax = "United States of America";
        investorDetails.taxId = RandomGenerator.randomNumber(9);
        investorDetails.walletName = "metamask";
        investorDetails.entityName = "entity-" + RandomGenerator.randomString(10);
        investorDetails.comment = "comment_" + RandomGenerator.randomString(20);
        investorDetails.gender = "Male";
        investorDetails.investorType = "Entity";
        investorDetails.pledgedAmount = "10";
        investorDetails.custodian = "Automation Custodian";
        investorDetails.sourceOfFunds = "Sales";
        investorDetails.lineOfBusiness = "Insurance";


        return investorDetails;
    }

    public static InvestorDetails generateUSInvestorForSSN() {
        InvestorDetails investorDetails = generateRandomUSInvestor();
        investorDetails.firstName = "JOHN";
        investorDetails.lastName = "SMITH";
        investorDetails.birthDate = "02/28/1975";
        investorDetails.countryOfTax = "United States of America";
        investorDetails.country = "United States of America";
        investorDetails.countryOfBirth = "United States of America";
        investorDetails.state = "Georgia";
        investorDetails.city = "ATLANTA";
        investorDetails.cityOfBirth = "ATLANTA";
        investorDetails.address = "222333 PEACHTREE PLACE";
        investorDetails.streetName = "222333 PEACHTREE PLACE";
        investorDetails.postalCode = "30318";
        investorDetails.taxId = "112223333";
        investorDetails.documentNumber = "3463463463";
        investorDetails.pledgedAmount = "10";
        investorDetails.phoneNumber = "12345678901";

        return investorDetails;
    }

    public static InvestorDetails generateCashAccountInvestorReady() {
        return generateCashAccountInvestorReady(".", false, null);
    }

    public static InvestorDetails generateCashAccountInvestorReady(String separator, boolean isApiTest, String apiTestName) {
        InvestorDetails investorDetails = new InvestorDetails();

        String testName;
        if (isApiTest) {
            testName = apiTestName;
        } else {
            testName = getJiraTestName();
        }

        investorDetails.email = RandomGenerator.randomEmail(6).replace("@", "_" + testName + "@");
        setTestDetails(investorDetails.getEmail());

        investorDetails.password = Users.getProperty(UsersProperty.defaultPasswordComplex);
        investorDetails.firstName = "JHON";
        investorDetails.middleName = null;
        investorDetails.lastName = "SMITH";
        investorDetails.birthDate = "05/07/1984";
        investorDetails.sumSubBirthDate = DateTimeUtils.convertDateFormat(investorDetails.birthDate, "MM/dd/yyyy", "yyyy-MM-dd");
        investorDetails.phoneNumber = "12345678901";
        investorDetails.documentNumber = RandomGenerator.randomNumber(10);
        investorDetails.address = "Address_" + RandomGenerator.randomString(6);
        investorDetails.streetName = "Street" + separator + RandomGenerator.randomString(6);
        investorDetails.streetNumber = RandomGenerator.randomNumber(10);
        investorDetails.streetAdditionalInfo = "StreetInfo." + RandomGenerator.randomString(6);
        investorDetails.country = "United States of America";
        investorDetails.city = "City" + separator + RandomGenerator.randomString(6);
        investorDetails.postalCode = RandomGenerator.randomNumber(5);
        // Look for special case for Accreditation First (test AUT539)
        if (investorDetails.email.contains("AUT539")) {
            investorDetails.state = "Alabama";
        } else {
            investorDetails.state = "Alaska";
        }
        investorDetails.countryOfBirth = "United States of America";
        investorDetails.cityOfBirth = "Colorado";
        investorDetails.countryOfTax = "United States of America";
        investorDetails.taxId = "222222222"; // Synapse requires this value to create User with Send and Receive status
        investorDetails.walletName = "metamask";
        investorDetails.entityName = "entity-" + RandomGenerator.randomString(10);
        investorDetails.comment = "comment_" + RandomGenerator.randomString(20);
        investorDetails.gender = "Male";
        investorDetails.investorType = "Individual";
        investorDetails.pledgedAmount = "10";
        investorDetails.custodian = "Automation Custodian";
        investorDetails.sourceOfFunds = "Sales";
        investorDetails.lineOfBusiness = "Insurance";

        return investorDetails;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPrefixPhoneNumber() {
        return prefixPhone;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    @SuppressWarnings("unused")
    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreetAdditionalInfo() {
        return streetAdditionalInfo;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getState() {
        return state;
    }

    public String getStateCode() {
        if (getCountryCode().equals("US")) {
            return states.get(state);
        } else if (getCountryCode().equals("AR")) {
            return states.get(state);
        } else if (getCountryCode().equals("IL")) {
            return null;
        } else {
            //errorAndStop("Only US states are supported at this time", false);
            return null;
        }
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public String getCountryOfBirthCode() {
        return getCodeOfCountry(countryOfBirth);
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public String getCountryOfTax() {
        return countryOfTax;
    }

    public String getCountryOfTaxCode() {
        return getCodeOfCountry(countryOfTax);
    }

    public String getTaxId() {
        return taxId;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryOfTax(String country) {
        this.countryOfTax = country;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getComment() {
        return comment;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return getCodeOfCountry(country);
    }

    private String getCodeOfCountry(String country) {
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }

        // try quick search
        String result = countries.get(country);
        if (result != null) {
            return result;
        }

        // if not found yet try advanced search such as cases of 'United states' vs 'United states of america'
        for (String currentCountry : countries.keySet()) {
            if (country.toLowerCase().startsWith(currentCountry.toLowerCase())) {
                return countries.get(currentCountry);
            }
        }

        errorAndStop("Country code not found for country: " + country, false);
        return null;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getInvestorType() {
        return investorType;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPledgedAmount() {
        return pledgedAmount;
    }

    public String getCustodian() {
        return custodian;
    }

    public String getSourceOfFunds() {
        return sourceOfFunds;
    }

    public String getLineOfBusiness() {
        return lineOfBusiness;
    }

    public String getSumSubBirthDate() {
        return sumSubBirthDate;
    }
}
