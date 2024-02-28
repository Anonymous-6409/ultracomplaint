package com.ultrasoft.ultracomplaint.responsebody;

import com.ultrasoft.ultracomplaint.enums.ActiveStaus;

public class Admin_Response_Body {

    private String adminId;

    private String emailId;

    private String name;

    private String phoneNumber;

    private ActiveStaus activeStatus;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ActiveStaus getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(ActiveStaus activeStatus) {
        this.activeStatus = activeStatus;
    }
}
