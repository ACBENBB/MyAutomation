package jenkinsPipelineJobs

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools secion
        maven "M3"
        jdk "OpenJDK11"
    }

    options {
        timeout(time: 3, unit: 'HOURS')
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production'], description: 'Environment on which to run')
        choice(name: 'testCategory', choices: ['NIGHTLY_E2E', 'NIGHTLY_API'], description: 'Category of these tests to be executed (Stands for E2E or API)')
        string(name: 'hoursBackToScan', defaultValue: '24', description: 'How many hours back to analyze')
        extendedChoice(name: 'DOMAINS_TO_TEST', defaultValue: 'ATS,FT,ISR,JP,SID,ST,TA,CA', description: 'Name of domain to test in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,FT,ISR,JP,SID,ST,TA,CA',
                visibleItemCount: 10)
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'If true will only list names of candidate tests to run. If false will execute them')
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
                    mainLoader.deleteOldArtifacts("index.html", "html report")
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
                    mainLoader.mavenCleanAndCompile("io.securitize.scripts.RunDynamicFailSkippedSuite")
                }
            }
        }
    }

    post {
        always {
            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, includes: '**/index.html', keepAll: true, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
        }
    }
}