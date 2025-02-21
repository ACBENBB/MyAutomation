package io.securitize.tests.apiTests.cicd.ATS.ATS_AtsGw;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ATS_AtsGw extends BaseApiTest {

    @Test()
    public void ConnectAssetsController_getAssetFiltersTest771() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/assets/filters", "ConnectAssetsController_getAssetFilters", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Filters\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetFiltersDto\"}}}}");
    }


    @Test()
    public void AssetsController_getAssetBySlugTest542() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets/slug/{slug}", "AssetsController_getAssetBySlug", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Data\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetDto\"}}}}");
    }


    @Test()
    public void BlockchainController_getBlockchainProviderTest695() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/blockchain/providers/{providerName}", "BlockchainController_getBlockchainProvider", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Blockchain Provider by name\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetBlockchainProviderDto\"}}}}");
    }


    @Test()
    public void ConnectAssetsController_getTradeHistorySummaryTest972() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/assets/{deploymentId}/trades", "ConnectAssetsController_getTradeHistorySummary", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Data for Asset Trade Chart\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetTradeChartTradesDto\"}}}}}");
    }


    @Test()
    public void ConnectWalletsController_getWalletBalanceTest365() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/account/wallets/{walletAddress}/web3-balance", "ConnectWalletsController_getWalletBalance", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Wallet Balance\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetWalletBalanceResponseDto\"}}}}");
    }


    @Test()
    public void ConnectBlockchainController_getBlockchainProviderTest55() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/blockchain/providers/{providerName}", "ConnectBlockchainController_getBlockchainProvider", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Blockchain Provider by name\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetBlockchainProviderDto\"}}}}");
    }


    @Test()
    public void ConnectAssetsController_getAssetCatalogTest388() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/assets/", "ConnectAssetsController_getAssetCatalog", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Catalog\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetAssetDto\"}}}}}");
    }


    @Test()
    public void AssetsController_getAssetInfoTest950() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets/{security}/info", "AssetsController_getAssetInfo", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Data for Asset Info Tab\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetInfoDto\"}}}}");
    }


    @Test()
    public void UserSummaryController_getUserSummaryTest483() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v2/user-summary", "UserSummaryController_getUserSummary", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get User Summary\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountSummaryDto\"}}}}");
    }


    @Test()
    public void ConnectAssetsController_getAssetBySlugTest895() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/assets/slug/{slug}", "ConnectAssetsController_getAssetBySlug", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Data\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetDto\"}}}}");
    }


    @Test()
    public void AssetsController_canTradeAssetTest765() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets/{security}/user-actions", "AssetsController_canTradeAsset", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get user actions for a specific asset\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetActions\"}}}}");
    }


    @Test()
    public void ConnectAssetsController_getTradeHistoryWithSummaryTest404() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v2/assets/{deploymentId}/trades", "ConnectAssetsController_getTradeHistoryWithSummary", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Data for Asset Trade Chart including summary\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetTradeChartWithSummaryDto\"}}}}");
    }


    @Test()
    public void AssetsController_getAssetFiltersTest995() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets/filters", "AssetsController_getAssetFilters", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Filters\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetFiltersDto\"}}}}");
    }


    @Test()
    public void HealthController_checkTest240() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/health", "HealthController_check", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void BalanceController_getUserBalanceTest489() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v2/account/balance", "BalanceController_getUserBalance", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get User Balance\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetBalanceDto\"}}}}");
    }


    @Test()
    public void AssetsController_getTradeHistorySummaryTest964() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets/{deploymentId}/trades", "AssetsController_getTradeHistorySummary", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Data for Asset Trade Chart\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetTradeChartTradesDto\"}}}}}");
    }


    @Test()
    public void WalletsController_getUserWalletsTest650() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/account/wallets", "WalletsController_getUserWallets", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Wallets\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetWalletsDto\"}}}}");
    }


    @Test()
    public void ConnectAssetsController_getOrderBookOldTest361() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/assets/{security}/book", "ConnectAssetsController_getOrderBookOld", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Order Book\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetOrderBookDtoOld\"}}}}}");
    }


    @Test()
    public void WalletsController_getWalletBalanceTest536() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/account/wallets/{walletAddress}/web3-balance", "WalletsController_getWalletBalance", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Wallet Balance\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetWalletBalanceResponseDto\"}}}}");
    }


    @Test()
    public void ConnectAssetsController_getTradeHistoryTest116() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/assets/{deploymentId}/trades-history", "ConnectAssetsController_getTradeHistory", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get history of trades for the asset with pagination\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetTradeHistoryDto\"}}}}");
    }


    @Test()
    public void AssetsController_getAssetCatalog_DeprecatedTest180() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets", "AssetsController_getAssetCatalog_Deprecated", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Catalog\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetAssetDto\"}}}}}");
    }


    @Test()
    public void AssetsController_getAssetTBEAddressTest782() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets/{deploymentId}/tbe-address", "AssetsController_getAssetTBEAddress", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Omnibus TBE Address\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetTBEAddressDto\"}}}}");
    }


    @Test()
    public void ConnectAssetsController_getAssetTBEAddressTest139() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/assets/{deploymentId}/tbe-address", "ConnectAssetsController_getAssetTBEAddress", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Omnibus TBE Address\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetTBEAddressDto\"}}}}");
    }


    @Test()
    public void ConnectHealthController_checkTest783() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/health", "ConnectHealthController_check", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void ConnectAssetsController_getOrderBookTest818() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v2/assets/{security}/book", "ConnectAssetsController_getOrderBook", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Order Book\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetOrderBookDto\"}}}}");
    }


    @Test()
    public void OrdersController_getOrdersTest165() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/orders", "OrdersController_getOrders", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get orders\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetOrdersDto\"}}}}");
    }


    @Test()
    public void ConnectOrdersController_getOrdersTest859() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/orders", "ConnectOrdersController_getOrders", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get orders\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetOrdersDto\"}}}}");
    }


    @Test()
    public void AssetsController_getTradeHistoryTest911() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets/{deploymentId}/trades-history", "AssetsController_getTradeHistory", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get history of trades for the asset with pagination\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetTradeHistoryDto\"}}}}");
    }


    @Test()
    public void ConnectConfigDeprecatedController_getConfigTest868() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/config", "ConnectConfigDeprecatedController_getConfig", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Config (Deprecated)\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetConfig_DeprecatedDto\"}}}}");
    }


    @Test()
    public void PreferencesController_getFavsTermsNotShowTest181() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/preferences/fav-terms-not-show", "PreferencesController_getFavsTermsNotShow", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Favourites Terms Preference\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetFavTermsNotShowDto\"}}}}");
    }


    @Test()
    public void AccountAssetsController_getAssetHoldingsTest77() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/account/assets", "AccountAssetsController_getAssetHoldings", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Holdings\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetAssetHoldingDto\"}}}}}");
    }


    @Test()
    public void ConfigDeprecatedController_getConfigDeprecatedTest870() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v2/config", "ConfigDeprecatedController_getConfigDeprecated", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Config (Deprecated)\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetConfig_DeprecatedDto\"}}}}");
    }


    @Test()
    public void UserSummaryController_getUserSummaryOldTest195() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/user-summary", "UserSummaryController_getUserSummaryOld", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get User Summary\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/UserSummary\"}}}}");
    }


    @Test()
    public void ConnectConfigController_getConfigTest612() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v2/config", "ConnectConfigController_getConfig", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Config\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetConfigDto\"}}}}");
    }


    @Test()
    public void ConfigController_getConfigTest375() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/config", "ConfigController_getConfig", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Config\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetConfigDto\"}}}}");
    }


    @Test()
    public void AssetsController_getAssetDetailsTest359() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets/asset-details", "AssetsController_getAssetDetails", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Details\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetDetailsDto\"}}}}");
    }


    @Test()
    public void AssetsController_getOrderBookOldTest690() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets/{security}/book", "AssetsController_getOrderBookOld", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Order Book\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetOrderBookDtoOld\"}}}}}");
    }


    @Test()
    public void ConnectAccountAssetsController_getAssetHoldingsTest42() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/account/assets", "ConnectAccountAssetsController_getAssetHoldings", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Holdings\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetAssetHoldingDto\"}}}}}");
    }


    @Test()
    public void ConnectPreferencesController_getFavsTermsNotShowTest369() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/preferences/fav-terms-not-show", "ConnectPreferencesController_getFavsTermsNotShow", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Favourites Terms Preference\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetFavTermsNotShowDto\"}}}}");
    }


    @Test()
    public void AssetsController_getOrderBookTest485() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v2/assets/{security}/book", "AssetsController_getOrderBook", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Asset Order Book\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetOrderBookDto\"}}}}");
    }


    @Test()
    public void ConnectAssetsController_getAssetInfoTest5() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/assets/{security}/info", "ConnectAssetsController_getAssetInfo", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Data for Asset Info Tab\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetInfoDto\"}}}}");
    }


    @Test()
    public void ConnectAssetsController_canTradeAssetTest792() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/assets/{security}/user-actions", "ConnectAssetsController_canTradeAsset", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get user actions for a specific asset\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetActions\"}}}}");
    }


    @Test()
    public void ConnectWalletsController_getUserWalletsTest447() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/account/wallets", "ConnectWalletsController_getUserWallets", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Wallets\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetWalletsDto\"}}}}");
    }


    @Test()
    public void AssetsController_getAssetCatalogTest830() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v1/assets-catalog", "AssetsController_getAssetCatalog", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Market Asset Catalog\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetAssetDto\"}}}}}");
    }


    @Test()
    public void AssetsController_getTradeHistoryWithSummaryTest753() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/api/v2/assets/{deploymentId}/trades", "AssetsController_getTradeHistoryWithSummary", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get Data for Asset Trade Chart including summary\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetTradeChartWithSummaryDto\"}}}}");
    }


    @Test()
    public void ConnectUserSummaryController_getUserSummaryTest453() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v1/user-summary", "ConnectUserSummaryController_getUserSummary", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get User Summary\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountSummaryDto\"}}}}");
    }


    @Test()
    public void ConnectBalanceController_getUserBalanceTest605() {
        testRequest(Method.GET, "https://ats-gw.{environment}.securitize.io/connect/v2/account/balance", "ConnectBalanceController_getUserBalance", LoginAs.SECURITIZE_ID, "ATS/ats-gw", "{\"description\":\"Get User Balance\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetBalanceDto\"}}}}");
    }

}

