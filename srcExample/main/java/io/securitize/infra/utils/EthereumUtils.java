package io.securitize.infra.utils;

import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.wallets.EthereumWallet;
import org.json.JSONObject;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.UUID;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class EthereumUtils {

    public static EthereumWallet generateRandomWallet() {
        String seed = UUID.randomUUID().toString();

        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

            String privateKeyInHex = privateKeyInDec.toString(16);

            WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
            String sAddress = aWallet.getAddress();

            return new EthereumWallet("0x" + sAddress, privateKeyInHex);

        } catch (Exception e) {
            MultiReporter.errorAndStop("An error occur trying to generate random Ethereum wallet address. Details: " + e, false);
        }

        return null;
    }

    public static String signTransaction(String transactionBodyForSigning, String signerPrivateKey) {
        try {
            info("Starting Ethereum transaction signing...");

            // parse provided details
            JSONObject transactionBodyForSigningAsJSON = new JSONObject(transactionBodyForSigning);
            String transactionData = transactionBodyForSigningAsJSON.getString("transactionData");
            int networkId = transactionBodyForSigningAsJSON.getJSONObject("additionalData").getInt("networkId");
            String networkType = transactionBodyForSigningAsJSON.getJSONObject("additionalData").getString("networkType");

            // sign the transaction
            RawTransaction rawTransaction = TransactionDecoder.decode(transactionData);
            Credentials credentials = Credentials.create(signerPrivateKey);
            byte[] signedMessage;
            if (networkType.equalsIgnoreCase("private")) {
                signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            } else {
                signedMessage = TransactionEncoder.signMessage(rawTransaction, networkId, credentials);
            }
            String result = Numeric.toHexString(signedMessage);

            info("Transaction signed successfully: " + result);
            return result;
        } catch (Exception e) {
            errorAndStop("An error occur trying to sign Ethereum transaction. Details: " + e, false);
            return null;
        }
    }
}
