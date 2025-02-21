package io.securitize.infra.wallets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// this annotation is used to decorate test methods which require the metaMask extension installed

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UsingMetaMask {
}
