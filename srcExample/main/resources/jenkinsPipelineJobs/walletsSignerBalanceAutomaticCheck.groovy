package jenkinsPipelineJobs

def slackChannel = "C04C0CJMUG3"
String outputFileName = "wallet-balance-results.log"

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['sandbox', 'rc', 'qa', 'production'], description: 'Environment on which to run')
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
                    mainLoader.deleteOldArtifacts(outputFileName, "previous run results")
                }
            }
        }

        stage('Build & Run Tests') {
            steps {
                script {
                    mainLoader.mavenCleanAndCompile("io.securitize.scripts.AUT644_BalanceCheck_Blackrock")
                }
            }
        }
    }

    post {
        failure {
            script {
                if (fileExists(outputFileName)) {
                    String content = readFile("${outputFileName}")
                    slackSend channel: slackChannel, color: "danger", message: "Problem! Not enough balance in Blackrock Wallets signer!\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)\n```\n" + content + "\n```"
                } else {
                    slackSend channel: slackChannel, color: "danger", message: "An error occur trying to check balance in Blackrock Wallets signer in ${env.environment}.\nPlease check the logs in ${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
                }
            }
        }
        success {
            script {
                String content = readFile("${outputFileName}")
                slackSend channel: slackChannel, color: "good", message: "Good! Blackrock Wallets signer balance is good! :party_parrot:\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)\n```\n" + content + "\n```"
            }
        }
    }
}