package io.securitize.infra.api.apicodegen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CodeGeneratorMultiple {

    public static void main(String[] args) throws IOException {
        List<CodeGenerationDetails> details = new ArrayList<>();

        details.add(new CodeGenerationDetails("SID",
                "investor-information-service2.json",
                "SID_InvestorInformationService2",
                "http://investor-information-service:5007.{internalUrlToRemoteRunCicdApi}",
                LoginAs.NONE));

        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.generateAll(details);
    }

}