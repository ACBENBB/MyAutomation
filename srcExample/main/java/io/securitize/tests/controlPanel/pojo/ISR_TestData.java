package io.securitize.tests.controlPanel.pojo;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.utils.RandomGenerator;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.abstractClass.AbstractISR;

import java.io.File;

public class ISR_TestData {

    public InvestorDetails investorDetails;
    public String browserType;
    public String investmentCurrency;
    public String currencyName;
    public String issuerName;
    public String investorFirstName;
    public String marketsTokenName;
    public String opportunityName;
    public String currentEnvironment;
    public String nonCorporateBasicEmail;
    public String nonCorporateAdminEmail;
    public String nonCorporateSuperAdminEmail;
    public String marketsPledgedAmount;
    public String marketsFundedAmount;
    public String issuanceReason;
    public String roundsTokenName;
    public String roundsAddRoundName;
    public String roundsAddMinimumInvestmentFiat;
    public String roundsAddMinimumInvestmentCrypto;
    public String roundsAddStartDate;
    public String roundsAddEndDate;
    public String roundsAddIssuanceDate;
    public String roundsAddTokenValue;
    public String roundsAddText;
    public String roundsEditRoundName;
    public String roundsEditMinimumInvestmentFiat;
    public String roundsEditMinimumInvestmentCrypto;
    public String roundsEditStartDate;
    public String roundsEditEndDate;
    public String roundsEditIssuanceDate;
    public String roundsEditTokenValue;
    public String roundsAddStatusName;
    public String roundsEditStatusName;
    public String activatedAffiliateStatus;
    public String deactivatedAffiliateStatus;
    public String issuerSignerName;
    public String autName;
    public String frontImagePath;
    public String importCSVFilePath;
    public String tokenName;
    public String investorFilterName;
    public String userToSearch;
    public String dateTimeFormat;
    public String bulkImportCSVFilePath;
    public String randomGeneratedLastName;
    public String documentID;
    public String url;
    public String documentCategory;
    public String documentFace;
    public String documentTitle;
    public String searchText;
    public String procedureType;
    public String tokensHeldIn;
    public int numberOfCardsToAssert;
    public int amountToBeTransferred;
    public int dateTimeDifferanceToleranceSeconds;
    public int amountOfInvestors;
    public int investmentAmount;
    public int tokenToProcedure;

