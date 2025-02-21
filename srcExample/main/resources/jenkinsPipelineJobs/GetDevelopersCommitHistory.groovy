package jenkinsPipelineJobs

String outputFileName = "commit_history.txt"

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools secion
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'developersBranch', choices: ['rc', 'dev', 'master'], description: 'branch on which to query commits')
        string(name: 'fromTimestamp', defaultValue: '0', description: 'Minimal timestamp of commits to include in the report (example: 1685517211000)')
        extendedChoice(name: 'allowedDomains', defaultValue: 'ATS,BC,CA,FR,INVT,ISR,JP,TA', description: 'Name of jobs domains to query',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,BC,CA,FR,INVT,ISR,JP,TA',
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

        stage('Delete old artifacts') {
            steps {
                script {
                    mainLoader.deleteOldArtifacts("${outputFileName}", "commit history file")
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
                    mainLoader.mavenCleanAndCompile("io.securitize.scripts.GetDevelopersCommitHistory -Dexec.cleanupDaemonThreads=false")
                }
            }
        }
    }

    post {
        success {
            script {
                String prefix = "Developers commit history report (<${env.BUILD_URL}|Open job>)\n"
                def content = readFile("${outputFileName}")
                if (content.trim().size() == 0) {
                    content = "no commits found"
                }
                // update links so in Slack they appear in short form
                content = content.replaceAll("https", "<https")
                content = content.replaceAll(" \n", "|open>\n")
                def attachments = [
                        [
                                text    : "${prefix}${content}",
                                fallback: 'Fallback'
                        ]
                ]
                slackSend(channel: "C049J38KK1V", attachments: attachments)
            }
        }
    }
}