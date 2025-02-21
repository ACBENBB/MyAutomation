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
        string(name: 'executionIdsToDelete', defaultValue: '', description: 'Comma separated list of execution IDs to delete (such as 1,2,3)')
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

        stage("Prompt for confirmation") {
            steps {
                script {
                    mainLoader.promptForConfirmation()
                }
            }
        }

        stage('Build & Run script') {
            steps {
                script {
                    mainLoader.mavenCleanAndCompile("io.securitize.scripts.googlesheetskiplist.DeleteDashboardExecutionsByIds")
                }
            }
        }
    }
}