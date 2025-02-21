package io.securitize.infra.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this annotation is used to decorate test methods which require Google Chrome extension to bypass Recaptcha
 * You can specify on which environments it should be used
  */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BypassRecaptcha {
    String[] environments() default "all";
}