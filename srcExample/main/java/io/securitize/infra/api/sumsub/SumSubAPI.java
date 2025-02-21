package io.securitize.infra.api.sumsub;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.utils.JsonUtils;
import io.securitize.tests.InvestorDetails;
import okhttp3.*;
import okio.Buffer;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static io.securitize.infra.reporting.MultiReporter.*;

public class SumSubAPI {

    public static void setApplicantAsTestComplete(String securitizeIdProfileId) {
        try {
            infoAndShowMessageInBrowser("Run api call: sumsub setApplicantAsTestComplete");

            String applicantID = SumSubAPI.getApplicantId(securitizeIdProfileId);
            info(String.format("securitizeProfileId=%s is applicantId=%s", securitizeIdProfileId, applicantID));

            String originalApplicantStatus = SumSubAPI.getApplicantStatus(applicantID);
            info("Original applicant status: " + originalApplicantStatus);

            String response = SumSubAPI.setApplicantTestComplete(applicantID);
            info("Set applicant status test complete response: " + response);

            String updatedApplicantStatus = SumSubAPI.getApplicantStatus(applicantID);
            info("Updated applicant status: " + updatedApplicantStatus);
        } catch (Exception e) {
            MultiReporter.errorAndStop("An error occur trying to update applicant status as TestComplete. Details: " + e, false);
        }
    }

