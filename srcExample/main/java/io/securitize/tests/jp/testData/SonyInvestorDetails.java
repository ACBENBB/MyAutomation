package io.securitize.tests.jp.testData;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SonyInvestorDetails extends InvestorDetailsCommon {
    private String branchNumber;
    private String accountType;
    private String accountNumber;
    private String birthDate;
    private String emailOfContact;
    private String address;
    private String residenceCountryCode;
    private String suitabilityCode;
    private int hashIterations;
    private String telephoneNumber;
    private String ssoExecutionDate;      // yyyyMMdd
    private String ssoExecutionTime;      // HHmmss
    private String ssoUri;
    private String redirectUri;
    private Map<String, String> countryCodeCountryMap = new HashMap<>();

    public SonyInvestorDetails() {
        this.suitabilityCode = "3";
        this.hashIterations = Integer.parseInt(Users.getProperty(UsersProperty.sonySsoHashIterations));
        setBirthDate(getRandomBirthday(LocalDate.of(1955, 1, 1).toEpochDay(), LocalDate.of(2005, 1, 1).toEpochDay()));
        setCurrentSsoExecutionDateTime();
        String baseUri = Users.getProperty(UsersProperty.sonyInvestorSiteBaseUrl);
        setUris(baseUri, "api/v1/session/sso", "/opportunities");
        setCountryCodeCountryMap();
    }

    public String getRandomBirthday(long dayStart, long dayEnd) {
        long randomDay = dayStart + random.nextInt((int)dayEnd - (int)dayStart);
        return LocalDate.ofEpochDay(randomDay).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getSsoData() {
        return getBranchNumber() +
                getAccountType() +
                getAccountNumber() +
                getSsoExecutionDate() +
                getSsoExecutionTime() +
                getFamilyName() +
                getGivenName() +
                getFamilyNameKana() +
                getGivenNameKana() +
                getAddress() +
                getEmailOfContact() +
                getResidenceCountryCode() +
                getBirthDate() +
                getSuitabilityCode();
    }

    public void setCurrentSsoExecutionDateTime() {
        Date date = new Date();
        TimeZone jst = TimeZone.getTimeZone("Asia/Tokyo");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        dateFormat.setTimeZone(jst);
        timeFormat.setTimeZone(jst);
        setSsoExecutionDate(dateFormat.format(date));
        setSsoExecutionTime(timeFormat.format(date));
    }

    public void setBankAccountNumber(String branchNumber, String accountType, String accountNumber) {
        setBranchNumber(branchNumber);
        setAccountType(accountType);
        setAccountNumber(accountNumber);
        setEmailOfContact(branchNumber + accountType + accountNumber + "@example.io");
    }

    public void setUris(String baseUri, String sso, String dashboard) {
        setSsoUri(baseUri.replace("#", sso));
        setRedirectUri(baseUri + dashboard);
    }

    private void setCountryCodeCountryMap() {
        countryCodeCountryMap.put("US", "United States of America");
        countryCodeCountryMap.put("HK", "Hong Kong");
        countryCodeCountryMap.put("JP", "Japan");
    }

    public String getCountryCode(Random random) {
        String[] countryCodeList = {
                "US",
                "HK",
                "JP"
        };
        return getRandomValueFromArray(countryCodeList, random);
    }

    public String getCountryNameFromCountryCode(String countryCode) {
        return countryCodeCountryMap.get(countryCode);
    }

    public void setAddressAndCountryCodeRandomly() {
        String addressLine = getAddressRandomly(random);
        String[] addresses = addressLine.split(",");
        setAddress(addresses[2] + addresses[3] + addresses[4] + addresses[5]);
        setResidenceCountryCode(getCountryCode(random));
    }

}
