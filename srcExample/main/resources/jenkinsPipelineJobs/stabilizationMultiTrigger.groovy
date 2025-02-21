package jenkinsPipelineJobs

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools secion
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production'], description: 'Environment on which to run')
        choice(name: 'testCategory', choices: ['NIGHTLY_API', 'NIGHTLY_E2E'], description: 'Category of these tests to be evaluated (affects which Google sheet page to load)')
        extendedChoice(name: 'DOMAINS_TO_TEST', defaultValue: 'ATS,FT,ISR,JP,SID,ST,TA,CA', description: 'Name of domain to test in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,FT,ISR,JP,SID,ST,TA,CA',
                visibleItemCount: 10)
        choice(name: 'numberOfExecutions', choices: ["1", "2", "3"], description: 'Number of times to execute the stabilization job')
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

        stage('Trigger the stabilization job') {
            steps {
                script {
                    mainLoader.triggerJobs([])
                }
            }
        }
    }
}
