package infra.config;

import infra.exceptions.InvalidURLException;
import infra.reporting.MultiReporter;
import infra.utils.VideoRecorder;
import tests.abstractClass.AbstractBaseTest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class PropertiesFile {


    static Properties prop = new Properties();
    static String projectPath = System.getProperty("user.dir");
    static String configPath = projectPath + "\\config.txt";

    // method that read properties and get data
    public static void readPropertiesFile(String websiteName) {
        try {
            InputStream input = new FileInputStream(configPath);
            prop.load(input);
        } catch (Exception e) {
            MultiReporter.error("An error occur trying to load config file: " + e);
        }
        AbstractBaseTest.driverPath = prop.getProperty("driverPath");
        AbstractBaseTest.driverName = prop.getProperty("driverName");
        AbstractBaseTest.url = getUrl(websiteName);
        VideoRecorder.maxLocalOldVideosToKeep = Integer.parseInt(prop.getProperty("maxLocalOldVideosToKeep"));
    }

    public static String setUrl(String websiteName) {
        String url;
        if (websiteName == null) {
            url = prop.getProperty("url");
        } else {
            url = prop.getProperty(websiteName + "-url");
        }
        return url;
    }

    public static String getUrl(String websiteName) {
        String url = null;
        try {
            InputStream input = Files.newInputStream(Paths.get(configPath));
            prop.load(input);
            url = setUrl(websiteName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (url == null || url.isEmpty()) {
            throw new InvalidURLException();
        }
        return url;
    }

}