    public ISR_TestData(AbstractCpInvestorRegistrationFlow.ISR_TestScenario isr_testScenario) {
        this.currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        this.investorDetails = InvestorDetails.generateRandomUSInvestor();
        this.url = MainConfig.getProperty(MainConfigProperty.cpUrl);
        this.issuerName = "Nie";
        this.investmentCurrency = "USD";
        this.currencyName = "United States Dollar";
        this.issuanceReason = autName + " issuance reason";
        this.importCSVFilePath = "tempImportFile.csv";
        this.bulkImportCSVFilePath = "tempImportBulkIssuanceFile.csv";
        this.randomGeneratedLastName = RandomGenerator.randomString(6);
        this.amountOfInvestors = 10;
        this.frontImagePath = ResourcesUtils.getResourcePathByName("images" + File.separator + "passport-front.jpg");
        this.amountToBeTransferred = 1;
        this.documentID = "123456";
        this.tokenToProcedure = 1;
        this.searchText = "AUT3.";
        switch (isr_testScenario) {
            case AUT73_ISR_Investor_Registration_ETH:
                this.currencyName = "Ether";
                this.investmentCurrency = "ETH";
                break;
            case AUT76_ISR_ExportList:
                this.investorFilterName = "middlename";
                break;
            case AUT77_ISR_Verify_Existing_Investor:
                this.searchText = "Auto77";
                break;
            case AUT135_ISR_Edit_Existing_Investor_Validation:
                break;
            case AUT228_ISR_MSF_Verify_Existing_Investor:
                this.searchText = "Auto228";
                break;
            case AUT388_ISR_Fundraise_Screen:
                this.searchText = "Auto388";
                break;
            case AUT409_ISR_Holders_Screen:
                this.searchText = "Auto409";
                break;
            case AUT413_ISR_Internal_TBE_Transfer:
                this.searchText = "AutoTBE413";
                this.autName = "AUT 413";
                this.tokensHeldIn = "TBE";
                this.procedureType = "Internal TBE Transfer";
                break;
            case AUT472_ISR_Login_And_Audit:
                this.dateTimeDifferanceToleranceSeconds = 60;
                this.userToSearch = "Securitize Automation AUT 472";
                this.dateTimeFormat = "yyyy-MM-dd, HH:mm:ss";
                break;
            case AUT507_ISR_Privacy_Control:
                this.issuerName = "PC Automation";
                break;
            case AUT519_ISR_Bulk_Issuance:
                this.importCSVFilePath = "tempImportBulkIssuanceFile.csv";
                break;
            case AUT523_ISR_Decimal_Flow:
                this.issuerName = "New_Automation_Issuer";
                break;
            case AUT532_ISR_Evergreen_Investments:
                this.tokenName = "evergreentkn";
                break;
            case AUT535_ISR_Lock_Tokens_TBE:
                this.searchText = "AutoTBE535";
                this.autName = "AUT 535";
                this.tokensHeldIn = "TBE";
                this.procedureType = "lock";
                break;
            case AUT536_ISR_Unlock_Tokens_TBE:
                this.searchText = "AutoTBE536";
                this.autName = "AUT 536";
                this.tokensHeldIn = "TBE";
                this.procedureType = "unlock";
                break;
            case AUT538_ISR_Markets_Overview:
                if (currentEnvironment.equals("rc")) {
                    this.marketsTokenName = "TKMK";
                } else if (currentEnvironment.equals("sandbox")) {
                    this.marketsTokenName = "SBT2";
                }
                this.opportunityName = "Opp from markets";
                this.numberOfCardsToAssert = 1;
                this.marketsPledgedAmount = "$10.00";
                this.marketsFundedAmount = "$10.00";
                break;
            case AUT540_ISR_Login_Auth_test:
                this.nonCorporateBasicEmail = "basicsecuritizeautomation@gmail.com";
                this.nonCorporateAdminEmail = "admisecuritizeautomation@gmail.com";
                this.nonCorporateSuperAdminEmail = "superadmisecuritizeautomation@gmail.com";
                break;
            case AUT542_ISR_Update_Control_Book:
                this.searchText = "AutoCB542";
                this.autName = "AUT 542";
                this.issuerName = "CB_Automation";
                this.tokensHeldIn = "wallet";
                this.procedureType = "Control Book";
                break;
            case AUT547_ISR_Add_Edit_Delete_Rounds:
                this.roundsTokenName = "rounds";
                this.roundsAddRoundName = "roundTest";
                this.roundsAddMinimumInvestmentFiat = "1";
                this.roundsAddMinimumInvestmentCrypto = "1";
                this.roundsAddStartDate = "Jun 1, 2023";
                this.roundsAddEndDate = "Jun 2, 2023";
                this.roundsAddIssuanceDate = "Jun 2, 2023";
                this.roundsAddTokenValue = "1";
                this.roundsAddText = "text";
                this.roundsEditRoundName = "updateRoundTest";
                this.roundsEditMinimumInvestmentFiat = "2";
                this.roundsEditMinimumInvestmentCrypto = "2";
                this.roundsEditStartDate = "Mar 2, 2024";
                this.roundsEditEndDate = "Mar 3, 2024";
                this.roundsEditIssuanceDate = "Mar 3, 2024";
                this.roundsEditTokenValue = "2";
                this.roundsAddStatusName = "active";
                this.roundsEditStatusName = "planned";
                break;
            case AUT563_ISR_Affiliate_Management:
                this.issuerName = "Affiliate Automation";
                this.issuerSignerName = "Affiliate_Automation";
                this.activatedAffiliateStatus = "yes";
                this.deactivatedAffiliateStatus = "no";
                break;
            case AUT608_ISR_Lock_Tokens_Wallet:
                this.searchText = "AutoWallet608";
                this.autName = "AUT 608";
                this.tokensHeldIn = "wallet";
                this.procedureType = "lock";
                break;
            case AUT609_ISR_Unlock_Tokens_Wallet:
                this.searchText = "AutoWallet609";
                this.autName = "AUT 609";
                this.tokensHeldIn = "wallet";
                this.procedureType = "unlock";
                break;
            case AUT618_ISR_Lost_Shares:
                this.autName = "AUT 618";
                this.investorFirstName = "firstname AUT618";
                break;
            case AUT619_ISR_Destroy_Tokens_Wallet:
                this.searchText = "AutoWallet619";
                this.autName = "AUT 619";
                this.procedureType = "destroy";
                this.tokensHeldIn = "wallet";
                break;
            case AUT620_ISR_Destroy_Tokens_TBE:
                this.searchText = "AutoTBE620";
                this.autName = "AUT 620";
                this.procedureType = "destroy";
                this.tokensHeldIn = "TBE";
                break;
            case AUT624_ISR_Hold_Trading:
                this.autName = "AUT 624";
                this.issuerName = "HT_Automation";
                break;
            case AUT690_ISR_New_UI_Sanity:
                this.autName = "AUT 690";
                this.documentCategory = "Driver License";
                this.documentFace = "Front";
                this.documentTitle = documentCategory + " " + documentFace;
                this.investmentAmount = 0;
                break;
            case AUT702_ISR_TBE_To_Wallet_Transfer:
                this.searchText = "AutoTBE702";
                this.autName = "AUT 702";
                this.procedureType = "Transfer TBE to Wallet";
                this.tokensHeldIn = "wallet";
                break;
        }
    }

