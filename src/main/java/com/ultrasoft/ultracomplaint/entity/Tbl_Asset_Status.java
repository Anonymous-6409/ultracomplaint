package com.ultrasoft.ultracomplaint.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

@Entity(name = "Tbl_Asset_Status")
@DynamicUpdate
@DynamicInsert
public class Tbl_Asset_Status {

    @Id
    private String assetStatusId;

    @Column(name = "assetStatusName", nullable = false, unique = true)
    private String assetStatusName;

    @Column(name= "createddate", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name= "updateddate")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public String getAssetStatusId() {
        return assetStatusId;
    }

    public void setAssetStatusId(String assetStatusId) {
        this.assetStatusId = assetStatusId;
    }

    public String getAssetStatusName() {
        return assetStatusName;
    }

    public void setAssetStatusName(String assetStatusName) {
        this.assetStatusName = assetStatusName;
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

    public static Comparator<Tbl_Asset_Status> compareByCreatedDate(){
        return Comparator.comparing(Tbl_Asset_Status::getCreatedDate).reversed();
    }
}
