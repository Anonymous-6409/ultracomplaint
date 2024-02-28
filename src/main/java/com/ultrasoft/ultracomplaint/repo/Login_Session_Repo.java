package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.Tbl_login_Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Login_Session_Repo extends JpaRepository<Tbl_login_Session , String> {

    Tbl_login_Session findByAuthToken(String authToke);
}
