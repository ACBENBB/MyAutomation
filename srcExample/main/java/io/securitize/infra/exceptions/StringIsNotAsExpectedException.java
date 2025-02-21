package io.securitize.infra.exceptions;

    public class StringIsNotAsExpectedException extends RuntimeException {

        public StringIsNotAsExpectedException(String errorMessage, String actual, String expected) {
            super(errorMessage + ". Expected:" + expected + ", Actual: " + actual);
        }

        public StringIsNotAsExpectedException(String errorMessage, String actual, String expected, Throwable err) {
            super(errorMessage + ". Expected:" + expected + ", Actual: " + actual, err);
        }
    }
