package jenkinsPipelineJobs

def maxHistoryBuildsToAnalyze = 10

pipeline {
    agent any

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production'], description: 'Environment on which to run')
        choice(name: 'testNetwork', choices: ['QUORUM', 'ETHEREUM_SEPOLIA', 'POLYGON_MUMBAI'], description: 'Which test network to use - affects token used')
        choice(name: 'browserType', choices: ['chrome-remote'], description: 'Browser to use')
        choice(name: 'browserLocation', choices: ['Moon','JenkinsLocal'], description: 'Where will the browsers be opened - locally on Jenkins or remotely on Moon')
        extendedChoice(name: 'DOMAINS_TO_TEST', defaultValue: 'ATS,CA,FT,ISR,JP,SID,ST,TA', description: 'Name of domain to test in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,CA,FT,ISR,JP,SID,ST,TA',
                visibleItemCount: 10)
        choice(name: 'testCategory', choices: ['JENKINS', 'MANUAL_STABILIZATION'], description: 'Category of these tests to be reported to the dashboard. The option of MANUAL_STABILIZATION will affect the statistics')
        booleanParam(name: 'skipTestsListedInGoogleSheet', defaultValue: false, description: 'Should skip tests listed in a dedicated Google sheet')
        booleanParam(name: 'forceRunAllJobs', defaultValue: false, description: 'Run all jobs ignoring their last run status')
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'Is this a dry run for debugging purposes only - no tests will actually be started')
    }

    stages {
        stage('Run failed jobs status') {
            steps {
                script {
                    for (job in Hudson.instance.getAllItems(org.jenkinsci.plugins.workflow.job.WorkflowJob)) {
                        String item = job.fullName
                        // run only jobs that contain AUT in their name
                        if (!item.contains("AUT")) {
                            println "skipping " + job.fullName.toString() + " as not an AUT job"
                            continue
                        }
                        if (item.contains("TotalTests")) {
                            println "skipping " + job.fullName.toString() + " this is a TotalTests job"
                            continue
                        }

                        // only analyze jobs of selected domain
                        def domainsAsList = DOMAINS_TO_TEST.split(",")
                        def isItemDomainInListOfDomainsToRun = domainsAsList.any { a -> item.contains("_" + a + "_") }
                        if (!isItemDomainInListOfDomainsToRun) {
                            println "skipping " + job.fullName.toString() + " as it is not in the selected tests domains"
                            continue
                        }

                        // only analyze jobs that are not disabled
                        def enabled = job.isBuildable()
                        if (!enabled) {
                            println "skipping " + job.fullName.toString() + " as it is marked as disabled"
                            continue
                        }

                        def pastBuilds = job.builds
                        println "job " + item + " has " + pastBuilds.size() + " previous builds but analyzing only up until the last " + maxHistoryBuildsToAnalyze + " of them."
                        def result = "unknown"
                        for (i = 0; i < Math.min(maxHistoryBuildsToAnalyze, pastBuilds.size()); i++) {
                            def currentOldBuild = pastBuilds[i]
                            def innerResult = currentOldBuild.getResult().toString()
                            def wasRunOnEnvironment = "???"
                            try {
                                wasRunOnEnvironment = currentOldBuild.allActions.find {it in hudson.model.ParametersAction}.getParameter("environment").value
                            } catch (Exception e) {
                                println "Can't find which environment was used in build #" + currentOldBuild.number.toString() + ". Details: " + e.toString()
                            }

                            println "job " + item.toString() + " build #" + currentOldBuild.number.toString() + " on environment " + wasRunOnEnvironment.toString() + " finished with status: " + innerResult.toString()

                            if (wasRunOnEnvironment.equalsIgnoreCase(environment.toString())) {
                                result = innerResult
                                break
                            }
                        }

                        // check if current job should be triggered
                        def triggerJob = false

                        if (forceRunAllJobs == "true") {
                            println "Triggering job " + item.toString() + " as forceRunAllJobs is True"
                            triggerJob = true
                        } else if (result.equalsIgnoreCase("unknown")) {
                            println "job " + item.toString() + " was never run on environment " + environment.toString() + " before (In searched past builds). Not running it now on it"
                        } else if (result.equalsIgnoreCase("success")) {
                            println "job " + item.toString() + " last run on environment " + environment.toString() +
                                    " was successful - no need to run it now"
                        } else {
                            println "job " + item.toString() + " was not successful last time running on environment " + environment.toString() + " as it finished with result: " + result + ". Triggering it now..."
                            triggerJob = true
                        }

                        // if needed - trigger the job
                        if (triggerJob) {
                            if (isDryRun == "true") {
                                println "Dry running job " + item.toString()
                            } else {
                                try {
                                    build wait: false, job: item, parameters: [string(name: 'branchName', value: branchName), string(name: 'environment', value: environment), string(name: 'browserType', value: browserType), string(name: 'testNetwork', value: testNetwork), string(name: 'browserLocation', value: browserLocation), string(name: 'testCategory', value: testCategory), booleanParam(name: 'skipTestsListedInGoogleSheet', value: skipTestsListedInGoogleSheet)]
                                } catch (Exception e) {
                                    println "An error occur trying to trigger job " + item.toString() + ". Details: " + e.toString()
                                    // if this error is not about invalid values for a parameter - stop the job with give an error
                                    if (!e.toString().contains("Value for choice parameter")) {
                                        throw e
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}