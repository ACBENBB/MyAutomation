package io.securitize.infra.api.apicodegen;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.utils.VowelDeterminerUtils;
import org.json.JSONObject;
import java.io.*;
import java.util.*;


public class CSVGenerator {

    private final static String GENERATED_FILE_PATH = "src/main/java/io/securitize/infra/api/apicodegen/generated/";
    private final static List<String> methodsToFilter = Arrays.asList("PUT", "PATCH", "DELETE", "POST", "all", "x-swagger-router-controller", "x-swagger-pipe");
    private static boolean filterMethods = true;

    public static void main(String[] args) throws IOException {
        /**
         * The default value for param 'generatedClassName' is 'jsonFileName' (+.csv extension).
         * However, it can be modified by adding a param with custom name.
         * Usage example:
         * generate("ATS", "ats-bank-api.json", "custom_generated_csv_name").
         */
        generate("SID", "investor-information-service2.json");
    }

    public static void generate(String projectName, String jsonFileName) throws IOException {
        generate(projectName, jsonFileName, jsonFileName);
    }

    public static void generate(String projectName, String jsonFileName, String generatedFileName) throws IOException {
        String specificationFilePath = "api" + File.separator + "specifications" + File.separator + projectName;
        String specificationFullPath = ResourcesUtils.getResourcePathByName(specificationFilePath + File.separator + jsonFileName);

        JSONObject json = new JSONObject(readFromInputStream(new FileInputStream(specificationFullPath)));
        JSONObject pathsObject = (JSONObject) json.get("paths");

        Iterator<String> keys = pathsObject.keys();
        ArrayList<HashMap> endpointsList = new ArrayList<>();
        ArrayList<String> operationIdList = new ArrayList<>();

        while (keys.hasNext()) {
            String key = keys.next();
            HashMap<String, HashMap> endpointMap = new HashMap<>();
            JSONObject endpointObject = (JSONObject) pathsObject.get(key);
            HashMap jsonEndpointMap = new Gson().fromJson(endpointObject.toString(), HashMap.class);
            endpointMap.put(key, jsonEndpointMap);
            endpointsList.add(endpointMap);
        }

        for (HashMap<String, HashMap<String, HashMap>> map : endpointsList) {
            Map.Entry<String, HashMap<String, HashMap>> hashMap = map.entrySet().iterator().next();
            HashMap<String, HashMap> endpointHttpMethodsMap = hashMap.getValue();

            for (String currentMethod : endpointHttpMethodsMap.keySet()) {
                if (methodsToFilter.stream().anyMatch(x -> x.equalsIgnoreCase(currentMethod)) && filterMethods) {
                    System.out.println("Ignoring " + VowelDeterminerUtils.setAAnDeterminer(currentMethod) + " '" + currentMethod + "' method definition for " + hashMap.getKey());
                    continue;
                }

                Object getObj = endpointHttpMethodsMap.get(currentMethod);
                LinkedTreeMap<String, String> getObjectLinkedTreeMap = (LinkedTreeMap<String, String>) getObj;
                operationIdList.add(getObjectLinkedTreeMap.get(RestAttributes.operationId));
            }
        }
        try (PrintWriter writer = new PrintWriter(GENERATED_FILE_PATH + generatedFileName + ".csv")) {
            StringBuilder sb = new StringBuilder();
            for (String a : operationIdList) {
                sb.append(a);
                sb.append("\n");
            }
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
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

}