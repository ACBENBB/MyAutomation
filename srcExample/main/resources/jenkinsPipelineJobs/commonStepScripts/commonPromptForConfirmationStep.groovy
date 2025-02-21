package jenkinsPipelineJobs.commonStepScripts

print("Loaded file commonPromptForConfirmationStep.groovy")

def promptForConfirmation() {
    env.isConfirmed = input message: 'Are you sure you want to delete entries from the DB?',
            parameters: [string(defaultValue: '',
                    ok: 'DELETE!',
                    description: 'Type "YES" (all capital) to confirm',
                    name: 'confirmation')]

    if (env.isConfirmed == "YES") {
        echo "Confirmed?: ${env.isConfirmed}"
    } else {
        echo "Not confirmed - aborted!"
        currentBuild.result = 'ABORTED'
        error('Not confirmed - aborted!')
    }
}

return this