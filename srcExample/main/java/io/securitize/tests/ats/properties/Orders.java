package io.securitize.tests.ats.properties;

public class Orders {

    public enum FE_STATUS {
        CANCELLED("Cancelled"),
        REJECTED("Rejected"),
        DONE("Done");

        private String status;

        FE_STATUS(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }

    public enum DB_STATUS {
        CANCELED("Canceled"),
        REJECTED("Rejected"),
        OPEN("Open");

        private String status;

        DB_STATUS(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }

    public enum PROPERTIES {
        orderId, referenceNumber, orderStatus, previousOrderStatus, security, side, amount, price, type, expirationDays, previousStatus
    }

    public enum SIDE {
        B, S
    }

    public enum STATUS {
        Canceled, Closed, Executed, Open, Rejected
    }

}
