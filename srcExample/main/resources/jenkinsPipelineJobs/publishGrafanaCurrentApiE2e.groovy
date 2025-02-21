package jenkinsPipelineJobs

def slackGroupNameToIds = [
        QA_internal_reports: "C049J38KK1V",
        QA_channel: "C04DN1KRE03"
]

pipeline {

    agent any

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production'], description: 'Environment on which to run')
        choice(name: 'publishTo', choices: slackGroupNameToIds.keySet() as ArrayList, description: 'Who should get this screenshot - which Slack channel to use')
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'Is this a dry run for debugging purposes only - no tests will actually be started')
    }


    stages {
        stage('Set job description') {
            steps {
                script {
                    currentBuild.description = "env: '${params.environment}'<br/>"
                }
            }
        }

        stage('Trigger the Grafana job') {
            steps {
                script {
                    if (isDryRun == "true") {
                        println "Dry running grafana job"
                    } else {
                        def grafanaUrl = "http://3.144.71.67:3000/d/VZHvdvKVz/e2e-api-status?orgId=1&var-Environment=${params.environment}&var-daysBackSearch=7"
                        final jobResult = build wait: true, job: 'Publish_Grafana_Dashboard_Screenshot', parameters: [string(name: 'branchName', value: "${params.branchName}"), string(name: 'environment', value: "${params.environment}"), string(name: 'publishTo', value: "${slackGroupNameToIds.get(params.publishTo)}"), string(name: 'grafanaUrl', value: "${grafanaUrl}"), string(name: 'initialComment', value: 'Current API/E2E status:')]
                        echo "Publish_Grafana_Dashboard_Screenshot job number: ${jobResult.number}"
                        String fullLink = "${env.JENKINS_URL}job/Publish_Grafana_Dashboard_Screenshot/${jobResult.number}/artifact/dashboard_screenshot.png"
                        currentBuild.description += "Link: <a href='${fullLink}' target='_blank'>dashboard screenshot</a>"
                    }
                }
            }
        }
    }
}