    public static String setApplicantTestComplete(String applicantId) {
        infoAndShowMessageInBrowser("Run api call: sumsub: setApplicantTestComplete");
        // https://developers.sumsub.com/api-reference/#access-tokens-for-sdks
        try {
            String body = new JSONObject()
                    .put("reviewAnswer", "GREEN")
                    .put("rejectLabels", new JSONArray()).toString();

            Response response = sendPost("/resources/applicants/" + applicantId + "/status/testCompleted", RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body));

            ResponseBody responseBody = response.body();
            return responseBody != null ? responseBody.string() : null;
        } catch (Exception e) {
            errorAndStop("An error occur trying to set applicant as test complete. Details: " + e, false);
            return null;
        }
    }

    public static void setApplicantAsRejected(String securitizeIdProfileId, RejectionLabels[] rejectionLabels, ReviewRejectType reviewRejectType, String moderationComment) {
        try {
            infoAndShowMessageInBrowser("Run api call: sumsub setApplicantAsRejected");

            String applicantID = SumSubAPI.getApplicantId(securitizeIdProfileId);
            info(String.format("securitizeProfileId=%s is applicantId=%s", securitizeIdProfileId, applicantID));

            String originalApplicantStatus = SumSubAPI.getApplicantStatus(applicantID);
            info("Original applicant status: " + originalApplicantStatus);

            String response = SumSubAPI.setApplicantRejection(applicantID, rejectionLabels, reviewRejectType, moderationComment);
            info("Set applicant status rejected response: " + response);

            String updatedApplicantStatus = SumSubAPI.getApplicantStatus(applicantID);
            info("Updated applicant status: " + updatedApplicantStatus);
        } catch (Exception e) {
            MultiReporter.errorAndStop("An error occur trying to update applicant status as Rejected. Details: " + e, false);
        }
    }

    private static String setApplicantRejection(String applicantId, RejectionLabels[] rejectionLabels, ReviewRejectType reviewRejectType, String moderationComment) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        // https://developers.sumsub.com/api-reference/#access-tokens-for-sdks

        String body = new JSONObject()
                .put("reviewAnswer", "RED")
                .put("reviewRejectType", reviewRejectType)
                .put("moderationComment", moderationComment)
                .put("rejectLabels", new JSONArray(rejectionLabels)).toString();

        Response response = sendPost("/resources/applicants/" + applicantId + "/status/testCompleted", RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body));

        ResponseBody responseBody = response.body();
        return responseBody != null ? responseBody.string() : null;
    }

    public static void setApplicantAsProvidedInfo(String securitizeIdProfileId) {
        try {
            infoAndShowMessageInBrowser("Run api call: sumsub setApplicantAsProvidedInfo");

            String applicantID = SumSubAPI.getApplicantId(securitizeIdProfileId);
            info(String.format("securitizeProfileId=%s is applicantId=%s", securitizeIdProfileId, applicantID));

            String originalApplicantStatus = SumSubAPI.getApplicantStatus(applicantID);
            info("Original applicant status: " + originalApplicantStatus);

            String response = SumSubAPI.setApplicantProvidedInfo(applicantID);
            info("Set applicant status provided info: " + response);
            Thread.sleep(10000);
            String updatedApplicantStatus = SumSubAPI.getApplicantStatus(applicantID);
            info("Updated applicant status: " + updatedApplicantStatus);
        } catch (Exception e) {
            MultiReporter.errorAndStop("An error occur trying to update applicant status as ProvidedInfo. Details: " + e, false);
        }
    }

    public static String setApplicantProvidedInfo(String applicantId) {
        infoAndShowMessageInBrowser("Run api call: sumsub: setApplicantProvidedInfo");
        // https://developers.sumsub.com/api-reference/#access-tokens-for-sdks
        try{
            String body = new JSONObject()
                    .put("middleName", "test-middleName").toString();
            Response response = sendPatch("/resources/applicants/" + applicantId + "/fixedInfo", RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body));
            ResponseBody responseBody = response.body();
            return responseBody != null ? responseBody.string() : null;
        }  catch (Exception e) {
            errorAndStop("An error occur trying to set applicant as ProvidedInfo. Details: " + e, false);
            return null;
        }
    }

    public static int addIdDocument(String applicantID, IdDocType idDocType, IdDocSubType idDocSubType, String imagePath, String countryShortName) {
        for (int i = 0; i < 10; i++) {
            int result = SumSubAPI.internalAddIdDocument(applicantID, idDocType, idDocSubType, imagePath, countryShortName);
            if (result == 200) {
                return i;
            } else if (result == 409) {
                debug("Checking if the current phase " + idDocType + " is required by Sumsub..");
                List<String> listOfRequiredLevels = SumSubAPI.getListRequiredDocuments(applicantID);
                if (listOfRequiredLevels.contains(idDocType.toString())) {
                    debug("It seems Sumsub needs a few more seconds to digest previous images before accepting the selfie image.. Giving them 10 seconds and trying again");
                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        debug("Got interrupted while sleeping.. Will abort now...");
                        return 99;
                    }
                } else {
                    debug("For some reason Sumsub doesn't yet require the next phase of document " + idDocType + ". Updating it now...");
                    SumSubAPI.setNextRequiredDocument(applicantID, idDocType.getLevelName());
                }
            } else if (result == 400) {
                MultiReporter.info("Sumsub rejected our image with 400, will try again if possible");
            } else {
                MultiReporter.errorAndStop("Sumsub didn't accept with 200 our uploaded image... Can't resume", false);
            }
        }

        MultiReporter.errorAndStop("Sumsub didn't accept our uploaded image even after 10 attempts, Can't resume", false);
        return 999;
    }

    private static int internalAddIdDocument(String applicantID, IdDocType idDocType, IdDocSubType idDocSubType, String imagePath, String countryShortName) {
        try {
            infoAndShowMessageInBrowser("Run api call: sumsub addIdDocument");

            // sumsub expected ISO3 country codes: so no 'IL' but 'ISR', no 'US' but 'USA'
            Locale locale = new Locale("", countryShortName);
            String iso3CountryCode = locale.getISO3Country();

            JSONObject metaData = new JSONObject()
                    .put("idDocType", idDocType)
                    .put("idDocSubType", idDocSubType)
                    .put("country", iso3CountryCode);

            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("metadata", metaData.toString())
                    .addFormDataPart("content", "file.jpg", RequestBody.create(MediaType.parse("image/jpeg"), new File(imagePath)))
                    .build();

            long ts = Instant.now().getEpochSecond();

            String url = "/resources/applicants/" + applicantID + "/info/idDoc";
            Request request = new Request.Builder()
                    .url(MainConfig.getProperty(MainConfigProperty.sumSubBaseUrl) + url)
                    .header("X-App-Token", Users.getProperty(UsersProperty.sumSubAppToken))
                    .header("X-App-Access-Sig", createSignature(ts, HttpMethod.POST, url, requestBodyToBytes(body)))
                    .header("X-App-Access-Ts", String.valueOf(ts))
                    .header("X-Return-Doc-Warnings", "true")
                    .header("Content-Type", "multipart/form-data")
                    .post(body)
                    .build();

            Response response = new OkHttpClient().newCall(request).execute();
            info("finished with status code: " + response.code());
            String responseBody = response.body() == null ? "no body" : response.body().string();
            info("finished with body: " + responseBody);

            // sometimes SumSub response has code of 200 but response body has errors inside
            if (new JSONObject(responseBody).has("errors")) {
                info("SumSub returned status of 200 but has errors in the body. Returning 500 instead");
                return 400;
            } else {
                return response.code();
            }
        } catch (Exception e) {
            MultiReporter.errorAndStop("An error occur trying to upload applicant id document. Details: " + e, false);
            return -1;
        }
    }

    public static List<String> getListRequiredDocuments(String applicantID) {
        List<String> result = new ArrayList<>();

        try {
            RequestBody reqbody = RequestBody.create(null, new byte[0]);
            long ts = Instant.now().getEpochSecond();

            String url = "/resources/applicants/" + applicantID + "/requiredIdDocs";
            Request request = new Request.Builder()
                    .url(MainConfig.getProperty(MainConfigProperty.sumSubBaseUrl) + url)
                    .header("X-App-Token", Users.getProperty(UsersProperty.sumSubAppToken))
                    .header("X-App-Access-Sig", createSignature(ts, HttpMethod.POST, url, requestBodyToBytes(reqbody)))
                    .header("X-App-Access-Ts", String.valueOf(ts))
                    .header("Content-Type", "application/json")
                    .post(reqbody)
                    .build();

            Response response = new OkHttpClient().newCall(request).execute();
            info("finished with status code: " + response.code());
            String responseBody = response.body() == null ? "no body" : response.body().string();
            info("finished with body: " + responseBody);

            JSONObject responseBodyAsJson = new JSONObject(responseBody);
            JSONArray requiredDocuments = responseBodyAsJson.getJSONArray("docSets");
            for (int i = 0; i < requiredDocuments.length(); i++) {
                JSONObject current = requiredDocuments.getJSONObject(i);
                result.add(current.getString("idDocSetType"));
            }
        } catch (Exception e) {
            MultiReporter.errorAndStop("An error occur trying to get list of applicant required documents. Details: " + e, false);
        }
        return result;
    }

    public static void setNextRequiredDocument(String applicantID, String levelName) {
        try {
            RequestBody reqbody = RequestBody.create(null, new byte[0]);
            long ts = Instant.now().getEpochSecond();

            String url = "/resources/applicants/" + applicantID + "/moveToLevel?name=" + levelName;
            Request request = new Request.Builder()
                    .url(MainConfig.getProperty(MainConfigProperty.sumSubBaseUrl) + url)
                    .header("X-App-Token", Users.getProperty(UsersProperty.sumSubAppToken))
                    .header("X-App-Access-Sig", createSignature(ts, HttpMethod.POST, url, requestBodyToBytes(reqbody)))
                    .header("X-App-Access-Ts", String.valueOf(ts))
                    .header("Content-Type", "application/json")
                    .post(reqbody)
                    .build();

            Response response = new OkHttpClient().newCall(request).execute();
            info("finished with status code: " + response.code());
            String responseBody = response.body() == null ? "no body" : response.body().string();
            info("finished with body: " + responseBody);
        } catch (Exception e) {
            MultiReporter.errorAndStop("An error occur trying to set next document level for applicant. Details: " + e, false);
        }
    }

    public static String getApplicantId(String investorId) {
        infoAndShowMessageInBrowser("Run api call: sumsub: getApplicantId");

        try {
            Response response = sendGet("/resources/applicants/-;externalUserId=" + investorId);

            return extractKeyFromJsonResponseBody(response, "id", "Applicant Id");
        } catch (Exception e) {
            errorAndStop("An error occur trying to obtain applicant Id from investor id of " + investorId + ". Deatils: " + e, false);
            return null;
        }
    }

    public static String getApplicantStatus(String applicantId) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        // https://developers.sumsub.com/api-reference/#getting-applicant-status-api

        Response response = sendGet("/resources/applicants/" + applicantId + "/status");
