package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Asset_Type_Repo extends JpaRepository<Tbl_Asset_Type, String> {

    Tbl_Asset_Type findByAssetTypeId(String id);

    Tbl_Asset_Type findByAssetTypeName(String assetTypeName);

    List<Tbl_Asset_Type> findAllByOrderByCreatedDateDesc();
}
