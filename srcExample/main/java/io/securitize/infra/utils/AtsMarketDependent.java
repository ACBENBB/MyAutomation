package io.securitize.infra.utils;

import io.securitize.infra.enums.AtsMarketState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When an ATS test is dependent on the market state, there is no point to run the test if the market isn't available
 * to that level. This annotation is used to specify the minimal level of market state to allow the annotated test to
 * run
 * AtsMarketDependent(expectedState = AtsMarketState.OPEN)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AtsMarketDependent {
    AtsMarketState expectedState();
}
