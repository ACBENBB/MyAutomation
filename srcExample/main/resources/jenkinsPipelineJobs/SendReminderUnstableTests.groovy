package jenkinsPipelineJobs

// defaultSlackChannel (automation-maintenance) will be used to send message if an error occur trying to run this script
def defaultSlackChannel = "C04C0CJMUG3"
def slackChannelPerDomain = ["CP": "C04B8UF08F7", "ISR": "C04B8UF08F7", "SID": "C04BEAZBGBW", "FT": "C04BBSZ407L", "ATS": "C04BBPVJUTV", "TA": "C04B8UGSV2R", "ST": "C04BBSVQBK4", "CA": "C04BBT0FQ82", "JP": "C05R2KMGY8M"]

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        extendedChoice(name: 'DOMAINS_TO_TEST', defaultValue: 'ATS,FT,ISR,JP,SID,ST,TA,CA', description: 'Name of domain to analyze in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_CHECKBOX',
                value: 'ATS,FT,ISR,JP,SID,ST,TA,CA',
                visibleItemCount: 10)
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
                    mainLoader.deleteOldArtifacts("unstable*.txt", "unstable*.txt")
                }
            }
        }

        stage('Build & Run script') {
            steps {
                script {
                    mainLoader.mavenCleanAndCompile("io.securitize.scripts.SendReminderUnstableTests")
                }
            }
        }
    }

    post {
        failure {
            script {
                slackSend channel: defaultSlackChannel, color: "danger", message: "$env.JOB_NAME #${env.BUILD_NUMBER} failed! Check the logs at: (<${env.BUILD_URL}|Open>)"
            }
        }

        success {
            script {
                slackChannelPerDomain.each { entry ->
                    println "working on $entry.key"

                    def fileName = "unstable_" + entry.key + ".txt"
                    if (fileExists(fileName)) {
                        println "Sending to $entry.value result of: $entry.key with this content:"
                        sh "cat ${fileName}"
                        slackUploadFile channel: entry.value, filePath: fileName, initialComment: "Daily reminder: Unstable tests status for ${entry.key}"
                        sh "mv ${fileName} ${fileName}_SENT"
                    } else {
                        println "Can't find file " + fileName + " - skipping sending report for that domain"
                    }
                }
            }
        }
    }
}