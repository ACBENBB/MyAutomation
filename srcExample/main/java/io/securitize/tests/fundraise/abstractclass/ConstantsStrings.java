package io.securitize.tests.fundraise.abstractclass;
public class ConstantsStrings {
    // STRING FOR DOCUMENTS SECTION ON OPPORTUNITY DETAILS
    public static final String DOCUMENT_EXAMPLE_URL_SANDBOX = "production-documents.s3.us-east-2.amazonaws.com/temp/oppid/3a155836-9f6d-4942-ba26-2795fa088c9f?";
    public static final String DOCUMENT_OFFERING_MEMORANDUM_URL_SANDBOX = "production-documents.s3.us-east-2.amazonaws.com/temp/oppid/e2ed27ae-df59-43f6-928b-317acf250e91?";
    public static final String DOCUMENT_FORM_CRS_URL_SANDBOX = "production-documents.s3.us-east-2.amazonaws.com/temp/oppid/923fedad-dc0f-4a85-b12a-a2a098d75632?";
    public static final String DOCUMENT_EXAMPLE_URL_RC = "https://staging-testing-documents.s3.us-east-2.amazonaws.com/temp/oppid/ce608e72-6981-47b6-8bd9-ece2d04a12a7?";
    public static final String DOCUMENT_OFFERING_MEMORANDUM_URL_RC   = "g-testing-documents.s3.us-east-2.amazonaws.com/temp/oppid/48c3e49b-483c-49e5-8715-d9e30c56f230?";
    public static final String DOCUMENT_FORM_CRS_URL_RC  = "https://staging-testing-documents.s3.us-east-2.amazonaws.com/temp/oppid/cb31be3a-087b-44ba-86af-bbf04881183c?";
    public static final String DOCUMENT_FIRST_NAME = "Document Example";
    public static final String DOCUMENT_SECOND_NAME = "Offering Memorandum";
    public static final String DOCUMENT_THIRD_NAME = "Form CRS";
    //STRING FOR KYC STATUS POPUP
    public static final String KYC_UPDATES_REQUIRED_TITLE = "Updates required";
    public static final String KYC_UPDATES_REQUIRED_BTN = "Update details";
    public static final String KYC_PROCESSING_TITLE = "Your verification is pending";
    public static final String CONTACT_US_BTN = "Contact us";
    public static final String KYC_EXPIRED_TITLE = "Please provide your details to invest";
    public static final String KYC_EXPIRED_BTN = "Complete verification";
    public static final String KYC_INFO_UPDATE = "/profile/verification";
    public static final String KYC_IDENTITY_VERIFICATION_STEP = "/verification/identity-verification";
    public static final String INVESTMENT_WIZARD = "/investment";
    //STRING FOR KYC STATUS REJECTED 'CHANGE OF STATUS'
    public static final String CHANGE_OF_STATUS_TITLE = "Change of status";

    private ConstantsStrings() {
    }

    public enum KYCstatus {
        UPDATES_REQUIRED ("updates-required"),
        PROCESSING("processing"),
        EXPIRED("expired"),
        REJECTED("rejected"),
        NONE("none"),
        VERIFIED("verified"),
        VERIFIED_EXPIRED("verified-expired"),
        REJECTED_BLOCKED("rejected-blocked"),
        VERIFIED_DOCS_EXPIRED("verified-docs-expired");
        private String status;
        KYCstatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return this.status;
        }
    }
}