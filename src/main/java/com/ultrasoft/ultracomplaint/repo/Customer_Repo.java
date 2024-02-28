package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.Tbl_Complaints;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Customer_Repo extends JpaRepository<Tbl_Customer_details, String> {

    Tbl_Customer_details findByCustomerMobile(String customerMobile);

    Tbl_Customer_details findByCustomerMobileAndPassword(String customerMobile, String password);

    Tbl_Customer_details findByCustomerId(String custId);

    Tbl_Customer_details findByCustomerEmail(String customerEmail);

    List<Tbl_Customer_details> findAllByOrderByCreatedDateDesc();
}
