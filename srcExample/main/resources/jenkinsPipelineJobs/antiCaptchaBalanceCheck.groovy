package jenkinsPipelineJobs

String outputFileName = "anti-captcha-balance.log"

pipeline {
    agent any

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
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
                    mainLoader.mavenCleanAndCompile("io.securitize.scripts.AUT694_BalanceCheck_AntiCaptcha")
                }
            }
        }
    }

    post {
        failure {
            script {
                if (fileExists(outputFileName)) {
                    String content = readFile("${outputFileName}")
                    slackSend channel: "C04C0CJMUG3", color: "danger", message: "Problem! Not enough balance in Anti-Captcha!\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)\n```\n" + content + "\n```"
                } else {
                    slackSend channel: "C04C0CJMUG3", color: "danger", message: "An error occur trying to check Anti-Captcha balance. Please check the logs in:\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
                }
            }
        }
        success {
            script {
                String content = readFile("${outputFileName}")
                slackSend channel: "C04C0CJMUG3", color: "good", message: "Anti-Captcha balance is good! :party_parrot:\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)\n```\n" + content + "\n```"
            }
        }
    }
}
