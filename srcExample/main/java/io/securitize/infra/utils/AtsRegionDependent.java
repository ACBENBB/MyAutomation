package io.securitize.infra.utils;

import io.securitize.infra.enums.AtsRegionState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When an ATS test is dependent on the region state, there is no point to run the test if the market isn't available
 * to that region.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AtsRegionDependent {
    AtsRegionState expectedState();
}
