package com.ultrasoft.ultracomplaint.mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Category;
import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Status;
import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Type;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.requestbody.Asset_Details_Request_Body;
import com.ultrasoft.ultracomplaint.responsebody.Asset_Details_Response;

public class Asset_DetailsMapper {

	public static Tbl_Asset_Details toDB(Asset_Details_Request_Body body,Tbl_Customer_details customerDetails,
			Tbl_Asset_Category asset_Category, Tbl_Asset_Status assetStatus, Tbl_Asset_Type assetType) {
		
		Tbl_Asset_Details asset_Details = new Tbl_Asset_Details();
		asset_Details.setCustomerDetails(customerDetails);
		asset_Details.setAssetCategory(asset_Category);
		asset_Details.setAssetStatus(assetStatus);
		asset_Details.setAssetType(assetType);
		asset_Details.setAssetId(body.getAssetId());
//		asset_Details.setAssetDetails();
		asset_Details.setSerialNo(body.getSerialNo());
		asset_Details.setExpiryDate(body.getExpiryDate());
		asset_Details.setCreatedDate(new Date());

		return asset_Details;
		
	}
	
	public static List<Asset_Details_Request_Body> toList(List<Tbl_Asset_Details> data){
        return data.stream().map(Asset_DetailsMapper::toShow).collect(Collectors.toList());
    }
	
	public static Asset_Details_Request_Body toShow(Tbl_Asset_Details asset_Details) {
		Asset_Details_Request_Body response = new Asset_Details_Request_Body();
        response.setAssetId(asset_Details.getAssetId());
        response.setCustomerId(asset_Details.getCustomerDetails().getCustomerId());
        response.setCustomerName(asset_Details.getCustomerDetails().getCustomerName());
        response.setAssetCategoryName(asset_Details.getAssetCategory().getAssetCategoryName());
        response.setAssetTypeId(asset_Details.getAssetType().getAssetTypeId());
        response.setAssetTypeName(asset_Details.getAssetType().getAssetTypeName());
        response.setAssetDetails(asset_Details.getAssetDetails());
        response.setSerialNo(asset_Details.getSerialNo());
        response.setAssetStatusId(asset_Details.getAssetStatus().getAssetStatusId());
        response.setAssetStatusName(asset_Details.getAssetStatus().getAssetStatusName());
        response.setExpiryDate(asset_Details.getExpiryDate());
        response.setCreatedDate(asset_Details.getCreatedDate());
        response.setUpdatedDate(asset_Details.getUpdatedDate());
    	
        return response;
	}
	
}
