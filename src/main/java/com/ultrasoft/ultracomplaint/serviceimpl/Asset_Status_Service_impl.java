package com.ultrasoft.ultracomplaint.serviceimpl;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Status;
import com.ultrasoft.ultracomplaint.repo.Asset_Status_Repo;
import com.ultrasoft.ultracomplaint.service.Asset_Status_Service;
import com.ultrasoft.ultracomplaint.service.Login_Session_Service;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class Asset_Status_Service_impl implements Asset_Status_Service {

    private final Login_Session_Service sessionService;

    private final Asset_Status_Repo repo;

    public Asset_Status_Service_impl(Login_Session_Service sessionService, Asset_Status_Repo repo) {
        this.sessionService = sessionService;
        this.repo = repo;
    }

    @Override
    public Tbl_Asset_Status createAssetStaus(Tbl_Asset_Status data, String authToken) throws Exception {
        try {
            sessionService.getSessionForAdmin(authToken);
            data.setAssetStatusId("ASTS"+String.valueOf(repo.count()));
            data.setCreatedDate(new Date());
            repo.save(data);
            return data;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Asset_Status updateAssetStaus(Tbl_Asset_Status data, String authToken) throws Exception {
        try {
            sessionService.getSessionForAdmin(authToken);
            Tbl_Asset_Status asset = repo.findByAssetStatusName(data.getAssetStatusName());
            if(asset != null){
                asset.setAssetStatusName(data.getAssetStatusName());
                asset.setUpdatedDate(new Date());
                repo.save(asset);
                return asset;
            }
            else{
                throw new Exception("Invalid asset status Id");
            }

        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Asset_Status> getAllAssetStaus() throws Exception {
        try {
            List<Tbl_Asset_Status> data = repo.findAllByOrderByCreatedDateDesc();
            if(data.isEmpty()){
                throw new Exception("No record found");
            }
            else{
//                Collections.sort(data, Tbl_Asset_Status.compareByCreatedDate());
                return data;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
