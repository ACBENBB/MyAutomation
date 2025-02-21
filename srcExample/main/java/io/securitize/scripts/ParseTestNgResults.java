package io.securitize.scripts;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParseTestNgResults {

    private static final String INPUT_FILE_PATH = "target/failsafe-reports/testng-results.xml";
    private static final String[] DOMAINS = { "ALL", "CP", "SID", "FT", "ATS", "TA", "ST", "CA", "JP"};

    private static final Map<String, String> DOMAINS_ALTERNATIVE_NAMES = Stream.of(new String[][] {
            { "CP", "ISR" },
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        ParseTestNgResults parseTestNgResults = new ParseTestNgResults();

        for (String currentDomain : DOMAINS) {
            parseTestNgResults.buildReportFileForDomain(currentDomain);
        }
    }

    private void buildReportFileForDomain(String domain) throws ParserConfigurationException, IOException, SAXException {
        String currentFileName = "failures_" + domain + ".txt";
        File currentFile = new File(currentFileName);
        if (currentFile.exists()) {
            boolean wasDeleted = currentFile.delete();
            if (!wasDeleted) {
                throw new IOException("Unable to remove old results file of " + currentFileName);
            }
        }

        StringBuilder stringBuilderSummary = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File( INPUT_FILE_PATH ));

        stringBuilderSummary.append("Here is the latest execution summary: (Reminder: skips are both from Google sheet and code skip.json files)").append(System.lineSeparator());

        // build failed and skipped part
        NodeList nodeList = document.getElementsByTagName("test-method");
        HashMap<String, List<String>> failedTests = new HashMap<>();
        List<String> skippedTests = new ArrayList<>();
        int failedCounter = 0;
        int totalTests = 0;
        int passedTests = 0;

        for (int i = 0; i < nodeList.getLength() ; i++) {
            Node currentNode = nodeList.item(i);
            String signature = currentNode.getAttributes().getNamedItem("signature").getNodeValue();
            String name = currentNode.getAttributes().getNamedItem("name").getNodeValue();
            String status = currentNode.getAttributes().getNamedItem("status").getNodeValue();

            // skip nodes of tests that were later on retried
            if (currentNode.getAttributes().getNamedItem("retried") != null) {
                continue;
            }

            String currentDomainAlternativeName = DOMAINS_ALTERNATIVE_NAMES.getOrDefault(domain, domain);
            if ((domain.equals("ALL")) ||
                    (name.contains("_" + domain + "_")) || (signature.contains("cicd." + domain + ".")) ||
                    (name.contains("_" + currentDomainAlternativeName + "_")) || (signature.contains("cicd." + currentDomainAlternativeName + "."))) {

                // ignore setup / tear down functions when counting total tests
                Node isConfigAttribute = currentNode.getAttributes().getNamedItem("is-config");
                boolean isConfigNode = isConfigAttribute != null && Boolean.parseBoolean(isConfigAttribute.getNodeValue());
                if (isConfigNode) {
                    continue;
                }
                totalTests++;

                String nameToReport = name;
                if (!name.contains("_" + domain + "_")) {
                    String className = signature.substring(signature.lastIndexOf('.') + 1).split("@")[0];
                    nameToReport = className + "." + name;
                }

                if ((status.equals("FAIL")) && (!signature.contains("afterMethod"))) {
                    // get reason and remove long line details
                    String reason = getExceptionContentFromNode(currentNode).trim().split("\n")[0];
                    reason = reason.split("\\(Session info:")[0].trim().replace(System.lineSeparator(), " ");

                    if (!failedTests.containsKey(reason)) {
                        List<String> list = new ArrayList<>();
                        list.add(nameToReport);
                        failedTests.put(reason, list);
                    } else {
                        failedTests.get(reason).add(nameToReport);
                    }
                    failedCounter++;
                } else if ((status.equals("SKIP")) && (!signature.contains("afterMethod")) && (!signature.contains("beforeMethod")) && (!signature.contains("beforeTest"))) {
                    skippedTests.add(nameToReport);
                } else if (!isConfigNode) {
                    passedTests++;
                }
            }
        }

        // build summary part
        String summaryHeader = prepareHeader("Summary");
        stringBuilderSummary.append(summaryHeader).append(System.lineSeparator());
        stringBuilderSummary.append("\tTotal: ").append(totalTests).append("    ");
        stringBuilderSummary.append("Passed: ").append(passedTests).append("    ");
        stringBuilderSummary.append("Failed: ").append(failedCounter).append("    ");
        stringBuilderSummary.append("Skipped: ").append(skippedTests.size()).append(System.lineSeparator());
        stringBuilderSummary.append(String.join("", Collections.nCopies(summaryHeader.length(), "="))).append(String.join("", Collections.nCopies(2, System.lineSeparator())));

        // build failed part
        if (failedCounter > 0) {
            String failedHeader = prepareHeader("Failed");
            stringBuilder.append(failedHeader).append(System.lineSeparator());
            List<String> sortedKeySet = failedTests.keySet().stream().sorted().collect(Collectors.toList());
            for (String currentFailedReason : sortedKeySet) {
                List<String> namesOfFailedPerReason = failedTests.get(currentFailedReason);
                stringBuilder.append(currentFailedReason).append(": ").append(System.lineSeparator());
                namesOfFailedPerReason.sort(new CustomComparator());
                for (String currentName : namesOfFailedPerReason) {
                    stringBuilder.append("\t\t").append(currentName).append(System.lineSeparator());
                }
                stringBuilder.append(System.lineSeparator());
            }
            stringBuilder.append(String.join("", Collections.nCopies(failedHeader.length(), "="))).append(String.join("", Collections.nCopies(3, System.lineSeparator())));
        }

        if (skippedTests.size() > 0) {
            // build skipped part
            String skippedHeader = prepareHeader("Skipped");
            stringBuilder.append(skippedHeader).append(System.lineSeparator());
            skippedTests.sort(new CustomComparator());
            for (String currentSkippedTest : skippedTests) {
                stringBuilder.append(currentSkippedTest).append(System.lineSeparator());
            }
            stringBuilder.append(String.join("", Collections.nCopies(skippedHeader.length(), "=")));
        }

        // write output to file - only such content is needed
        Path path = Paths.get(currentFileName);
        if (totalTests == 0) {
            System.out.println("No tests detected for domain " + domain + " will not created results file!");
        } else if ((failedCounter > 0)) {
            Files.write(path, (stringBuilderSummary.toString() + stringBuilder).getBytes());
        } else if (passedTests == 0) {
            Files.write(path, (stringBuilderSummary + "No failed nor passed tests detected! Did we skip it all?" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + stringBuilder).getBytes());
        } else {
            Files.write(path, (stringBuilderSummary + "No failures detected! Huzzah!" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + stringBuilder).getBytes());
        }
    }

    private static String prepareHeader(String title) {
        return String.join("", Collections.nCopies(25, "=")) +
                " " + title + " " +
                String.join("", Collections.nCopies(25, "="));
    }

    private static String getExceptionContentFromNode(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node currentChild = children.item(i);
            if (currentChild.getNodeName().equals("exception")) {
                return currentChild.getTextContent();
            }
        }

        return "Can't extract exception details";
    }

    public static class CustomComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            String o1NumberAsString = o1.split("_")[0].replaceAll("\\D", "");
            String o2NumberAsString = o2.split("_")[0].replaceAll("\\D", "");

            if ((o1NumberAsString.length() > 0) && (o2NumberAsString.length() > 0)) {
                int o1AsNumber = Integer.parseInt(o1NumberAsString);
                int o2AsNumber = Integer.parseInt(o2NumberAsString);
                return Integer.compare(o1AsNumber, o2AsNumber);
            }
            return o1.compareTo(o2);
        }
    }
}
