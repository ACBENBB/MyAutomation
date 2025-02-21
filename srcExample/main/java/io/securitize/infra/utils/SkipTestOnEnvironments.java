package io.securitize.infra.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows specifying for a test method on which environments it is not allowed to be executed.
 * Usage example:
 * SkipTestOnEnvironments(environments = {"rc", "sandbox"})
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SkipTestOnEnvironments {
    String[] environments();
}
