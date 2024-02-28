package com.ultrasoft.ultracomplaint.entity;

import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

@Entity(name = "Tbl_Admin_Users")
@DynamicUpdate
@DynamicInsert
public class Tbl_Admin_Users {

    @Id
    private String adminId;

    @Column(nullable = false, unique = true, length = 30)
    private String emailId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "phoneNumber", length = 12, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    private ActiveStaus activeStatus;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public static Comparator<Tbl_Admin_Users> compareByCreatedDate(){
        return Comparator.comparing(Tbl_Admin_Users::getCreatedDate);
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
}
