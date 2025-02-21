package jenkinsPipelineJobs

String resultFileName = "result.txt"

pipeline {
    agent any

    parameters {
        string(name: 'searchString', defaultValue: 'POLYGON_MUMBAI', description: 'String to search in executions log examples: ETHEREUM_SEPOLIA | POLYGON_MUMBAI | AVALANCHE')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production', 'apac'], description: 'Environment on which to run')
        string(name: 'resultsCount', defaultValue: '10', description: 'How many results to return (x latest results containing provided search string)')
    }

    stages {
        stage('Set job description') {
            steps {
                script {
                    if (params.environment != null) {
                        currentBuild.description = "Env: '${environment}'<br/>"
                    }
                }
            }
        }

        stage('Search') {
            steps {
                script {
                    /* The command consists of several parts:
                    * grep -v -r ${env.JOB_NAME} /var/log/jenkins/audit - search all logs for rows not mentioning current job's name
                    * grep '${searchString}' - return only rows mentioning search term
                    * tail -${resultsCount} - return only desired number of results
                    * sed 'G' - place an empty line between each result row (for readability)
                    */
                    sh "grep -v -r ${env.JOB_NAME} /var/log/jenkins/audit | grep 'environment: {${environment}}' | grep -i '${searchString}' | tail -${resultsCount} | sed 'G' > ${resultFileName}"
                    echo "Here are the results:"
                    sh "cat ${resultFileName}"
                }
            }
        }
    }

    post {
        success {
            script {
                archiveArtifacts allowEmptyArchive: true, artifacts: resultFileName, followSymlinks: false
            }
        }
    }
}
