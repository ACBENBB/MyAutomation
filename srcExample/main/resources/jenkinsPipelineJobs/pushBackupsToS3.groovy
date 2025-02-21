package jenkinsPipelineJobs

pipeline {
    agent any

    stages {
        stage('Validate the backups folder exists') {
            steps {
                script {
                    echo("Making sure the backups folder exists, if not - creating it now!")
                    sh "mkdir -p backups"
                }
            }
        }

        stage('Validate updated backup files do exist') {
            steps {
                script {
                    backupsFound = sh(script: "cd backups && ls -l \$(date '+backup_%Y_%m_%d')*.zip 2> /dev/null | awk '{if (\$5 != 0) print \$9}' | wc -l",
                            returnStdout: true).trim()
                    if (backupsFound == "0") {
                        echo("Error! No backups found!")
                        error("Error! No backups found!")
                    } else {
                        echo("Backups found! Will copy them to S3!")
                    }
                }
            }
        }

        stage('Backup') {
            steps {
                s3Upload consoleLogLevel: 'INFO', dontSetBuildResultOnFailure: false, dontWaitForConcurrentBuildCompletion: false, entries: [[bucket: 'jenkins-configuration-backup-securitize', excludedFile: '', flatten: false, gzipFiles: false, keepForever: false, managedArtifacts: false, noUploadOnFailure: false, selectedRegion: 'us-east-2', showDirectlyInBrowser: false, sourceFile: 'backups/*', storageClass: 'STANDARD', uploadFromSlave: false, useServerSideEncryption: false]], pluginFailureResultConstraint: 'FAILURE', profileName: 'automation-jenkins-backup', userMetadata: []
            }
        }
    }

    post {
        failure {
            slackSend channel: "C04C0CJMUG3", color: "danger", message: "Jenkins backup to S3 job failed!!\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
        }
        success {
            slackSend channel: "C04C0CJMUG3", color: "good", message: "Jenkins backup to S3 job success! :party_parrot:\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
        }
    }
}
