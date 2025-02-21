package io.securitize.tests.apiTests.cicd.ISR.ISR_IssuerInvestors;

import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.tests.InvestorDetails;

public class ISR_IssuerInvestors extends BaseApiTest {

    private InvestorDetails investorDetails;
    private String id;

//    @BeforeClass()
//    public void initNewInvestor() {
//        investorDetails = InvestorDetails.generateRandomInvestor(true, "Api_AddInvestorToIssuer");
//        InvestorsAPI investorsAPI = new InvestorsAPI();
//        investorsAPI.createNewInvestor(investorDetails);
//    }
//
//    @Test()
//    public void addInvestorTest641() {
//        Map<String, Object> parameters = new HashMap<String, Object>() {{
//            put("email", investorDetails.getEmail());
//            put("countryCode", investorDetails.getCountryCode());
//            put("state", investorDetails.getStateCode());
//        }};
//        String responseAsString = testRequest(Method.POST, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors", "addInvestor", LoginAs.OPERATOR, "ISR/main-api","{\"type\":\"object\",\"properties\":{\"firstName\":{\"default\":\"\",\"type\":\"string\"},\"lastName\":{\"default\":\"\",\"type\":\"string\"},\"issuerId\":{\"type\":\"string\"},\"countryCode\":{\"format\":\"countryCode\",\"type\":\"string\"},\"externalId\":{\"type\":\"string\"},\"middleName\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"number\"},\"state\":{\"type\":\"string\"},\"blockchainId\":{\"type\":[\"string\",\"null\"]},\"email\":{\"format\":\"email\",\"type\":\"string\"}}}",  parameters);
//        JSONObject response = new JSONObject(responseAsString);
//        id = response.getInt("id") + "";
//    }
//
//
//    @Test(dependsOnMethods = "addInvestorTest641")
//    public void getInvestorsTest423() {
//        Map<String, Object> parameters = new HashMap<String, Object>() {{
//            put("email", investorDetails.getEmail());
//            put("id", id);
//        }};
//
//        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors", "getInvestors", LoginAs.OPERATOR, "ISR/main-api","{\"type\":\"object\",\"properties\":{\"totalSelectableItems\":{\"type\":\"number\"},\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"lastName\":{\"default\":\"\",\"type\":\"string\"},\"tokenStatus\":{\"type\":\"string\",\"enum\":[\"issued\",\"not-issued\"]},\"kycStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"processing\",\"updates-required\",\"manual-review\",\"verified\",\"rejected\",\"expired\"]},\"subscriptionAgreementStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"requested\",\"sent\",\"signed\",\"confirmed\",\"rejected\",\"pre-signed\"]},\"accreditedStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"pending\",\"confirmed\",\"rejected\",\"no\",\"no-accepted\"]},\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"entityName\":{\"type\":\"string\"},\"countryCode\":{\"format\":\"countryCode\",\"type\":\"string\"},\"investorStatus\":{\"description\":\"if not 'ready' this investor is in process\",\"type\":\"string\",\"enum\":[\"ready\",\"no-kyc\",\"no-accredited\",\"no-subscription-agreement\",\"no-funded\"]},\"percentage\":{\"type\":\"number\"},\"isLocked\":{\"type\":\"boolean\"},\"tokens\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"email\":{\"format\":\"email\",\"type\":\"string\"},\"bonusTokens\":{\"type\":\"number\"},\"amountFunded\":{\"type\":\"number\"},\"fullName\":{\"type\":\"string\",\"example\":\"Itai Raz\"},\"tokensAssigned\":{\"type\":\"number\"},\"totalOmnibusTokens\":{\"type\":\"number\"},\"labels\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"firstName\":{\"default\":\"\",\"type\":\"string\"},\"walletRegistrationStatus\":{\"type\":\"string\"},\"roundIds\":{\"type\":\"array\",\"items\":{\"type\":\"number\"}},\"name\":{\"description\":\"In case the user is indevidual, it will be full name, in case user is entity it will be the company's name\",\"type\":\"string\",\"example\":\"Apple\"},\"registrationSource\":{\"type\":\"string\"},\"stateCode\":{\"format\":\"stateCode\",\"type\":\"string\"},\"walletAddress\":{\"format\":\"blockchainWalletAddress\",\"type\":\"string\"},\"amountPledged\":{\"type\":\"number\"},\"tokenInTreasury\":{\"type\":\"number\"},\"investorType\":{\"type\":\"string\",\"enum\":[\"individual\",\"entity\",\"custodian-wallet-manager\",\"fbo-individual\",\"fbo-entity\"]}}}}}}",  parameters);
//    }

}
