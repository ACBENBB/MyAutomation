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
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'Should avoid running the job and just provide details')
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
                    if (params.isDryRun == false) {
                        mainLoader.mavenCleanAndCompile("e2e/SID/AUT84_SID_API_SecuritizeID_Create_Investor.xml")
                    } else {
                        echo "Dry running this job"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                String failedFileName = "testng-failed-${params.environment}.xml"
                if (fileExists(failedFileName)) {
                    echo "removing old ${failedFileName} as it is no longer relevant"
                    sh "rm ${failedFileName}"
                }
            }

            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, includes: '**/index.html', keepAll: true, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
            step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])

        }

        failure {
            script {
                if (fileExists('target/failsafe-reports/testng-failed.xml')) {
                    String fileName = "testng-failed-${params.environment}.xml"
                    echo "copying testng-failed.xml so it is available for next execution (as ${fileName})"
                    sh "cp target/failsafe-reports/testng-failed.xml ./${fileName}"
                } else {
                    echo "test failed but target/failsafe-reports/testng-failed.xml was not found..."
                }
            }
        }
    }
}