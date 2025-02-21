package jenkinsPipelineJobs.commonStepScripts

print("Loaded file commonCheckoutCodeStep.groovy")

def checkoutCode() {
    checkout([
            $class  : 'GitSCM',
            branches: [[name: "*/${branchName}"]]
    ])
}

return this