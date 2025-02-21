package io.securitize.infra.api;

import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

import static io.restassured.RestAssured.given;

public class AvalancheAPI {

    private AvalancheAPI() {
        throw new IllegalStateException("AvalancheAPI is a utility class and should not be instantiated");
    }

    public static double getWalletBalance(String walletAddress) {

        String url = "https://api.avax.network/ext/bc/C/rpc";

        JSONArray jsonArray = new JSONArray()
            .put(walletAddress)
            .put("latest");

        JSONObject body = new JSONObject()
            .put("jsonrpc", "2.0")
            .put("method", "eth_getBalance")
            .put("params", jsonArray)
            .put("id", 1);

        String balanceAsString = given()
                .when()
                .contentType(ContentType.JSON)
                .log().all()
                .body(body.toString())
                .post(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("result");

        // remove hex prefix of 0x
        String balanceWithoutPrefix = balanceAsString.substring(2);

        // convert value from hex to regular double
        var balanceAsBigInteger = new BigInteger(balanceWithoutPrefix, 16);
        var balanceAsBigDecimal = new BigDecimal(balanceAsBigInteger);
        return Convert.fromWei(balanceAsBigDecimal, Convert.Unit.ETHER).doubleValue();
    }
}
