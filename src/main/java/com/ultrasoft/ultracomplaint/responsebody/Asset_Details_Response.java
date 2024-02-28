package com.ultrasoft.ultracomplaint.responsebody;

import java.util.Date;

public class Asset_Details_Response {
	private String assetId;

    private String CustomerId;

    private String CustomerName;

    private String assetCategoryId;

    private String assetCategoryName;

    private String assetTypeId;

    private String assetTypeName;

    private String assetDetails;

    private String serialNo;

    private String assetStatusId;

    private String assetStatusName;

    private Date expiryDate;

    private Date createdDate;

    private Date updatedDate;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getAssetCategoryId() {
		return assetCategoryId;
	}

	public void setAssetCategoryId(String assetCategoryId) {
		this.assetCategoryId = assetCategoryId;
	}

	public String getAssetCategoryName() {
		return assetCategoryName;
	}

	public void setAssetCategoryName(String assetCategoryName) {
		this.assetCategoryName = assetCategoryName;
	}

	public String getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(String assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
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
    
    
}
