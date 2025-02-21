package io.securitize.infra.utils;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.jp.testData.SonyInvestorDetails;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

/**
 * to get url for sony single sign on
 */
public class SingleSignOnSony {

    private SingleSignOnSony() {
        throw new IllegalStateException("Utility class");
    }

    private static String createHash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(text.getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte b : hashBytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch(NoSuchAlgorithmException e) {
           errorAndStop("exception in createHash: " + e, false);
        }
        return null;
    }

    private static String bankDataWithTimestamp(String branchCode, String accountNumber, String depositType, String birthdate, String riskLevel, String countryCode) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        return branchCode + depositType + accountNumber + dateFormat.format(date) + birthdate + riskLevel + countryCode;
    }

    private static String decrypt(String text, String key) {
        String[] textParts = text.split(":");
        byte[] iv = hexStringToByteArray(textParts[0]);
        byte[] encryptedText = hexStringToByteArray(textParts[1]);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // [note] I think data passes through a secure network
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decrypted = cipher.doFinal(encryptedText);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            errorAndStop("exception in decrypt: " + e, false);
        }
        return null;
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    public static String getData(String branchCode, String accountNumber, String depositType, String birthdate, String riskLevel, String countryCode) {
        String bankData = bankDataWithTimestamp(branchCode, accountNumber, depositType, birthdate, riskLevel, countryCode);
        String salt = Users.getProperty(UsersProperty.sonySsoSalt);
        String secret = Users.getProperty(UsersProperty.sonySsoSecret);
        String decryptedSalt = decrypt(salt, secret);
        String hash = createHash(bankData + decryptedSalt);
        return bankData + hash;
    }

    public static String getData(SonyInvestorDetails investorDetails) {
        String bankData = investorDetails.getSsoData();
        int hashIterations = investorDetails.getHashIterations();
        String salt = Users.getProperty(UsersProperty.sonySsoSalt);
        String secret = Users.getProperty(UsersProperty.sonySsoSecret);
        String decryptedSalt = decrypt(salt, secret);
        String hash = createHash(bankData + decryptedSalt);
        for (int i = 1; i < hashIterations ; i++) {
            hash = createHash(Objects.requireNonNull(hash));
        }
        return hash;
    }

    /**
     * get url to single sign on
     * @param branchCode 3-digit number e.g. 001
     * @param accountNumber 7-digit number e.g. 1234567
     * @param depositType 1-digit number e.g. 1
     * @param birthDate YYYY-MM-DD e.g. 2000-06-19
     * @param riskLevel 1-digit number 1, 2 or 3
     * @param countryCode country code e.g. JP, US, HK
     * @return url to single sign on
     */
    public static String getUrl(String branchCode, String accountNumber, String depositType, String birthDate, String riskLevel, String countryCode) {
        String baseUrl = Users.getProperty(UsersProperty.sonyInvestorSiteBaseUrl);
        String data = getData(branchCode, accountNumber, depositType, birthDate, riskLevel, countryCode);
        return baseUrl + "/opportunities?r01=" + data;
    }

    /**
     * (1) current sso url in use, which is WITHOUT birthdate, risk level, and country code
     */
    public static String getUrl(String branchCode, String accountNumber) {
        String depositType = "1";
        String birthDate = "";
        String riskLevel = "";
        String countryCode = "";
        return getUrl(branchCode, accountNumber, depositType, birthDate, riskLevel, countryCode);
    }

    /**
     * (2) additional new sso url, which is WITH birthdate, risk level, and country code
     */
    public static String getUrl(String branchCode, String accountNumber, String birthDate, String riskLevel, String countryCode) {
        String depositType = "1";
        return getUrl(branchCode, accountNumber, depositType, birthDate, riskLevel, countryCode);
    }

    /**
     *  (3) sso login with post
     */
    public static String getJavascriptCodeForSso(SonyInvestorDetails investorDetails) {
        String hashValue = getData(investorDetails);
        return
               "const form = document.createElement('form');" +
               "form.method = 'POST';" +
               "form.action = '" + investorDetails.getSsoUri() + "';" +
               "form.hidden = true;" +
               "[" +
                   "['data[brchNum]', '" + investorDetails.getBranchNumber() + "']," +
                   "['data[acTyp]', '" + investorDetails.getAccountType() + "']," +
                   "['data[acNum]', '" + investorDetails.getAccountNumber() + "']," +
                   "['data[lnkDt]', '" + investorDetails.getSsoExecutionDate() + "']," +
                   "['data[lnkTm]', '" + investorDetails.getSsoExecutionTime() + "']," +
                   "['data[additional][0][key]', 'familyNmKanji']," +
                   "['data[additional][0][value]', '" + investorDetails.getFamilyName() + "']," +
                   "['data[additional][1][key]', 'nmKanji']," +
                   "['data[additional][1][value]', '" + investorDetails.getGivenName() + "']," +
                   "['data[additional][2][key]', 'familyNmKana']," +
                   "['data[additional][2][value]', '" + investorDetails.getFamilyNameKana() + "']," +
                   "['data[additional][3][key]', 'nmKana']," +
                   "['data[additional][3][value]', '" + investorDetails.getGivenNameKana() + "']," +
                   "['data[additional][4][key]', 'addrKanji']," +
                   "['data[additional][4][value]', '" + investorDetails.getAddress() + "']," +
                   "['data[additional][5][key]', 'eMail']," +
                   "['data[additional][5][value]', '" + investorDetails.getEmailOfContact() + "']," +
                   "['data[additional][6][key]', 'residencecntryCd']," +
                   "['data[additional][6][value]', '" + investorDetails.getResidenceCountryCode() + "']," +
                   "['data[additional][7][key]', 'brthDate']," +
                   "['data[additional][7][value]', '" + investorDetails.getBirthDate() + "']," +
                   "['data[additional][8][key]', 'suitabilityCd']," +
                   "['data[additional][8][value]', '" + investorDetails.getSuitabilityCode() + "']," +
                   "['data[hash]', '" + hashValue + "']," +
                   "['redirectUri', '" + investorDetails.getRedirectUri() + "']," +
                   "['provider', 'sony']," +
               "].forEach(([name, value]) => {" +
                   "const input = document.createElement('input');" +
                   "input.type = 'hidden';" +
                   "input.name = name;" +
                   "input.value = value;" +
                   "form.appendChild(input);" +
               "});" +
               "document.body.appendChild(form);" +
               "form.submit();" +
               "document.body.removeChild(form);";
    }

}
