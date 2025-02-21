package jenkinsPipelineJobs

String stagingFolder = "./Staging"
String outputFolder = "browserContext"

String chromeExtensionsPrefix = "src/main/resources/chrome-extensions/"
def chromeExtensions = [
        ["name": "chromeExtension", path: "chrome-extension/chrome-extension.crx"],
        ["name": "busterExtension", path: "buster/buster.crx"],
        ["name": "coinBaseExtension", path: "coinBase/coinBase.crx"],
        ["name": "metaMaskExtension", path: "metaMask/metaMask.crx"]
]
def otherFiles = [
        "src/main/resources/images/livenessCheck.mjpeg"
]

pipeline {
    agent any

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
    }

    stages {
        stage('Checkout code - requested branch') {
            steps {
                checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${params.branchName}"]],
                        userRemoteConfigs: [[credentialsId: 'dd990eb6-b802-4f90-a182-fe404ab754a1', url: 'https://bitbucket.org/securitize_dev/securitizewebautomation.git']]
                ])
            }
        }

        stage('Clean work folder') {
            steps {
                sh "rm -rf ${stagingFolder}"
                sh "mkdir ${stagingFolder}"
                sh "rm -rf ${outputFolder}"
                sh "mkdir ${outputFolder}"
            }
        }

        stage('Copy and extract extensions') {
            steps {
                script {
                    chromeExtensions.each { currentExtension ->
                        String currentExtensionName = currentExtension["name"]
                        String currentExtensionPath = currentExtension["path"]
                        String path = stagingFolder + "/" + currentExtensionName
                        sh "cd ${stagingFolder} && unzip ../${chromeExtensionsPrefix}${currentExtensionPath} -d ${currentExtensionName} || echo done!"
                    }
                }
            }
        }

        stage('Copy other files') {
            steps {
                script {
                    otherFiles.each { currentFile ->
                        sh "cp ${currentFile} ${stagingFolder}/"
                    }
                }
            }
        }

        stage('Zip and Upload to S3') {
            steps {
                script {
                    sh "tar cvzf ${outputFolder}/browser-data.tar.gz -C ${stagingFolder} ."
                    s3Upload consoleLogLevel: 'INFO', dontSetBuildResultOnFailure: false, dontWaitForConcurrentBuildCompletion: false, entries: [[bucket: "securitize-dev-moon/${outputFolder}", excludedFile: '', flatten: false, gzipFiles: false, keepForever: false, managedArtifacts: false, noUploadOnFailure: false, selectedRegion: 'us-east-2', showDirectlyInBrowser: false, sourceFile: "${outputFolder}/*", storageClass: 'STANDARD', uploadFromSlave: false, useServerSideEncryption: false]], pluginFailureResultConstraint: 'FAILURE', profileName: 'automation-jenkins-backup', userMetadata: []
                }
            }
        }
    }
}
