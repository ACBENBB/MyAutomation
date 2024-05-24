package config;

import test.BaseTest;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesFile {


    static Properties prop = new Properties();
    static String projectPath = System.getProperty("user.dir");
    static String configPath = projectPath + "\\config.txt"; // path to config file

    public static void main(String[] args) {
    }

    // method that read properties and get data
    public static void readPropertiesFile(String taskNumber) {
        try {
            InputStream input = Files.newInputStream(Paths.get(configPath));
            //   InputStream input = new FileInputStream(configPath);
            prop.load(input);
            BaseTest.driverPath = prop.getProperty("driverPath");
            BaseTest.driverName = prop.getProperty("driverName");
            BaseTest.url = getUrl(taskNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUrl(String taskNumber) {
        String url = null;
        if (taskNumber == null){
            return prop.getProperty("A-url");
        }
        try {
            InputStream input = Files.newInputStream(Paths.get(configPath));
            //  InputStream input = new FileInputStream(configPath);
            prop.load(input);
            url = prop.getProperty(taskNumber + "-url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

}
