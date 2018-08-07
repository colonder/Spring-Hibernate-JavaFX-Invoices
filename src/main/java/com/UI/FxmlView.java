package com.UI;

import java.util.ResourceBundle;

public enum FxmlView {

    HOME {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("home.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxmls/home.fxml";
        }
    },
    CUSTOMERS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("customers.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxmls/customers.fxml";
        }
    },
    NEW_CUSTOMER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("new_customer.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxmls/newcustomer.fxml";
        }
    },
    PRODUCTS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("products.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxmls/invoices.fxml";
        }
    },
    NEW_PROUCT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("new_product.fxml");
        }

        @Override
        public String getFxmlFile() {
            return "/fxmls/newproduct.fxml";
        }
    };


    public abstract String getTitle();
    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
