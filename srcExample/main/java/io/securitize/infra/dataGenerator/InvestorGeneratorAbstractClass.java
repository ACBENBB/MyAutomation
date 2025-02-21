package io.securitize.infra.dataGenerator;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.LoginAPI;
import io.securitize.infra.api.MarketsAPI;
import io.securitize.infra.api.transferAgent.TaxFormAPI;
import io.securitize.infra.utils.CSVUtils;
import io.securitize.tests.InvestorDetails;

import java.util.ArrayList;
import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class InvestorGeneratorAbstractClass {

    protected static List<InvestorDataObject> invObjects = new ArrayList<InvestorDataObject>();

    protected InvestorsAPI investorsAPI = new InvestorsAPI();
    protected LoginAPI loginAPI = new LoginAPI();
    protected TaxFormAPI taxFormAPI = new TaxFormAPI();
    protected MarketsAPI marketsAPI = new MarketsAPI();

    public InvestorGeneratorAbstractClass(String csv) {
        invObjects = CSVUtils.importTestDataFromCsv(csv);
        checkMailsDontExists(invObjects);
    }
    private void checkMailsDontExists(List<InvestorDataObject> investors) throws RuntimeException {
        for(InvestorDataObject inv : investors) {
            if (investorsAPI.isEmailAlreadyInUse(inv.getEmail())) {
                errorAndStop("Email: " + inv.getEmail() + " was found in SecId there cannot be duplicated investors", false);
            }
        }
    }

    public static List<InvestorDataObject> getInvestorsDataObjectList() {
        return invObjects;
    }

    public InvestorDataObject getInvestorDataObjectByEmail(String email) {
        InvestorDataObject investor = null;
        for (InvestorDataObject inv : getInvestorsDataObjectList()) {
            if (inv.getEmail().equalsIgnoreCase(email)) {
                investor = inv;
            }
        }
        return investor;
    }

    public void addNewInvestorDataObject(InvestorDataObject investor) {
        getInvestorsDataObjectList().add(investor);
    }

    protected InvestorDetails getBasicInvDetailsFromInvDataObject(InvestorDataObject investor) {
        InvestorDetails invDetails = new InvestorDetails();
        invDetails.setInvestorType(investor.getSecIDType());
        invDetails.setFirstName(investor.getFirstName());
        invDetails.setMiddleName(investor.getMiddleName());
        invDetails.setLastName(investor.getLastName());
        invDetails.setEmail(investor.getEmail());
        invDetails.setCountry(investor.getCountry());
        invDetails.setCountryOfBirth(investor.getCountryOfBirth());
        invDetails.setState(investor.getStateCode());
        return invDetails;
    }
    protected InvestorDetails getCompleteInvDetailsFromInvDataObject(InvestorDataObject investor) {
        InvestorDetails invDetails = new InvestorDetails();
        invDetails.setInvestorType(investor.getSecIDType());
        invDetails.setComment("Generated Automatically");
        invDetails.setFirstName(investor.getFirstName());
        invDetails.setMiddleName(investor.getMiddleName());
        invDetails.setLastName(investor.getLastName());
        invDetails.setLegalSigner(investor.getLegalSigner());
        invDetails.setEmail(investor.getEmail());
        invDetails.setAddress(investor.getAddress2());
        invDetails.setCountry(investor.getCountry());
        invDetails.setCountryOfBirth(investor.getCountryOfBirth());
        invDetails.setState(investor.getState());
        invDetails.setStreetName(investor.getAddress1());
        invDetails.setCountryOfTax(investor.getTaxCountry());
        invDetails.setTaxId(investor.getTaxID());
        invDetails.setBirthDate(investor.getBirthDay());
        invDetails.setCity(investor.getCity());
        invDetails.setPhoneNumber(investor.getPhone());
        invDetails.setPostalCode(investor.getPostalCode());
        invDetails.setDocumentNumber(investor.getIdentityDocumentNumber());
        invDetails.setGender(investor.getGender());
        invDetails.setLegalSigner(investor.getLegalSigner());
        if (investor.getSecIDType().equalsIgnoreCase("Entity")) {
            invDetails.setEntityDba(investor.getFirstName());
            invDetails.setEntityType("LLC");
            invDetails.setEntityIdNumber(investor.getTaxID());
        }
        return invDetails;
    }
    public void generate() {
        //To be implemented in child class
    }

}
