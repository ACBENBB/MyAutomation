package io.securitize.tests.apiTests.cicd.SID.SID_I18nService;


import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class SID_I18nService extends BaseApiTest {

    @Test()
    public void getSystemLanguagesTest368() {
        testRequest(Method.GET, "http://i18n-service:5018.{internalUrlToRemoteRunCicdApi}/{system}/languages", "getSystemLanguages", LoginAs.NONE, "SID/i18n-service", "{\"$ref\":\"#/definitions/languagesList\"}");
    }


    @Test()
    public void getSystemTemplatesLanguageNameTest638() {
        testRequest(Method.GET, "http://i18n-service:5018.{internalUrlToRemoteRunCicdApi}/{system}/templates/{language}/{name}", "getSystemTemplatesLanguageName", LoginAs.NONE, "SID/i18n-service", "{\"$ref\":\"#/definitions/templateResponse\"}");
    }


    @Test()
    public void getSystemTranslationsTest566() {
        testRequest(Method.GET, "http://i18n-service:5018.{internalUrlToRemoteRunCicdApi}/{system}/translations", "getSystemTranslations", LoginAs.NONE, "SID/i18n-service", "{\"$ref\":\"#/definitions/Model3\"}");
    }


    @Test()
    public void putSystemTranslationsKeyTest673() {
        testRequest(Method.PUT, "http://i18n-service:5018.{internalUrlToRemoteRunCicdApi}/{system}/translations/{key}", "putSystemTranslationsKey", LoginAs.NONE, "SID/i18n-service", "{\"$ref\":\"#/definitions/Model6\"}");
    }


    @Test()
    public void getSystemTranslationsLanguageTest617() {
        testRequest(Method.GET, "http://i18n-service:5018.{internalUrlToRemoteRunCicdApi}/{system}/translations/{language}", "getSystemTranslationsLanguage", LoginAs.NONE, "SID/i18n-service", "{\"$ref\":\"#/definitions/translationsMap\"}");
    }


    @Test()
    public void getHealthTest585() {
        testRequest(Method.GET, "http://i18n-service:5018.{internalUrlToRemoteRunCicdApi}/health", "getHealth", LoginAs.NONE, "SID/i18n-service", "{\"type\":\"string\"}");
    }


    @Test()
    public void getTranslationsTest862() {
        testRequest(Method.GET, "http://i18n-service:5018.{internalUrlToRemoteRunCicdApi}/translations", "getTranslations", LoginAs.NONE, "SID/i18n-service", "{\"$ref\":\"#/definitions/Model3\"}");
    }

}

