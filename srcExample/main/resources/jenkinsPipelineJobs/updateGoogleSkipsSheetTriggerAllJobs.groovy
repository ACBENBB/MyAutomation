package jenkinsPipelineJobs

def jobsNames = [
        'GoogleSkipsSheet_Add_API',
        'GoogleSkipsSheet_Add_E2E',
        'GoogleSkipsSheet_Remove_API',
        'GoogleSkipsSheet_Remove_E2E'
]

pipeline {

    agent any

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: ['rc', 'sandbox', 'production'], description: 'Environment on which to run')
        choice(name: 'testTypesToRun', choices: ['all', 'E2E', 'API'], description: 'Which jobs to trigger - all jobs, E2E jobs or API jobs')
        extendedChoice(name: 'DOMAINS_TO_ANALYZE', defaultValue: 'ATS,CA,FT,ISR,JP,SID,ST,TA', description: 'Name of domain to analyze in this execution',
                multiSelectDelimiter: ',',
                quoteValue: false,
                saveJSONParameterToFile: false,
                type: 'PT_MULTI_SELECT',
                value: 'ATS,CA,FT,ISR,JP,SID,ST,TA',
                visibleItemCount: 10)
        booleanParam(name: 'isDryRun', defaultValue: false, description: 'Should avoid running the job and just log which jobs will be triggered')
    }

    stages {
        stage('Load') {
            steps {
                script {
                    mainLoader = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/stepsMainLoad.groovy"
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

        stage('Trigger the jobs') {
            steps {
                script {
                    mainLoader.triggerJobs(jobsNames)
                }
            }
        }
    }
}
