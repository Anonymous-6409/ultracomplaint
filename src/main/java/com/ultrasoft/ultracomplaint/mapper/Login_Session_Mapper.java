package com.ultrasoft.ultracomplaint.mapper;

import com.ultrasoft.ultracomplaint.entity.Tbl_Admin_Users;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.entity.Tbl_login_Session;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.responsebody.Login_Response_Body;
import java.time.LocalDateTime;
import java.util.UUID;

public class Login_Session_Mapper {

    public static Tbl_login_Session loginAsAdmin(Tbl_Admin_Users data){
//        System.out.println("ADMINNNNNN");
        String uuid = UUID.randomUUID().toString();
//        System.out.println(uuid);
        Tbl_login_Session session = new Tbl_login_Session();
        session.setAdminCustomerEngineerId(data.getAdminId());
        session.setAdmin(data);
        session.setAuthToken(uuid);
        session.setLoginDateTime(LocalDateTime.now());
        session.setRole(UserRoles.ADMIN);
        return session;
    }

    public static Tbl_login_Session loginAsEmloyee(Tbl_Engineer_Details data){
        Tbl_login_Session session = new Tbl_login_Session();
        session.setAdminCustomerEngineerId(data.getEngineerId());
        session.setEngineer(data);
        session.setAuthToken(UUID.randomUUID().toString());
        session.setLoginDateTime(LocalDateTime.now());
        session.setRole(UserRoles.ENGINEER);
        return session;
    }

    public static Tbl_login_Session loginAsCustomer(Tbl_Customer_details data){
        Tbl_login_Session session = new Tbl_login_Session();
        session.setAdminCustomerEngineerId(data.getCustomerId());
        session.setCustomer(data);
        session.setAuthToken(UUID.randomUUID().toString());
        session.setLoginDateTime(LocalDateTime.now());
        session.setRole(UserRoles.CUSTOMER);
        return session;
    }

    public static Login_Response_Body toShow(Tbl_login_Session data){
        Login_Response_Body session = new Login_Response_Body();
        session.setAuthToken(data.getAuthToken());
        session.setLoginDateTime(LocalDateTime.now());
        session.setRole(data.getRole());
        if(data.getRole().equals(UserRoles.ADMIN)){
            session.setName(data.getAdmin().getName());
        }
        if(data.getRole().equals(UserRoles.CUSTOMER)){
            session.setName(data.getCustomer().getCustomerName());
        }
        if(data.getRole().equals(UserRoles.ENGINEER)){
            session.setName(data.getEngineer().getEngineerName());
        }
        return session;
    }
}
