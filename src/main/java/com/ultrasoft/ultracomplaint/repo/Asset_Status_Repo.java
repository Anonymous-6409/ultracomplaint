package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Asset_Status_Repo extends JpaRepository<Tbl_Asset_Status, String> {

    Tbl_Asset_Status findByAssetStatusName(String name);

    List<Tbl_Asset_Status> findAllByOrderByCreatedDateDesc();
}
