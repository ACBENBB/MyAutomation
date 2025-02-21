package jenkinsPipelineJobs

def isEthPriceOk = true
String OUTPUT_FILE_NAME = "output.txt"
String OUTPUT_FILE_GAS_PRICE_PATH = "gasPrice.txt";

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production'], description: 'Environment on which to run')
        string(name: 'maxAllowedGasPrice', defaultValue: "18", description: 'Max allowed price of "suggestBaseFee" ETH gas fee to trigger job - <a href=\'https://api.etherscan.io/api?module=gastracker&amp;action=gasoracle\'>https://api.etherscan.io/api?module=gastracker&amp;action=gasoracle</a>')
        string(name: 'slackChannel', defaultValue: "C04C0CJMUG3", description: 'Slack channel to send results')
        string(name: 'jobNameToExecute', defaultValue: "AUT3_ISR_Investor_Registration_USD", description: 'Name of job to execute')
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'Is this a dry run for debugging purposes only - no tests will actually be started')
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

        stage('Delete old artifacts') {
            steps {
                script {
                    mainLoader.deleteOldArtifacts("${OUTPUT_FILE_NAME}", "${OUTPUT_FILE_NAME}")
                    mainLoader.deleteOldArtifacts("${OUTPUT_FILE_GAS_PRICE_PATH}", "${OUTPUT_FILE_GAS_PRICE_PATH}")
                }
            }
        }

        stage('Build & Run script') {
            steps {
                script {
                    try {
                        mainLoader.mavenCleanAndCompile("io.securitize.scripts.GetEthGasPrice")
                    } catch (e) {
                        isEthPriceOk = false
                    }
                }
            }
        }

        stage('Set job description') {
            steps {
                script {
                    def gasPrice = "";
                    if (fileExists(OUTPUT_FILE_GAS_PRICE_PATH))
                        gasPrice = readFile OUTPUT_FILE_GAS_PRICE_PATH
                    mainLoader.setJobDescription(gasPrice)
                }

            }
        }

        stage('Trigger job if price is right') {
            steps {
                script {
                    mainLoader.triggerJobs([params.jobNameToExecute], isEthPriceOk)
                }
            }
        }
    }

    post {
        always {
            script {
                if (fileExists(OUTPUT_FILE_NAME)) {
                    def messageForSlack = readFile OUTPUT_FILE_NAME
                    String color = isEthPriceOk ? "good" : "danger"
                    slackSend channel: "${params.slackChannel}", color: color, message: "${env.JOB_NAME} #${env.BUILD_NUMBER} - ${messageForSlack} (<${env.BUILD_URL}|Open>)"
                }
            }
        }
    }
}