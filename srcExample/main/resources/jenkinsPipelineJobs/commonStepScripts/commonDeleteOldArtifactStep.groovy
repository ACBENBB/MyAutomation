package jenkinsPipelineJobs.commonStepScripts

print("Loaded file commonDeleteOldArtifactStep.groovy")

def deleteOldArtifacts(String artifactType, String artifactDescription) {
    script {
        if (fileExists("${artifactType}") || ("${artifactType}" == "unstable*.txt")) {
            echo "old ${artifactDescription} exists, removing it"
            sh "rm -f ${artifactType}"
        } else {
            echo "old ${artifactDescription} doesn't exist, nothing to do here"
        }
    }
}

return this
