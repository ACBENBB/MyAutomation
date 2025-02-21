package io.securitize.infra.utils;

import io.securitize.infra.enums.TestNetwork;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this annotation is used to configure the default blockchain network (and eventually token) to be used in the test
  */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultTestNetwork {
    TestNetwork value() default TestNetwork.QUORUM;
}