package com.ultrasoft.ultracomplaint.service;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Type;
import java.util.List;

public interface Asset_Type_Service {

    Tbl_Asset_Type createAssetType(Tbl_Asset_Type data, String authToken) throws Exception;

    Tbl_Asset_Type updateAssetType(Tbl_Asset_Type data, String authToken) throws Exception;

    List<Tbl_Asset_Type> getAllAssetType() throws Exception;
}
