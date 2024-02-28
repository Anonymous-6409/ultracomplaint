package com.ultrasoft.ultracomplaint.serviceimpl;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Type;
import com.ultrasoft.ultracomplaint.repo.Asset_Type_Repo;
import com.ultrasoft.ultracomplaint.service.Asset_Type_Service;
import com.ultrasoft.ultracomplaint.service.Login_Session_Service;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class Asset_Type_Service_impl implements Asset_Type_Service {

    private final Login_Session_Service sessionService;

    private final Asset_Type_Repo repo;

    public Asset_Type_Service_impl(Login_Session_Service sessionService, Asset_Type_Repo repo) {
        this.sessionService = sessionService;
        this.repo = repo;
    }

    @Override
    public Tbl_Asset_Type createAssetType(Tbl_Asset_Type data, String authToken) throws Exception {
        try {
            sessionService.getSessionForAdmin(authToken);
            data.setAssetTypeId("ASTY"+String.valueOf(repo.count()));
            repo.save(data);
            return data;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Asset_Type updateAssetType(Tbl_Asset_Type data, String authToken) throws Exception {
        try {
            sessionService.getSessionForAdmin(authToken);
            Tbl_Asset_Type assetType = repo.findByAssetTypeId(data.getAssetTypeId());
            if(assetType !=null){
                assetType.setAssetTypeName(data.getAssetTypeName());
                assetType.setUpdatedDate(new Date());
                repo.save(assetType);
                return assetType;
            }
            else{
                throw new Exception("Invalid asset type id");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Asset_Type> getAllAssetType() throws Exception {
        try {
            List<Tbl_Asset_Type> data = repo.findAllByOrderByCreatedDateDesc();
            if(data.isEmpty()){
                throw new Exception("No record found");
            }
            else{
                Collections.sort(data, Tbl_Asset_Type.compareByCreatedDate());
                return data;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
