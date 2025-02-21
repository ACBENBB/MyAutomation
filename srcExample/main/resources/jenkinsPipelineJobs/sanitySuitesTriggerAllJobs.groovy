package jenkinsPipelineJobs

def jobsNames = [
        "AUT412_SID_SanitySuite",
        "AUT414_ATS_SanitySuite",
        "AUT415_FT_SanitySuite",
        "AUT416_ISR_SanitySuite",
        "AUT417_ST_SanitySuite",
        "AUT418_TA_SanitySuite",
        "AUT419_CA_SanitySuite",
        "AUT421_ATS_SanitySuite_API",
        "AUT422_FT_SanitySuite_API",
        "AUT423_ISR_SanitySuite_API",
        "AUT424_SID_SanitySuite_API",
        "AUT425_ST_SanitySuite_API",
        "AUT426_TA_SanitySuite_API",
        "AUT427_CA_SanitySuite_API"
]

pipeline {
    agent any

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['RC', 'SANDBOX', 'PROD'], description: 'Environment on which to run')
        booleanParam(name: 'skipTestsListedInGoogleSheet', defaultValue: true, description: 'Should skip tests listed in a dedicated Google sheet')
        booleanParam(name: 'runFailedTests', defaultValue: false, description: 'Should re-run only failed tests from the previous execution')
        choice(name: 'testTypesToRun', choices: ['all', 'E2E', 'API'], description: 'Which jobs to trigger - all jobs, E2E jobs or API jobs')
        extendedChoice(name: 'domainsToTest', defaultValue: 'ATS,CA,FT,ISR,JP,SID,ST,TA', description: 'Name of domain to test in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,CA,FT,ISR,JP,SID,ST,TA',
                visibleItemCount: 10)
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'Should avoid running the job and just log which jobs will be triggered')
    }


    stages {
        stage('Load') {
            steps {
                script {
                    mainLoader = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/stepsMainLoad.groovy"
                }
            }
        }

        stage('Set job description') {
            steps {
                script {
                    mainLoader.setJobDescription()
                }
            }
        }

        stage('Trigger the jobs') {
            steps {
                script {
                    mainLoader.triggerJobs(jobsNames)
                }
            }
        }
    }
}
