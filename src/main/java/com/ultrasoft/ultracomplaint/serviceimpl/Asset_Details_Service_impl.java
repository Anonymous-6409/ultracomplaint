package com.ultrasoft.ultracomplaint.serviceimpl;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Category;
import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Status;
import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Type;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.mapper.Asset_DetailsMapper;
import com.ultrasoft.ultracomplaint.repo.Asset_Category_Repo;
import com.ultrasoft.ultracomplaint.repo.Asset_Details_Repo;
import com.ultrasoft.ultracomplaint.repo.Asset_Status_Repo;
import com.ultrasoft.ultracomplaint.repo.Asset_Type_Repo;
import com.ultrasoft.ultracomplaint.repo.Customer_Repo;
import com.ultrasoft.ultracomplaint.requestbody.Asset_Details_Request_Body;
import com.ultrasoft.ultracomplaint.responsebody.Asset_Details_Response;
import com.ultrasoft.ultracomplaint.service.Asset_Details_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Asset_Details_Service_impl implements Asset_Details_Service {


	@Autowired
	private Customer_Repo customerRepo;
	
	@Autowired
	private Asset_Category_Repo assetCategoryRepo;
	
	@Autowired
	private Asset_Type_Repo assetTypeRepo;
	
	@Autowired
	private Asset_Status_Repo assetStatusRepo;
	
	@Autowired
	private Asset_Details_Repo assetDetailRepo;

    @Override
    public Tbl_Asset_Details createNewAsset(Asset_Details_Request_Body data) throws Exception {
        try {
        	if (data.getAssetCategoryId() == null) {
        		throw new Exception("Customer Details is Required");
			}
        	if (data.getAssetTypeId() == null) {
        		throw new Exception("Asset Type is Required");
			}
        	if (data.getAssetStatusId()==null) {
        		throw new Exception("Asset Status is Required");
			}
        	if (data.getAssetDetails()==null) {
        		throw new Exception("Asset Details is Required");
			}
        	if (data.getSerialNo()==null) {
        		throw new Exception("Serial Number is Required");
			}
        	Tbl_Customer_details customerDetails = customerRepo.findByCustomerId(data.getCustomerId());
			if(customerDetails==null) {
				throw new Exception("Customer Details not found");
			}
        	Tbl_Asset_Category asset_Category = assetCategoryRepo.findByAssetCategoryName(data.getAssetCategoryName());
			if(asset_Category==null) {
				throw new Exception("Asset Category not found");
			}
        	Tbl_Asset_Status assetStatus = assetStatusRepo.findByAssetStatusName(data.getAssetStatusName());
			if(assetStatus==null) {
				throw new Exception("Asset Status not found");
			}
        	Tbl_Asset_Type assetType = assetTypeRepo.findByAssetTypeId(data.getAssetTypeId());
        	if(assetType==null) {
        		throw new Exception("Asset Type not found");
        	}
			data.setAssetId("ASTD"+(assetDetailRepo.count()+1));
        	Tbl_Asset_Details asset_Details = Asset_DetailsMapper.toDB(data,customerDetails,asset_Category,assetStatus,assetType);
            assetDetailRepo.save(asset_Details);
            
            return asset_Details;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    }

    @Override
    public List<Asset_Details_Request_Body> getAllAsset() throws Exception {
    	try {
			List<Tbl_Asset_Details> data = assetDetailRepo.findAll();
			if (data.isEmpty()) {
				 throw new Exception("No record found");
			}
			else {
				Collections.sort(data, Tbl_Asset_Details.compareByCreatedDate());
			return Asset_DetailsMapper.toList(data);
			}
		}
    	catch (Exception e) 
    	{
			 throw new Exception(e.getMessage());
    	}
    }

    @Override
    public List<Asset_Details_Request_Body> getAllAssetByCustomer(String customerId) throws Exception {
        try {
			Tbl_Customer_details customer = customerRepo.findByCustomerId(customerId);
			if (customer==null) {
				throw new Exception("Invalid id");
			}
			List<Tbl_Asset_Details> data = assetDetailRepo.findByCustomerDetails(customer);
			if (data.isEmpty()) {
				throw new Exception("No record found");
			}
			Collections.sort(data,Tbl_Asset_Details.compareByCreatedDate());
			return Asset_DetailsMapper.toList(data);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    }

    
    @Override
    public List<Asset_Details_Request_Body> getAllAssetByCategory(String categoryId) throws Exception {
		try {
			Tbl_Asset_Category as = assetCategoryRepo.findByAssetCategoryName(categoryId);
			if (as==null) {
				throw new Exception("Invalid id");
			}
			List<Tbl_Asset_Details> data = assetDetailRepo.findByAssetCategory(as);
			if (data == null) {
				throw new Exception("no record found");
			}
			Collections.sort(data, Tbl_Asset_Details.compareByCreatedDate());
			return Asset_DetailsMapper.toList(data);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    }

    @Override
    public List<Asset_Details_Request_Body> getAllAssetByType(String assetType) throws Exception {
        try {
			Tbl_Asset_Type type = assetTypeRepo.findByAssetTypeId(assetType);
			if (type==null) {
    			throw new Exception("Invalid id");
			}
			List<Tbl_Asset_Details> data = assetDetailRepo.findByAssetType(type);
			
			if (data == null) {
				throw new Exception("no record found");
			}
			Collections.sort(data, Tbl_Asset_Details.compareByCreatedDate());
			return Asset_DetailsMapper.toList(data);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    }

    @Override
    public List<Asset_Details_Request_Body> getAllAssetByStatus(String assetStatus) throws Exception {
        try {
			Tbl_Asset_Status status = assetStatusRepo.findByAssetStatusName(assetStatus);
			if (status==null) {
				throw new Exception("Invalid id");
			}
			List<Tbl_Asset_Details> data = assetDetailRepo.findByAssetStatus(status);
			if (data.isEmpty()) {
				throw new Exception("No Record Found");
			}
			Collections.sort(data, Tbl_Asset_Details.compareByCreatedDate());
			return Asset_DetailsMapper.toList(data);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    }

}
