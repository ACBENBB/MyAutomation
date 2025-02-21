package jenkinsPipelineJobs

def productionEnvironments = ["production", "apac"];

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools secion
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production', 'apac'], description: 'Environment on which to run')
        string(name: 'emailRecipients', defaultValue: 'yoav.l@securitize.io,daniel@securitize.io', description: 'list of emails to inform is this job fails (comma separated list)')
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
                    if (productionEnvironments.contains(params.environment))
                        mainLoader.deleteOldArtifacts("index.html", "html report")
                }
            }
        }

        stage('Build & Run script') {
            steps {
                script {
                    if (productionEnvironments.contains(params.environment))
                        mainLoader.mavenCleanAndCompile("updateProductionBearer.xml")
                    else
                        mainLoader.mavenCleanAndCompile("io.securitize.scripts.AUT236_UpdateCpBearer")
                }
            }
        }
    }

    post {
        always {
            script {
                if (productionEnvironments.contains(params.environment))
                // publish html report to be used to analyze failures
                    publishHTML([allowMissing: true, alwaysLinkToLastBuild: false, includes: '**/index.html', keepAll: true, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
            }
        }

        failure {
            mail bcc: '', body: "<b>Details:</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Jenkins job failed -> ${env.JOB_NAME} (build: #${env.BUILD_NUMBER} env: ${params.environment})", to: "${params.emailRecipients}"
            slackSend channel: "C04C0CJMUG3", color: "danger", message: "Jenkins update CP bearer job failed on environment ${params.environment}!!\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
        }

        success {
            slackSend channel: "C04C0CJMUG3", color: "good", message: "Jenkins update CP bearer job success on environment ${params.environment}! :party_parrot:\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
        }
    }
}