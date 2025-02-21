package jenkinsPipelineJobs

def allJobsDefaults = [

        "AUT412_SID_SanitySuite_RC"         : [suiteFileName: "e2e/AUT303_SID_TotalTests.xml", testCategory: "CICD_E2E", environment: ['rc'], emailRecipients: "daniel@securitize.io", testsGroup: "sanitySID"],
        "AUT414_ATS_SanitySuite_RC"         : [suiteFileName: "e2e/AUT300_ATS_TotalTests.xml", testCategory: "CICD_E2E", environment: ['rc'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityATS"],
        "AUT415_FT_SanitySuite_RC"          : [suiteFileName: "e2e/AUT301_FT_TotalTests.xml", testCategory: "CICD_E2E", environment: ['rc'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityFT"],
        "AUT416_ISR_SanitySuite_RC"         : [suiteFileName: "e2e/AUT302_ISR_TotalTests.xml", testCategory: "CICD_E2E", environment: ['rc'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityISR"],
        "AUT417_ST_SanitySuite_RC"          : [suiteFileName: "e2e/AUT304_ST_TotalTests.xml", testCategory: "CICD_E2E", environment: ['rc'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityST"],
        "AUT418_TA_SanitySuite_RC"          : [suiteFileName: "e2e/AUT305_TA_TotalTests.xml", testCategory: "CICD_E2E", environment: ['rc'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityTA"],
        "AUT419_CA_SanitySuite_RC"          : [suiteFileName: "e2e/AUT299_CA_TotalTests.xml", testCategory: "CICD_E2E", environment: ['rc'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityCA"],
        "AUT650_JP_SanitySuite_RC"          : [suiteFileName: "e2e/AUT651_JP_TotalTests.xml", testCategory: "CICD_E2E", environment: ['rc'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityJP"],
        "AUT421_ATS_SanitySuite_API_RC"     : [suiteFileName: "api/AUT306_ATS_TotalTests_API.xml", testCategory: "CICD_API", environment: ['rc'], emailRecipients: "daniel@securitize.io"],
        "AUT422_FT_SanitySuite_API_RC"      : [suiteFileName: "api/AUT307_FT_TotalTests_API.xml", testCategory: "CICD_API", environment: ['rc'], emailRecipients: "daniel@securitize.io"],
        "AUT423_ISR_SanitySuite_API_RC"     : [suiteFileName: "api/AUT308_ISR_TotalTests_API.xml", testCategory: "CICD_API", environment: ['rc'], emailRecipients: "daniel@securitize.io"],
        "AUT424_SID_SanitySuite_API_RC"     : [suiteFileName: "api/AUT309_SID_TotalTests_API.xml", testCategory: "CICD_API", environment: ['rc'], emailRecipients: "daniel@securitize.io"],
        "AUT425_ST_SanitySuite_API_RC"      : [suiteFileName: "api/AUT310_ST_TotalTests_API.xml", testCategory: "CICD_API", environment: ['rc'], emailRecipients: "daniel@securitize.io"],
        "AUT426_TA_SanitySuite_API_RC"      : [suiteFileName: "api/AUT311_TA_TotalTests_API.xml", testCategory: "CICD_API", environment: ['rc'], emailRecipients: "daniel@securitize.io"],
        "AUT427_CA_SanitySuite_API_RC"      : [suiteFileName: "api/AUT342_CA_TotalTests_API.xml", testCategory: "CICD_API", environment: ['rc'], emailRecipients: "daniel@securitize.io"],

        "AUT412_SID_SanitySuite_SANDBOX"    : [suiteFileName: "e2e/AUT303_SID_TotalTests.xml", testCategory: "CICD_E2E", environment: ['sandbox'], emailRecipients: "daniel@securitize.io", testsGroup: "sanitySID"],
        "AUT414_ATS_SanitySuite_SANDBOX"    : [suiteFileName: "e2e/AUT300_ATS_TotalTests.xml", testCategory: "CICD_E2E", environment: ['sandbox'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityATS"],
        "AUT415_FT_SanitySuite_SANDBOX"     : [suiteFileName: "e2e/AUT301_FT_TotalTests.xml", testCategory: "CICD_E2E", environment: ['sandbox'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityFT"],
        "AUT416_ISR_SanitySuite_SANDBOX"    : [suiteFileName: "e2e/AUT302_ISR_TotalTests.xml", testCategory: "CICD_E2E", environment: ['sandbox'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityISR"],
        "AUT417_ST_SanitySuite_SANDBOX"     : [suiteFileName: "e2e/AUT304_ST_TotalTests.xml", testCategory: "CICD_E2E", environment: ['sandbox'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityST"],
        "AUT418_TA_SanitySuite_SANDBOX"     : [suiteFileName: "e2e/AUT305_TA_TotalTests.xml", testCategory: "CICD_E2E", environment: ['sandbox'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityTA"],
        "AUT419_CA_SanitySuite_SANDBOX"     : [suiteFileName: "e2e/AUT299_CA_TotalTests.xml", testCategory: "CICD_E2E", environment: ['sandbox'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityCA"],
        "AUT650_JP_SanitySuite_SANDBOX"     : [suiteFileName: "e2e/AUT651_JP_TotalTests.xml", testCategory: "CICD_E2E", environment: ['sandbox'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityJP"],
        "AUT421_ATS_SanitySuite_API_SANDBOX": [suiteFileName: "api/AUT306_ATS_TotalTests_API.xml", testCategory: "CICD_API", environment: ['sandbox'], emailRecipients: "daniel@securitize.io"],
        "AUT422_FT_SanitySuite_API_SANDBOX" : [suiteFileName: "api/AUT307_FT_TotalTests_API.xml", testCategory: "CICD_API", environment: ['sandbox'], emailRecipients: "daniel@securitize.io"],
        "AUT423_ISR_SanitySuite_API_SANDBOX": [suiteFileName: "api/AUT308_ISR_TotalTests_API.xml", testCategory: "CICD_API", environment: ['sandbox'], emailRecipients: "daniel@securitize.io"],
        "AUT424_SID_SanitySuite_API_SANDBOX": [suiteFileName: "api/AUT309_SID_TotalTests_API.xml", testCategory: "CICD_API", environment: ['sandbox'], emailRecipients: "daniel@securitize.io"],
        "AUT425_ST_SanitySuite_API_SANDBOX" : [suiteFileName: "api/AUT310_ST_TotalTests_API.xml", testCategory: "CICD_API", environment: ['sandbox'], emailRecipients: "daniel@securitize.io"],
        "AUT426_TA_SanitySuite_API_SANDBOX" : [suiteFileName: "api/AUT311_TA_TotalTests_API.xml", testCategory: "CICD_API", environment: ['sandbox'], emailRecipients: "daniel@securitize.io"],
        "AUT427_CA_SanitySuite_API_SANDBOX" : [suiteFileName: "api/AUT342_CA_TotalTests_API.xml", testCategory: "CICD_API", environment: ['sandbox'], emailRecipients: "daniel@securitize.io"],

        "AUT412_SID_SanitySuite_PROD"       : [suiteFileName: "e2e/AUT303_SID_TotalTests.xml", testCategory: "CICD_E2E", environment: ['production'], emailRecipients: "daniel@securitize.io", testsGroup: "sanitySID"],
        "AUT414_ATS_SanitySuite_PROD"       : [suiteFileName: "e2e/AUT300_ATS_TotalTests.xml", testCategory: "CICD_E2E", environment: ['production'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityATS"],
        "AUT415_FT_SanitySuite_PROD"        : [suiteFileName: "e2e/AUT301_FT_TotalTests.xml", testCategory: "CICD_E2E", environment: ['production'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityFT"],
        "AUT416_ISR_SanitySuite_PROD"       : [suiteFileName: "e2e/AUT302_ISR_TotalTests.xml", testCategory: "CICD_E2E", environment: ['production'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityISR"],
        "AUT417_ST_SanitySuite_PROD"        : [suiteFileName: "e2e/AUT304_ST_TotalTests.xml", testCategory: "CICD_E2E", environment: ['production'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityST"],
        "AUT418_TA_SanitySuite_PROD"        : [suiteFileName: "e2e/AUT305_TA_TotalTests.xml", testCategory: "CICD_E2E", environment: ['production'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityTA"],
        "AUT419_CA_SanitySuite_PROD"        : [suiteFileName: "e2e/AUT299_CA_TotalTests.xml", testCategory: "CICD_E2E", environment: ['production'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityCA"],
        "AUT650_JP_SanitySuite_PROD"        : [suiteFileName: "e2e/AUT651_JP_TotalTests.xml", testCategory: "CICD_E2E", environment: ['production'], emailRecipients: "daniel@securitize.io", testsGroup: "sanityJP"],
        "AUT421_ATS_SanitySuite_API_PROD"   : [suiteFileName: "api/AUT306_ATS_TotalTests_API.xml", testCategory: "CICD_API", environment: ['production'], emailRecipients: "daniel@securitize.io"],
        "AUT422_FT_SanitySuite_API_PROD"    : [suiteFileName: "api/AUT307_FT_TotalTests_API.xml", testCategory: "CICD_API", environment: ['production'], emailRecipients: "daniel@securitize.io"],
        "AUT423_ISR_SanitySuite_API_PROD"   : [suiteFileName: "api/AUT308_ISR_TotalTests_API.xml", testCategory: "CICD_API", environment: ['production'], emailRecipients: "daniel@securitize.io"],
        "AUT424_SID_SanitySuite_API_PROD"   : [suiteFileName: "api/AUT309_SID_TotalTests_API.xml", testCategory: "CICD_API", environment: ['production'], emailRecipients: "daniel@securitize.io"],
        "AUT425_ST_SanitySuite_API_PROD"    : [suiteFileName: "api/AUT310_ST_TotalTests_API.xml", testCategory: "CICD_API", environment: ['production'], emailRecipients: "daniel@securitize.io"],
        "AUT426_TA_SanitySuite_API_PROD"    : [suiteFileName: "api/AUT311_TA_TotalTests_API.xml", testCategory: "CICD_API", environment: ['production'], emailRecipients: "daniel@securitize.io"],
        "AUT427_CA_SanitySuite_API_PROD"    : [suiteFileName: "api/AUT342_CA_TotalTests_API.xml", testCategory: "CICD_API", environment: ['production'], emailRecipients: "daniel@securitize.io"],
]

// Output for debugging if it is failing
def jobPrinting = ""
for (job in allJobsDefaults) {
    jobPrinting += "\n-> " + job
}
println "All jobs default values list: " + jobPrinting

def currentJobDefaults = allJobsDefaults["${JOB_NAME}"]
println "Loading defaults for job ${JOB_NAME} as: " + currentJobDefaults

if (currentJobDefaults == null) {
    println "ERROR: The job '${JOB_NAME}' that you are running was not found in the job list. Possible solutions: \n" +
            "- Be sure that '${JOB_NAME}' is listed in the upper job list as the first parameter. If it is not listed, you should change the 'Branches to build' job config, pointing to your branch (*/branchName) \n" +
            "- Be sure that '${JOB_NAME}' matches the first param of the job list you added. If it is not matching, you could change the Jenkins job name or the sanity.groovy job name, to make them match"
}

// set list of allowed browsers - default list or specific value from job configuration
defaultListOfBrowsers = ['chrome-remote']
def listOfBrowsers

if (currentJobDefaults.containsKey("browserType")) {
    listOfBrowsers = currentJobDefaults["browserType"]
} else {
    listOfBrowsers = defaultListOfBrowsers
}
println("List of browsers to be used: " + listOfBrowsers)

// This NonCPS annotation is used to tell Jenkins not to serialize this function nor its parameters to disk
// thus helps us cope with Matcher class not being serializable
@NonCPS
def sendSlackUpdate(String statusColor, String status) {
    def map = ["CP": "C04B8UF08F7", "ISR": "C04B8UF08F7", "SID": "C04BEAZBGBW", "FT": "C04BBSZ407L", "ATS": "C04BBPVJUTV", "TA": "C04B8UGSV2R", "ST": "C04BBSVQBK4", "CA": "C04BBT0FQ82", "JP": "C05R2KMGY8M"]

    // set default values in case of extraction failure
    String domainName = "Can't deduce domain name!"
    String triggeredBy = "Can't find who triggered!"
    String serviceCommittedName = "Can't find Jenkins Ops job name!"
    String serviceCommittedUrl = "url extraction error"

    // extract domain from job name
    String jobName = env.JOB_NAME
    def regexGetDomain = /AUT[\d]+_([A-Z]+)_.*/
    def matcher = (jobName =~ regexGetDomain)
    if (matcher.matches()) {
        domainName = matcher[0][1]
    }

    // try to extract who triggered the Jenkins Ops job and the name of that job
    // based on the cause parameter which should have been passed to us
    if (params.cause != "") {
        String cause = params.cause
        def regexGetAuthor = /.*author: <b>([a-zA-Z -_]+?)<\/b>.*/
        matcher = (cause =~ regexGetAuthor)
        if (matcher.matches()) {
            triggeredBy = matcher[0][1]
        }

        def regexServiceCommitted = /.*build:.*?<b>([a-zA-Z0-9-_\/]+).*<\/b>.*/
        matcher = (cause =~ regexServiceCommitted)
        if (matcher.matches()) {
            serviceCommittedName = matcher[0][1]
        }

        def regexServiceCommittedUrl = /.*build:.*?<a href=['"]([a-zA-Z0-9-_\/:\.]+)['"].*/
        matcher = (cause =~ regexServiceCommittedUrl)
        if (matcher.matches()) {
            serviceCommittedUrl = matcher[0][1]
        }
    }

    String jobType = jobName.contains("API") ? "API" : "E2E"

    println("Domain name identified as: " + domainName)
    println("Triggered by: " + triggeredBy)
    println("Service name identified as: " + serviceCommittedName)
    println("Service job url identified as: " + serviceCommittedUrl)
    println("Detected Job type as:: " + jobType)

    def message = "SanitySuites (CI/CD) Report of: *${domainName}*\n●   Environment: *${params.environment}*\n●   Job name: *${env.JOB_NAME} #${env.BUILD_NUMBER}*\n●   Job type: *${jobType}*\n●   Service committed: *${serviceCommittedName}* (<${serviceCommittedUrl}|Open>)\n●   Triggered by: *${triggeredBy}*\n●   Status: *${status}* (<${env.BUILD_URL}|Open>)"
    if (triggeringUserId == 'ops-integration') {
        println("Will send message of: " + message)
        slackSend channel: map[domainName], color: statusColor, message: message
    } else {
        println("As this build was not triggered by ops rather by $triggeringUserId, this prepared message will not be sent via slack:\n$message")
    }
}

pipeline {

    agent any

    options {
        // used to automatically discard runs that end in: ABORTED, NOT_BUILT or UNSTABLE
        // Had to do this logic in two steps as the plugin doesn't natively support the NOT_BUILT state
        // so it can't erase job runs in that state (such as when automatically aborting running jobs
        // upon triggering a new instance)
        buildDiscarder(BuildHistoryManager([
                [
                        conditions        : [
                                BuildResult(matchSuccess: true, matchFailure: true)
                        ],
                        matchAtMost: 15,
                        continueAfterMatch: false
                ],
                [
                        conditions: [
                                MatchEveryBuild()
                        ],
                        actions   : [DeleteBuild()]
                ]
        ]))
    }

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: currentJobDefaults['environment'], description: 'Environment on which to run')
        choice(name: 'browserType', choices: listOfBrowsers, description: 'Browser to use')
        string(name: 'suiteFileName', defaultValue: currentJobDefaults['suiteFileName'], description: 'Name of suite file to run')
        booleanParam(name: 'skipTestsListedInGoogleSheet', defaultValue: true, description: 'Should skip tests listed in a dedicated Google sheet')
        booleanParam(name: 'runFailedTests', defaultValue: false, description: 'Should re-run only failed tests from the previous execution')
        string(name: 'emailRecipients', defaultValue: currentJobDefaults['emailRecipients'], description: 'list of emails to inform is this job fails (comma separated list)')
        string(name: 'cause', defaultValue: "", description: 'additional job trigger information to be added to the run description. Should be filled automatically')
    }

    environment {
        PATH = "/home/ubuntu/.nvm/versions/node/v12.16.3/bin:$PATH"
        testCategory = "${currentJobDefaults['testCategory']}"
        testsGroup = "${currentJobDefaults['testsGroup']}"
        triggeringUserId = "${currentBuild.getBuildCauses()[0].userId}"
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
                    def filesToClean = ["index.html", "dashboard.html", "exception.html", "tag.html"]
                    filesToClean.each { currentFile ->
                        mainLoader.deleteOldArtifacts(currentFile, currentFile)
                    }
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

                sendSlackUpdate("danger", "Failed")
            }

            mail bcc: '', body: "<b>Details:</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Jenkins job failed -> ${env.JOB_NAME} (build: #${env.BUILD_NUMBER} env: ${params.environment})", to: "${params.emailRecipients}"
        }

        success {
            script {
                sendSlackUpdate("good", "Stable")
            }
        }
    }
}