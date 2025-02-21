package io.securitize.infra.utils.helpers;

import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class DefaultSepoliaGasProvider extends StaticGasProvider {
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(200000);
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(0);

    public DefaultSepoliaGasProvider() {
        super(GAS_PRICE, GAS_LIMIT);
    }

}
