package io.securitize.infra.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// this annotation is used to decorate test methods which depend on idology service being available

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IdologyDependent {
}
