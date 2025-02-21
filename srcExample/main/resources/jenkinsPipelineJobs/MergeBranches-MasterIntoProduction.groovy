package jenkinsPipelineJobs

pipeline {
    agent any

    stages {
        stage('Checkout code - master branch') {
            steps {
                checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/master']],
                        userRemoteConfigs: [[credentialsId: 'dd990eb6-b802-4f90-a182-fe404ab754a1', url: 'https://bitbucket.org/securitize_dev/securitizewebautomation.git']]
                ])
            }
        }

        stage('Checkout code - production branch') {
            steps {
                // these are important: they cache git credentials so we can push merged content later on
                sh 'git config --global credential.helper cache'
                sh 'git config --global push.default simple'

                checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/production']],
                        userRemoteConfigs: [[credentialsId: 'dd990eb6-b802-4f90-a182-fe404ab754a1', url: 'https://bitbucket.org/securitize_dev/securitizewebautomation.git']]
                ])
            }
        }

        stage('Merge code') {
            steps {
                script {
                    sh 'git fetch'
                    sh 'git checkout production'
                    sh 'git merge origin/master'
                }
            }
        }

        stage('Push code') {
            steps {
                script {
                    sh 'git push origin production'
                }
            }
        }
    }

    post {
        always {
            mail bcc: '', body: "<b>Details:</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Jenkins merge MASTER into PRODUCTION branch -> build: #${env.BUILD_NUMBER} - Status: ${currentBuild.currentResult}", to: "daniel@securitize.io,yoav.l@securitize.io"
        }
    }
}
