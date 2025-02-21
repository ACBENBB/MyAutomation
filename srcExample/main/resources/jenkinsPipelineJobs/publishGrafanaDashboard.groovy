package jenkinsPipelineJobs

def screenshotFileName = "dashboard_screenshot.png"

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production','apac'], description: 'Environment on which to run')
        string(name: 'publishTo', defaultValue: 'C03U3AJNCDR', description: 'Who should get this screenshot - which Slack channel to use')
        string(name: 'grafanaUrl', defaultValue: "", description: 'url to grafana dashboard')
        string(name: 'initialComment', defaultValue: 'MISSING INITIAL COMMENT PARAMETER!', description: 'Header of the message preceding the Grafana screenshot to be sent via slack')
    }

    environment {
        PATH = "/home/ubuntu/.nvm/versions/node/v12.16.3/bin:$PATH"
        testCategory = "JENKINS"
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
                    mainLoader.deleteOldArtifacts(screenshotFileName, "screenshot")
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
                    mainLoader.mavenCleanAndCompile("grabGrafanaDashboard.xml")
                }
            }
        }
    }

    post {
        always {
            script {
                if (fileExists(screenshotFileName)) {
                    String defaultChannel = "C03U3AJNCDR"
                    String channel = publishTo.toString()
                    if ((channel == null) || (channel.isEmpty())) {
                        channel = defaultChannel
                    }
                    println "sending to slack channel of: " + channel
                    slackUploadFile filePath: screenshotFileName, channel: channel, initialComment: "${params.initialComment}"

                    archiveArtifacts artifacts: screenshotFileName, followSymlinks: false
                } else {
                    echo "Screenshot not found - nothing to publish!"
                }
            }
            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, includes: '**/index.html', keepAll: true, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
        }
    }
}
