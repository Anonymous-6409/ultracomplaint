package com.ultrasoft.ultracomplaint.entity;

import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Comparator;
import java.util.Date;

@Entity(name = "Tbl_Asset_Details")
@DynamicUpdate
@DynamicInsert
public class Tbl_Asset_Details {

    @Id
    private String assetId;

    @ManyToOne(fetch = FetchType.LAZY,cascade =  CascadeType.ALL,
            optional = true)
    @Fetch(value= FetchMode.SELECT)
    private Tbl_Customer_details customerDetails;

    @ManyToOne(fetch = FetchType.LAZY,cascade =  CascadeType.ALL,
            optional = true)
    @Fetch(value= FetchMode.SELECT)
    private Tbl_Asset_Category assetCategory;

    @ManyToOne(fetch = FetchType.LAZY,cascade =  CascadeType.ALL,
            optional = true)
    @Fetch(value= FetchMode.SELECT)
    private Tbl_Asset_Type assetType;

    private String assetDetails;

    private String serialNo;

    @ManyToOne(fetch = FetchType.LAZY,cascade =  CascadeType.ALL,
            optional = true)
    @Fetch(value= FetchMode.SELECT)
    private Tbl_Asset_Status assetStatus;

    private Date expiryDate;

    @Column(name= "createddate", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name= "updateddate")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Tbl_Customer_details getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(Tbl_Customer_details customerDetails) {
        this.customerDetails = customerDetails;
    }

    public Tbl_Asset_Category getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(Tbl_Asset_Category assetCategory) {
        this.assetCategory = assetCategory;
    }

    public Tbl_Asset_Type getAssetType() {
        return assetType;
    }

    public void setAssetType(Tbl_Asset_Type assetType) {
        this.assetType = assetType;
    }

    public String getAssetDetails() {
        return assetDetails;
    }

    public void setAssetDetails(String assetDetails) {
        this.assetDetails = assetDetails;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Tbl_Asset_Status getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Tbl_Asset_Status assetStatus) {
        this.assetStatus = assetStatus;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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

	@Override
	public String toString() {
		return "Tbl_Asset_Details [assetId=" + assetId + ", customerDetails=" + customerDetails + ", assetCategory="
				+ assetCategory + ", assetType=" + assetType + ", assetDetails=" + assetDetails + ", serialNo="
				+ serialNo + ", assetStatus=" + assetStatus + ", expiryDate=" + expiryDate + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + "]";
	}

    public static Comparator<Tbl_Asset_Details> compareByCreatedDate(){
        return Comparator.comparing(Tbl_Asset_Details::getCreatedDate).reversed();
    }
    
}
