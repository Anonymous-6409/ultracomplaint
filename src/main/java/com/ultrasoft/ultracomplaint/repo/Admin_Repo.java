package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.Tbl_Admin_Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Admin_Repo extends JpaRepository<Tbl_Admin_Users , String> {

    Tbl_Admin_Users findByAdminIdAndPassword(String adminId, String password);

    Tbl_Admin_Users findByPhoneNumberAndPassword(String phoneNumber, String password);

    Tbl_Admin_Users findByAdminId(String adminId);

    Tbl_Admin_Users findByPhoneNumber(String phoneNumber);

    Tbl_Admin_Users findByEmailId(String emailId);

    List<Tbl_Admin_Users> findAllByOrderByCreatedDateDesc();
}
