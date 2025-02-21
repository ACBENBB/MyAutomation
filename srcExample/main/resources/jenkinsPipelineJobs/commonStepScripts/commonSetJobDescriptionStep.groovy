package jenkinsPipelineJobs.commonStepScripts

print("Loaded file commonSetJobDescriptionStep.groovy")

def setJobDescription(String descriptionArg = "") {
    script {
        //common code for all jobs
        if ("${branchName}" == "master") {
            echo 'Working on master branch, no need to set custom build description'
            currentBuild.description = ""
        } else {
            echo "Working on custom branch of '${branchName}'. Setting custom build description"
            currentBuild.description = "Branch: '${params.branchName}'<br/>"
        }

        if (params.environment != null)
            currentBuild.description += "Env: '${environment}'<br/>"

        if ((params.testNetwork != null) && (params.testNetwork != "QUORUM")) {
            currentBuild.description += "TestNetwork: '${testNetwork}'<br/>"
        }

        if ((params.browserType != null) && (params.browserType == "ios-browserstack")) {
            currentBuild.description += "BrowserType: '${browserType}'<br/>"
        }

        if (params.browserLocation != null) {
            if (params.browserLocation == "JenkinsLocal") {
                echo "setting browser location to local: '${params.browserLocation}'"
                env.hubUrl = "http://localhost:4444/wd/hub"
                currentBuild.description += "BrowserLocation: '${params.browserLocation}'<br/>"
            } else
                echo "making no change to the browser location: '${params.browserLocation}'"
        }

        if (params.DOMAINS_TO_TEST != null) {
            if (params.DOMAINS_TO_TEST == "ATS,CA,FT,ISR,JP,SID,ST,TA") {
                currentBuild.description += "Domains: all<br/>"
            } else {
                currentBuild.description += "Domains: '${params.DOMAINS_TO_TEST}'<br/>"
            }
        }

        if (params.DOMAINS_TO_ANALYZE != null) {
            if (params.DOMAINS_TO_ANALYZE == "ATS,CA,FT,ISR,JP,SID,ST,TA") {
                currentBuild.description += "Domains: all<br/>"
            } else {
                currentBuild.description += "Domains: '${params.DOMAINS_TO_ANALYZE}'<br/>"
            }
        }

        if (params.GROUPS_TO_TEST != null && !params.GROUPS_TO_TEST.contains("none")) {
            currentBuild.description += "Groups: '${params.GROUPS_TO_TEST}'<br/>"
        }

        if (params.runFailedTests != null && params.runFailedTests == true)
            currentBuild.description += "RunFailedTests: true<br/>"

        if (params.developersBranch != null)
            currentBuild.description += "DevelopersBranch: '${params.developersBranch}'<br/>"

        if (params.testTypesToRun != null)
            currentBuild.description += "TestTypesToRun: '${params.testTypesToRun}'<br/>"


        // code for a specific job
        if ("${JOB_NAME}" == 'AUT479_Production_Regression')
            currentBuild.description += "TestType: '${params.testType}'"

        if ("${JOB_NAME}" == 'Stabilization')
            currentBuild.description += "TestCategory: '${params.testCategory}'"

        if ("${JOB_NAME}" == 'Run_job_depending_on_ETH_price' && descriptionArg != "")
            currentBuild.description += "GasPrice: '${descriptionArg}'"

        if ("${JOB_NAME}" == 'AUT639_blackrock_daily_deposit_10_investors')
            currentBuild.description += "InvestorNumber: '${params.investorNumber}'"

        if ("${JOB_NAME}" == 'AUT442_SidCpKYCUpdate') {
            def cause = currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')
            currentBuild.description += " TriggeredBy: '${cause.userName?.find()}'"
            currentBuild.description += " Set_KYC_Status: '${params.KYC_STATUS_TO_SET}'"
            currentBuild.description += "<br/>ProfileId: '${params.PROFILE_ID}'"
        }

        if ("${JOB_NAME}".contains('_SanitySuite_')) {
            if (params.cause != "")
                currentBuild.description += "Trigger details: '${params.cause}'"
        }

        if ("${JOB_NAME}".contains('GoogleSkipsSheet_Add_') || "${JOB_NAME}".contains('GoogleSkipsSheet_Remove_')) {
            // &nbsp; means the space character in HTML - to make output more readable
            currentBuild.description -= "<br/>"
            currentBuild.description += "&nbsp;&nbsp;&nbsp;&nbsp;mode: '${env.AddOrRemoveMode}'"
        }
    }
}

return this