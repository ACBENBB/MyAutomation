package io.securitize.tests.blockchain.abstractClass;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.api.LoginAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.webdriver.Browser;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage2FA;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSideMenu;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSignatures;
import io.securitize.tests.abstractClass.AbstractUiTest;
import java.util.UUID;
import java.util.function.Function;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.info;

public class AbstractBC extends AbstractUiTest {

    public String generateRandomTokenAUT() {
            UUID uuid = UUID.randomUUID();
            String randomStr = uuid.toString().replace("-", "").substring(0, 3);
            return "AUT_499_" + randomStr;
    }

    public void loginToCp() {
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl));
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingEmailPassword();
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
    }

    public boolean signTransaction(String userName, String walletAddress, String privateKey){
        CpSideMenu cpSideMenu = new CpSideMenu(getBrowser());
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
        cpSideMenu.clickSignatures();
        cpSignatures.waitForPendingTableToContainRowsByDescriptionContent(userName, 0)
                .checkRowByUserFirstName(userName)
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(walletAddress)
                .typePrivateKey(privateKey)
                .clickSignNow();
        return cpSignatures.filterByStatus("Sent")
                .waitForTableToContainRowsByDescriptionContent(userName, 0)
                .isTableContainingRowsByDescriptionContent(userName, 0);

    }
    public String getTokenBalancesByType(String issuerId,String investorCpId, String tokenId, String balanceType) {

        LoginAPI loginAPI = new LoginAPI();
        Response loginResponse = loginAPI.postLoginControlPanelReturnResponse();
        // LOGIN SESSION TOKEN
        String headerSetCookie[] = loginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];
        info("bearerToken -- > " + bearerToken);

        String env = MainConfig.getProperty(MainConfigProperty.environment);

        RequestSpecification request = new RequestSpecBuilder()
                .setBaseUri("https://cp."+env+".securitize.io")
                .addHeader("accessToken", bearerToken)
                .addCookies(loginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();


        String response2 = given().log().all()
                .spec(request)
                .when().get("https://cp."+env+".securitize.io"+"/api/v2/issuers/" + issuerId + "/investors/" + investorCpId + "/tokens?token-id=" + tokenId)
                .then().assertThat().statusCode(200).extract().response().asString();

        info(response2);

        JsonPath jsonPath = new JsonPath(response2);
        return jsonPath.getString(balanceType);
    }

    public void assertTokenBalance(String issuerId,String investorCpId, String tokenId, String balanceType, int expectingTokenBalance) {

        // keep trying to get issues securities until the expected result matches.
        Function<String, Boolean> internalWaitForTokenBalance = t -> {
            int actualTokenBalance = Integer.parseInt(getTokenBalancesByType(issuerId,investorCpId, tokenId, balanceType));

            info("actualTokenBalance - > " + actualTokenBalance);
            info("expectedTokenBalance - > " + expectingTokenBalance);

            if(expectingTokenBalance == actualTokenBalance) {
                return true;
            } else {
                return false;
            }
        };
        String description = "Expecting Token Balance To Be: " + expectingTokenBalance;
        Browser.waitForExpressionToEqual(internalWaitForTokenBalance, null, true, description, 120, 20000);

    }

}