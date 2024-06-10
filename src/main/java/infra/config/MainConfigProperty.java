package infra.config;

public enum MainConfigProperty {
    browserType,
    browserVersion,
    browserDeviceNameToEmulate,
    browserLogNetworkErrors,

    environment,
    remoteRunCicdApi,
    internalUrlToRemoteRunCicdApi,
    testCategory,
    testNetwork,
    runHealthChecksEndpointsBeforeTests,
    runHealthChecksUrlsBeforeTests,
    skipTestsListedInGoogleSheet,
    hubUrl,
    moonBrowserContextFile,
    chromeUserAgent,

    browserStack_hubUrl,
    browserstack_browserName,
    browserstack_device,
    browserstack_realMobile,
    browserstack_os_version,
    browserstack_ios_platform_version,
    browserstack_ios_device_name,

    simulateWebCam,
    simulateWebCamVideoFile,
    livenessCheckEnabled,

    videoRecording,
    reportStatistics,   // should report test metadata to the dashboard
    maxLocalOldVideosToKeep,

    visualTestingEngine,
    imageComparisonMinimumSimilarity,
    imageComparisonMaximumPixelChangedPercent,

    quorumTestNet,

    skipTestsGoogleSheetsUrl,
    baseUrl,
    investUrl,
    landingPageUrl,
    baseDsApiUrl,
    baseIpsApiUrl,
    baseConnectGwApiUrl,
    baseAPIUrl,
    cpUrl,
    cpUrlApac,
    cpMarketInvestorUrl,
    secIdUrl,
    atsUrl,
    atsGwUrl,
    secIdMarketUrl,
    primaryOfferingMarketstUrl,
    accreditationUrl,
    secAcctOwnerUrl,
    idologyApiBaseUrl,
    vanityETHUrl,
    grafanaUrl,
    caInvestorCashAccountsV1Url,
    caInvestorDeleteCashAccount,
    etherScanUrlTemplate,
    videoBucketMoonPrefix,
    videoBucketJenkinsPrefix,
    iOSFilesBucketName,
    sumSubBaseUrl,
    webDriverImplicitWaitInSeconds,
    webDriverExplicitWaitInSeconds,
    handleHttpAuthentication,
    addCustomHttpHeader,
    chromeExtensionId,
    addHTTPHeaderToDomain,
    httpEventsHandler,
    securitizePublicSite,
    spiceVCLoginPage,
    arcaLabsLoginPage,

    downloadsPath,

    reportPortalBaseUri,
    reportPortalExternalLaunchUri,
    reportPortalProjectName,

    waitTimeWalletToBecomePendingSeconds,
    intervalTimeWalletToBecomePendingSeconds,

    waitTimeWalletToBecomeSyncingQuorumSeconds,
    intervalTimeWalletToBecomeSyncingQuorumSeconds,
    waitTimeWalletToBecomeSyncingEthereumSeconds,
    intervalTimeWalletToBecomeSyncingEthereumSeconds,

    waitTimeWalletToBecomeReadyQuorumSeconds,
    intervalTimeWalletToBecomeReadyQuorumSeconds,
    waitTimeWalletToBecomeReadyEthereumSeconds,
    intervalTimeWalletToBecomeReadyEthereumSeconds,

    waitTimeWalletToBecomeReadyAlgorandSeconds,
    intervalTimeWalletToBecomeReadyAlgorandSeconds,

    waitTimeTransactionToBecomeSuccessQuorumSeconds,
    intervalTimeTransactionToBecomeSuccessQuorumSeconds,
    waitTimeTransactionToBecomeSuccessEthereumSeconds,
    intervalTimeTransactionToBecomeSuccessEthereumSeconds,

    waitTimeTokensToArriveInWalletQuorumSeconds,
    intervalTimeTokensToArriveInWalletQuorumSeconds,
    waitTimeTokensToArriveInWalletEthereumSeconds,
    intervalTimeTokensToArriveInWalletEthereumSeconds,


    waitTimeUseIdReturnSeconds,
    intervalTimeUserIdReturnSeconds,
}

