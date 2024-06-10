package infra.aws;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static infra.reporting.MultiReporter.warning;

public abstract class AbstractAwsService {

    private static List<String> profiles = null;

    /*
     * As the AWS java bindings doesn't currently hold a method for listing configured profiles,
     * we have no choice but to use the AWS cli command in this raw format
     */
    protected synchronized static List<String> listProfiles() {
        if (profiles != null) {
            return profiles;
        }

        profiles = new ArrayList<>();
        try {
            // read raw aws credentials file
            Path credentialsFilePath = Paths.get(System.getProperty("user.home"), ".aws", "credentials");
            List<String> credentialsRawContent = Files.readAllLines(credentialsFilePath, Charset.defaultCharset());

            // filter out only the names of the profiles
            profiles = credentialsRawContent.stream()
                    .filter(x -> x.startsWith("[") && x.endsWith("]")) // profile names are wrapped in [] such as [default]
                    .map(x -> x.substring(1, x.length() - 1)) // remove [] from the profile list, so we have only the name
                    .collect(Collectors.toList());
        } catch (IOException e) {
            warning("An error occur trying to load list of AWS profiles. Details: " + e, false);
        }

        return profiles;
    }
}


