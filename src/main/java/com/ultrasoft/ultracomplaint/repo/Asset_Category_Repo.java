package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Asset_Category_Repo extends JpaRepository<Tbl_Asset_Category, String> {

    Tbl_Asset_Category findByAssetCategoryName(String assetCategoryName);

    Tbl_Asset_Category findById(long id);

    List<Tbl_Asset_Category> findAllByOrderByCreatedDateDesc();
}
