package io.securitize.infra.utils;

import io.securitize.infra.utils.helpers.DefaultQuorumGasProvider;
import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

import static io.securitize.infra.reporting.MultiReporter.info;

public class TokensTransferUtils {

    public static String sendTokens(String testNet, String senderPK, String tokenContract, String receiverAddress, long amount2) {
        // Connect to the Ethereum network using Web3j library
        Web3j web3j = Web3j.build(new HttpService(testNet));

        // Load the sender's Metamask wallet by importing the private key
        Credentials credentials = Credentials.create(senderPK);

        // Load the ERC20 token contract for the token you want to transfer
        ERC20 token = ERC20.load(tokenContract, web3j, credentials, new DefaultQuorumGasProvider());

        // Create a transaction object that specifies the token transfer
        BigInteger amount = BigInteger.valueOf(amount2);
        TransactionReceipt receipt = null;
        try {
            receipt = token.transfer(receiverAddress, amount).send();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Transaction failure");
        }

        // Check the status of the transaction
        if (receipt.isStatusOK()) {
            info("Token transfer succeeded");
        } else {
            info("Token transfer failed");
        }

        web3j.shutdown();

        return receipt.getTransactionHash();
    }


}
