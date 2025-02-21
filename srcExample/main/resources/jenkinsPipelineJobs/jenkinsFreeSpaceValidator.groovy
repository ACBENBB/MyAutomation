package jenkinsPipelineJobs

pipeline {
    agent any

    stages {
        stage('Validate enough free disk space') {
            steps {
                script {
                    sh 'xa=\$(df / --output=avail | tail -n1); if [ "\$xa" -gt "15000000" ]; then echo "ok, enough free space: \$xa"; else echo "Problem! Not enough free space: \$xa" && exit 1; fi'
                }
            }
        }
    }

    post {
        failure {
            slackSend channel: "C04C0CJMUG3", color: "danger", message: "Problem! Not enough free space in Jenkins EC2 machine!\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
        }
        success {
            slackSend channel: "C04C0CJMUG3", color: "good", message: "Good! Jenkins EC2 machine has enough free space! :party_parrot:\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
        }
    }
}
