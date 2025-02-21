package io.securitize.tests.apiTests.cicd.ATS.ATS_AtsAccountApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ATS_AtsAccountApi extends BaseApiTest {

    @Test()
    public void AssetsController_getDsTokensBalanceTest418() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/accounts/{investorId}/web3-balance/ds-tokens", "AssetsController_getDsTokensBalance", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetDSTokensBalanceResponseDto\"}}}}");
    }


    @Test()
    public void BalanceController_getTokenBalanceTest634() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/accounts/{investorId}/balance/tokens", "BalanceController_getTokenBalance", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"Get Tokens Balance\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/TokenBalance\"}}}}}");
    }


    @Test()
    public void AccountFavouritesController_getFavouritesTest732() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/accounts/{investorId}/favourites", "AccountFavouritesController_getFavourites", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"Get account favourites\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetFavouriteDto\"}}}}}");
    }


    @Test()
    public void FavouritesController_getFavouritesTest782() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/favourites", "FavouritesController_getFavourites", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"Get favourites\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetFavouriteDto\"}}}}}");
    }


    @Test()
    public void SummariesController_getAccountSummaryTest333() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/accounts/{investorId}/summary", "SummariesController_getAccountSummary", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AccountSummary\"}}}}");
    }


    @Test()
    public void PreferencesController_getPreferencesTest776() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/preferences/{investorId}", "PreferencesController_getPreferences", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"Get preferences\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Preferences\"}}}}");
    }


    @Test()
    public void AssetsController_getWeb3BalanceTest268() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/accounts/{investorId}/web3-balance", "AssetsController_getWeb3Balance", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetWeb3BalanceResponseDto\"}}}}");
    }


    @Test()
    public void AccountController_getUserWalletsTest965() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/accounts/{investorId}/wallets", "AccountController_getUserWallets", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"Get User Wallets\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Wallets\"}}}}");
    }


    @Test()
    public void HealthController_checkTest374() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/api/health", "HealthController_check", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void AssetsController_getAssetsTest322() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/accounts/{investorId}/assets", "AssetsController_getAssets", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/AssetBalance\"}}}}}");
    }


    @Test()
    public void PrometheusController_indexTest292() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/metrics", "PrometheusController_index", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"\"}");
    }


    @Test()
    public void AssetsController_getStableCoinsBalanceTest774() {
        testRequest(Method.GET, "https://ats-account-api.internal.{environment}.securitize.io/v1/accounts/{investorId}/web3-balance/stable-coins", "AssetsController_getStableCoinsBalance", LoginAs.NONE, "ATS/ats-account-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetStableCoinsBalanceResponseDto\"}}}}");
    }




}

