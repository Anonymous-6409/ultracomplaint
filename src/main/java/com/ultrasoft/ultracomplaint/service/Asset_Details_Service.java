package com.ultrasoft.ultracomplaint.service;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Details;
import com.ultrasoft.ultracomplaint.requestbody.Asset_Details_Request_Body;
import com.ultrasoft.ultracomplaint.responsebody.Asset_Details_Response;

import java.util.List;

public interface Asset_Details_Service {

	Tbl_Asset_Details createNewAsset(Asset_Details_Request_Body data) throws Exception;

    List<Asset_Details_Request_Body> getAllAsset() throws Exception;

    List<Asset_Details_Request_Body> getAllAssetByCustomer(String customerId) throws Exception;

    List<Asset_Details_Request_Body> getAllAssetByCategory(String categoryId) throws Exception;

    List<Asset_Details_Request_Body> getAllAssetByType(String assetType) throws Exception;

    List<Asset_Details_Request_Body> getAllAssetByStatus(String assetStatus) throws Exception;
}
