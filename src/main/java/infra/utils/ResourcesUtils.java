package infra.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import static infra.reporting.MultiReporter.errorAndStop;
import static infra.reporting.MultiReporter.info;

public class ResourcesUtils {
    public static boolean isResourceExistsByPath(String resourcePath) {
        return (ResourcesUtils.class.getClassLoader().getResource(resourcePath) != null);
    }

    public static String getResourcePathByName(String resourcePath) {
        URL resource = ResourcesUtils.class.getClassLoader().getResource(resourcePath);
        String fileName = resourcePath.substring(resourcePath.lastIndexOf('/') + 1);
        if (resource != null) {
            if (resource.getPath().contains(".jar!")) {
                info("Extracting resource file from jar - " + fileName);
                try (FileOutputStream fos = new FileOutputStream(fileName)){
                    InputStream is = Objects.requireNonNull(ResourcesUtils.class.getClassLoader().getResourceAsStream(resourcePath));
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                    return java.net.URLDecoder.decode(fileName, StandardCharsets.UTF_8.name());
                } catch (Exception e) {
                    errorAndStop("Unable to load the resource " + fileName + " - loading it returned null value", false);
                }
            } else {
                String result = resource.getPath().replace("%5c", "/");
                if (result.startsWith("/C") || result.startsWith("/D")) {
                    return result.substring(1);
                }
                try {
                    return java.net.URLDecoder.decode(result, StandardCharsets.UTF_8.name());
                } catch (Exception e) {
                    errorAndStop("Unable to load the resource " + fileName + " - loading it returned null value", false);
                }
            }
        }
        errorAndStop("Unable to locate needed resource file of: " + resourcePath, false);
        return "";
    }

    public static String searchResourcePathByFileName(String knownResourcePath, String filename) {
        try {
            URL url = ResourcesUtils.class.getResource(knownResourcePath);
            if (url == null) return null;
            Path path = Paths.get(url.toURI());
            Optional<Path> result = Files.walk(path, 10).filter(p -> p.endsWith(filename)).findFirst();
            return result.map(Path::toString).orElse(null);
        } catch (URISyntaxException | IOException e) {
            errorAndStop("An error occur trying to find resource file " + filename + ". Details: " + e, false);
        }
        return null;
    }

    public static String getResourceContentAsString(String path) throws IOException {
        String fullPath = getResourcePathByName(path);
        return new String(Files.readAllBytes(Paths.get(fullPath)));
    }
}