package com.ultrasoft.ultracomplaint.serviceimpl;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Category;
import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import com.ultrasoft.ultracomplaint.repo.Asset_Category_Repo;
import com.ultrasoft.ultracomplaint.requestbody.AssetCategoryRequestBody;
import com.ultrasoft.ultracomplaint.service.Asset_Category_Service;
import com.ultrasoft.ultracomplaint.service.Login_Session_Service;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class Asset_Category_Service_impl implements Asset_Category_Service {

    private final Asset_Category_Repo repo;

    private final Login_Session_Service sessionService;

    public Asset_Category_Service_impl(Asset_Category_Repo repo, Login_Session_Service sessionService) {
        this.repo = repo;
        this.sessionService = sessionService;
    }

    @Override
    public Tbl_Asset_Category createAssetCategory(AssetCategoryRequestBody data, String authToken) throws Exception {
        try {
            sessionService.getSessionForAdmin(authToken);
            Tbl_Asset_Category assetCategory = new Tbl_Asset_Category();
            assetCategory.setCreatedDate(new Date());
            assetCategory.setAssetCategoryName(data.getAssetCategoryName());
            assetCategory.setBlockStatus(ActiveStaus.ACTIVE);
            repo.save(assetCategory);
            return assetCategory;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Asset_Category updateAssetCategory(Tbl_Asset_Category data, String authToken) throws Exception {
        try {
            sessionService.getSessionForAdmin(authToken);
            Tbl_Asset_Category asset = repo.findById(data.getId());
            if(asset != null){
                asset.setAssetCategoryName(data.getAssetCategoryName());
                asset.setUpdatedDate(new Date());
                repo.save(asset);
                return asset;
            }
            else{
                throw new Exception("Invalid asset category Id");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Asset_Category> getAllAssetCategory() throws Exception {
        try {
            List<Tbl_Asset_Category> data = repo.findAllByOrderByCreatedDateDesc();
//            data.sort(Tbl_Asset_Category.compareByUpdatedDate().reversed());
            if(data.isEmpty()){
                throw new Exception("No record found");
            }
            else{
                return data;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Asset_Category blockAssetCategory(String authToken, long id) throws Exception {
        try {
            sessionService.getSessionForAdmin(authToken);
            Tbl_Asset_Category assetCategory = repo.findById(id);
            if (assetCategory==null){
                throw new Exception("Invalid Asset Category Id");
            }
            if (assetCategory.getBlockStatus().equals(ActiveStaus.ACTIVE)){
                assetCategory.setBlockStatus(ActiveStaus.INACTIVE);
            }
            else {
                assetCategory.setBlockStatus(ActiveStaus.ACTIVE);
            }
            repo.save(assetCategory);
            return assetCategory;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
