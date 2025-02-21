package jenkinsPipelineJobs

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox'], description: 'Environment on which to run')
        string(name: 'securitizeId', defaultValue: 'null', description: 'Investor Securitize Id')
        string(name: 'fundingAmount', defaultValue: '10000', description: 'Funding amount')
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


        stage('Build & Run script') {
            steps {
                script {
                    mainLoader.mavenCleanAndCompile("e2e/CA/AUT641_CreateCashAccount.xml")

                }
            }
        }
    }

}