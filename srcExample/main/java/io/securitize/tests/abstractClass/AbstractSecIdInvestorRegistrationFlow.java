package io.securitize.tests.abstractClass;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.sumsub.IdDocSubType;
import io.securitize.infra.api.sumsub.IdDocType;
import io.securitize.infra.api.sumsub.SumSubAPI;
import io.securitize.infra.api.sumsub.SumsubUniqueImage;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.exceptions.LivenessCheckException;
import io.securitize.infra.exceptions.SumsubBypassFailureException;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdNavigationPane;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc01IndividualIdentityVerification;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc02IndividualLivenessCheck;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc03IndividualAddress;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc04IndividualLegalInformation;
import io.securitize.tests.InvestorDetails;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.Assert;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractSecIdInvestorRegistrationFlow extends AbstractUiTest {

    protected void fillIndividualKycSteps(InvestorDetails investorDetails, Class<? extends AbstractPage> expectedPageAfterProcess) {
        fillIndividualKycSteps(investorDetails, true, expectedPageAfterProcess, false);
    }

    protected void fillIndividualKycSteps(InvestorDetails investorDetails, Class<? extends AbstractPage> expectedPageAfterProcess, boolean setSumsubKycPassed) throws IOException {
        fillIndividualKycSteps(investorDetails, true, expectedPageAfterProcess, setSumsubKycPassed);
    }

    protected void fillIndividualKycSteps(InvestorDetails investorDetails, boolean performSumSubBypass, Class<? extends AbstractPage> expectedPageAfterProcess, boolean setSumsubKycPassed) {
        // load paths of needed image files for this test - if loading it fails, there is no point in
        // running the test
        int imageIndex = SumsubUniqueImage.getDriverLicenseFrontIndex();
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-front" + imageIndex + ".jpg");
        String backImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-back.jpg");
        String israeliPassportPath = ResourcesUtils.getResourcePathByName("images" + "/" + "IsraeliPassport.jpg");
        String israeliIdPath = ResourcesUtils.getResourcePathByName("images" + "/" + "Israeli-Id.jpg");
        String selfiePath = ResourcesUtils.getResourcePathByName("images" + "/" + "selfie.jpg");

        startTestLevel("Handle phase 01 - Identity verification");
        SecuritizeIdInvestorKyc02IndividualLivenessCheck livenessCheck = null;
        waitForCurrentPhaseToBe("identity verification");

        // This is the condition for applying the SecuritizeID (OCR, Liveness) bypass
        if (performSumSubBypass) {
            // to handle Sumsub not being stable
            RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                    .handle(SumsubBypassFailureException.class)
                    .withDelay(Duration.ofSeconds(5))
                    .withMaxRetries(2)
                    .onRetry(e -> info("Sumsub byapss failed, will try again"))
                    .build();

            Failsafe.with(retryPolicy).run(() -> {
                InvestorsAPI investorsAPI = new InvestorsAPI();
                String securitizeIdProfileId = investorsAPI.getSecuritizeIdOfIndividualByEmail(investorDetails.getEmail());
                String applicantID = SumSubAPI.getApplicantId(securitizeIdProfileId);
                info(String.format("securitizeProfileId=%s is applicantId=%s", securitizeIdProfileId, applicantID));
                if (investorDetails.getCountry().equalsIgnoreCase("United States of America")) {
                    int usFrontSideStatusCode200UploadTry, usBackSideStatusCode200UploadTry;
                    do {
                        usFrontSideStatusCode200UploadTry = SumSubAPI.addIdDocument(applicantID, IdDocType.DRIVERS, IdDocSubType.FRONT_SIDE, frontImagePath, investorDetails.getCountryCode());
                        usBackSideStatusCode200UploadTry = SumSubAPI.addIdDocument(applicantID, IdDocType.DRIVERS, IdDocSubType.BACK_SIDE, backImagePath, investorDetails.getCountryCode());
                    } while (usFrontSideStatusCode200UploadTry + usBackSideStatusCode200UploadTry != 0);
                } else {
                    int nonUSfrontSideStatusCode200UploadTry, nonUSbackSideStatusCode200UploadTry;
                    do {
                        nonUSfrontSideStatusCode200UploadTry = SumSubAPI.addIdDocument(applicantID, IdDocType.PASSPORT, IdDocSubType.FRONT_SIDE, israeliIdPath, investorDetails.getCountryCode());
                        nonUSbackSideStatusCode200UploadTry = SumSubAPI.addIdDocument(applicantID, IdDocType.PASSPORT, IdDocSubType.BACK_SIDE, israeliPassportPath, investorDetails.getCountryCode());
                    } while (nonUSfrontSideStatusCode200UploadTry + nonUSbackSideStatusCode200UploadTry != 0);
                }

                SumSubAPI.addIdDocument(applicantID, IdDocType.SELFIE, IdDocSubType.FRONT_SIDE, selfiePath, investorDetails.getCountryCode());

                getBrowser().refreshPage();
                if (!new SecuritizeIdNavigationPane(getBrowser()).getActivePhaseName().toLowerCase().contains("address")) {
                    throw new SumsubBypassFailureException("Didn't reach the address page");
                }
            });
        } else {
            //add var identityVerification
            SecuritizeIdInvestorKyc01IndividualIdentityVerification identityVerification = new SecuritizeIdInvestorKyc01IndividualIdentityVerification(getBrowser());

            identityVerification.approveLetsGetYouVerifiedPopUp();
            identityVerification.setCountry(investorDetails.getCountry());
            // usa residents: use drivers license
            if (investorDetails.getCountry().equalsIgnoreCase("United States of America")) {
                identityVerification.switchDriversLicensePane()
                        .uploadDriverImages(frontImagePath, backImagePath);

                // for other countries use passport
            } else {
                identityVerification
                        .switchPassportPane()
                        .uploadDriverImageFront(israeliPassportPath);
            }
            visualTesting.snapshot("Handle phase 01 - Identity verification");
            livenessCheck = identityVerification.clickSumbsubContinue();
        }
        endTestLevel();

        startTestLevel("Handle phase 02 - Liveness Check");
        SecuritizeIdInvestorKyc03IndividualAddress addressPage;
        if (performSumSubBypass) {
            info("Skipping phase 02 of Liveness in the UI as handled via API");
            addressPage = new SecuritizeIdInvestorKyc03IndividualAddress(getBrowser());
        } else {
            try {
                waitForCurrentPhaseToBe("liveness check");

                livenessCheck
                        .clickImReadyButton()
                        .waitForScanSuccessful();
                visualTesting.snapshot("Handle phase 02 - Liveness Check");
                addressPage = livenessCheck.clickContinue();
            } catch (Exception e) {
                throw new LivenessCheckException("An error occurred inside the liveness check screen. Check inner exception for further details", e);
            }
        }
        endTestLevel();


        startTestLevel("Handle phase 03 - Address & phone");
        waitForCurrentPhaseToBe("address & phone");
        visualTesting.snapshot("Handle phase 03 - Address & phone");

        String streetName = investorDetails.getStreetName();
        String streetAdditionalInfo = investorDetails.getStreetAdditionalInfo();

        addressPage
                .typeStreetName(streetName)
                .typeStreetAdditionalInfo(streetAdditionalInfo)
                .typeCity(investorDetails.getCity())
                .typeZipCode(investorDetails.getPostalCode())
                .typePhoneNumber(investorDetails.getPhoneNumber());

        //make sure that field 'streetNameField' is not empty
        if (!addressPage.getTypedStreetName().equalsIgnoreCase(streetName)) {
            addressPage.typeStreetName(streetName);
        }
        //make sure that field 'streetAdditionalInfoField' is not empty
        if (!addressPage.getTypedStreetAdditionalInfo().equalsIgnoreCase(streetAdditionalInfo)) {
            addressPage.typeStreetAdditionalInfo(streetAdditionalInfo);
        }
        addressPage.clickContinueIndividual();
        endTestLevel();


        SecuritizeIdInvestorKyc04IndividualLegalInformation legalInformationPage;
        legalInformationPage = new SecuritizeIdInvestorKyc04IndividualLegalInformation(getBrowser());


        startTestLevel("Handle phase 04 - Legal information");
        waitForCurrentPhaseToBe("legal information");
        visualTesting.snapshot("Handle phase 04 - Legal information");

        legalInformationPage
                .selectCountryOfBirth(investorDetails.getCountryOfBirth())
                .selectStateOfBirth(investorDetails.getState())
                .typeCityOfBirth(investorDetails.getCityOfBirth())
                .selectCountryOfTax(investorDetails.getCountryOfTax())
                .typeTaxId(investorDetails.getTaxId())
                .clickContinue();
        endTestLevel();

        // wait for expected after process page to load
        try {
            Constructor<?> ctor = expectedPageAfterProcess.getConstructor(Browser.class);
            ctor.newInstance(getBrowser());
        } catch (Exception e) {
            errorAndStop("An error occurred while trying to create an instance of expected after process page. Make sure standard ctor exists for this page object. Details: " + ExceptionUtils.getStackTrace(e), false);
        }


        // set sumbsub applicant valid data - name and date of birth
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String securitizeIdProfileId = investorsAPI.getSecuritizeIdOfIndividualByEmail(investorDetails.getEmail());
        String applicantID = SumSubAPI.getApplicantId(securitizeIdProfileId);


        String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        if (!currentEnvironment.equalsIgnoreCase("production")) {
            SumSubAPI.updateApplicantOCRData(applicantID, investorDetails);
        }

        if (setSumsubKycPassed) {
            String response = SumSubAPI.setApplicantTestComplete(applicantID);
            info("Set applicant status test complete response: " + response);
        }
    }

    protected void fillEntityKybSteps(InvestorDetails investorDetails, String legalSignerType, boolean isNie) {
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "passport-front.jpg");
        String samplePDFPath = ResourcesUtils.getResourcePathByName("images" + "/" + "sample.pdf");

        startTestLevel("Handle phase 01 - Entity information");
        validatePhase("entity information", "Active phase should be 'entity information' but this is not the case");
        visualTesting.snapshot("Handle phase 01 - Entity information");
        //add var entityInformation
        SecuritizeIdInvestorKyb01EntityInformation entityInformation = new SecuritizeIdInvestorKyb01EntityInformation(getBrowser());

        SecuritizeIdInvestorKyb02Address entityAddress = entityInformation
                .insertEntityLegalName(investorDetails.getEntityName())
                .insertDoingBusiness()
                .selectEntityTypeCooperation()
                .insertTaxId(investorDetails.getTaxId())
                .insertSourceOfFunds(investorDetails.getSourceOfFunds())
                .insertLineOfBusiness(investorDetails.getLineOfBusiness())
                .insertIncorporationDate()
                .clickContinue();
        endTestLevel();

        startTestLevel("Handle phase 02 - Address");
        validatePhase("address", "Active phase should be 'address' but this is not the case");
        visualTesting.snapshot("Handle phase 02 - Address");
        entityAddress
                .typeStreetName(investorDetails.getStreetName())
                .typeStreetAdditionalInfo(investorDetails.getStreetAdditionalInfo())
                .typeCity(investorDetails.getCity())
                .typeZipCode(investorDetails.getPostalCode())
                .clickContinueEntity();
        endTestLevel();

        startTestLevel("Handle phase 03 - Entity Documents");
        validatePhase("entity documents", "Active phase should be 'Entity Documents' but this is not the case");
        visualTesting.snapshot("Handle phase 03 - Entity Documents");
        SecuritizeIdInvestorRegistration04KeyParties keyParties = new SecuritizeIdInvestorKyb03EntityDocuments(getBrowser())
                .byLawsDocumentUpload(frontImagePath) //********* need to upload new documents for upload
                .certificateUpload(frontImagePath)
                .articlesUpload(frontImagePath)
                .shareHoldersUpload(frontImagePath)
                .orgChartUpload(samplePDFPath)
                .clickContinue();
        endTestLevel();

        startTestLevel("Handle phase 04 - Key Parties");
        validatePhase("key parties", "Active phase should be 'Key Parties' but this is not the case");

        // Individual Legal Signer
        SecuritizeIdNavigationPane navigationPane = null;
        if (legalSignerType.equalsIgnoreCase("individual")) {
            visualTesting.snapshot("Handle phase 04 - Key Parties");
            SecuritizeIdInvestorKyb04_1KeyPartiesAddress legalSignersAddress = keyParties.typeFirstName(investorDetails.getFirstName())
                    .typeMiddleName(investorDetails.getMiddleName())
                    .typeLastName(investorDetails.getLastName())
                    .setBirthDate(investorDetails.getBirthDate())
                    .typeTaxID(investorDetails.getTaxId())
                    .selectCountryPayTaxUSA()
                    .insertEmail()
                    .beneficialOwnerSelect()
                    .legalSignerSelect()
                    .clickContinue();
            endTestLevel();

            startTestLevel("Handle phase 04.1 - Key Parties - Address");
            validatePhase("key parties", "Active phase should be 'Key Parties' but this is not the case");
            visualTesting.snapshot("Handle phase 04.1 - Key Parties - Address");
            // add var navigationPane
            navigationPane = new SecuritizeIdNavigationPane(getBrowser());

            String activeSubPhaseName = navigationPane.getActiveSubPhaseName();
            Assert.assertEquals(activeSubPhaseName.trim().toLowerCase(), "address", "Active phase should be 'address' but this is not the case");

            SecuritizeIdInvestorRegistration04_2KeyPartiesIdentityDocuments legalSignerDocuments = legalSignersAddress
                    .typeStreetName(investorDetails.getStreetName())
                    .typeCity(investorDetails.getCity())
                    .typeStreetAdditionalInfo(investorDetails.getStreetAdditionalInfo())
                    .typeZipCode(investorDetails.getPostalCode())
                    .SetCountryOfResidenceUSA_NY()
                    .clickContinue();
            endTestLevel();

            startTestLevel("Handle phase 04.2 - Key Parties - Identity Documents");
            activeSubPhaseName = navigationPane.getActiveSubPhaseName();
            visualTesting.snapshot("Handle phase 04.2 - Key Parties - Identity Documents");
            Assert.assertEquals(activeSubPhaseName.trim().toLowerCase(), "identity documents", "Active phase should be 'Identity Documents' but this is not the case");

            SecuritizeIdInvestorRegistration04_3KeyPartiesCheck legalSignersCheck = legalSignerDocuments
                    .uploadFrontImage(frontImagePath)
                    .clickContinue();
            endTestLevel();

            startTestLevel("Handle phase 04.3 - Key Parties - Check");
            activeSubPhaseName = navigationPane.getActiveSubPhaseName();
            visualTesting.snapshot("Handle phase 04.3 - Key Parties - Check");
            Assert.assertEquals(activeSubPhaseName.trim().toLowerCase(), "check", "Active phase should be 'check' but this is not the case");
            legalSignersCheck.clickContinue();
            endTestLevel();
        }

        // Entity Legal Signer
        else if (legalSignerType.equalsIgnoreCase("entity")) {
            keyParties.switchToEntityTab()
                    .typeEntityLegalName("Automation Legal Name")
                    .typeEntityDoingBusinessAs("Business as Automation")
                    .typeEntityTaxID_EIN(investorDetails.getTaxId())
                    .selectEntityCountryPayTaxUSA()
                    .pickEntityType("Corporation")
                    .insertEntityEmail("daniel+legalEntitySigner@securitize.io")
                    .clickContinueReturningVoid();
            endTestLevel();

            startTestLevel("Handle phase 04.1 - Key Parties - Legal Documents");
            String activeSubPhaseName = navigationPane.getActiveSubPhaseName();
            visualTesting.snapshot("Handle phase 04.1 - Key Parties - Legal Documents");
            Assert.assertEquals(activeSubPhaseName.trim().toLowerCase(), "legal documents", "Active phase should be 'Legal Documents' but this is not the case");

            SecuritizeIdInvestorKyb03EntityDocuments legalSignerDocuments = new SecuritizeIdInvestorKyb03EntityDocuments(getBrowser());
            SecuritizeIdInvestorRegistration04_3KeyPartiesCheck legalSignersCheck = legalSignerDocuments
                    .byLawsDocumentUpload(frontImagePath)
                    .clickContinueToLegalSignerCheck();
            endTestLevel();

            startTestLevel("Handle phase 04.2 - Key Parties - Review Information");
            activeSubPhaseName = navigationPane.getActiveSubPhaseName();
            visualTesting.snapshot("Handle phase 04.2 - Key Parties - Review Information");
            Assert.assertEquals(activeSubPhaseName.trim().toLowerCase(), "review information", "Active phase should be 'review information' but this is not the case");
            legalSignersCheck.clickContinue();
            endTestLevel();
        }

        startTestLevel("Handle phase 05 - Review Information");
        validatePhase("review information", "Active phase should be 'Review Information' but this is not the case");
        visualTesting.snapshot("Handle phase 05 - Review Information");
        SecuritizeIdInvestorRegistration05KeyParties reviewInformation = new SecuritizeIdInvestorRegistration05KeyParties(getBrowser());
        if (isNie) {
            reviewInformation.clickSubmitDocumentsForReview();
        }
        endTestLevel();
    }

    private void waitForCurrentPhaseToBe(String expectedPhase) {
        if (SecuritizeIdNavigationPane.isSideBarVisible(getBrowser())) {
            SecuritizeIdNavigationPane navigationPane = new SecuritizeIdNavigationPane(getBrowser());

            Function<String, Boolean> internalWaitForCurrentPhaseToBe = t -> {
                String activePhaseName = navigationPane.getActivePhaseName();
                return activePhaseName.equalsIgnoreCase(expectedPhase);
            };

            String description = "waitForCurrentActivePhaseToBe: " + expectedPhase;
            Browser.waitForExpressionToEqual(internalWaitForCurrentPhaseToBe, null, true, description, 10, 200);
        }
    }

    private void validatePhase(String expectedPhase, String message) {
        if (SecuritizeIdNavigationPane.isSideBarVisible(getBrowser())) {
            SecuritizeIdNavigationPane navigationPane = new SecuritizeIdNavigationPane(getBrowser());
            String activePhaseName = navigationPane.getActivePhaseName();
            Assert.assertEquals(activePhaseName.trim().toLowerCase(), expectedPhase, message);
        }
    }

    public void setKYCAsVerifiedWithoutValidation(String securitizeIdProfileId, boolean setKycViaNativeApi) {
        InvestorsAPI investorsAPI = new InvestorsAPI();
        if (setKycViaNativeApi) {
            investorsAPI.setKYCToPassed(securitizeIdProfileId, null);
        } else {
            SumSubAPI.setApplicantAsTestComplete(securitizeIdProfileId);
        }
        getBrowser().refreshPage();
    }

    public void setKYCAsVerified(String securitizeIdProfileId, boolean setKycViaNativeApi) {
        setKYCAsVerifiedWithoutValidation(securitizeIdProfileId, setKycViaNativeApi);
    }

    public void addEntityInvestorViaUserMenu(InvestorDetails investorDetails) {
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard
                .clickCreateAccountButton()
                .clickEntitylAcccoutType()
                .clickNext()
                .setEntityInformation(investorDetails.getEntityName() + "-via-user-menu", investorDetails.getCountry(), investorDetails.getState());
    }

}