package jenkinsPipelineJobs.commonStepScripts

import groovy.transform.Field

@Field private commonCheckoutCodeStepFile
@Field private commonDeleteOldArtifactStepFile
@Field private commonMavenCleanAndCompileStepFile
@Field private commonPromptForConfirmationStepFile
@Field private commonSetJobDescriptionStepFile
@Field private commonTriggerJobsStepFile

print("Loaded file stepsMainLoad.groovy - start")
commonCheckoutCodeStepFile = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/commonCheckoutCodeStep.groovy"
commonDeleteOldArtifactStepFile = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/commonDeleteOldArtifactStep.groovy"
commonMavenCleanAndCompileStepFile = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/commonMavenCleanAndCompileStep.groovy"
commonPromptForConfirmationStepFile = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/commonPromptForConfirmationStep.groovy"
commonSetJobDescriptionStepFile = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/commonSetJobDescriptionStep.groovy"
commonTriggerJobsStepFile = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/commonTriggerJobsStep.groovy"
print("Loaded file stepsMainLoad.groovy - end")


def checkoutCode() {
    echo "Inside method checkoutCode() inside file stepsMainLoad.groovy"

    commonCheckoutCodeStepFile.checkoutCode()
}

def deleteOldArtifacts(String artifactType, String artifactDescription) {
    echo "Inside method deleteOldArtifacts(String artifactType, String artifactDescription) inside file stepsMainLoad.groovy"

    commonDeleteOldArtifactStepFile.deleteOldArtifacts(artifactType, artifactDescription)
}

def mavenCleanAndCompile(String runFile = "") {
    echo "Inside method mavenCleanAndCompile(String runFile = '') inside file stepsMainLoad.groovy"

    commonMavenCleanAndCompileStepFile.mavenCleanAndCompile(runFile)
}

def promptForConfirmation() {
    echo "Inside method promptForConfirmation() inside file stepsMainLoad.groovy"

    commonPromptForConfirmationStepFile.promptForConfirmation()
}

def setJobDescription(String descriptionArg = "") {
    echo "Inside method setJobDescription(String descriptionArg = '') inside file stepsMainLoad.groovy"

    commonSetJobDescriptionStepFile.setJobDescription(descriptionArg)
}

def triggerJobs(ArrayList<String> jobsNames, boolean isEthPriceOk = false) {
    echo "Inside method triggerJobs(ArrayList<String> jobsNames, boolean isEthPriceOk = false) inside file stepsMainLoad.groovy"

    commonTriggerJobsStepFile.triggerJobs(jobsNames, isEthPriceOk)
}

return this