package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Asset_Details_Repo extends JpaRepository<Tbl_Asset_Details, String> {

    List<Tbl_Asset_Details> findByCustomerDetails(Tbl_Customer_details customerDetails);

    List<Tbl_Asset_Details> findByAssetCategoryAndCustomerDetails(Tbl_Asset_Category category,Tbl_Customer_details customerDetails);

    List<Tbl_Asset_Details> findByAssetTypeAndCustomerDetails(Tbl_Asset_Type assetType,Tbl_Customer_details customerDetails);

    List<Tbl_Asset_Details> findByAssetStatusAndCustomerDetails(Tbl_Asset_Status status,Tbl_Customer_details customerDetails);

    Tbl_Asset_Details findByAssetId(String assetId);

    List<Tbl_Asset_Details> findByAssetCategory(String categoryId);

    List<Tbl_Asset_Details> findByAssetType(Tbl_Asset_Type assetType);

    List<Tbl_Asset_Details> findByAssetStatus(Tbl_Asset_Status status);

	Tbl_Asset_Details findByCustomerDetails(String customerId);

	List<Tbl_Asset_Details> findByAssetCategory(Tbl_Asset_Category data);
}
