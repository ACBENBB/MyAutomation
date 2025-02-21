package jenkinsPipelineJobs

def allJobsDefaults1 = [
        //stand alone test jobs AUT337-AUT627
        "AUT337_SID_CP_Regression_Overview": [suiteFileName: "e2e/SID/AUT337_SID_CP_Regression_Overview.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT342_CA_TotalTests_API": [suiteFileName: "api/AUT342_CA_TotalTests_API.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT354_FT_Public_Securitize_Website_PrimaryMarket_Overview": [suiteFileName: "e2e/FT/AUT354_FT_Public_Securitize_Website_PrimaryMarket_Overview.xml", testCategory: "JENKINS",environment: ['production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT355_ATS_Public_Securitize_Website_SecondaryMarket_Overview": [suiteFileName: "e2e/ATS/AUT355_ATS_Public_Securitize_Website_SecondaryMarket_Overview.xml", testCategory: "JENKINS",environment: ['production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT365_TA_Distribution_Dividend_Complete": [suiteFileName: "e2e/TA/AUT365_TA_Distribution_Dividend_Complete.xml", testCategory: "JENKINS",environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT368_ISR_Import_Multiple_Investors": [suiteFileName: "e2e/ISR/AUT368_ISR_Import_Multiple_Investors.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT369_ATS_Trade_Partial_Match": [suiteFileName: "e2e/ATS/AUT369_ATS_Trade_Partial_Match.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT388_ISR_Fundraise_Screen": [suiteFileName: "e2e/ISR/AUT388_ISR_Fundraise_Screen.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT392_TA_Accreditation_Individual_ThirdParty": [suiteFileName: "e2e/TA/AUT392_TA_Accreditation_Individual_ThirdParty.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT393_TA_Accreditation_Entity_ThirdParty": [suiteFileName: "e2e/TA/AUT393_TA_Accreditation_Entity_ThirdParty.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT404_ST_InvestorPays_HSM": [suiteFileName: "e2e/ST/AUT404_ST_InvestorPays_HSM.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, joaquin.ibanez@securitize.io, lautaro.rial@securitize.io"],
        "AUT408_TA_TaxForm_Autocomplete_Entity_NON_US": [suiteFileName: "e2e/TA/AUT408_TA_TaxForm_Autocomplete_Entity_NON_US.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT409_ISR_Holders_Screen": [suiteFileName: "e2e/ISR/AUT409_ISR_Holders_Screen.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT413_ISR_Internal_TBE_Transfer": [suiteFileName: "e2e/ISR/AUT413_ISR_Internal_TBE_Transfer.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT420_ISR_Investor_Registration_Sanity": [suiteFileName: "e2e/ISR/AUT420_ISR_Investor_Registration_Sanity.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox','production', 'apac'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT428_CA_CashAccount_Btn_Add_Funds": [suiteFileName: "e2e/CA/AUT428_CA_CashAccount_Btn_Add_Funds.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT429_ST_SecDid": [suiteFileName: "e2e/ST/AUT429_ST_SecDid.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, joaquin.ibanez@securitize.io, lautaro.rial@securitize.io"],
        "AUT434_FT_Nie_Opportunity_Investment_Sanity": [suiteFileName: "e2e/FT/AUT434_FT_Nie_Opportunity_Investment_Sanity.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT443_SID_SecuritizeID_Login_Sanity": [suiteFileName: "e2e/SID/AUT443_SID_SecuritizeID_Login_Sanity.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT444_SID_Individual_Flow_Sanity": [suiteFileName: "e2e/SID/AUT444_SID_Individual_Flow_Sanity.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT447_ST_Investor_Registration_Wallet_Registration_Sanity": [suiteFileName: "e2e/ST/AUT447_ST_Investor_Registration_Wallet_Registration_Sanity.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "daniel@securitize.io, lautaro.rial@securitize.io, joaquin.ibanez@securitize.io"],
        "AUT462_TA_Accreditation_Sanity_Prod": [suiteFileName: "e2e/TA/AUT462_TA_Accreditation_Sanity_Prod.xml", testCategory: "JENKINS", environment: ['production'], emailRecipients: "daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT466_ATS_Sanity_Prod": [suiteFileName: "e2e/ATS/AUT466_ATS_Sanity_Prod.xml", testCategory: "JENKINS", environment: ['production'], emailRecipients: "daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT472_ISR_Login_And_Audit": [suiteFileName: "e2e/ISR/AUT472_ISR_Login_And_Audit.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT473_ST_InvestorPays_Reallocate": [suiteFileName: "e2e/ST/AUT473_ST_InvestorPays_Reallocate.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, joaquin.ibanez@securitize.io, lautaro.rial@securitize.io"],
        "AUT474_ST_Web3Swap_HolderTokenTransaction": [suiteFileName: "e2e/ST/AUT474_ST_Web3Swap_HolderTokenTransaction.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, joaquin.ibanez@securitize.io"],
        "AUT475_TA_Accreditation_Individual_Income_UpdatesRequired": [suiteFileName: "e2e/TA/AUT475_TA_Accreditation_Individual_Income_UpdatesRequired.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT476_TA_Distribution_Redemption_Complete": [suiteFileName: "e2e/TA/AUT476_TA_Distribution_Redemption_Complete.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT477_CA_Holdings_Btn_Create_Account": [suiteFileName: "e2e/CA/AUT477_CA_Holdings_Btn_Create_Account.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT478_CA_Holdings_Btn_Add_Funds": [suiteFileName: "e2e/CA/AUT478_CA_Holdings_Btn_Add_Funds.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT488_CA_WireTransfer_Withdraw_US": [suiteFileName: "e2e/CA/AUT488_CA_WireTransfer_Withdraw_US.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT490_TA_Accreditation_Entity_OwnersOfEquity_Rejected": [suiteFileName: "e2e/TA/AUT490_TA_Accreditation_Entity_OwnersOfEquity_Rejected.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT491_TA_Distributions_W8BENE_Accept": [suiteFileName: "e2e/TA/AUT491_TA_Distributions_W8BENE_Accept.xml", testCategory: "JENKINS", environment: ['rc'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT492_TA_Distributions_W8BENE_Reject": [suiteFileName: "e2e/TA/AUT492_TA_Distributions_W8BENE_Reject.xml", testCategory: "JENKINS", environment: ['rc'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT497_ATS_Cancel_Buy_Order": [suiteFileName: "e2e/ATS/AUT497_ATS_Cancel_Buy_Order.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT499_ST_ManageTokens_DeployTokens": [suiteFileName: "e2e/ST/AUT499_ST_ManageTokens_DeployTokens.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, joaquin.ibanez@securitize.io, lautaro.rial@securitize.io"],
        "AUT501_SID_Redirect_SecuritizeID_KYC_Stepper_To_NIE_Dashboard": [suiteFileName: "e2e/SID/AUT501_SID_Redirect_SecuritizeID_KYC_Stepper_To_NIE_Dashboard.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT502_ATS_Cancel_Sell_Order": [suiteFileName: "e2e/ATS/AUT502_ATS_Cancel_Sell_Order.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT503_TA_Accreditation_Individual_NetWorth_UpdatesRequired": [suiteFileName: "e2e/TA/AUT503_TA_Accreditation_Individual_NetWorth_UpdatesRequired.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT504_SID_Redirect_Nie_To_SecuritizeID_And_Back": [suiteFileName: "e2e/SID/AUT504_SID_Redirect_Nie_To_SecuritizeID_And_Back.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT505_SID_Redirect_Back_To_PrimaryMarket_From_KYC_Return_To_Button": [suiteFileName: "e2e/SID/AUT505_SID_Redirect_Back_To_PrimaryMarket_From_KYC_Return_To_Button.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT506_SID_Redirect_Back_To_ATS_From_KYC_Return_To_Button": [suiteFileName: "e2e/SID/AUT506_SID_Redirect_Back_To_ATS_From_KYC_Return_To_Button.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT507_ISR_Privacy_Control": [suiteFileName: "e2e/AUT507_ISR_Privacy_Control.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,ben.berko@securitize.io"],
        "AUT509_ATS_Reject_Buy_Order": [suiteFileName: "e2e/ATS/AUT509_ATS_Reject_Buy_Order.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT511_CA_Deposit_US_Account_ACH": [suiteFileName: "e2e/CA/AUT511_CA_Deposit_US_Account_ACH.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT512_CA_Deposit_EU_Account_International": [suiteFileName: "e2e/CA/AUT512_CA_Deposit_EU_Account_International.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT513_ATS_Reject_Sell_Order": [suiteFileName: "e2e/ATS/AUT513_ATS_Reject_Buy_Order.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT516_FT_Invest_WireTransfer": [suiteFileName: "e2e/FT/AUT516_FT_Invest_WireTransfer.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT517_CA_Withdrawal_US_Account_InternationalWire": [suiteFileName: "e2e/CA/AUT517_CA_Withdrawal_US_Account_InternationalWire.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT518_TA_Accreditation_Entity_AssetsAndInvestments_UpdatesRequired": [suiteFileName: "e2e/TA/AUT518_TA_Accreditation_Entity_AssetsAndInvestments_UpdatesRequired.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT520_ATS_Order_Cancel_And_Reject_Status": [suiteFileName: "e2e/ATS/AUT520_ATS_Order_Cancel_And_Reject_Status.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT523_ISR_Decimal_Flow": [suiteFileName: "e2e/ISR/AUT523_ISR_Decimal_Flow.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,ben.berko@securitize.io"],
        "AUT525_CA_ATSCreateOrder_Btn_Add_Funds": [suiteFileName: "e2e/CA/AUT525_CA_ATSCreateOrder_Btn_Add_Funds.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT524_FT_Invest_CashAccount": [suiteFileName: "e2e/FT/AUT524_FT_Invest_CashAccount.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT526_CA_Site_Navigation_GoBack_Arrow": [suiteFileName: "e2e/CA/AUT526_CA_Site_Navigation_GoBack_Arrow.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT528_FT_Redirect_Back_To_Primary_Market_Opportunity_From_Securitize_Id_Registration": [suiteFileName: "e2e/FT/AUT528_FT_Redirect_Back_To_Primary_Market_Opportunity_From_Securitize_Id_Registration.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT532_ISR_Evergreen_Investments": [suiteFileName: "e2e/ISR/AUT532_ISR_Evergreen_Investments.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT535_ISR_Lock_Tokens_TBE": [suiteFileName: "e2e/ISR/AUT535_ISR_Lock_Tokens_TBE.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,ben.berko@securitize.io"],
        "AUT536_ISR_Unlock_Tokens_TBE": [suiteFileName: "e2e/ISR/AUT536_ISR_Unlock_Tokens_TBE.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,ben.berko@securitize.io"],
        "AUT537_CA_Withdrawal_US_Account_ACH": [suiteFileName: "e2e/CA/AUT537_CA_Withdrawal_US_Account_ACH.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT538_ISR_MarketsOverview": [suiteFileName: "e2e/ISR/AUT538_ISR_MarketsOverview.xml", testCategory: "JENKINS", environment: ['rc'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT539_FT_NieInvest_CashAccount_AccreditationFirst": [suiteFileName: "e2e/FT/AUT539_FT_NieInvest_CashAccount_AccreditationFirst.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT540_ISR_Login_Auth_test": [suiteFileName: "e2e/ISR/AUT540_ISR_Login_Auth_test.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT541_CA_Withdrawal_US_Account_USDC": [suiteFileName: "e2e/CA/AUT541_CA_Withdrawal_US_Account_USDC.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT542_ISR_Update_Control_Book": [suiteFileName: "e2e/ISR/AUT542_ISR_Update_Control_Book.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT544_ATS_TokenTradeOnlyAvailableForAccreditedInvestors": [suiteFileName: "e2e/ATS/AUT544_ATS_TokenTradeOnlyAvailableForAccreditedInvestors.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT545_ATS_HoldingIcon": [suiteFileName: "e2e/ATS/AUT545_ATS_HoldingIcon.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT546_ATS_AddFundsModal": [suiteFileName: "e2e/ATS/AUT546_ATS_Add_funds_modal.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT547_ISR_Add_Edit_Delete_Rounds": [suiteFileName: "e2e/ISR/AUT547_ISR_Add_Edit_Delete_Rounds.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT548_FT_NieInvestment_Docusign_WireTransfer": [suiteFileName: "e2e/FT/AUT548_FT_NieInvestment_Docusign_WireTransfer.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT549_ATS_AddAssetToFavorites": [suiteFileName: "e2e/ATS/AUT549_ATS_AddAssetToFavorites.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT552_CA_LinkUnlink_US_Account_ACH": [suiteFileName: "e2e/CA/AUT552_CA_LinkUnlink_US_Account_ACH.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT553_ATS_AssetsAreDisplayedInTheCorrectMarket": [suiteFileName: "e2e/ATS/AUT553_ATS_AssetsAreDisplayedInTheCorrectMarket.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT555_FT_Individual_Sanity_Flow": [suiteFileName: "e2e/FT/AUT555_FT_Individual_Sanity_Flow.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT556_ATS_OrdersAreDisplayedInTheOrderBook": [suiteFileName: "e2e/ATS/AUT556_ATS_OrdersAreDisplayedInTheOrderBook.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT562_TA_TransactionHistory_IssuanceDisplayed": [suiteFileName: "e2e/TA/AUT562_TA_TransactionHistory_IssuanceDisplayed.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT563_ISR_Affiliate_Management": [suiteFileName: "e2e/ISR/AUT563_ISR_Affiliate_Management.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT568_JP_ControlPanelPlus_Login": [suiteFileName: "e2e/JP/AUT568_JP_ControlPanelPlus_Login.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT571_JP_CpPlus_Bank_Deposit_File": [suiteFileName: "e2e/JP/AUT571_JP_CpPlus_Bank_Deposit_File.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT573_JP_CpPlus_Corporate_Bond_Ledger": [suiteFileName: "e2e/JP/AUT573_JP_CpPlus_Corporate_Bond_Ledger.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT592_JP_ControlPanel_Login": [suiteFileName: "e2e/JP/AUT592_JP_ControlPanel_Login.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT594_JP_ControlPanel_Fundraise": [suiteFileName: "e2e/JP/AUT594_JP_ControlPanel_Fundraise.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT595_JP_ControlPanel_Holders": [suiteFileName: "e2e/JP/AUT595_JP_ControlPanel_Holders.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT596_JP_ControlPanel_Onboarding": [suiteFileName: "e2e/JP/AUT596_JP_ControlPanel_Onboarding.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT600_JP_Marui_Investor_Login_Sanity": [suiteFileName: "e2e/JP/AUT600_JP_Marui_Investor_Login_Sanity.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT601_JP_Marui_Application_PreEntry": [suiteFileName: "e2e/JP/AUT601_JP_Marui_Application_PreEntry.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT602_JP_Marui_InvestorRegistration": [suiteFileName: "e2e/JP/AUT602_JP_Marui_Investor_Registration.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT606_JP_Investor_Documents": [suiteFileName: "e2e/JP/AUT606_JP_Investor_Documents.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT608_ISR_Lock_Tokens_Wallet": [suiteFileName: "e2e/ISR/AUT608_ISR_Lock_Tokens_Wallet.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,ben.berko@securitize.io"],
        "AUT609_ISR_Unlock_Tokens_Wallet": [suiteFileName: "e2e/ISR/AUT609_ISR_Unlock_Tokens_Wallet.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,ben.berko@securitize.io"],
        "AUT611_ST_Pier_FullMatch": [suiteFileName: "e2e/ST/AUT611_ST_Pier_FullMatch.xml", testCategory: "JENKINS", environment: [ 'rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, lautaro.rial@securitize.io, joaquin.ibanez@securitize.io"],
        "AUT618_ISR_Lost_Shares": [suiteFileName: "e2e/ISR/AUT618_ISR_Lost_Shares.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT619_ISR_Destroy_Tokens_Wallet": [suiteFileName: "e2e/ISR/AUT619_ISR_Destroy_Tokens_Wallet.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,ben.berko@securitize.io"],
        "AUT620_ISR_Destroy_Tokens_TBE": [suiteFileName: "e2e/ISR/AUT620_ISR_Destroy_Tokens_TBE.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,ben.berko@securitize.io"],
        "AUT624_ISR_Hold_Trading": [suiteFileName: "e2e/ISR/AUT624_ISR_Hold_Trading.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT626_TA_Drip_HappyFlow": [suiteFileName: "e2e/TA/AUT626_TA_Drip_HappyFlow.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT627_ISR_Lost_Shares": [suiteFileName: "e2e/ISR/AUT627_ISR_Lost_Shares.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT639_blackrock_daily_deposit_10_investors": [suiteFileName: "e2e/blackrock/AUT639_blackrock_daily_deposit_10_investors.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT662_blackrock_entity_registration_Issuance_E2E": [suiteFileName: "e2e/blackrock/AUT662_blackrock_entity_registration_Issuance_E2E.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT663_blackrock_accrued_dividend": [suiteFileName: "e2e/blackrock/AUT663_blackrock_accrued_dividend.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT640_ATS_AssetDetails": [suiteFileName: "e2e/ATS/AUT640_ATS_AssetDetails.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT655_ATS_LimitedTrading": [suiteFileName: "e2e/ATS/AUT655_ATS_LimitedTrading.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT657_JP_Onboarding_Export_List": [suiteFileName: "e2e/JP/AUT657_JP_Onboarding_Export_List.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT658_JP_Fundraise_Export_List": [suiteFileName: "e2e/JP/AUT658_JP_Fundraise_Export_List.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT659_JP_Holders_Export_List": [suiteFileName: "e2e/JP/AUT659_JP_Holders_Export_List.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT673_FT_Opportunities_check_Production": [suiteFileName: "e2e/FT/AUT673_FT_Opportunities_check_Production.xml", testCategory: "JENKINS", environment: ['production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT674_JP_Sony_Investor_Login": [suiteFileName: "e2e/JP/AUT674_JP_Sony_Investor_Login.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT679_FT_ArcaLabs_CustomDomain_Login_production": [suiteFileName: "e2e/FT/AUT679_FT_ArcaLabs_CustomDomain_Login_production.xml", testCategory: "JENKINS", environment: ['production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT680_FT_SpiceVC_CustomDomain_Login_production": [suiteFileName: "e2e/FT/AUT680_FT_SpiceVC_CustomDomain_Login_production.xml", testCategory: "JENKINS", environment: ['production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT684_blackrock_E2E_primary_market": [suiteFileName: "e2e/blackrock/AUT684_blackrock_E2E_primary_market.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT686_ATS_SearchAsset": [suiteFileName: "e2e/ATS/AUT686_ATS_SearchAsset.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io,carlos.a.leguizamon@securitize.io"],
        "AUT687_ATS_MarketStatus": [suiteFileName: "e2e/ATS/AUT687_ATS_MarketStatus.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io,carlos.a.leguizamon@securitize.io"],
        "AUT696_ST_Issue_Tokens_To_Wallet": [suiteFileName: "e2e/ST/AUT696_ST_Issue_Tokens_To_Wallet.xml", testCategory: "JENKINS", environment: [ 'rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, lautaro.rial@securitize.io, joaquin.ibanez@securitize.io"],
        "AUT700_ATS_Trade_Match_Sell_order": [suiteFileName: "e2e/ATS/AUT700_ATS_Trade_Match_Sell_order.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io,carlos.a.leguizamon@securitize.io"],
        "AUT702_ISR_TBE_To_Wallet_Transfer": [suiteFileName: "e2e/ISR/AUT702_ISR_TBE_To_Wallet_Transfer.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,ben.berko@securitize.io"],
        "AUT704_JP_Sony_Investor_Login_New": [suiteFileName: "e2e/JP/AUT704_JP_Sony_Investor_Login_New.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT706_JP_Sony_Investor_Login_Post": [suiteFileName: "e2e/JP/AUT706_JP_Sony_Investor_Login_Post.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,satoshi.ishigami@securitize.io"],
        "AUT714_ST_IssueTokens_ToTbe": [suiteFileName: "e2e/ST/AUT714_ST_IssueTokens_ToTbe.xml", testCategory: "JENKINS", environment: [ 'rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io, daniel@securitize.io, lautaro.rial@securitize.io, joaquin.ibanez@securitize.io"],
        "AUT722_blackrock_hold_trading": [suiteFileName: "e2e/blackrock/AUT722_blackrock_hold_trading.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"]
]

def allJobsDefaults2 = [
        //stand alone test jobs AUT3-AUT293
        "AUT3_ISR_Investor_Registration_USD": [suiteFileName: "e2e/ISR/AUT3_ISR_Investor_Registration_USD.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production', 'apac'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT5_FT_InvestorRegistration_Individual_NIE_SumSub_ETH": [suiteFileName: "e2e/FT/AUT5_FT_InvestorRegistration_Individual_NIE_SumSub_ETH.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT5_FT_InvestorRegistration_Individual_NIE_SumSub_USD": [suiteFileName: "e2e/FT/AUT5_FT_InvestorRegistration_Individual_NIE_SumSub_USD.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT28_FT_InvestorRegistration_NIE_Entity_USD": [suiteFileName: "e2e/FT/AUT28_FT_InvestorRegistration_NIE_Entity_USD.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT29_SID_Sync_SecuritizeID_To_Issuer": [suiteFileName: "e2e/SID/AUT29_SID_Sync_SecuritizeID_To_Issuer.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT42_SID_SecuritizeID_Login_2FA_Issuer": [suiteFileName: "e2e/SID/AUT42_SID_SecuritizeID_Login_2FA_Issuer.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT65_SID_SecuritizeID_SumSub_KYC_Regression_NonUS": [suiteFileName: "e2e/SID/AUT65_SID_SecuritizeID_SumSub_KYC_Regression_NonUS.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT66_SID_SecuritizeID_SumSub_KYC_Regression_US": [suiteFileName: "e2e/SID/AUT66_SID_SecuritizeID_SumSub_KYC_Regression_US.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT67_SID_SecuritizeID_KYB_Regression_US": [suiteFileName: "e2e/SID/AUT67_SID_SecuritizeID_KYB_Regression_US.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT68_SID_SumSub_Updates_Required": [suiteFileName: "e2e/SID/AUT68_SID_SumSub_Updates_Required.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT70_SID_SumSub_Reject": [suiteFileName: "e2e/SID/AUT70_SID_SumSub_Reject.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT72_SID_SumSub_Manual_Review": [suiteFileName: "e2e/SID/AUT72_SID_SumSub_Manual_Review.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT73_ISR_InvestorRegistration_ETH": [suiteFileName: "e2e/ISR/AUT73_ISR_InvestorRegistration_ETH.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT74_ISR_InvestorRegistration_FBO": [suiteFileName: "e2e/ISR/AUT74_ISR_InvestorRegistration_FBO.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT76_ISR_ExportList": [suiteFileName: "e2e/ISR/AUT76_ISR_ExportList.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT77_ISR_VerifyExistingInvestor": [suiteFileName: "e2e/ISR/AUT77_ISR_VerifyExistingInvestor.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT82_ISR_Login_2FA_Test": [suiteFileName: "e2e/ISR/AUT82_ISR_Login_2FA_Test.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT95_SID_Validate_Personal_Information": [suiteFileName: "e2e/SID/AUT95_SID_Validate_Personal_Information.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT116_ST_Pier_PartialMatch_CancelOrder": [suiteFileName: "e2e/ST/AUT116_ST_Pier_PartialMatch_CancelOrder.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, joaquin.ibanez@securitize.io, lautaro.rial@securitize.io"],
        "AUT117_HealthCheckEndpoints": [suiteFileName: "e2e/General/AUT117_HealthCheckEndpoints.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT128_ATS_Trade_Full_Match": [suiteFileName: "e2e/ATS/AUT128_ATS_Trade_Full_Match.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT130_SID_Investor_Pays": [suiteFileName: "e2e/SID/AUT130_SID_Investor_Pays.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT132_SID_Issuer_Pays": [suiteFileName: "e2e/SID/AUT132_SID_Issuer_Pays.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT133_SID_Single_Txn_Issuer_Pays": [suiteFileName: "e2e/SID/AUT133_SID_Single_Txn_Issuer_Pays.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT134_ISR_Import_Investor_And_Multiple_Investors": [suiteFileName: "e2e/ISR/AUT134_ISR_Import_Investor_And_Multiple_Investors.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT135_ISR_EditExistingInvestor": [suiteFileName: "e2e/ISR/AUT135_ISR_EditExistingInvestor.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT150_ISR_API_Registration_Flow_And_Control_Book": [suiteFileName: "e2e/ISR/AUT150_ISR_API_Registration_Flow_And_Control_Book.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT165_SID_Validate_Entity_Information_Issuer": [suiteFileName: "e2e/SID/AUT165_SID_Validate_Entity_Information_Issuer.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT167_HealthCheckUrls": [suiteFileName: "e2e/General/AUT167_HealthCheckUrls.xml", testCategory: "JENKINS", environment: ['rc', 'all'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT169_ISR_API_Registration_Flow_Algorand": [suiteFileName: "e2e/ISR/AUT169_ISR_API_Registration_Flow_Algorand.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT171_CA_Deposit_US_Account_InternationalWire": [suiteFileName: "e2e/CA/AUT171_CA_Deposit_US_Account_InternationalWire.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT172_CA_Deposit_US_Account_Wire": [suiteFileName: "e2e/CA/AUT172_CA_Deposit_US_Account_Wire.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT173_CA_Deposit_US_Account_USDC": [suiteFileName: "e2e/CA/AUT173_CA_Deposit_US_Account_USDC.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,moses.levy@securitize.io"],
        "AUT175_SID_Adding_Wallet_Via_CP": [suiteFileName: "e2e/SID/AUT175_SID_Adding_Wallet_Via_CP.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT181_SID_Add_Wallet_Via_UI": [suiteFileName: "e2e/SID/AUT181_SID_Add_Wallet_Via_UI.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT184_ATS_InvestorRegistration_ATS_Ready": [suiteFileName: "e2e/ATS/AUT184_ATS_InvestorRegistration_ATS_Ready.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT188_FT_InvestorRegistration_Individual_NIE_CoinBase": [suiteFileName: "e2e/FT/AUT188_FT_InvestorRegistration_Individual_NIE_CoinBase.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT202_TA_Accreditation_Individual_Income_Verified": [suiteFileName: "e2e/TA/AUT202_TA_Accreditation_Individual_Income_Verified.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT205_TA_TaxForm_Autocomplete_Individual_US": [suiteFileName: "e2e/TA/AUT205_TA_TaxForm_Autocomplete_Individual_US.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT220_TA_Accreditation_Entity_Income_Verified": [suiteFileName: "e2e/TA/AUT220_TA_Accreditation_Entity_Income_Verified.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT228_ISR_MSF_VerifyExistingInvestor": [suiteFileName: "e2e/ISR/AUT228_ISR_MSF_VerifyExistingInvestor.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT229_FT_InvestorDocuments": [suiteFileName: "e2e/FT/AUT229_FT_InvestorDocuments.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT234_ISR_SecuritiesTransactionsExistingInvestor": [suiteFileName: "e2e/ISR/AUT234_ISR_SecuritiesTransactionsExistingInvestor.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,matias.auslender@securitize.io"],
        "AUT261_SID_Website_Regression_Overview": [suiteFileName: "e2e/SID/AUT261_SID_Website_Regression_Overview.xml", browserType: ['chrome-remote', 'ios-browserstack'], testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io", slackChannel: "C063HPZB6EB", slackMessagePrefix: "IOS real device website regression test on Jenkins"],
        "AUT263_TA_Accreditation_NON_US_Individual_Investor": [suiteFileName: "e2e/TA/AUT263_TA_Accreditation_NON_US_Individual_Investor.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT271_TA_Accreditation_Entity_Income_UpdatesRequired": [suiteFileName: "e2e/TA/AUT271_TA_Accreditation_Entity_Income_UpdatesRequired.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT274_FT_PrimaryOffering_AccreditationFirst": [suiteFileName: "e2e/FT/AUT274_FT_PrimaryOffering_AccreditationFirst.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT276_TA_Accreditation_Individual_Income_Rejected": [suiteFileName: "e2e/TA/AUT276_TA_Accreditation_Individual_Income_Rejected.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT278_TA_Accreditation_Entity_Income_Rejected": [suiteFileName: "e2e/TA/AUT278_TA_Accreditation_Entity_Income_Rejected.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT279_TA_Accreditation_Individual_Income_Expired": [suiteFileName: "e2e/TA/AUT279_TA_Accreditation_Individual_Income_Expired.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT280_TA_Accreditation_Entity_Income_Expired": [suiteFileName: "e2e/TA/AUT280_TA_Accreditation_Entity_Income_Expired.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT283_TA_Accreditation_Individual_NetWorth_Verified": [suiteFileName: "e2e/TA/AUT283_TA_Accreditation_Individual_NetWorth_Verified.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT284_TA_Accreditation_Individual_ProfessionalLicence_Verified": [suiteFileName: "e2e/TA/AUT284_TA_Accreditation_Individual_ProfessionalLicence_Verified.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT285_TA_Accreditation_Individual_OwnersOfSecurities_Verified": [suiteFileName: "e2e/TA/AUT285_TA_Accreditation_Individual_OwnersOfSecurities_Verified.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT288_TA_TaxForm_Autocomplete_Entity_US": [suiteFileName: "e2e/TA/AUT288_TA_TaxForm_Autocomplete_Entity_US.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT289_TA_TaxForm_Autocomplete_Individual_NONUS": [suiteFileName: "e2e/TA/AUT289_TA_TaxForm_Autocomplete_Individual_NONUS.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT290_SID_SumSub_Reject_Blocked": [suiteFileName: "e2e/SID/AUT290_SID_SumSub_Reject_Blocked.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        "AUT293_SID_SumSub_Verified_Documents_Expired": [suiteFileName: "e2e/SID/AUT293_SID_SumSub_Verified_Documents_Expired.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,israel.brener@securitize.io"],
        //miscellaneous jobs
        "AUT99_Percy_POC": [suiteFileName: "percy_poc.xml", browserType: ['chrome-remote', 'chrome-browserstack'], testCategory: "JENKINS", environment: ['rc', 'sandbox'], usePercy: true, emailRecipients: "yoav.l@securitize.io"],
        "AUT100_visualTestingPOC": [suiteFileName: "percy_poc.xml", browserType: ['chrome-remote', 'chrome-browserstack'], testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io"],
        "AUT299_CA_TotalTests": [suiteFileName: "e2e/AUT299_CA_TotalTests.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT300_ATS_TotalTests": [suiteFileName: "e2e/AUT300_ATS_TotalTests.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'],skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT301_FT_TotalTests": [suiteFileName: "e2e/AUT301_FT_TotalTests.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT302_ISR_TotalTests": [suiteFileName: "e2e/AUT302_ISR_TotalTests.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT303_SID_TotalTests": [suiteFileName: "e2e/AUT303_SID_TotalTests.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT304_ST_TotalTests": [suiteFileName: "e2e/AUT304_ST_TotalTests.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT305_TA_TotalTests": [suiteFileName: "e2e/AUT305_TA_TotalTests.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT651_JP_TotalTests": [suiteFileName: "e2e/AUT651_JP_TotalTests.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT306_ATS_TotalTests_API": [suiteFileName: "api/AUT306_ATS_TotalTests_API.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true,  emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT307_FT_TotalTests_API": [suiteFileName: "api/AUT307_FT_TotalTests_API.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io, fernando.martin@securitize.io"],
        "AUT308_ISR_TotalTests_API": [suiteFileName: "api/AUT308_ISR_TotalTests_API.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT309_SID_TotalTests_API": [suiteFileName: "api/AUT309_SID_TotalTests_API.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT310_ST_TotalTests_API": [suiteFileName: "api/AUT310_ST_TotalTests_API.xml", testCategory: "JENKINS", environment:['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT311_TA_TotalTests_API": [suiteFileName: "api/AUT311_TA_TotalTests_API.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, lautaro.rial@securitize.io"],
        "AUT718_JP_TotalTests_API": [suiteFileName: "api/AUT718_JP_TotalTests_API.xml", testCategory: "JENKINS", environment: ['sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io, satoshi.ishigami@securitize.io"],
        "AUT480_ATS_Cancel_Buy_Order": [suiteFileName: "api/ATS/AUT480_ATS_Cancel_Buy_Order.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT481_ATS_Cancel_Sell_Order": [suiteFileName: "api/ATS/AUT481_ATS_Cancel_Sell_Order.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT482_ATS_Reject_Order": [suiteFileName: "api/ATS/AUT482_ATS_Reject_Order.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT483_ATS_Match_Order_Buy_Side": [suiteFileName: "api/ATS/AUT483_ATS_Match_Order_Buy_Side.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT484_ATS_Match_Order_Sell_Side": [suiteFileName: "api/ATS/AUT484_ATS_Match_Order_Sell_Side.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT485_ATS_Partial_Match_Order_Buy_Side": [suiteFileName: "api/ATS/AUT485_ATS_Partial_Match_Order_Buy_Side.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT486_ATS_Partial_Match_Order_Sell_Side": [suiteFileName: "api/ATS/AUT486_ATS_Partial_Match_Order_Sell_Side.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox', 'production'], skipTestsListedInGoogleSheet: true, emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT670_FT_PrimaryOffering_Documents": [suiteFileName: "e2e/FT/AUT670_FT_PrimaryOffering_Documents.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "ATS-ats-order-api_RC": [suiteFileName: "api/ATS/ATS-order-service-api-RC-sanity.xml", testCategory: "JENKINS", environment: ['rc'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io, carlos.a.leguizamon@securitize.io"],
        "ATS-ats-order-api_SANDBOX": [suiteFileName: "api/ATS/ATS-order-service-api-SB-sanity.xml", testCategory: "JENKINS", environment: ['sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io, carlos.a.leguizamon@securitize.io"],
        "AUT673_FT_Opportunities_check_Production": [suiteFileName: "e2e/FT/AUT673_FT_Opportunities_check_Production.xml", testCategory: "JENKINS", environment: ['production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT675_IOS_Login": [suiteFileName: "e2e/AUT675_IOS_TotalTests.xml", browserType: ['ios-browserstack'], testCategory: "JENKINS", environment: ['rc', 'production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io", slackChannel: "C063HPZB6EB", slackMessagePrefix: "IOS real device login test on Jenkins"],
        "AUT679_FT_ArcaLabs_CustomDomain_Login_production": [suiteFileName: "e2e/FT/AUT679_FT_ArcaLabs_CustomDomain_Login_production.xml", testCategory: "JENKINS", environment: ['production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT680_FT_SpiceVC_CustomDomain_Login_production": [suiteFileName: "e2e/FT/AUT680_FT_SpiceVC_CustomDomain_Login_production.xml", testCategory: "JENKINS", environment: ['production'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"],
        "AUT672_FT_NIE_KYC_Status": [suiteFileName: "e2e/FT/AUT672_FT_NIE_KYC_Status.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io,lautaro.rial@securitize.io"],
        "AUT705_FT_PrimaryOffering_Additional_Information_Modal": [suiteFileName: "e2e/FT/AUT705_FT_PrimaryOffering_Additional_Information_Modal.xml", testCategory: "JENKINS", environment: ['rc', 'sandbox'], emailRecipients: "yoav.l@securitize.io,daniel@securitize.io"]
]

def merge(Map lhs, Map rhs) {
    return rhs.inject(lhs.clone()) { map, entry ->
        if (map[entry.key] instanceof Map && entry.value instanceof Map) {
            map[entry.key] = merge(map[entry.key], entry.value)
        } else {
            map[entry.key] = entry.value
        }
        return map
    }
}

def allJobsDefaultsTotal = merge(allJobsDefaults1, allJobsDefaults2)

// Output for debugging if it is failing
def jobPrinting = ""
for (job in allJobsDefaultsTotal) {
    jobPrinting += "\n-> " + job
}
println "All jobs default values list: " + jobPrinting

def currentJobDefaults = allJobsDefaultsTotal["${JOB_NAME}"]
println "Loading defaults for job ${JOB_NAME} as: " + currentJobDefaults

if (currentJobDefaults == null) {
    println "ERROR: The job '${JOB_NAME}' that you are running was not found in the job list. Possible solutions: \n" +
            "- Be sure that '${JOB_NAME}' is listed in the upper job list as the first parameter. If it is not listed, you should change the 'Branches to build' job config, pointing to your branch (*/branchName) \n" +
            "- Be sure that '${JOB_NAME}' matches the first param of the job list you added. If it is not matching, you could change the Jenkins job name or the sanity.groovy job name, to make them match"
}

// set list of allowed browsers - default list or specific value from job configuration
defaultListOfBrowsers = ['chrome-remote']
def listOfBrowsers

if (currentJobDefaults.containsKey("browserType")) {
    listOfBrowsers = currentJobDefaults["browserType"]
} else {
    listOfBrowsers = defaultListOfBrowsers
}
println("List of browsers to be used: " + listOfBrowsers)

// set of default param to skipTestsListedInGoogleSheet
defaultSkipTestsListedInGoogleSheet = false
def actualSkipTestsListedInGoogleSheet;

if (currentJobDefaults.containsKey("skipTestsListedInGoogleSheet")) {
    actualSkipTestsListedInGoogleSheet = currentJobDefaults["skipTestsListedInGoogleSheet"];
} else {
    actualSkipTestsListedInGoogleSheet = defaultSkipTestsListedInGoogleSheet;
}
println("Actual skipTestsListedInGoogleSheet: " + actualSkipTestsListedInGoogleSheet)

def runSilentCommand(cmd) {
    return sh(returnStdout: true, script: '#!/bin/sh -e\n' + cmd).trim()
}

def GetParameterFromVault(parameterPath) {
    return runSilentCommand("aws ssm get-parameter --name '${parameterPath}' --region us-east-2 | jq -r '.Parameter.Value'")
}

def executePercyRun() {
    // load percy token from users.properties by job name
    def percyKey = "percy_token_${JOB_NAME}"
    percyToken = GetParameterFromVault("/qa/generic/${percyKey}")

    // set credentials to allow percy to pull needed css files
    username = GetParameterFromVault("/qa/generic/httpAuthenticationUsername")
    runSilentCommand("sed -i -e 's/USERNAME/" + username + "/g' src/main/resources/percy/percy.yml")

    password = GetParameterFromVault("/qa/generic/httpAuthenticationPassword")
    runSilentCommand("sed -i -e 's/PASSWORD/" + password + "/g' src/main/resources/percy/percy.yml")

    // run the test from percy executable with token as environment variable
    withEnv(["PERCY_TOKEN=${percyToken}", "visualTestingEngine=percy"]) {
        sh "percy exec --config src/main/resources/percy/percy.yml -- mvn compile failsafe:integration-test failsafe:verify -DsuiteXmlFile=${env.suiteFileName}"
    }
}

// add the MANUAL_STABILIZATION test category for all E2E jobs
def updatedTestCategories = [currentJobDefaults['testCategory']]
if (currentJobDefaults['suiteFileName'].toString().startsWith("e2e")) {
    updatedTestCategories.add("MANUAL_STABILIZATION")
}

pipeline {

    agent any

    tools {
        // Install the Maven and JDK versions as configured in the global tools section
        maven "M3"
        jdk "OpenJDK11"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'master', description: 'Name of git branch to pull our code from')
        choice(name: 'environment', choices: currentJobDefaults['environment'], description: 'Environment on which to run')
        choice(name: 'testNetwork', choices: ['QUORUM', 'ETHEREUM_SEPOLIA', 'POLYGON_MUMBAI'], description: 'Which test network to use - affects token used')
        choice(name: 'browserType', choices: listOfBrowsers, description: 'Browser to use')
        choice(name: 'browserDeviceNameToEmulate', choices: ['desktop', 'iPhone 14', 'iPhone 14 Pro', 'iPhone 14 Plus', 'iPad 10th', 'Galaxy S23'], description: 'Browser resolution to use - mimicking known devices')
        choice(name: 'browserLocation', choices: ['Moon', 'JenkinsLocal'], description: 'Where will the browsers be opened - locally on Jenkins or remotely on Moon')
        string(name: 'suiteFileName', defaultValue: currentJobDefaults['suiteFileName'], description: 'Name of suite file to run')
        choice(name: 'testCategory', choices: updatedTestCategories, description: 'Category of these tests to be reported to the dashboard. E2E tests have also the option of MANUAL_STABILIZATION')
        booleanParam(name: 'skipTestsListedInGoogleSheet', defaultValue: actualSkipTestsListedInGoogleSheet, description: 'Should skip tests listed in a dedicated Google sheet')
        booleanParam(name: 'runFailedTests', defaultValue: false, description: 'Should re-run only failed tests from the previous execution')
        string(name: 'emailRecipients', defaultValue: currentJobDefaults['emailRecipients'], description: 'list of emails to inform is this job fails (comma separated list)')
    }

    environment {
        PATH = "/home/ubuntu/.nvm/versions/node/v12.16.3/bin:$PATH"
    }

    stages {
        stage('Load') {
            steps {
                script {
                    mainLoader = load "src/main/resources/jenkinsPipelineJobs/commonStepScripts/stepsMainLoad.groovy"
                }
            }
        }

        stage('Delete old artifacts') {
            steps {
                script {
                    def filesToClean = ["index.html", "dashboard.html", "exception.html"]
                    filesToClean.each { currentFile ->
                        mainLoader.deleteOldArtifacts(currentFile, currentFile)
                    }
                }
            }
        }

        stage('Checkout code') {
            steps {
                script {
                    mainLoader.checkoutCode()
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

        stage('Build & Run Tests') {
            steps {
                script {
                    if (currentJobDefaults['usePercy']) {
                       executePercyRun()
                    } else {
                        if (currentJobDefaults["testsGroup"]) {
                            mainLoader.mavenCleanAndCompile(params.suiteFileName + " -Dgroups=" + currentJobDefaults['testsGroup'])
                        } else {
                            mainLoader.mavenCleanAndCompile(params.suiteFileName)
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                String failedFileName = "testng-failed-${params.environment}.xml"
                if (fileExists(failedFileName)) {
                    echo "removing old ${failedFileName} as it is no longer relevant"
                    sh "rm ${failedFileName}"
                }
            }

            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, includes: '**/index.html', keepAll: true, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
            step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
        }

        failure {
            script {
                if (fileExists('target/failsafe-reports/testng-failed.xml')) {
                    String fileName = "testng-failed-${params.environment}.xml"
                    echo "copying testng-failed.xml so it is available for next execution (as ${fileName})"
                    sh "cp target/failsafe-reports/testng-failed.xml ./${fileName}"
                } else {
                    echo "test failed but target/failsafe-reports/testng-failed.xml was not found..."
                }

                if (currentJobDefaults['slackChannel'] && params.browserType == "ios-browserstack") {
                    String slackChannel = currentJobDefaults['slackChannel']
                    String slackMessagePrefix = currentJobDefaults['slackMessagePrefix']
                    slackSend channel: slackChannel, color: "danger", message: slackMessagePrefix + " FAILED! (${params.environment})\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
                }
            }
            mail bcc: '', body: "<b>Details:</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Jenkins job failed -> ${env.JOB_NAME} (build: #${env.BUILD_NUMBER} env: ${params.environment})", to: "${params.emailRecipients}"
        }

        success {
            script {
                if (currentJobDefaults['slackChannel'] && params.browserType == "ios-browserstack") {
                    String slackChannel = currentJobDefaults['slackChannel']
                    String slackMessagePrefix = currentJobDefaults['slackMessagePrefix']
                    slackSend channel: slackChannel, color: "good", message: slackMessagePrefix + " success! (${params.environment})\n${env.JOB_NAME} #${env.BUILD_NUMBER} - (<${env.BUILD_URL}|Open>)"
                }
            }

        }
    }
}
