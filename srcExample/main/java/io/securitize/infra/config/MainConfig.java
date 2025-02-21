package io.securitize.infra.config;

import io.securitize.infra.reporting.MultiReporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.securitize.infra.reporting.MultiReporter.debug;
import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class MainConfig {
    private static Properties properties = null;
    private static final String PROPERTY_FILE_NAME = "config/MainConfig.properties";
    private static final List<String> productionEnvironments = Arrays.asList("production", "apac");

    private static void init() {

        if (properties == null) {
            try {
                InputStream input = MainConfig.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
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

    public static String getProperty(MainConfigProperty propertyName) {
        Map<String, String> environmentVariablesMap = System.getenv();
        String environmentVariableValue = environmentVariablesMap.get(propertyName.name());
        if (environmentVariableValue != null) {
            debug("Loading variable '" + propertyName.name() + "' value from environment: '" + environmentVariableValue + "'");
            return environmentVariableValue;
        }

        init();

        String value = properties.getProperty(propertyName.name());
        if (value == null) {
            errorAndStop("Can't find property " + propertyName + " in the main configuration!", false);
        }
        assert value != null; // added just to remove warnings - will never fail due to previous if clause and errorAndStop
        return fillEnvironment(value);
    }

    public static int getIntProperty(MainConfigProperty propertyName) {
        return Integer.parseInt(getProperty(propertyName));
    }

    public static double getDoubleProperty(MainConfigProperty propertyName) {
        return Double.parseDouble(getProperty(propertyName));
    }

    public static long getLongProperty(MainConfigProperty propertyName) {
        return Long.parseLong(getProperty(propertyName));
    }

    public static boolean getBooleanProperty(MainConfigProperty propertyName) {
        return Boolean.parseBoolean(getProperty(propertyName));
    }

    public static String fillEnvironment(String originalValue) {
        if (originalValue.contains("%environment%")) {
            String actualEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
            if ((actualEnvironment.equals("production"))) {
                return originalValue.replace("%environment%.", "");
            } else {
                return originalValue.replace("%environment%", actualEnvironment);
            }
        } else {
            return originalValue;
        }
    }

    public static String getCpUrl() {
        String currentEnvironment = getProperty(MainConfigProperty.environment);
        if (currentEnvironment.equalsIgnoreCase("apac")) {
            return getProperty(MainConfigProperty.cpUrlApac);
        } else {
            return getProperty(MainConfigProperty.cpUrl);
        }
    }

    public static String getInvestPageUrl(String issuerName) {
        String originalValue = getProperty(MainConfigProperty.investUrl);
        return originalValue.replace("%issuerInvestPrefix%", Users.getIssuerDetails(issuerName, IssuerDetails.issuerInvestPrefix));
    }

    public static boolean isProductionEnvironment() {
        String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        return productionEnvironments.stream().anyMatch(x -> x.equalsIgnoreCase(currentEnvironment));
    }

    public static void setProperty(MainConfigProperty property, String value) {
        init();
        properties.setProperty(property.name(), value);
    }
}