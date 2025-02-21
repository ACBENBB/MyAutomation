package io.securitize.infra.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// this annotation is used to decorate test methods which specify they use Visual regression in the test

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UseVisualTesting {
}
