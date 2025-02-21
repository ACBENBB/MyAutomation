package jenkinsPipelineJobs.commonStepScripts

print("Loaded file commonMavenCleanAndCompileStep.groovy")

def mavenCleanAndCompile(String runFile = "") {
    // code for a specific job
    if ("${JOB_NAME}" == 'Nightly_Run_E2E')
        runFile = setFileToRun('Nightly_Run_E2E')
    if ("${JOB_NAME}" == 'Nightly_Run_API')
        runFile = setFileToRun('Nightly_Run_API')
    if ("${JOB_NAME}" == 'AUT479_Production_Regression')
        runFile = setFileToRun('AUT479_Production_Regression')
    if ("${JOB_NAME}".contains('_SanitySuite_')) {
        runFile = setFileToRun("${JOB_NAME}")
        if (env.testsGroup != "null") {
            echo "testsGroup: " + env.testsGroup
            runFile += " -Dgroups=" + env.testsGroup
        }
    }

    //common code for all jobs
    if (params.runFailedTests || fileExists("testng-failed-${params.environment}.xml"))
        runFile = handleTestNGFailedSuiteFile(runFile)

    if (runFile.contains("xml")) {
        sh "mvn clean compile failsafe:integration-test failsafe:verify -DsuiteXmlFile=${runFile}"
    } else if (runFile != "") {
        sh "mvn clean compile exec:java -Dexec.mainClass=${runFile}"
    } else {
        sh "mvn clean compile"
    }
}

def private setFileToRun(String jobName) {
    String suiteFileName
    //set suiteFileName & testCategory
    if (jobName == "Nightly_Run_API") {
        suiteFileName = "api/Nightly_Run_API.xml"
    }
    if (jobName == "Nightly_Run_E2E") {
        suiteFileName = "e2e/Nightly_Run_E2E.xml"
        if (params.environment == "production") {
            String productionSuiteFilename = "e2e/Nightly_Run_E2E_Production.xml"
            echo "running on production so changing suiteFileName to: " + productionSuiteFilename
            suiteFileName = productionSuiteFilename
        }
        if (!params.GROUPS_TO_TEST.equalsIgnoreCase("none")) {
            String groupsPerDomain = setGroupsPerDomain()
            suiteFileName += groupsPerDomain
        }
    }
    if (jobName == "AUT479_Production_Regression") {
        if (params.testType == "API") {
            suiteFileName = "api/AUT479_Production_Regression_API.xml"
            env.testCategory = "NIGHTLY_API"
        } else {
            suiteFileName = "e2e/AUT479_Production_Regression_E2E.xml"
            env.testCategory = "NIGHTLY_E2E"
        }
    }
    if (jobName.contains("_SanitySuite_")) {
        suiteFileName = "${params.suiteFileName}"
    }
    return suiteFileName
}

def private handleTestNGFailedSuiteFile(String suiteFileName) {
//logic for runFailedTests & handling testng failed .xml suite file
    String fileName = "testng-failed-${params.environment}.xml"
    if (params.runFailedTests) {
        String filePathPrefix = "../../../"
        if (fileExists(fileName)) {
            echo "setting suiteFileName to " + fileName
            suiteFileName = filePathPrefix + fileName
        } else {
            String errorMessage = "Error! Can't find needed file of ${fileName}, can't resume. " +
                    "Keep in mind we can only run failed tests after the previous job " +
                    "finished running and had failures in it"
            echo(errorMessage)
            error(errorMessage)
        }
    } else {
        if (fileExists(fileName)) {
            echo "removing old ${fileName} as it is no longer relevant"
            sh "rm ${fileName}"
        }
    }
    return suiteFileName
}

def private setGroupsPerDomain() {
    // this section handles populating Dgroups argument in the mvn compile command, with the selected domains & groups retrieved from the parameters.
    // for example, selection of (DOMAINS_TO_TEST=FT,SID & GROUPS_TO_TEST=sanity,core) will be populated as: -Dgroups=sanityFT,sanitySID,coreFT,coreSID
    String testNgGroupsParam = ""
    String groupsPerDomains = ""

    String groups = params.GROUPS_TO_TEST
    println 'groups: ' + groups
    String[] groupsArray
    groupsArray = groups.split(',')

    String domains = params.DOMAINS_TO_TEST
    println 'domains: ' + domains
    String[] domainsArray
    domainsArray = domains.split(',')

    for (int i = 0; i < groupsArray.length; i++) {
        for (int j = 0; j < domainsArray.length; j++) {
            //first or last item doesn't need a comma
            if ((groupsArray.length == 1 && domainsArray.length == 1) || (groupsArray.length - 1 == i && domainsArray.length - 1 == j)) {
                groupsPerDomains += groupsArray[i] + domainsArray[j]
            } else {
                groupsPerDomains += groupsArray[i] + domainsArray[j] + ","
            }
        }
    }

    println 'groupsPerDomains: ' + groupsPerDomains
    if (!groupsPerDomains.isEmpty()) {
        testNgGroupsParam = " -Dgroups=${groupsPerDomains}"
    }
    return testNgGroupsParam
}

return this