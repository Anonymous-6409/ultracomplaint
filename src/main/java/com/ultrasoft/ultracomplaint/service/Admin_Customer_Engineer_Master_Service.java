package com.ultrasoft.ultracomplaint.service;

import com.ultrasoft.ultracomplaint.entity.Tbl_Admin_Users;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.requestbody.*;
import com.ultrasoft.ultracomplaint.responsebody.Admin_Response_Body;
import com.ultrasoft.ultracomplaint.responsebody.Engineer_Response_Body;

import java.util.List;

public interface Admin_Customer_Engineer_Master_Service {

    Admin_Response_Body createNewAdmin(Admin_Request_Body data , String authToken) throws Exception;

    Tbl_Customer_details createNewCustomer(Customer_Request_Body data, String authToken) throws Exception;

    Engineer_Response_Body createNewEngineer(Engineer_Request_Body data, String authToken) throws Exception;

    List<Admin_Response_Body> getAllAdminUser(String authToken) throws Exception;

    List<Tbl_Customer_details> getAllCustomer(String authToken) throws Exception;

    List<Tbl_Engineer_Details> getAllEngineer(String authToken) throws Exception;

    void blockAdmin_Customer_Engineer(UserRoles role, String id, String authToken, ActiveStaus action) throws Exception;

    Tbl_Admin_Users changePassword(String authToken, String currentPassword, String newPassword) throws Exception;

    Tbl_Customer_details updateCustomer(Customer_Request_Body data, String authToken, String customerId) throws Exception;

    Tbl_Customer_details getCustomerById(String authToken, String id) throws Exception;

    Object updateAdmin(String authToken, AdminUpdateRequestBody body) throws Exception;

    Object updateEngineer(String authToken, EngineerUpdateRequestBody body) throws Exception;
}
