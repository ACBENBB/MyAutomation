package io.securitize.infra.api.apicodegen;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import groovy.json.StringEscapeUtils;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.utils.VowelDeterminerUtils;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class CodeGenerator {

//    private final static String PROJECT_NAME = "ISR";
//    private final static String JSON_FILE_NAME = "control-panel-gw.json";
//    private final static String GENERATED_CLASS_NAME = "ControlPanelGW";
//    private final static String BASE_URL = "http://control-panel-gw:3010";

    //    private final static String PROJECT_NAME = "ATS";
//    private final static String JSON_FILE_NAME = "atsmainapi.dev.securitize.io.json";
//    private final static String GENERATED_CLASS_NAME = "AtsMainAPI";
//    private final static String BASE_URL = "https://atsmainapi.{environment}.securitize.io";
    private final static List<String> methodsToFilter = Arrays.asList("PUT", "PATCH", "DELETE", "POST", "all", "x-swagger-router-controller", "x-swagger-pipe");
    private static boolean filterMethods = true;

    private final static String GENERATED_CLASS_PATH = "src/main/java/io/securitize/infra/api/apicodegen/generated/";
    private final static String TEST_FUNCTION_GET_TEMPLATE_PATH = "src/main/java/io/securitize/infra/api/apicodegen/templates/test_function.template";
    private final static String TEST_CLASS_TEMPLATE_PATH = "src/main/java/io/securitize/infra/api/apicodegen/templates/test_class.template";


//    public static void main(String[] args) throws IOException {
//        CodeGenerator codeGenerator = new CodeGenerator();
//        codeGenerator.generate(PROJECT_NAME, JSON_FILE_NAME, GENERATED_CLASS_NAME, BASE_URL);
//    }

    public void generate(String projectName, String jsonFileName, String generatedClassName, String baseUrl, LoginAs loginAs) throws IOException {
        String specificationFilePath = "api" + File.separator + "specifications" + File.separator + projectName;

        final String TEST_METHOD_GET_TEMPLATE = readFromInputStream(new FileInputStream(TEST_FUNCTION_GET_TEMPLATE_PATH));
        String testClassTemplate = readFromInputStream(new FileInputStream(TEST_CLASS_TEMPLATE_PATH));
        String specificationFullPath = ResourcesUtils.getResourcePathByName(specificationFilePath + File.separator + jsonFileName);

        JSONObject json = new JSONObject(readFromInputStream(new FileInputStream(specificationFullPath)));

        JSONObject pathsObject = (JSONObject) json.get("paths");

        Iterator<String> keys = pathsObject.keys();
        ArrayList<HashMap> endpointsList = new ArrayList<>();

        while (keys.hasNext()) {
            String key = keys.next();
            HashMap<String, HashMap> endpointMap = new HashMap<>();
            JSONObject endpointObject = (JSONObject) pathsObject.get(key);

            HashMap jsonEndpointMap = new Gson().fromJson(endpointObject.toString(), HashMap.class);
            endpointMap.put(key, jsonEndpointMap);
            endpointsList.add(endpointMap);

        }
        ArrayList<String> testMethodsList = new ArrayList<>();
        for (HashMap<String, HashMap<String, HashMap>> map : endpointsList) {
            Map.Entry<String, HashMap<String, HashMap>> hashMap = map.entrySet().iterator().next();

            String endpoint = hashMap.getKey();
            HashMap<String, HashMap> endpointHttpMethodsMap = hashMap.getValue();

            for (String currentMethod : endpointHttpMethodsMap.keySet()) {
                if (methodsToFilter.stream().anyMatch(x -> x.equalsIgnoreCase(currentMethod)) && filterMethods) {
                    System.out.println("Ignoring " + VowelDeterminerUtils.setAAnDeterminer(currentMethod) + " '" + currentMethod + "' method definition for " + endpoint);
                    continue;
                }

                Object getObj = endpointHttpMethodsMap.get(currentMethod);
                LinkedTreeMap<String, String> getObjectLinkedTreeMap = (LinkedTreeMap<String, String>) getObj;
                String testMethod = generateTestMethod(projectName, jsonFileName, TEST_METHOD_GET_TEMPLATE, currentMethod, baseUrl, endpoint, getObjectLinkedTreeMap, loginAs);
                testMethodsList.add(testMethod + "\n");
            }
            // if (endpointHttpMethodsMap.containsKey(HttpMethods.get)) {
        }
        StringBuilder testMethodsString = new StringBuilder();

        for (String testMethod : testMethodsList) {
            testMethodsString.append(testMethod);
        }

        testClassTemplate = testClassTemplate.replace("#CLASS_NAME#", generatedClassName);
        testClassTemplate = testClassTemplate.replace("#TEST_METHODS#", testMethodsString.toString());
        testClassTemplate = testClassTemplate.replace("#BASE_URL#", baseUrl);
        testClassTemplate = testClassTemplate.replace("#FILE_NAME#", jsonFileName.replace(".json", ""));

        File generatedFolderPath = new File(GENERATED_CLASS_PATH);
        if (!generatedFolderPath.exists()) {
            boolean wasCreated = generatedFolderPath.mkdir();
            if (!wasCreated) {
                errorAndStop("Unable to create output folder for generated classes. Can't resume", false);
            }
        }

        try (PrintWriter writer = new PrintWriter(GENERATED_CLASS_PATH + generatedClassName + ".java")) {
            writer.println(testClassTemplate);
        }
    }

    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    private static String generateTestMethod(String projectName, String jsonFileName, String testTemplate, String method, String baseUrl, String endpoint, LinkedTreeMap linkedTreeMap, LoginAs loginAs) {
        testTemplate = testTemplate.replace("#METHOD#", method.toUpperCase());
        testTemplate = testTemplate.replace("#ENDPOINT#", baseUrl + endpoint);
        testTemplate = testTemplate.replace("#TEST_FUNCTION_NAME#", linkedTreeMap.get(RestAttributes.operationId) + "Test" + new Random().nextInt(1000));

        Object responsesObject = linkedTreeMap.get(RestAttributes.responses);
        LinkedTreeMap<String, Object> responsesLinkedTreeMap = (LinkedTreeMap<String, Object>) responsesObject;
        System.out.println(responsesLinkedTreeMap);

        JSONObject responsesJSONObject = new JSONObject(new Gson().toJson(responsesLinkedTreeMap));
        JSONObject firstResponseJSONObject;

        Optional<String> firstValidResponse = responsesJSONObject.keySet().stream().filter(x -> x.startsWith("2")).findFirst();
        if (firstValidResponse.isPresent()) {
            firstResponseJSONObject = (JSONObject) responsesJSONObject.get(firstValidResponse.get());
        } else if (responsesJSONObject.keySet().contains("default")) {
            firstResponseJSONObject = (JSONObject) responsesJSONObject.get("default");
        } else {
            throw new RuntimeException("Error in the json file - can't find 2xx nor default section in " + endpoint);
        }
        JSONObject schemaJSONObject;
        if (firstResponseJSONObject.keySet().contains("schema")) {
            schemaJSONObject = (JSONObject) firstResponseJSONObject.get("schema");
        } else {
            schemaJSONObject = firstResponseJSONObject;
        }

        if (!linkedTreeMap.containsKey(RestAttributes.operationId)) {
            throw new RuntimeException("Invalid specification! Missing operationId for action of '" + method + "' on: " + endpoint);
        }
        testTemplate = testTemplate.replace("#OPERATION_ID#", linkedTreeMap.get(RestAttributes.operationId).toString());
        testTemplate = testTemplate.replace("#RESPONSE_SCHEMA#", StringEscapeUtils.escapeJava(schemaJSONObject.toString()));
        testTemplate = testTemplate.replace("#LOGIN_AS#", "LoginAs." + loginAs.toString().toUpperCase());
        testTemplate = testTemplate.replace("#RESOURCE_PATH#", projectName + "/" + jsonFileName.replace(".json", ""));
        System.out.println(testTemplate);
        return testTemplate;
    }

    public void generateAll(List<CodeGenerationDetails> details) throws IOException {
        for (CodeGenerationDetails current : details) {
            generate(current.getProjectName(), current.getJsonFileName(), current.getGeneratedClassName(), current.getBaseUrl(), current.getLoginAs());
        }
    }
}