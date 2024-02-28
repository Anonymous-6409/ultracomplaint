package com.ultrasoft.ultracomplaint.entity;

import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

@Entity(name = "Tbl_Engineer_Details")
@DynamicUpdate
@DynamicInsert
public class Tbl_Engineer_Details {

    @Id
    private String engineerId;

    private String engineerEmailId;

    @Column(name = "engineerMobile", nullable = false, unique = true)
    private String engineerMobile;

    private String engineerName;

    private String engineerWorkDiscription;

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

    public String getEngineerEmailId() {
        return engineerEmailId;
    }

    public void setEngineerEmailId(String engineerEmailId) {
        this.engineerEmailId = engineerEmailId;
    }

    public String getEngineerMobile() {
        return engineerMobile;
    }

    public void setEngineerMobile(String engineerMobile) {
        this.engineerMobile = engineerMobile;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getEngineerWorkDiscription() {
        return engineerWorkDiscription;
    }

    public void setEngineerWorkDiscription(String engineerWorkDiscription) {
        this.engineerWorkDiscription = engineerWorkDiscription;
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

    public String getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(String engineerId) {
        this.engineerId = engineerId;
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

    public static Comparator<Tbl_Engineer_Details> compareByCreatedDate(){
        return Comparator.comparing(Tbl_Engineer_Details::getCreatedDate);
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
}
