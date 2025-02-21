package io.securitize.tests.blockchain.testData;

public class AUT499_POJO {

    //Fill these parameters to run the script locally
    private static final String TOKEN_TICKER = null;
    private static final String ISSUER_ID = null;
    private static final String TOKEN_ID = null;
    private static final String BLOCKCHAIN_NETWORK = null;
    private static final String CUSTODIAN_ADDRESS = null;
    private static final String TOKEN_DECIMALS = null;
    private static final String COMPLIANCE_TYPE = null;
    private static final String MASTER_WALLET_ADDRESS = null;
    private static final String REDEMPTION_WALLET = null;
    private static final String ISSUER_WALLET = null;
    private static final String TBE_WALLET = null;

    public static String getTokenTicker() {
        String valueFromEnvironment = System.getenv("tokenTicker");
        if (valueFromEnvironment == null) {
            return TOKEN_TICKER;
        }
        return valueFromEnvironment;
    }

    public static String getIssuerId() {
        String valueFromEnvironment = System.getenv("issuerId");
        if (valueFromEnvironment == null) {
            return ISSUER_ID;
        }
        return valueFromEnvironment;
    }

    public static String getTokenId() {
        String valueFromEnvironment = System.getenv("tokenId");
        if (valueFromEnvironment == null) {
            return TOKEN_ID;
        }
        return valueFromEnvironment;
    }

    public static String getBlockchainNetwork() {
        String valueFromEnvironment = System.getenv("tokenNetwork");
        if (valueFromEnvironment == null) {
            return BLOCKCHAIN_NETWORK;
        }
        return valueFromEnvironment;
    }

    public static String getCustodianAddress() {
        String valueFromEnvironment = System.getenv("custodianAddress");
        if (valueFromEnvironment == null) {
            return CUSTODIAN_ADDRESS;
        }
        return valueFromEnvironment;
    }

    public static String getTokenDecimals() {
        String valueFromEnvironment = System.getenv("tokenDecimal");
        if (valueFromEnvironment == null) {
            return TOKEN_DECIMALS;
        }
        return valueFromEnvironment;
    }

    public static String getComplianceType() {
        String valueFromEnvironment = System.getenv("complianceType");
        if (valueFromEnvironment == null) {
            return COMPLIANCE_TYPE;
        }
        return valueFromEnvironment;
    }

    public static String getMasterWalletAddress() {
        String valueFromEnvironment = System.getenv("masterAddress");
        if (valueFromEnvironment == null) {
            return MASTER_WALLET_ADDRESS;
        }
        return valueFromEnvironment;
    }

    public static String getRedemptionWallet() {
        String valueFromEnvironment = System.getenv("redemptionAddress");
        if (valueFromEnvironment == null) {
            return REDEMPTION_WALLET;
        }
        return valueFromEnvironment;
    }

    public static String getIssuerWallet() {
        String valueFromEnvironment = System.getenv("issuerAddress");
        if (valueFromEnvironment == null) {
            return ISSUER_WALLET;
        }
        return valueFromEnvironment;
    }

    public static String getTbeWallet() {
        String valueFromEnvironment = System.getenv("tbeAddress");
        if (valueFromEnvironment == null) {
            return TBE_WALLET;
        }
        return valueFromEnvironment;
    }

}