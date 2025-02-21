package io.securitize.infra.webdriver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// this annotation is used to decorate test methods which require running on a specific remote
// hub address - thus overriding the configuration and even the environment variable values
// url value is mandatory. Example: "http://localhost:4444/wd/hub"
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ForceBrowserHubUrl {
    String url();

    String[] forceOnEnvironments() default {};
}
