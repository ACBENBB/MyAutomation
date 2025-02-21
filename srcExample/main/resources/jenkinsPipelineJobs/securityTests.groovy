package jenkinsPipelineJobs

def allJobsDefaults = [
        "AUT615_Security_Internal_Endpoints": [suiteFileName: "api/Security/AUT615_Security_Internal_Endpoints.xml", testCategory: "JENKINS", environment: ['rc'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT616_Security_Public_Endpoints"  : [suiteFileName: "api/Security/AUT616_Security_Public_Endpoints.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT617_Security_RateLimiting"      : [suiteFileName: "api/Security/AUT617_Security_RateLimiting.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
]

def outputFile = "securityOutput.txt"

def currentJobDefaults = allJobsDefaults["${JOB_NAME}"]
println "Loading defaults for job ${JOB_NAME} as: " + currentJobDefaults

if (currentJobDefaults == null) {
    println "ERROR: The job '${JOB_NAME}' that you are running was not found in the job list. Possible solutions: \n" +
            "- Be sure that '${JOB_NAME}' is listed in the upper job list as the first parameter. If it is not listed, you should change the 'Branches to build' job config, pointing to your branch (*/branchName) \n" +
            "- Be sure that '${JOB_NAME}' matches the first param of the job list you added. If it is not matching, you could change the Jenkins job name or the sanity.groovy job name, to make them match"
}

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: currentJobDefaults['environment'], description: 'Environment on which to run')
        string(name: 'suiteFileName', defaultValue: currentJobDefaults['suiteFileName'], description: 'Name of suite file to run')
        choice(name: 'testCategory', choices: currentJobDefaults['testCategory'], description: 'Category of these tests to be reported to the dashboard. E2E tests have also the option of MANUAL_STABILIZATION')
        string(name: 'slackChannel', defaultValue: "C05JEEHKZ8A", description: 'Slack channel to send status')
        string(name: 'emailRecipients', defaultValue: currentJobDefaults['emailRecipients'], description: 'list of emails to inform is this job fails (comma separated list)')
    }


    stages {
        stage('Load') {
            steps {
                script {
                    mainLoader = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/stepsMainLoad.groovy"
                }
            }
        }

        stage('Delete old artifacts') {
            steps {
                script {
                    def filesToClean = ["index.html", "dashboard.html", "exception.html", outputFile]
                    filesToClean.each { currentFile ->
                            mainLoader.deleteOldArtifacts(currentFile, currentFile)
                    }
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

        stage('Build & Run script') {
            steps {
                script {
                    mainLoader.mavenCleanAndCompile(params.suiteFileName)
                }
            }
        }
    }

    post {
        always {
            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, includes: '**/index.html', keepAll: true, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
            step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
        }

        success {
            script {
                slackSend(channel: "${env.slackChannel}", message: "Security tests success! (${params.environment}): ${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)", color: "good")
                if (fileExists(outputFile)) {
                    slackUploadFile channel: "${env.slackChannel}", filePath: outputFile
                }
            }
        }

        failure {
            script {
                slackSend(channel: "${env.slackChannel}", message: "Security tests FAILURE! (${params.environment}): ${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)", color: "danger")
                if (fileExists(outputFile)) {
                    slackUploadFile channel: "${env.slackChannel}", filePath: outputFile
                }
            }
            mail bcc: '', body: "<b>Details:</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Jenkins job failed -> ${env.JOB_NAME} (build: #${env.BUILD_NUMBER} env: ${params.environment})", to: "${params.emailRecipients}"
        }
    }
}