package jenkinsPipelineJobs

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox'], description: 'Environment on which to run')
        choice(name: 'browserType', choices: ['chrome-remote'], description: 'Browser to use')
        choice(name: 'browserDeviceNameToEmulate', choices: ['desktop', 'iPhone 12 Pro', 'iPhone SE', 'Galaxy S20 Ultra', 'iPad Air', 'Pixel 5'], description: 'Browser resolution to use - mimicking known devices')
        choice(name: 'browserLocation', choices: ['Moon', 'JenkinsLocal'], description: 'Where will the browsers be opened - locally on Jenkins or remotely on Moon')
        string(name: 'issuerId', defaultValue: '', description: 'Issuer Id used to redirect test to Issuer of your choosing')
        string(name: 'tokenId', defaultValue: '', description: 'Token Id used to redirect test to Issuer of your choosing')
        string(name: 'tokenTicker', defaultValue: '', description: 'A ticker is a symbol representing a token or cryptocurrency. For example: Bitcoin: BTC. Ethereum: ETH')
        string(name: 'tokenDecimal', defaultValue: '0', description: 'Amount of decimals you want your token to have')
        choice(name: 'tokenNetwork', choices: ['AVALANCHE - Testnet', 'POLYGON - Testnet', 'SEPOLIA ETH - Testnet', 'GOERLI ETH - Testnet', 'GANACHE - Private Testnet'], description: 'Available networks on which to deploy your token')
        string(name: 'custodianAddress', defaultValue: '', description: 'Web3 Flow Configuration')
        choice(name: 'complianceType', choices: ['whitelisted', 'partitioned', 'regulated'], description: 'Compliance rules on which to deploy your token. Whitelisted only has authorized tokens field. Regulated and partitioned manage more complex rules')
        string(name: 'masterAddress', defaultValue: '', description: 'Address will be given master role for the token.')
        string(name: 'tbeAddress', defaultValue: '', description: 'Address will be given TBE role for the token.')
        string(name: 'redemptionAddress', defaultValue: '', description: 'Address will be given redemption role for the token.')
        string(name: 'issuerAddress', defaultValue: '', description: 'Address will be given issuer role for the token.')
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'Should avoid running the job and just provide details')
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
                    if (params.isDryRun == false) {
                        mainLoader.mavenCleanAndCompile("e2e/ST/AUT499_ST_ManageTokens_DeployTokens.xml")
                    } else {
                        echo "Dry running this job"
                    }
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
            }
        }
    }

}