package io.securitize.infra.api.algorand;

import com.algorand.algosdk.account.Account;
import com.algorand.algosdk.crypto.Address;
import com.algorand.algosdk.transaction.SignedTransaction;
import com.algorand.algosdk.transaction.Transaction;
import com.algorand.algosdk.util.Encoder;
import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.common.Response;
import com.algorand.algosdk.v2.client.model.NodeStatusResponse;
import com.algorand.algosdk.v2.client.model.PendingTransactionResponse;
import com.algorand.algosdk.v2.client.model.PostTransactionsResponse;
import com.algorand.algosdk.v2.client.model.TransactionParametersResponse;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.wallets.AlgorandWallet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AlgorandApi {

    private static AlgodClient client = null;

    public AlgorandWallet createWallet() {
        try {
            Account account = new Account();
            info("new wallet created with address: " + account.getAddress().toString());
            return new AlgorandWallet(account.getAddress().toString(), account.toMnemonic());
        } catch (NoSuchAlgorithmException e) {
            errorAndStop("An error occur trying to create new algorand wallet. Details: " + e, false);
            return null;
        }
    }

    public long getWalletBalance(AlgorandWallet wallet) {
        try {
            Response<com.algorand.algosdk.v2.client.model.Account> respAcct = getClient().AccountInformation(getAddressFromWallet(wallet)).execute();
            if (!respAcct.isSuccessful()) {
                errorAndStop("An error occur trying to obtain wallet information. Details: " + respAcct.code() + " " + respAcct.message(), false);
            }
            com.algorand.algosdk.v2.client.model.Account accountInfo = respAcct.body();
            debug(String.format("Account Balance of %s: %d microAlgos%n", wallet.getAddress(), accountInfo.amount));

            info(String.format("Wallet %s current balance is: %s", wallet.getAddress(), accountInfo.amount));
            return accountInfo.amount;
        } catch (Exception e) {
            errorAndStop(String.format("An error occur trying to get balance of wallet %s. Details: %s",wallet.getAddress(), e), false);
            return -1;
        }
    }


    public String loadWalletWithFundsFromInternalFaucet(AlgorandWallet wallet, long amount) {
        info(String.format("Transferring funds to wallet %s from internal faucet. amount=%s", wallet.getAddress(), amount));

        // Construct the transaction
        String note = "Load initial funds";

        // obtain transaction parameters
        TransactionParametersResponse params = obtainTransactionParameters();

        // build the transaction
        SignedTransaction signedTransaction = buildTransaction(wallet, note, amount, params);

        // Submit the transaction to the network
        String txId = submitTransaction(signedTransaction);
        info("Successfully submitted transaction!");

        return txId;
    }

    /**
     * utility function to wait on a transaction to be confirmed
     * the timeout parameter indicates how many rounds do you wish to check pending transactions for
     */
    public void waitForConfirmation(String txID, long timeoutRounds) {
        debug("Waiting for transaction confirmation of txID=" + txID);
        Response<NodeStatusResponse> resp;
        try {
            resp = getClient().GetStatus().execute();
        } catch (Exception e) {
            errorAndStop("An error occur trying to figure out current round. Details: " + e, false);
            return;
        }
        if (!resp.isSuccessful()) {
            errorAndStop("An error occur trying to figure out current round. Details: " + resp.code() + " " + resp.message(), false);
        }

        // get current round to figure out max allowed round
        NodeStatusResponse nodeStatusResponse = resp.body();
        long startRound = nodeStatusResponse.lastRound + 1;
        long currentRound = startRound;
        long maxRound = startRound + timeoutRounds;

        // try until getting confirmation or until timeout
        while (currentRound < maxRound) {
            // Check the pending transactions
            Response<PendingTransactionResponse> resp2;
            try {
                resp2 = getClient().PendingTransactionInformation(txID).execute();
            } catch (Exception e) {
                warning("An error occur trying to get pending transaction information. Details: " + e, false);
                continue;
            }

            if (resp2.isSuccessful()) {
                PendingTransactionResponse pendingInfo = resp2.body();
                if (pendingInfo != null) {
                    if (pendingInfo.confirmedRound != null && pendingInfo.confirmedRound > 0) {
                        // Got the completed Transaction
                        debug("Confirmed in round: " + pendingInfo.confirmedRound);
                        return;
                    }
                    if (pendingInfo.poolError != null && pendingInfo.poolError.length() > 0) {
                        // If there was a pool error, then the transaction has been rejected!
                        errorAndStop("The transaction has been rejected with a pool error: " + pendingInfo.poolError, false);
                    }
                }
            }

            // wait for next block execution and we will search there
            try {
                resp = getClient().WaitForBlock(currentRound).execute();
            } catch (Exception e) {
                errorAndStop("An error occur waiting for next block execution. Details: " + e, false);
                return;
            }
            if (!resp.isSuccessful()) {
                errorAndStop("An error occur waiting for next block execution. Details: " + resp.code() + " " + resp.message(), false);
                return;
            }

            currentRound++;
            debug(String.format("Not yet, trying another time... (%s/%s)%s", currentRound, maxRound, System.lineSeparator()));
        }

        errorAndStop("Transaction not confirmed even after " + timeoutRounds + " rounds!", false);
    }


    public int getAssetHolding(AlgorandWallet wallet, long assetID) {
        debug(String.format("Getting wallet %s asset holding for assetId=%s", wallet.getAddress(), assetID));

        String accountInfo;
        try {
            accountInfo = getClient().AccountInformation(getAddressFromWallet(wallet)).execute().toString();
        } catch (Exception e) {
            errorAndStop(String.format("An error occur trying to obtain wallet %s asset %s holding. Details: %s", wallet.getAddress(), assetID, e), false);
            return -1;
        }

        // iterate on all assets to find relevant asset balance
        JSONObject jsonObj = new JSONObject(accountInfo);
        JSONArray jsonArray = (JSONArray) jsonObj.get("assets");
        if (jsonArray.length() > 0) {
            for (Object o : jsonArray) {
                JSONObject currentAsset = (JSONObject) o;
                Integer myAssetIDInt = (Integer) currentAsset.get("asset-id");
                if (assetID == myAssetIDInt.longValue()) {
                    debug("Asset Holding Info: " + currentAsset.toString(2)); // pretty print
                    return currentAsset.getInt("amount");
                }
            }
        }

        errorAndStop(String.format("Current address %s has no assets!", wallet.getAddress()), false);
        return -1;
    }

    public void optInAsset(AlgorandWallet wallet, long assetID) {

        // obtain transaction parameters
        TransactionParametersResponse params = obtainTransactionParameters();
        //getClient().TransactionParams().execute().body();
        //params.fee = (long) 1000;

        // build the transaction
        Transaction tx = Transaction
                .AssetAcceptTransactionBuilder()
                .acceptingAccount(wallet.getAddress())
                .assetIndex(assetID)
                .suggestedParams(params).build();

        // Sign the transaction
        SignedTransaction signedTx;
        try {
            signedTx = new Account(wallet.getSecret()).signTransaction(tx);
        } catch (GeneralSecurityException e) {
            errorAndStop("An error occur trying to sign the transaction. Details: " + e, false);
            return;
        }

        // send the transaction to the network
        submitTransaction(signedTx);
        waitForConfirmation(signedTx.transactionID, 20);
    }


    private AlgodClient getClient() {
        if (client == null) {
            client = new AlgodClient(Users.getProperty(UsersProperty.algorand_api_address),
                    Users.getIntProperty(UsersProperty.algorand_api_port),
                    Users.getProperty(UsersProperty.algorand_api_token));
        }
        return client;
    }


    private Address getAddressFromWallet(AlgorandWallet wallet) {
        try {
            return new Address(wallet.getAddress());
        } catch (NoSuchAlgorithmException e) {
            errorAndStop("An error occur trying to parse address string to object. Details: " + e, false);
            return null;
        }
    }


    // Utility function for sending a raw signed transaction to the network
    private String submitTransaction(SignedTransaction signedTx) {
        byte[] encodedTxBytes;
        try {
            // Msgpack encode the signed transaction
            encodedTxBytes = Encoder.encodeToMsgPack(signedTx);
        } catch (Exception e) {
            errorAndStop("An error occur trying to encode transaction. Details: " + e, false);
            return null;
        }

        try {
            Response<PostTransactionsResponse> response = getClient().RawTransaction().rawtxn(encodedTxBytes).execute();
            if (response.isSuccessful()) {
                return response.body().txId;
            } else {
                errorAndStop("An error occur trying to submitTransaction: " + response.code() + " " + response.body() + " " + response.message(), false);
                return null;
            }
        } catch (Exception e) {
            errorAndStop("An error occur trying to submit the transaction. Details: " + e, false);
            return null;
        }
    }


    private TransactionParametersResponse obtainTransactionParameters() {
        debug("Obtaining transaction parameters...");
        TransactionParametersResponse params;
        try {
            Response<TransactionParametersResponse> resp = getClient().TransactionParams().execute();
            if (!resp.isSuccessful()) {
                throw new Exception("Response error from client: " + resp.code() + " message: " + resp.message());
            }
            params = resp.body();
            if (params == null) {
                throw new Exception("Params retrieval error (params are null)");
            }
            debug("Successful!");
            return params;
        } catch (Exception e) {
            errorAndStop("An error occur trying to obtain transaction parameter. Details: " + e, false);
            return null;
        }
    }

    private SignedTransaction buildTransaction(AlgorandWallet wallet, String note, long amount, TransactionParametersResponse params) {
        debug(String.format("Building transaction to wallet %s with amount of %s with note of '%s'", wallet.getAddress(), amount, note));
        Transaction txn = Transaction
                .PaymentTransactionBuilder()
                .sender(Users.getProperty(UsersProperty.algorand_internal_faucet_address))
                .note(note.getBytes())
                .amount(amount)
                .receiver(getAddressFromWallet(wallet))
                .suggestedParams(params)
                .build();

        // sign the transaction
        try {
            SignedTransaction signedTxn = new Account(Users.getProperty(UsersProperty.algorand_internal_faucet_passphrase)).signTransaction(txn);
            debug("Signed transaction with txid: " + signedTxn.transactionID);
            return signedTxn;
        } catch (GeneralSecurityException e) {
            errorAndStop("An error occur trying to sign the transaction. Details: " + e, false);
            return null;
        }
    }

    public String signTransaction(JSONObject transactionData, String mnemonic) {
        try {
            Account account = new Account(mnemonic);
            JSONObject suggestedParams = transactionData.getJSONObject("suggestedParams");

            Transaction txn = Transaction
                    .AssetTransferTransactionBuilder()
                    .sender(transactionData.getString("from"))
                    .assetReceiver(transactionData.getString("to"))
                    .assetAmount(transactionData.getLong("amount"))
                    .fee(suggestedParams.getLong("fee"))
                    .genesisID(suggestedParams.getString("genesisID"))
                    .genesisHash(Encoder.decodeFromBase64(suggestedParams.getString("genesisHash")))
                    .assetIndex(transactionData.getLong("assetIndex"))
                    .firstValid(suggestedParams.getLong("firstRound"))
                    .lastValid(suggestedParams.getLong("lastRound"))
                    .build();

            SignedTransaction signedTransaction = account.signTransaction(txn);
            byte[] transactionBytes = Encoder.encodeToMsgPack(signedTransaction);
            return Encoder.encodeToBase64(transactionBytes);
        } catch (Exception e) {
            errorAndStop("An error occur trying to sign Algorand transaction. Details: " + e, false);
            return null;
        }
    }
}