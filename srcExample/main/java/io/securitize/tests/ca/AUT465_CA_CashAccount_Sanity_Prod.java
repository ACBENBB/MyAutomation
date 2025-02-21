package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.annotations.Test;

public class AUT465_CA_CashAccount_Sanity_Prod extends AbstractCashAccount {

    @SkipTestOnEnvironments(environments = {"rc","sandbox"})

    @Test(description = "AUT465_CA_CashAccount_Sanity_Prod", groups = {"sanityCA"})
    public void AUT465_CA_CashAccount_Sanity_Prod_test() {
        cashAccountSanityFlowProd(
                Users.getProperty(UsersProperty.ca_investorMail_sanity_flow_prod_aut465),
                Users.getProperty(UsersProperty.ca_investorPass_sanity_flow_prod_aut465));

    }
}