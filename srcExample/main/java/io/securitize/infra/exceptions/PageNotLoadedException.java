package io.securitize.infra.exceptions;

import io.securitize.infra.webdriver.ExtendedBy;

public class PageNotLoadedException extends RuntimeException {
    public PageNotLoadedException(String pageName, ExtendedBy locator) {
        super(String.format("Page '%s' not loaded in time, can't find required element '%s' searched by '%s'", pageName, locator.getDescription(), locator.getBy()));
    }
}