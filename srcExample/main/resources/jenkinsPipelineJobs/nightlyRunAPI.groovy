package jenkinsPipelineJobs

String fileLinkToReportPortal = "reportPortalLink.txt"

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
        extendedChoice(name: 'DOMAINS_TO_TEST', defaultValue: 'ATS,FT,ISR,JP,SID,ST,TA,CA', description: 'Name of domain to test in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,FT,ISR,JP,SID,ST,TA,CA',
                visibleItemCount: 10)
        booleanParam(name: 'runFailedTests', defaultValue: false, description: 'Should re-run only failed tests from the previous execution')
        booleanParam(name: 'skipTestsListedInGoogleSheet', defaultValue: true, description: 'Should skip tests listed in a dedicated Google sheet')
        booleanParam(name: 'skipStatistics', defaultValue: false, description: 'Should avoid reporting execution results to the Grafana dashboard db')
        choice(name: 'sendSlackSummaryOnFailures', choices: ['No', 'Main', 'Test'], description: 'Should send failure summary in slack: No, Main report channel or qa test channel')
        string(name: 'emailRecipients', defaultValue: "yoav.l@securitize.io,daniel@securitize.io", description: 'list of emails to inform is this job fails (comma separated list)')
    }

    environment {
        PATH = "/home/ubuntu/.nvm/versions/node/v12.16.3/bin:$PATH"
        testCategory = "NIGHTLY_API"
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
                    mainLoader.deleteOldArtifacts("index.html", " html report")
                    mainLoader.deleteOldArtifacts("${fileLinkToReportPortal}", "link to ReportPortal")
                }
            }
        }

        stage('Build & Run tests') {
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

                    if (sendSlackSummaryOnFailures.equalsIgnoreCase("No")) {
                        echo "Skipping sending slack summary on failure due to parameter value of sendSlackSummaryOnFailures"
                    } else {
                        String channel = ""
                        if (sendSlackSummaryOnFailures.equalsIgnoreCase("Main")) {
                            channel = "C04DN1KRE03"
                        } else if (sendSlackSummaryOnFailures.equalsIgnoreCase("Test")) {
                            channel = "C049J38KK1V"
                        }
                        slackUploadFile filePath: 'failures_ALL.txt', channel: channel, initialComment: "Failures of: ${env.JOB_NAME} #${env.BUILD_NUMBER} - env: ${params.environment} (<${env.BUILD_URL}|Open>)"
                    }

                    // this section handles sending slack report per domain
                    if (!sendSlackSummaryOnFailures.equalsIgnoreCase("No")) {
                        def map = ["ISR": "C04B8UF08F7", "CP": "C04B8UF08F7", "SID": "C04BEAZBGBW", "FT": "C04BBSZ407L", "ATS": "C04BBPVJUTV", "TA": "C04B8UGSV2R", "ST": "C04BBSVQBK4", "CA": "C04BBT0FQ82", "JP": "C05R2KMGY8M"]

                        map.each { entry ->
                            println "working on $entry.key"

                            def fileName = "failures_" + entry.key + ".txt"
                            if (fileExists(fileName)) {
                                String channel = entry.value
                                if (sendSlackSummaryOnFailures.equalsIgnoreCase("test")) {
                                    channel = "C049J38KK1V"
                                }
                                println "Sending to $channel result of: $entry.key with this content:"
                                sh "cat ${fileName}"
                                //******to disable the report - comment out the line below*****
                                slackUploadFile channel: channel, filePath: fileName, initialComment: "Failures of: ${env.JOB_NAME} #${env.BUILD_NUMBER} - env: ${params.environment} (<${env.BUILD_URL}|Open>)"
                                sh "mv ${fileName} ${fileName}_SENT"
                            } else {
                                println "Can't find file " + fileName + " - skipping sending report for that domain"
                            }
                        }
                    } else {
                        echo "Skipping sending slack summary per domain on failure due to parameter value of sendSlackSummaryOnFailures"
                    }

                } else {
                    echo "testng-results.xml not found. Not creating text failure report"
                }

                // only send dashboard summary if the slack option is enabled
                if (!sendSlackSummaryOnFailures.equalsIgnoreCase("No")) {
                    def grafanaUrl = "http://3.144.71.67:3000/d/KUkaazvVz/latest-api-test-execution-status-by-domain?orgId=1&kiosk"
                    def reportPortalDashboardUri = "http://172.18.0.1:8083/ui/#securitize/dashboard/36"
                    String channel = ""
                    if (sendSlackSummaryOnFailures.equalsIgnoreCase("Main")) {
                        channel = "C04DN1KRE03"
                    } else if (sendSlackSummaryOnFailures.equalsIgnoreCase("Test")) {
                        channel = "C049J38KK1V"
                    }
                    build wait: true, job: 'Publish_Grafana_Dashboard_Screenshot', parameters: [string(name: 'branchName', value: 'master'), string(name: 'environment', value: "${params.environment}"), string(name: 'publishTo', value: channel), string(name: 'grafanaUrl', value: "${grafanaUrl}"), string(name: 'initialComment', value: 'Nightly API - current state:')]
                    build wait: true, job: 'Publish_ReportPortal_Dashboard_Screenshot', parameters: [string(name: 'branchName', value: "${params.branchName}"), string(name: 'environment', value: "${params.environment}"), string(name: 'publishTo', value: channel), string(name: 'reportPortalDashboardUri', value: "${reportPortalDashboardUri}"), string(name: 'initialComment', value: "Nightly API - current state - ${environment} :")]
                }

                if (fileExists(fileLinkToReportPortal)) {
                    def reportPortalLink = readFile "${fileLinkToReportPortal}"
                    currentBuild.description += " <a href='${reportPortalLink}' target='_blank'>reportPortal</a>"
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

            archiveArtifacts allowEmptyArchive: true, artifacts: 'target/failsafe-reports/testng-results.xml', followSymlinks: false
            sh "mvn exec:java -Dexec.mainClass=io.securitize.scripts.CheckFailureRate"
            mail bcc: '', body: "<b>Details:</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Jenkins job failed -> ${env.JOB_NAME} (build: #${env.BUILD_NUMBER} env: ${params.environment})", to: "${params.emailRecipients}"
        }
    }
}