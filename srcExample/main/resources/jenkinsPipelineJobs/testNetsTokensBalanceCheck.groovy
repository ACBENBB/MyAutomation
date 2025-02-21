package jenkinsPipelineJobs

String outputFileName = "wallet-balance-results.log"

pipeline {
    agent any

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production'], description: 'Environment on which to run')
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
                    mainLoader.mavenCleanAndCompile("io.securitize.scripts.AUT683_BalanceCheck_TestNetworks")
                }
            }
        }
    }

    post {
        failure {
            script {

                if (fileExists(outputFileName)) {
                    String content = readFile("${outputFileName}")
                    slackSend channel: "C067P7DGJRJ", color: "danger", message: "Problem! Not enough balance in at least one wallet! ${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)\n```\n" + content + "\n```"
                } else {
                    slackSend channel: "C067P7DGJRJ", color: "danger", message: "An error occur trying to check test networks balance in ${env.environment}. Please check the logs in ${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
                }
            }
        }
        success {
            script {
                String content = readFile("${outputFileName}")
                slackSend channel: "C067P7DGJRJ", color: "good", message: "Good! All wallets have enough balance! :party_parrot: ${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)\n```\n" + content + "\n```"
            }
        }
    }
}
