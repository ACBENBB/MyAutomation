package jenkinsPipelineJobs

String fileLinkToReportPortal = "reportPortalLink.txt"

pipeline {

    agent any

    options {
        timeout(time: 5, unit: 'HOURS')
    }

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production'], description: 'Environment on which to run')
        choice(name: 'browserType', choices: ['chrome-remote'], description: 'Browser to use')
        choice(name: 'browserDeviceNameToEmulate', choices: ['desktop', 'iPhone 14', 'iPhone 14 Pro', 'iPhone 14 Plus', 'iPad 10th', 'Galaxy S23'], description: 'Browser resolution to use - mimicking known devices')
        choice(name: 'browserLocation', choices: ['Moon', 'JenkinsLocal'], description: 'Where will the browsers be opened - locally on Jenkins or remotely on Moon')
        string(name: 'threadCount', defaultValue: "15", description: 'Maximum number of parallel tests (max=15)')
        extendedChoice(name: 'DOMAINS_TO_TEST', defaultValue: 'ATS,CA,FT,ISR,JP,SID,ST,TA', description: 'Name of domain to test in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,CA,FT,ISR,JP,SID,ST,TA',
                visibleItemCount: 10)
        extendedChoice(name: 'GROUPS_TO_TEST', defaultValue: 'none', description: 'Name of groups to test in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'none,sanity,core',
                visibleItemCount: 10)
        booleanParam(name: 'skipTestsListedInGoogleSheet', defaultValue: true, description: 'Should skip tests listed in a dedicated Google sheet')
        booleanParam(name: 'skipStatistics', defaultValue: false, description: 'Should avoid reporting execution results to the Grafana dashboard db')
        booleanParam(name: 'runFailedTests', defaultValue: false, description: 'Should re-run only failed tests from the previous execution')
        choice(name: 'sendSlackSummaryOnFailures', choices: ['No', 'Main', 'Test'], description: 'Should send failure summary in slack: No, Main report channel or qa test channel')
        string(name: 'emailRecipients', defaultValue: "yoav.l@securitize.io,daniel@securitize.io", description: 'list of emails to inform is this job fails (comma separated list)')
    }

    environment {
        PATH = "/home/ubuntu/.nvm/versions/node/v12.16.3/bin:$PATH"
        testCategory = "NIGHTLY_E2E"
        ALLOW_TEST_RETRY = "true"
        testNetwork = "QUORUM"
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

                    // this section handles sending slack report per param's 'sendSlackSummaryOnFailureschoice' choice ( 'No', 'Main', 'Test')
                    if (sendSlackSummaryOnFailures.equalsIgnoreCase("No")) {
                        echo "Skipping sending slack summary on failure due to parameter value of sendSlackSummaryOnFailures"
                    } else {
                        String channel = ""
                        if (sendSlackSummaryOnFailures.equalsIgnoreCase("Main")) {
                            channel = "C04DN1KRE03"
                        } else if (sendSlackSummaryOnFailures.equalsIgnoreCase("Test")) {
                            channel = "C049J38KK1V"
                        }
                        String initialComment = "Failures of: ${env.JOB_NAME} #${env.BUILD_NUMBER} - env: ${params.environment} (<${env.BUILD_URL}|Open>)"
                        if (!params.GROUPS_TO_TEST.equalsIgnoreCase("none")) {
                            initialComment = "Failures of: ${env.JOB_NAME} #${env.BUILD_NUMBER} - env: ${params.environment}, groups: ${params.GROUPS_TO_TEST} (<${env.BUILD_URL}|Open>)"
                        }
                        slackUploadFile filePath: 'failures_ALL.txt', channel: channel, initialComment: initialComment
                    }


                    // this section handles sending slack report per domain
                    if (!sendSlackSummaryOnFailures.equalsIgnoreCase("No")) {
                        def map = ["CP": "C04B8UF08F7", "ISR": "C04B8UF08F7", "SID": "C04BEAZBGBW", "FT": "C04BBSZ407L", "ATS": "C04BBPVJUTV", "TA": "C04B8UGSV2R", "ST": "C04BBSVQBK4", "CA": "C04BBT0FQ82", "JP": "C05R2KMGY8M"]

                        map.each { entry ->
                            println "working on $entry.key"

                            def fileName = "failures_" + entry.key + ".txt"
                            if (fileExists(fileName)) {
                                String channel = entry.value
                                if (sendSlackSummaryOnFailures.equalsIgnoreCase("test")) {
                                    channel = "C049J38KK1V"
                                }
                                String initialComment = "Failures of: ${env.JOB_NAME} #${env.BUILD_NUMBER} - env: ${params.environment} (<${env.BUILD_URL}|Open>)"
                                if (!params.GROUPS_TO_TEST.equalsIgnoreCase("none")) {
                                    initialComment = "Failures of: ${env.JOB_NAME} #${env.BUILD_NUMBER} - env: ${params.environment}, groups: ${params.GROUPS_TO_TEST} (<${env.BUILD_URL}|Open>)"
                                }
                                println "Sending to $channel result of: $entry.key with initial comment: " + initialComment + ", with this content:"
                                sh "cat ${fileName}"
                                //******to disable the report - comment out the line below*****
                                slackUploadFile channel: channel, filePath: fileName, initialComment: initialComment
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
                    def grafanaUrl = "http://3.144.71.67:3000/d/s_Qrh9Lnk/qa-e2e-nightly_status?orgId=1&var-environment=${params.environment}&kiosk"
                    String channel = ""
                    if (sendSlackSummaryOnFailures.equalsIgnoreCase("Main")) {
                        channel = "C04DN1KRE03"
                    } else if (sendSlackSummaryOnFailures.equalsIgnoreCase("Test")) {
                        channel = "C049J38KK1V"
                    }
                    build wait: true, job: 'Publish_Grafana_Dashboard_Screenshot', parameters: [string(name: 'branchName', value: "${params.branchName}"), string(name: 'environment', value: "${params.environment}"), string(name: 'publishTo', value: channel), string(name: 'grafanaUrl', value: "${grafanaUrl}"), string(name: 'initialComment', value: 'Nightly E2E - current state:')]
                    // Trigger report portal dashboard screenshot only when triggering Nightly E2E on all domains)
                    if (params.DOMAINS_TO_TEST == "ATS,CA,FT,ISR,JP,SID,ST,TA") {
                        // build dashboard url
                        def dashboardUrlMappings = ["rc": "57", "sandbox": "64", "production": "68"]
                        String reportPortalDashboardUriTemplate = "http://172.18.0.1:8083/ui/#securitize/dashboard/%s"
                        String reportPortalDashboardUri = String.format(reportPortalDashboardUriTemplate, dashboardUrlMappings[params.environment])
                        build wait: true, job: 'Publish_ReportPortal_Dashboard_Screenshot', parameters: [string(name: 'branchName', value: "${params.branchName}"), string(name: 'environment', value: "${params.environment}"), string(name: 'publishTo', value: channel), string(name: 'reportPortalDashboardUri', value: "${reportPortalDashboardUri}"), string(name: 'initialComment', value: "Nightly E2E - current state - ${environment} :")]
                    } else {
                        echo "Not sending report portal dashboard via slack. Running on environment: ${environment} and domain to test: ${DOMAINS_TO_TEST}"
                    }
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