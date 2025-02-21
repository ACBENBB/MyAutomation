package io.securitize.infra.api.transferAgent;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.api.AbstractAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.tests.transferAgent.pojo.w8benTaxForm;
import io.securitize.tests.transferAgent.pojo.w8beneTaxForm;
import io.securitize.tests.transferAgent.pojo.w9TaxForm;
import org.json.JSONArray;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.info;

public class TaxFormAPI extends AbstractAPI {

    public String deleteTaxForm(Response loginResponse) {
        String headerSetCookie[] = loginResponse.getHeader("set-cookie").split(";");
        System.out.println("headerSetCookie " + headerSetCookie[0].split("=")[1]);
        JSONObject jsonObject = new JSONObject(loginResponse.getBody().asString());
        String bearerToken = jsonObject.get("token").toString();
        System.out.println("bearerToken" + bearerToken);

        String env = null;
        String baseUrl;
        if(MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production")) {
            baseUrl = "https://api-payouts.securitize.io";
        } else {
            env = MainConfig.getProperty(MainConfigProperty.environment);
            baseUrl = "https://api-payouts."+env+".securitize.io";
        }
        RequestSpecification taxFormRequestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .addHeader("authorization", bearerToken)
                .addCookies(loginResponse.getCookies())
                .build();

        String getTaxFormResponse = given().spec(taxFormRequestSpec).when().get("api/v1/tax-forms").then()
                .assertThat().statusCode(200).extract().response().asString();
        JSONObject taxFormResponseObject = new JSONObject(getTaxFormResponse);
        if(taxFormResponseObject.get("itemCount").toString().equalsIgnoreCase("0")) {
            try {
                throw new Exception("investor has no submitted taxforms");
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        } else {
            String taxFormId = getTaxFormId(taxFormResponseObject);
            return given().spec(taxFormRequestSpec).pathParam("taxFormId", taxFormId)
                    .when().delete("api/v1/tax-forms/{taxFormId}").then()
                    .assertThat().statusCode(200).extract().response().asString();
        }
    }

    public String getTaxFormId(JSONObject object) {
        JSONObject object1 = new JSONObject(object.toString());
        JSONArray dataObject = new JSONArray(object1.get("data").toString());
        JSONObject taxFormObject = new JSONObject(dataObject.get(0).toString());
        return taxFormObject.get("id").toString();
    }

    public String submitTaxFormViaApiByTaxFormType(Response loginResponse, String taxFormType) {
        // LOGIN SESSION TOKEN
        String headerSetCookie[] = loginResponse.getHeader("set-cookie").split(";");
        System.out.println("headerSetCookie " + headerSetCookie[0].split("=")[1]);
        JSONObject jsonObject = new JSONObject(loginResponse.getBody().asString());
        String bearerToken = jsonObject.get("token").toString();
        System.out.println("bearerToken" + bearerToken);

        deleteTaxForm(loginResponse);

        String env = MainConfig.getProperty(MainConfigProperty.environment);
        RequestSpecification taxFormRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api-payouts."+env+".securitize.io")
                .addHeader("authorization", bearerToken)
                .addCookies(loginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String getTaxFormResponse = null;
        String resource = "api/v1/tax-forms";
        if (taxFormType.equalsIgnoreCase("w9")) {
            getTaxFormResponse = given().log().all()
                    .spec(taxFormRequestSpec)
                    .body(new w9TaxForm("Individual"))
                    .when().post(resource + "/w9")
                    .then().assertThat().statusCode(201).extract().response().asString();
        } else if (taxFormType.equalsIgnoreCase("w8ben")) {
            getTaxFormResponse = given().log().all()
                    .spec(taxFormRequestSpec)
                    .body(new w8benTaxForm())
                    .when().post(resource + "/w8ben")
                    .then().extract().response().asString();
        } else if (taxFormType.equalsIgnoreCase("w8bene")) {
            // TODO FIX THIS W8BENE Submit POST request needs file attachment.
            getTaxFormResponse = given().log().all()
                    .spec(taxFormRequestSpec)
                    .body(new w8beneTaxForm())
                    .when().post(resource + "/w8bene")
                    .then().extract().response().asString();
        }
        return getTaxFormResponse;
    }

    public String submitTaxFormByType(Response loginResponse, String taxFormType) {
        // LOGIN SESSION TOKEN
        String headerSetCookie[] = loginResponse.getHeader("set-cookie").split(";");
        System.out.println("headerSetCookie " + headerSetCookie[0].split("=")[1]);
        JSONObject jsonObject = new JSONObject(loginResponse.getBody().asString());
        String bearerToken = jsonObject.get("token").toString();
        System.out.println("bearerToken" + bearerToken);

        String env = MainConfig.getProperty(MainConfigProperty.environment);
        RequestSpecification  taxFormRequestSpec2 = null;
        if (taxFormType.equalsIgnoreCase("w8bene")) {
            taxFormRequestSpec2 = new RequestSpecBuilder()
                    .setBaseUri("https://api-payouts." + env + ".securitize.io")
                    .addHeader("authorization", bearerToken)
                    .addCookies(loginResponse.getCookies())
                    .build();
        }
        RequestSpecification taxFormRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api-payouts." + env + ".securitize.io")
                .addHeader("authorization", bearerToken)
                .addCookies(loginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();


        String getTaxFormResponse = null;
        String resource = "api/v1/tax-forms";
        if (taxFormType.equalsIgnoreCase("w9")) {
            getTaxFormResponse = given().log().all()
                    .spec(taxFormRequestSpec)
                    .body(new w9TaxForm("Individual"))
                    .when().post(resource + "/w9")
                    .then().assertThat().statusCode(201).extract().response().asString();
        } else if (taxFormType.equalsIgnoreCase("w8ben")) {
            getTaxFormResponse = given().log().all()
                    .spec(taxFormRequestSpec)
                    .body(new w8benTaxForm())
                    .when().post(resource + "/w8ben")
                    .then().extract().response().asString();
        } else if (taxFormType.equalsIgnoreCase("w8bene")) {

            JSONObject json2 = new JSONObject();
            json2.put("formType", "W-8BENE");
            json2.put("contentType", "application/pdf");
            json2.put("fileKey", "tmp/6321df4b2d6d9e0013711bff/28012ef4-e410-461e-ba65-669e4a19c3fe.undefined");

            getTaxFormResponse = given().log().all()
                    .spec(taxFormRequestSpec)
                    .body(json2.toString())
                    .when().post(resource + "/w8ben-e")
                    .then().extract().response().asString();
            info("Submmited W8BEN-E: " + getTaxFormResponse);

        } else if (taxFormType.equalsIgnoreCase("w9e")) {
            getTaxFormResponse = given().log().all()
                    .spec(taxFormRequestSpec)
                    .body(new w9TaxForm("Entity"))
                    .when().post(resource + "/w9")
                    .then().extract().response().asString();

        }
        return getTaxFormResponse;
    }

}
