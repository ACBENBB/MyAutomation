package io.securitize.infra.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * this annotation is used to decorate test methods which require the skip of the google sheet (Stabilization)
 * i.e. not executing the methods
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SkipTestOnStabilization {
}
