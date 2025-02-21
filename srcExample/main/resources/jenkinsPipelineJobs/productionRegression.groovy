package jenkinsPipelineJobs

pipeline {

    agent any

    options {
        timeout(time: 3, unit: 'HOURS')
    }

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'production', description: 'Name of git branch to pull our code from')
        choice(name: 'testType', choices: ['E2E', 'API'], description: 'Which test suite should execute')
        choice(name: 'testNetwork', choices: ['QUORUM', 'AVALANCHE_AVAX'], description: 'Which test network to use - affects token used')
        extendedChoice(name: 'DOMAINS_TO_TEST', defaultValue: 'ATS,CA,FT,ISR,JP,SID,ST,TA', description: 'Name of domain to test in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,CA,FT,ISR,JP,SID,ST,TA',
                visibleItemCount: 10)
        booleanParam(name: 'skipTestsListedInGoogleSheet', defaultValue: true, description: 'Should skip tests listed in a dedicated Google sheet')
        booleanParam(name: 'skipStatistics', defaultValue: false, description: 'Should avoid reporting execution results to the Grafana dashboard db')
        booleanParam(name: 'runFailedTests', defaultValue: false, description: 'Should re-run only failed tests from the previous execution')
        string(name: 'emailRecipients', defaultValue: "daniel@securitize.io", description: 'list of emails to inform is this job fails (comma separated list)')
    }

    environment {
        environment = "production"
        browserType = 'chrome-remote'
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

        stage('Build & Run Tests') {
            steps {
                script {
                    mainLoader.mavenCleanAndCompile()
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

                if (fileExists('target/failsafe-reports/testng-results.xml')) {
                    sh "mvn compile exec:java -Dexec.mainClass=io.securitize.scripts.ParseTestNgResults"
                    String channel = "C049J38KK1V"
                    slackUploadFile filePath: 'failures_ALL.txt', channel: channel, initialComment: "Failures of: ${env.JOB_NAME} #${env.BUILD_NUMBER} - testType: ${params.testType} (<${env.BUILD_URL}|Open>)"

                    // this section handles sending slack report per domain
                    def domains = ["CP", "ISR", "SID", "FT", "ATS", "TA", "ST", "CA", "JP"]

                    domains.each { currentDomain ->
                        println "working on $currentDomain"

                        def fileName = "failures_" + currentDomain + ".txt"
                        if (fileExists(fileName)) {
                            println "Sending to $channel result of: $currentDomain with this content:"
                            sh "cat ${fileName}"
                            slackUploadFile channel: channel, filePath: fileName, initialComment: "Failures of: ${env.JOB_NAME} #${env.BUILD_NUMBER} - testType: ${params.testType} (<${env.BUILD_URL}|Open>)"
                            sh "mv ${fileName} ${fileName}_SENT"
                        } else {
                            println "Can't find file " + fileName + " - skipping sending report for that domain"
                        }
                    }
                } else {
                    echo "testng-results.xml not found. Not creating text failure report"
                }
            }

            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, includes: '**/index.html', keepAll: true, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
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

            mail bcc: '', body: "<b>Details:</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Jenkins job failed -> ${env.JOB_NAME} (build: #${env.BUILD_NUMBER} testType: ${params.testType})", to: "${params.emailRecipients}"
        }
    }
}