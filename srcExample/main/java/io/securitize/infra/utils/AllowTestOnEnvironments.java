package io.securitize.infra.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows specifying for a test method on which exclusive environments it is allowed to be executed.
 * Usage example:
 * AllowTestOnEnvironments(environments = {"production", "sandbox"})
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AllowTestOnEnvironments {
    String[] environments();
}
