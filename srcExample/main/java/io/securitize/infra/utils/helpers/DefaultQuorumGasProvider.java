package io.securitize.infra.utils.helpers;

import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;


public class DefaultQuorumGasProvider extends StaticGasProvider {
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(4300000L);
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(0);

    public DefaultQuorumGasProvider() {
        super(GAS_PRICE, GAS_LIMIT);
    }

}
