package io.securitize.infra.config;

import groovy.lang.MissingPropertyException;
import io.securitize.infra.reporting.MultiReporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InvestorsData {
    private static Properties properties = null;
    private static final String PROPERTY_FILE_NAME = "investorMetaData/ExistingInvestors.properties";

    private static void init() {

        if (properties == null) {
            try {
                InputStream input = InvestorsData.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
                properties = new Properties();
                properties.load(input);
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                MultiReporter.error("An error occur trying to load main config: " + e);
            }
        }
    }

    public static String getUserDetails(InvestorsProperty propertyName) {
        String env = MainConfig.getProperty(MainConfigProperty.environment);
        String fullPropertyName = env + "_" + propertyName;

        String valueFromEnv = System.getenv(fullPropertyName);
        if (valueFromEnv != null) {
            return valueFromEnv;
        }

        init();

        String value = properties.getProperty(fullPropertyName);
        if (value != null) {
            return value;
        } else {
            throw new MissingPropertyException("Can't find property " + propertyName + ". Searched by " + fullPropertyName + ". Validate value exists in the users properties file");
        }
    }
}
