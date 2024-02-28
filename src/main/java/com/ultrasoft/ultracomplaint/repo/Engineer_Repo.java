package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Engineer_Repo extends JpaRepository<Tbl_Engineer_Details, String> {

    Tbl_Engineer_Details findByEngineerIdAndPassword(String engId, String password);

    Tbl_Engineer_Details findByEngineerMobileAndPassword(String engineerNumber, String password);

    Tbl_Engineer_Details findByEngineerId(String engId);

    Tbl_Engineer_Details findByEngineerMobile(String engineerMobile);

    Tbl_Engineer_Details findByEngineerEmailId(String emailId);

    List<Tbl_Engineer_Details> findAllByOrderByCreatedDateDesc();
}
