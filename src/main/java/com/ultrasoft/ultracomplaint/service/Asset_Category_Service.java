package com.ultrasoft.ultracomplaint.service;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Category;
import com.ultrasoft.ultracomplaint.requestbody.AssetCategoryRequestBody;

import java.util.List;

public interface Asset_Category_Service {

    Tbl_Asset_Category createAssetCategory(AssetCategoryRequestBody data, String authToken) throws Exception;

    Tbl_Asset_Category updateAssetCategory(Tbl_Asset_Category data, String authToken) throws Exception;

    List<Tbl_Asset_Category> getAllAssetCategory() throws Exception;

    Tbl_Asset_Category blockAssetCategory(String authToken, long id) throws Exception;
}