//        Response response = sendGet("/resources/applicants/" + applicantId + "/requiredIdDocsStatus");

        return extractKeyFromJsonResponseBody(response, "reviewStatus", "Review status");
    }

    public static void updateApplicantOCRData(String applicantId, InvestorDetails investorDetails) {
        infoAndShowMessageInBrowser("Run api call: sumsub: updateApplicantOCRData");

        // https://developers.sumsub.com/api-reference/#changing-applicant-data

        try {
            String body = new JSONObject()
                    .put("firstName", investorDetails.getFirstName())
                    .put("middleName", investorDetails.getMiddleName())
                    .put("lastName", investorDetails.getLastName())
                    .put("dob", investorDetails.getSumSubBirthDate())
                    .toString();

            Response response = sendPatch("/resources/applicants/" + applicantId + "/info", RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body));

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                responseBody.string();
            }
        } catch (Exception e) {
            errorAndStop("An error occur trying to update applicant OCR data. Details: " + e, false);
        }
    }

    private static Response sendPost(String url, RequestBody requestBody) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        long ts = Instant.now().getEpochSecond();

        Request request = new Request.Builder()
                .url(MainConfig.getProperty(MainConfigProperty.sumSubBaseUrl) + url)
                .header("X-App-Token", Users.getProperty(UsersProperty.sumSubAppToken))
                .header("X-App-Access-Sig", createSignature(ts, HttpMethod.POST, url, requestBodyToBytes(requestBody)))
                .header("X-App-Access-Ts", String.valueOf(ts))
                .post(requestBody)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (response.code() != 200 && response.code() != 201) {
            MultiReporter.errorAndStop("An error occur: SumSub response code isn't 2xx. Details: " + response.code(), false);
            if (response.body() != null) {
                MultiReporter.errorAndStop("Response body: " + response.body().string(), false);
            }
            // https://developers.sumsub.com/api-reference/#errors
            // If an unsuccessful answer is received, please log the value of the "correlationId" parameter.
            // Then perhaps you should throw the exception. (depends on the logic of your code)
        }
        return response;
    }

    private static Response sendPatch(String url, RequestBody requestBody) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        long ts = Instant.now().getEpochSecond();

        Request request = new Request.Builder()
                .url(MainConfig.getProperty(MainConfigProperty.sumSubBaseUrl) + url)
                .header("X-App-Token", Users.getProperty(UsersProperty.sumSubAppToken))
                .header("X-App-Access-Sig", createSignature(ts, HttpMethod.PATCH, url, requestBodyToBytes(requestBody)))
                .header("X-App-Access-Ts", String.valueOf(ts))
                .patch(requestBody)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (response.code() != 200 && response.code() != 201) {
            MultiReporter.errorAndStop("An error occur: SumSub response code isn't 2xx. Details: " + response.code(), false);
            if (response.body() != null) {
                MultiReporter.errorAndStop("Response body: " + response.body().string(), false);
            }
            // https://developers.sumsub.com/api-reference/#errors
            // If an unsuccessful answer is received, please log the value of the "correlationId" parameter.
            // Then perhaps you should throw the exception. (depends on the logic of your code)
        }
        return response;
    }

    private static Response sendGet(String url) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        long ts = Instant.now().getEpochSecond();

        Request request = new Request.Builder()
                .url(MainConfig.getProperty(MainConfigProperty.sumSubBaseUrl) + url)
                .header("X-App-Token", Users.getProperty(UsersProperty.sumSubAppToken))
                .header("X-App-Access-Sig", createSignature(ts, HttpMethod.GET, url, null))
                .header("X-App-Access-Ts", String.valueOf(ts))
                .get()
                .build();

        Response response = new OkHttpClient().newCall(request).execute();

        if (response.code() != 200 && response.code() != 201) {
            MultiReporter.errorAndStop("An error occur: SumSub response code isn't 2xx. Details: " + response.code(), false);
            if (response.body() != null) {
                MultiReporter.errorAndStop("Response body: " + response.body().string(), false);
            }
            // https://developers.sumsub.com/api-reference/#errors
            // If an unsuccessful answer is received, please log the value of the "correlationId" parameter.
            // Then perhaps you should throw the exception. (depends on the logic of your code)
        }
        return response;
    }

    private static String createSignature(long ts, HttpMethod httpMethod, String path, byte[] body) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(new SecretKeySpec(Users.getProperty(UsersProperty.sumSubSecretKey).getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        hmacSha256.update((ts + httpMethod.name() + path).getBytes(StandardCharsets.UTF_8));
        byte[] bytes = body == null ? hmacSha256.doFinal() : hmacSha256.doFinal(body);
        return Hex.encodeHexString(bytes);
    }

    private static byte[] requestBodyToBytes(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readByteArray();
    }

    private static String extractKeyFromJsonResponseBody(Response response, String key, String description) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            MultiReporter.errorAndStop("An error occur: SumSub response body is null", false);
        } else {
            String responseBodyAsString = responseBody.string();
            JsonElement responseAsJsonObject = JsonParser.parseString(responseBodyAsString);
            String applicantId = JsonUtils.searchJsonRecursivelyForKey(responseAsJsonObject, key);
            if (applicantId != null) {
                info(description + ": " + applicantId);
                return applicantId;
            }
        }

        return null;
    }
}