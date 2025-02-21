package io.securitize.infra.api.apicodegen;

public class CodeGenerationDetails {
    private final String projectName;
    private final String jsonFileName;
    private final String generatedClassName;
    private final String baseUrl;
    private final LoginAs loginAs;

    public CodeGenerationDetails(String projectName, String jsonFileName, String generatedClassName, String baseUrl, LoginAs loginAs) {
        this.projectName = projectName;
        this.jsonFileName = jsonFileName;
        this.generatedClassName = generatedClassName;
        this.baseUrl = baseUrl;
        this.loginAs = loginAs;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getJsonFileName() {
        return jsonFileName;
    }

    public String getGeneratedClassName() {
        return generatedClassName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public LoginAs getLoginAs() {
        return loginAs;
    }
}
