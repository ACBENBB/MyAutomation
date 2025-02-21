package jenkinsPipelineJobs

def allJobsDefaults = [
        "GoogleSkipsSheet_Add_API"   : [testCategory: "NIGHTLY_API", mode: "ADD"],
        "GoogleSkipsSheet_Remove_API": [testCategory: "NIGHTLY_API", mode: "REMOVE"],
        "GoogleSkipsSheet_Add_E2E"   : [testCategory: "NIGHTLY_E2E", mode: "ADD"],
        "GoogleSkipsSheet_Remove_E2E": [testCategory: "NIGHTLY_E2E", mode: "REMOVE"],
]

def currentJobDefaults = allJobsDefaults["${JOB_NAME}"]
println "Loading defaults for job ${JOB_NAME} as: " + currentJobDefaults

if (currentJobDefaults == null) {
    println "ERROR: The job '${JOB_NAME}' that you are running was not found in the job list. Possible solutions: \n" +
            "- Be sure that '${JOB_NAME}' is listed in the upper job list as the first parameter. If it is not listed, you should change the 'Branches to build' job config, pointing to your branch (*/branchName) \n" +
            "- Be sure that '${JOB_NAME}' matches the first param of the job list you added. If it is not matching, you could change the Jenkins job name or the sanity.groovy job name, to make them match"
}

def slackChannel = "C04C0CJMUG3"

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'qa', 'production'], description: 'Environment on which to run')
        extendedChoice(name: 'DOMAINS_TO_ANALYZE', defaultValue: 'ATS,CA,FT,ISR,JP,SID,ST,TA', description: 'Name of domain to analyze in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,CA,FT,ISR,JP,SID,ST,TA',
                visibleItemCount: 10)
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'Should avoid affecting the GoogleSkipsSheet and just simulate the work')
    }

    environment {
        AddOrRemoveMode = "${currentJobDefaults['mode']}"
        testCategory = "${currentJobDefaults['testCategory']}"
    }

    stages {
        stage('Load') {
            steps {
                script {
                    mainLoader = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/stepsMainLoad.groovy"
                }
            }
        }

        stage('Checkout code') {
            steps {
                script {
                    mainLoader.checkoutCode()
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

        stage('Delete old artifacts') {
            steps {
                script {
                    mainLoader.deleteOldArtifacts("results.log", "log file")
                }
            }
        }

        stage('Build & Run script') {
            steps {
                script {
                    mainLoader.mavenCleanAndCompile("io.securitize.scripts.googlesheetskiplist.AddRemoveSkipTestsFromGoogleSheet")

                }
            }
        }
    }

    post {
        failure {
            script {
                slackSend channel: slackChannel, color: "danger", message: "$env.JOB_NAME #${env.BUILD_NUMBER} failed! Check the logs at: (<${env.BUILD_URL}|Open>)"
            }
        }

        success {
            script {
                if (params.isDryRun == false) {
                    println "Sending to slack channel $slackChannel this content:"
                    sh "cat results.log"
                    slackUploadFile channel: slackChannel, filePath: "results.log", initialComment: "Summary of ${env.JOB_NAME} #${env.BUILD_NUMBER} - env: ${params.environment} (<${env.BUILD_URL}|Open>)"
                }
            }
        }
    }
}