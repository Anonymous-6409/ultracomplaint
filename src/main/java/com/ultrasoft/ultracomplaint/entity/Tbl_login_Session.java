package com.ultrasoft.ultracomplaint.entity;

import com.ultrasoft.ultracomplaint.enums.UserRoles;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "Tbl_login_Session")
@DynamicUpdate
@DynamicInsert
public class Tbl_login_Session {

    @Id
    private String adminCustomerEngineerId;

    private String authToken;

    private LocalDateTime loginDateTime;

    private UserRoles role;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @Fetch(value=FetchMode.SELECT)
    private Tbl_Admin_Users admin;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @Fetch(value=FetchMode.SELECT)
    private Tbl_Customer_details customer;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @Fetch(value=FetchMode.SELECT)
    private Tbl_Engineer_Details engineer;

    @Column(name= "createddate", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name= "updateddate")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public String getAdminCustomerEngineerId() {
        return adminCustomerEngineerId;
    }

    public void setAdminCustomerEngineerId(String adminCustomerEngineerId) {
        this.adminCustomerEngineerId = adminCustomerEngineerId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public LocalDateTime getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(LocalDateTime loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Tbl_Admin_Users getAdmin() {
        return admin;
    }

    public void setAdmin(Tbl_Admin_Users admin) {
        this.admin = admin;
    }

    public Tbl_Customer_details getCustomer() {
        return customer;
    }

    public void setCustomer(Tbl_Customer_details customer) {
        this.customer = customer;
    }

    public Tbl_Engineer_Details getEngineer() {
        return engineer;
    }

    public void setEngineer(Tbl_Engineer_Details engineer) {
        this.engineer = engineer;
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
}
