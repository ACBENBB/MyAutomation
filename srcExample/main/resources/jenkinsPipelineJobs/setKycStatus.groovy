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
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production'], description: 'Environment on which to run')
        string(name: 'PROFILE_ID', defaultValue: '', description: 'Securitize profile Id to update')
        choice(name: 'KYC_STATUS_TO_SET', choices: ['verified', 'rejected'], description: 'value of KYC to set')
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'Should avoid running the job and just provide details')
    }

    stages {
        stage('Validate parameters') {
            steps {
                script {
                    if (params.PROFILE_ID == null || params.PROFILE_ID.trim().isEmpty()) {
                        error("Build failed because parameter 'PROFILE_ID' must have a value!")
                    }
                }
            }
        }

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
                    if (params.isDryRun == false) {
                        mainLoader.mavenCleanAndCompile("io.securitize.scripts.AUT442_SidCpKYCUpdate")
                    } else {
                        echo "Dry running this job"
                    }
                }
            }
        }
    }
}