    public ISR_TestData(AbstractISR.ISR_TestScenario isr_testScenario) {

        this.currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        this.investorDetails = InvestorDetails.generateRandomUSInvestor();
        this.url = MainConfig.getProperty(MainConfigProperty.cpUrl);
        this.issuerName = "Nie";
        this.investmentCurrency = "USD";
        this.currencyName = "United States Dollar";
        this.issuanceReason = autName + " issuance reason";
        this.importCSVFilePath = "tempImportFile.csv";
        this.bulkImportCSVFilePath = "tempImportBulkIssuanceFile.csv";
        this.randomGeneratedLastName = RandomGenerator.randomString(6);
        this.amountOfInvestors = 10;
        this.frontImagePath = ResourcesUtils.getResourcePathByName("images" + File.separator + "passport-front.jpg");
        this.amountToBeTransferred = 1;
        this.documentID = "123456";
        this.searchText = "AUT3.";

        switch (isr_testScenario) {
            case AUT73_ISR_Investor_Registration_ETH:
                this.currencyName = "Ether";
                this.investmentCurrency = "ETH";
                break;
            case AUT76_ISR_ExportList:
                this.investorFilterName = "middlename";
                break;
            case AUT77_ISR_Verify_Existing_Investor:
                this.searchText = "AUT77.";
                break;
            case AUT135_ISR_Edit_Existing_Investor_Validation:
                break;
            case AUT388_ISR_Fundraise_Screen:
                break;
            case AUT472_ISR_Login_And_Audit:
                this.dateTimeDifferanceToleranceSeconds = 60;
                this.userToSearch = "Securitize Automation AUT 472";
                this.dateTimeFormat = "yyyy-MM-dd, HH:mm:ss";
                break;
            case AUT413_ISR_Internal_TBE_Transfer:
                this.autName = "AUT 413";
                this.procedureType = "Internal TBE Transfer";
                break;
            case AUT519_ISR_Bulk_Issuance:
                this.importCSVFilePath = "tempImportBulkIssuanceFile.csv";
                break;
            case AUT507_ISR_Privacy_Control:
                this.issuerName = "PC Automation";
                break;
            case AUT532_ISR_Evergreen_Investments:
                this.tokenName = "evergreentkn";
                break;
            case AUT535_ISR_Lock_Tokens_TBE:
                this.autName = "AUT 535";
                this.tokensHeldIn = "TBE";
                this.procedureType = "lock";
                break;
            case AUT538_ISR_Markets_Overview:
                if (currentEnvironment.equals("rc")) {
                    this.marketsTokenName = "TKMK";
                } else if (currentEnvironment.equals("sandbox")) {
                    this.marketsTokenName = "SBT2";
                }
                this.opportunityName = "Opp from markets";
                this.numberOfCardsToAssert = 1;
                this.marketsPledgedAmount = "$10.00";
                this.marketsFundedAmount = "$10.00";
                break;
            case AUT540_ISR_Login_Auth_test:
                this.nonCorporateBasicEmail = "basicsecuritizeautomation@gmail.com";
                this.nonCorporateAdminEmail = "admisecuritizeautomation@gmail.com";
                this.nonCorporateSuperAdminEmail = "superadmisecuritizeautomation@gmail.com";
                break;
            case AUT542_ISR_Update_Control_Book:
                this.searchText = "AUT542";
                this.autName = "AUT 542";
                this.issuerName = "CB_Automation";
                this.tokensHeldIn = "wallet";
                this.procedureType = "Control Book";
                break;
            case AUT547_ISR_Add_Edit_Delete_Rounds:
                this.roundsTokenName = "rounds";
                this.roundsAddRoundName = "roundTest";
                this.roundsAddMinimumInvestmentFiat = "1";
                this.roundsAddMinimumInvestmentCrypto = "1";
                this.roundsAddStartDate = "Jun 1, 2023";
                this.roundsAddEndDate = "Jun 2, 2023";
                this.roundsAddIssuanceDate = "Jun 2, 2023";
                this.roundsAddTokenValue = "1";
                this.roundsAddText = "text";
                this.roundsEditRoundName = "updateRoundTest";
                this.roundsEditMinimumInvestmentFiat = "2";
                this.roundsEditMinimumInvestmentCrypto = "2";
                this.roundsEditStartDate = "Mar 2, 2024";
                this.roundsEditEndDate = "Mar 3, 2024";
                this.roundsEditIssuanceDate = "Mar 3, 2024";
                this.roundsEditTokenValue = "2";
                this.roundsAddStatusName = "active";
                this.roundsEditStatusName = "planned";
                break;
            case AUT563_ISR_Affiliate_Management:
                this.issuerName = "Affiliate Automation";
                this.issuerSignerName = "Affiliate_Automation";
                this.activatedAffiliateStatus = "yes";
                this.deactivatedAffiliateStatus = "no";
                break;
            case AUT608_ISR_Lock_Tokens_Wallet:
                this.autName = "AUT 608";
                this.tokensHeldIn = "wallet";
                this.procedureType = "lock";
                break;
            case AUT609_ISR_Unlock_Tokens_Wallet:
                this.searchText = "AUT 608";
                this.autName = "AUT 609";
                this.tokensHeldIn = "wallet";
                this.procedureType = "unlock";
                break;
            case AUT618_ISR_Lost_Shares:
                this.autName = "AUT 618";
                this.investorFirstName = "firstname AUT618";
                break;
            case AUT619_ISR_Destroy_Tokens_Wallet:
                this.autName = "AUT 619";
                this.procedureType = "destroy";
                this.tokensHeldIn = "wallet";
                break;
            case AUT620_ISR_Destroy_Tokens_TBE:
                this.autName = "AUT 620";
                this.procedureType = "destroy";
                this.tokensHeldIn = "TBE";
                break;
            case AUT624_ISR_Hold_Trading:
                this.autName = "AUT 624";
                this.issuerName = "HT_Automation";
                break;
            case AUT690_ISR_New_UI_Sanity:
                this.autName = "AUT 690";
                this.documentCategory = "Driver License";
                this.documentFace = "Front";
                this.documentTitle = documentCategory + " " + documentFace;
                this.investmentAmount = 0;
                break;
            case AUT702_ISR_TBE_To_Wallet_Transfer:
                this.autName = "AUT 702";
                this.procedureType = "Transfer TBE to Wallet";
                break;
        }
    }
}
