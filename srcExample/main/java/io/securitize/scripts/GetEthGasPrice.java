package io.securitize.scripts;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class GetEthGasPrice {

    private static final int DEFAULT_MAX_ALLOWED_GAS_PRICE_GWEI = 18;
    private static final String OUTPUT_MESSAGE_TEMPLATE = "Current ETH rate is %s (%s) - required rate to run tests is %s%n";
    private static final String OUTPUT_FILE_PATH = "output.txt";
    private static final String OUTPUT_FILE_GAS_PRICE_PATH = "gasPrice.txt";


    public static void main(String[] args) throws IOException {
        // load max allowed value from env
        int maxAllowedGasPrice = DEFAULT_MAX_ALLOWED_GAS_PRICE_GWEI;
        String maxAllowedGasPriceAsString = System.getenv("maxAllowedGasPrice");
        if (maxAllowedGasPriceAsString != null) {
            maxAllowedGasPrice = Integer.parseInt(maxAllowedGasPriceAsString);
        }

        int minimumGasPrice = getGasViaApi();

        String fullMessage;
        if (minimumGasPrice == -1) {
            writeFullMessage(-1, "An error occur trying to obtain gas price - check logs");
            System.exit(3);
        }
        else if (minimumGasPrice > maxAllowedGasPrice) {
            fullMessage = String.format(OUTPUT_MESSAGE_TEMPLATE, minimumGasPrice, "too high" , maxAllowedGasPrice);
            writeFullMessage(minimumGasPrice, fullMessage);
            System.exit(1);
        } else {
            fullMessage = String.format(OUTPUT_MESSAGE_TEMPLATE, minimumGasPrice, "valid" , maxAllowedGasPrice);
            writeFullMessage(minimumGasPrice, fullMessage);
            System.exit(0);
        }
   }

    private static void writeFullMessage(int minimumGasPrice, String fullMessage) {
        System.out.println(fullMessage);
        try {
            Files.write(Paths.get(OUTPUT_FILE_PATH), fullMessage.getBytes());
            Files.write(Paths.get(OUTPUT_FILE_GAS_PRICE_PATH), (minimumGasPrice+"").getBytes());
        } catch (IOException e) {
            System.err.println("An error occur trying to write to output file: " + ExceptionUtils.getStackTrace(e));
            System.exit(2);
        }
    }


    public static int getGasViaApi() {
        try {
            String baseFeeAsString = given()
                    .when()
                    .log().all()
                    .get("https://api.etherscan.io/api?module=gastracker&action=gasoracle")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().path("result.suggestBaseFee");
            return (int)Double.parseDouble(baseFeeAsString);
        } catch (Exception e) {
            System.out.println("An error occur trying to get gas price via API: " + e);
            return -1;
        }
    }
}
