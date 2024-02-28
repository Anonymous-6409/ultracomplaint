package com.ultrasoft.ultracomplaint.service;

import com.ultrasoft.ultracomplaint.entity.Tbl_Admin_Users;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.entity.Tbl_login_Session;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.requestbody.Login_Request_Body;
import com.ultrasoft.ultracomplaint.utility.EmailTokenPojo;

public interface Login_Session_Service {

    Tbl_login_Session generateSessionAdmin(Login_Request_Body data, String notificationId) throws Exception;

    Tbl_Admin_Users getSessionForAdmin(String authToken) throws Exception;

    Tbl_Customer_details getSessionForCustomer(String authToken) throws Exception;

    Tbl_Engineer_Details getSessionForEng(String authToken) throws Exception;

    void logout(String authToken) throws Exception;

    Object chenagePassword(String email, UserRoles type) throws Exception;

    Object resetPassword(String email, String password, String body) throws Exception;
}
