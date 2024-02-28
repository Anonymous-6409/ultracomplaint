package com.ultrasoft.ultracomplaint.entity;

import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import com.ultrasoft.ultracomplaint.enums.FirstTimeLogin;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

@Entity(name = "Tbl_Customer_details")
@DynamicInsert
@DynamicUpdate
public class Tbl_Customer_details {

    @Id
    private String customerId;

    @Column(name = "gst_number", length = 15)
    private String customerGSTNumber;

    @Column(nullable = false, name = "city")
    private String customerCity;

    @Column(name = "state", nullable = false)
    private String customerState;

    @Column(name = "mobile", nullable = false, length = 10)
    private String customerMobile;

    private String customerName;

    private String customerEmail;

    private ActiveStaus activeStatus;

    private String password;

    @Column(nullable = false)
    private String companyName;

    @Column(name = "address1", nullable = false)
    private String address1;

    private String address2;

    @Column(name = "pinCode")
    private String pinCode;

    private FirstTimeLogin isFirstTimeLogin;

    @Column(name = "notificationId", nullable = true)
    private String notificationId;

    @Column(name= "createddate", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name= "updateddate")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public FirstTimeLogin getIsFirstTimeLogin() {
        return isFirstTimeLogin;
    }

    public void setIsFirstTimeLogin(FirstTimeLogin isFirstTimeLogin) {
        this.isFirstTimeLogin = isFirstTimeLogin;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerGSTNumber() {
        return customerGSTNumber;
    }

    public void setCustomerGSTNumber(String customerGSTNumber) {
        this.customerGSTNumber = customerGSTNumber;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public static Comparator<Tbl_Customer_details> compareByCreatedDate(){
        return Comparator.comparing(Tbl_Customer_details::getCreatedDate);
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
}
