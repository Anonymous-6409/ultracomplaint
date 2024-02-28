package com.ultrasoft.ultracomplaint.service;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Status;
import java.util.List;

public interface Asset_Status_Service {

    Tbl_Asset_Status createAssetStaus(Tbl_Asset_Status data, String authToken) throws Exception;

    Tbl_Asset_Status updateAssetStaus(Tbl_Asset_Status data, String authToken) throws Exception;

    List<Tbl_Asset_Status> getAllAssetStaus() throws Exception;
}
