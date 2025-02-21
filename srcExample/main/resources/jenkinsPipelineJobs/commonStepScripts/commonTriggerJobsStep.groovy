package jenkinsPipelineJobs.commonStepScripts

print("Loaded file commonTriggerJobsStep.groovy")

def triggerJobs(ArrayList<String> jobsNames, boolean isEthPriceOk) {
    if ("${JOB_NAME}" == 'SanitySuites_Trigger_All_Jobs')
        triggerAllJobsSanitySuites(jobsNames)
    if ("${JOB_NAME}" == 'GoogleSkipsSheet_Trigger_All_Jobs')
        triggerAllJobsGoogleSkipsSheet(jobsNames)
    if ("${JOB_NAME}" == 'Run_job_depending_on_ETH_price')
        triggerJobDependingOnEthPrice(isEthPriceOk)
    if ("${JOB_NAME}" == 'Stabilization-MultiTrigger')
        multiTriggerStabilization()
}

def private triggerAllJobsSanitySuites(ArrayList<String> jobsNames) {
    script {
        jobsNames.each { currentJobName ->
            String currentJobNameWithEnv = currentJobName + "_" + params.environment.toString()
            // filter by chosen domains (if current job to consider contains any of the allowed domain names)
            //noinspection GroovyAssignabilityCheck - avoid getting intelliJ warnings
            if (params.domainsToTest.split(',').any { element -> currentJobNameWithEnv.contains("_" + element + "_") }) {
                if ((params.testTypesToRun == "all") ||
                        ((params.testTypesToRun == "API") && (currentJobNameWithEnv.contains("_API"))) ||
                        ((params.testTypesToRun == "E2E") && (!currentJobNameWithEnv.contains("_API")))) {
                    if (params.isDryRun == false) {
                        String realEnvironmentToUse = params.environment
                        if (realEnvironmentToUse.equalsIgnoreCase("prod")) {
                            realEnvironmentToUse = "production"
                        }
                        build wait: false, job: currentJobNameWithEnv, parameters:
                                [
                                        string(name: 'branchName', value: "${params.branchName}"),
                                        string(name: 'environment', value: "${realEnvironmentToUse.toLowerCase()}"),
                                        booleanParam(name: 'skipTestsListedInGoogleSheet', value: "${params.skipTestsListedInGoogleSheet}"),
                                        booleanParam(name: 'runFailedTests', value: "${params.runFailedTests}")
                                ]
                    } else {
                        echo "Dry running job " + currentJobNameWithEnv.toString()
                    }
                } else {
                    echo "Job ${currentJobNameWithEnv} will not be executed"
                }
            } else {
                echo "Job ${currentJobNameWithEnv} filtered by domains to run filter"
            }
        }
    }
}

def private triggerAllJobsGoogleSkipsSheet(ArrayList<String> jobsNames) {
    script {
        jobsNames.each { currentJobName ->
            if ((params.testTypesToRun == "all") ||
                    ((params.testTypesToRun == "API") && (currentJobName.contains("_API"))) ||
                    ((params.testTypesToRun == "E2E") && (currentJobName.contains("_E2E")))) {
                if (params.isDryRun == false) {
                    build wait: false, job: currentJobName, parameters: [string(name: 'branchName', value: "${params.branchName}"), string(name: 'environment', value: "${params.environment.toLowerCase()}"), string(name: 'DOMAINS_TO_ANALYZE', value: "${params.DOMAINS_TO_ANALYZE}")]
                } else {
                    echo "Dry running job " + currentJobName.toString()
                }
            } else {
                echo "Job ${currentJobName} will not be executed"
            }
        }
    }
}

def private triggerJobDependingOnEthPrice(boolean isEthPriceOk) {
    script {
        if (isEthPriceOk) {
            echo "ETH price is good right now. Running job: ${params.jobNameToExecute}"
            if (params.isDryRun == false) {
                build wait: false, job: params.jobNameToExecute, parameters: [string(name: 'branchName', value: "${params.branchName}"), string(name: 'environment', value: "${params.environment}")]
            } else {
                echo "This is dry run. Not really running the suite"
            }
        } else {
            echo "ETH price is not good right now. Skipping suite"
        }
    }
}

def private multiTriggerStabilization() {
    script {
        int maxRuns = params.numberOfExecutions as Integer
        for (int i = 1; i <= maxRuns; i++) {
            build wait: true, propagate: false, job: 'Stabilization', parameters:
                    [
                            string(name: 'branchName', value: "${params.branchName}"),
                            string(name: 'environment', value: "${params.environment}"),
                            string(name: 'testCategory', value: "${params.testCategory}"),
                            string(name: 'DOMAINS_TO_TEST', value: "${params.DOMAINS_TO_TEST}")
                    ]
        }
    }
}

return this