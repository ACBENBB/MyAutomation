package io.securitize.tests.ats.properties;

public class AssertionErrors {
    private AssertionErrors(){
        //Hides the implicit public constructor
    }
    public static final String ITS_NOT_A_SELL_ORDER = "Its not a sell order";
    public static final String ITS_NOT_A_BUY_ORDER = "Its not a buy order";
    public static final String ORDER_PRICE_DOES_NOT_MATCH = "Order price does not match";
    public static final String ORDER_QUANTITY_DOES_NOT_MATCH = "Order quantity does not match";
    public static final String ORDER_STATUS_DOES_NOT_MATCH = "Order status does not match";
    public static final String FILLED_DOES_NOT_MATCH = "Filled does not match";
    public static final String ORDER_ID_DOES_NOT_MATCH = "The orderId does not match";

}
