package com.ultrasoft.ultracomplaint.responsebody;

import com.ultrasoft.ultracomplaint.enums.ActiveStaus;

public class Customer_Response_Body {

    private String customerId;

    private String customerMobile;

    private String customerName;

    private String customerEmail;

    private ActiveStaus activeStatus;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public ActiveStaus getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(ActiveStaus activeStatus) {
        this.activeStatus = activeStatus;
    }
}
