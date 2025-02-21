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
        choice(name: 'environment', choices: ['sandbox', 'rc'], description: 'Environment on which to run')
        choice(name: 'browserLocation', choices: ['Moon', 'JenkinsLocal'], description: 'Where will the browsers be opened - locally on Jenkins or remotely on Moon')
        string(name: 'suiteFileName', defaultValue: "e2e/blackrock/AUT639_blackrock_daily_deposit_10_investors.xml", description: 'Name of suite file to run')
        choice(name: 'testCategory', choices: ['JENKINS', 'MANUAL_STABILIZATION'], description: 'Category of these tests to be reported to the dashboard. E2E tests have also the option of MANUAL_STABILIZATION')
        string(name: 'investorNumber', defaultValue: "1", description: 'Number of investor to use (1-10)')
    }

    stages {
        stage('Load') {
            steps {
                script {
                    mainLoader = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/stepsMainLoad.groovy"
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
                    mainLoader.deleteOldArtifacts("index.html", " html report")
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

        stage('Build & Run script') {
            steps {
                script {
                    mainLoader.mavenCleanAndCompile(params.suiteFileName)
                }
            }
        }
    }

    post {
        always {
            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, includes: '**/index.html', keepAll: true, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
            step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
        }
    }
}
