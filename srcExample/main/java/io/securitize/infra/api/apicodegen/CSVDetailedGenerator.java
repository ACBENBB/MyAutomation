package io.securitize.infra.api.apicodegen;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import io.securitize.infra.utils.ResourcesUtils;
import org.json.JSONObject;
import java.io.*;
import java.util.*;


public class CSVDetailedGenerator {

    private final static String GENERATED_FILE_PATH = "src/main/java/io/securitize/infra/api/apicodegen/generated/";

    public static void main(String[] args) throws IOException {
        /**
         * The default value for param 'generatedClassName' is 'jsonFileName' (+.csv extension).
         * However, it can be modified by adding a param with custom name.
         * Usage example:
         * generate("ATS", "ats-bank-api.json", "custom_generated_csv_name").
         */
        generate("ATS", "ats-gw.json", "http://atsbankapi.{environment}.securitize.io");
    }

    public static void generate(String projectName, String jsonFileName,String baseUrl ) throws IOException {
        generate(projectName, jsonFileName, jsonFileName, baseUrl);
    }

    public static void generate(String projectName, String jsonFileName, String generatedFileName, String baseUrl) throws IOException {
        String specificationFilePath = "api" + File.separator + "specifications" + File.separator + projectName;
        String specificationFullPath = ResourcesUtils.getResourcePathByName(specificationFilePath + File.separator + jsonFileName);

        Map<String, String> mapOpeartionToEndpoint= new HashMap<>();

        JSONObject json = new JSONObject(readFromInputStream(new FileInputStream(specificationFullPath)));
        JSONObject pathsObject = (JSONObject) json.get("paths");

        Iterator<String> keys = pathsObject.keys();

        ArrayList<HashMap> endpointsList = new ArrayList<>();
        ArrayList<String> operationIdList = new ArrayList<>();

        while (keys.hasNext()) {
            HashMap<String, HashMap> endpointMap = new HashMap<>();
            String key = keys.next();
            JSONObject endpointObject = (JSONObject) pathsObject.get(key);

            HashMap jsonEndpointMap = new Gson().fromJson(endpointObject.toString(), HashMap.class);
            endpointMap.put(key, jsonEndpointMap);
            endpointsList.add(endpointMap);
        }

        for (HashMap<String, HashMap<String, HashMap>> map : endpointsList) {
            Map.Entry<String, HashMap<String, HashMap>> hashMap = map.entrySet().iterator().next();

            String endpoint = hashMap.getKey();
            HashMap<String, HashMap> endpointHttpMethodsMap = hashMap.getValue();

            for (String currentMethod : endpointHttpMethodsMap.keySet()) {
                if (currentMethod.equals("all")) {
                    //System.out.println("Ignoring an 'all' method definition for " + endpoint);
                    continue;
                }
                Object getObj = endpointHttpMethodsMap.get(currentMethod);
                LinkedTreeMap<String, String> getObjectLinkedTreeMap = (LinkedTreeMap<String, String>) getObj;
                //System.out.println(getObjectLinkedTreeMap.get(RestAttributes.operationId));
                operationIdList.add(getObjectLinkedTreeMap.get(RestAttributes.operationId));

                mapOpeartionToEndpoint.put(getObjectLinkedTreeMap.get(RestAttributes.operationId), endpoint);

            }
        }
        try (PrintWriter writer = new PrintWriter(GENERATED_FILE_PATH + generatedFileName + ".csv")) {
            StringBuilder sb = new StringBuilder();
            sb.append("domain: " + projectName);
            sb.append("\n");
            sb.append("service: " + jsonFileName.substring(0,jsonFileName.indexOf(".")));
            sb.append("\n");
            sb.append("base url: " + baseUrl);
            sb.append("\n");

            for (Map.Entry<String, String> entry : mapOpeartionToEndpoint.entrySet()) {
                String key = entry.getKey();
                sb.append(key);
                sb.append("\t");
                String value = entry.getValue();
                sb.append(value);
                sb.append("\n");
            }

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
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

}