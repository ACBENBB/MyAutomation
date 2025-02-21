package io.securitize.infra.dataGenerator;

import io.restassured.response.Response;
import io.securitize.infra.api.MarketsAPI;

public class SecIDInvestorGenerator extends InvestorGeneratorAbstractClass {

    public SecIDInvestorGenerator(String csv) {
        super(csv);
    }

    @Override
    public void generate() {
        for(InvestorDataObject inv : getInvestorsDataObjectList()) {
            createSecIdInvestor(inv);
            addInvestorDetails(inv);
            verifyEmailInvestor(inv);
            addDocumentAsValid(inv);
            if (inv.getVerificationStatus() != null) {
                setVerification(inv, inv.getVerificationStatus());
            }
            if (inv.getTaxForm() != null) {
                submitTaxFormViaApi(inv);
            }
            if (inv.getIsSuitabilityApproved().contains("yes")) {
                addSuitabilityVerified(inv, "verified");
            } else {
                addSuitabilityVerified(inv, "rejected");
            }
        }
    }
    private void createSecIdInvestor(InvestorDataObject investor) {
        String id = investorsAPI.createNewInvestor(getBasicInvDetailsFromInvDataObject(investor));
        investor.setSecID(id);
    }
    private void addInvestorDetails(InvestorDataObject investor) {
        investorsAPI.addInvestorDetails(investor.getSecID(), getCompleteInvDetailsFromInvDataObject(investor));
    }
    private void verifyEmailInvestor(InvestorDataObject investor) {
        investorsAPI.verifyEmail(investor);
    }
    private void addDocumentAsValid(InvestorDataObject investor) {
        try {
            investorsAPI.addValidDocument(investor.getSecID());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setVerification(InvestorDataObject investor, String... status) {

        if (investor.getSecIDType().equalsIgnoreCase("Individual")) {
            setKYCStatus(investor.getSecID(), status);
        } else if (investor.getSecIDType().equalsIgnoreCase("Entity")) {
            setKYBStatus(investor.getSecID(), status);
        }
    }

    public void setKYCStatus(String profileId, String... status) {
        try {
            investorsAPI.setVerificationStatus(profileId, status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setKYBStatus(String profileId, String... status) {
        try {
            investorsAPI.setVerificationStatus(profileId, status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void submitTaxFormViaApi(InvestorDataObject investor) {
        Response response = loginAPI.loginSecIdAndReturnBearerToken(investor.getEmail(), investor.getPassword());
        String taxformType = null;
        String invType = investor.getSecIDType();
        String invCountry = investor.getCountryCode();
        if (invCountry.equalsIgnoreCase("US")) {
            if (invType.equalsIgnoreCase("Individual")) {
                taxformType = "w9";
            } else if (invType.equalsIgnoreCase("Entity")) {
                taxformType = "w9e";
            }
        } else {
            if (invType.equalsIgnoreCase("Individual")) {
                taxformType = "w8ben";
            } else if (invType.equalsIgnoreCase("Entity")) {
                taxformType = "w8bene";
            }
        }
        String response2 = "";
        try {
            response2 = taxFormAPI.submitTaxFormByType(response, taxformType);
            System.out.println(response2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSuitabilityVerified(InvestorDataObject investor, String status) {
        marketsAPI.setMarketUSSuitability(investor.getSecID(), status);
    }

}
