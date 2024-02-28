package com.ultrasoft.ultracomplaint.mapper;

import com.ultrasoft.ultracomplaint.entity.Tbl_Admin_Users;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import com.ultrasoft.ultracomplaint.enums.FirstTimeLogin;
import com.ultrasoft.ultracomplaint.requestbody.Admin_Request_Body;
import com.ultrasoft.ultracomplaint.requestbody.Customer_Request_Body;
import com.ultrasoft.ultracomplaint.requestbody.Engineer_Request_Body;
import com.ultrasoft.ultracomplaint.responsebody.Admin_Response_Body;
import com.ultrasoft.ultracomplaint.responsebody.Customer_Response_Body;
import com.ultrasoft.ultracomplaint.responsebody.Engineer_Response_Body;
import com.ultrasoft.ultracomplaint.serviceimpl.Sha256PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

public class Admin_Customer_Employee_Mapper {

    public static Tbl_Admin_Users todbAdmin(Admin_Request_Body data, long id) throws NoSuchAlgorithmException {
        Tbl_Admin_Users admin  = new Tbl_Admin_Users();
        admin.setAdminId("ADMIN"+String.valueOf(id + 1));
        admin.setActiveStatus(ActiveStaus.ACTIVE);
        admin.setPassword(Sha256PasswordHash.generateSHA256Hash(data.getPhoneNumber()));
        admin.setName(data.getName());
        admin.setEmailId(data.getEmailId());
        admin.setPhoneNumber(data.getPhoneNumber());
        return admin;
    }

    public static Admin_Response_Body toShowAdmin(Tbl_Admin_Users data){
        Admin_Response_Body res = new Admin_Response_Body();
        res.setActiveStatus(data.getActiveStatus());
        res.setAdminId(data.getAdminId());
        res.setEmailId(data.getEmailId());
        res.setName(data.getName());
        res.setPhoneNumber(data.getPhoneNumber());
        return res;
    }
    public static List<Admin_Response_Body> toListAdmin(List<Tbl_Admin_Users> data){
        return data.stream().map(Admin_Customer_Employee_Mapper::toShowAdmin).collect(Collectors.toList());
    }


    public static Tbl_Engineer_Details todbEng(Engineer_Request_Body data, long id) throws NoSuchAlgorithmException {
        Tbl_Engineer_Details eng = new Tbl_Engineer_Details();
        eng.setEngineerId("ENGINEER"+String.valueOf(id +1));
        eng.setActiveStatus(ActiveStaus.ACTIVE);
        eng.setPassword(Sha256PasswordHash.generateSHA256Hash(data.getEngineerMobile()));
        eng.setEngineerEmailId(data.getEngineerEmailId());
        eng.setEngineerMobile(data.getEngineerMobile());
        eng.setEngineerName(data.getEngineerName());
        eng.setEngineerWorkDiscription(data.getEngineerWorkDiscription());
        return eng;
    }

    public static Engineer_Response_Body toShowEng(Tbl_Engineer_Details data){
        Engineer_Response_Body res = new Engineer_Response_Body();
        res.setActiveStatus(data.getActiveStatus());
        res.setEngineerId(data.getEngineerId());
        res.setEngineerMobile(data.getEngineerMobile());
        res.setEngineerName(data.getEngineerName());
        res.setEngineerWorkDiscription(data.getEngineerWorkDiscription());
        res.setEngineerEmailId(data.getEngineerEmailId());
        return res;
    }
    public static List<Engineer_Response_Body> toListEng(List<Tbl_Engineer_Details> data){
        return data.stream().map(Admin_Customer_Employee_Mapper::toShowEng).collect(Collectors.toList());
    }

    public static Tbl_Customer_details todbCust(Customer_Request_Body data, long id) throws NoSuchAlgorithmException {
        Tbl_Customer_details cust = new Tbl_Customer_details();
        cust.setActiveStatus(ActiveStaus.ACTIVE);
        if (id<9) {
            cust.setCustomerId("C000"+String.valueOf(id +1));
        }
        else if (id<99) {
            cust.setCustomerId("C00"+String.valueOf(id +1));
        }
        else if (id<999) {
            cust.setCustomerId("C0"+String.valueOf(id +1));
        }
        else {
            cust.setCustomerId("C"+String.valueOf(id +1));
        }
        cust.setPassword(Sha256PasswordHash.generateSHA256Hash(data.getCustomerMobile()));
        cust.setCustomerEmail(data.getCustomerEmail());
        cust.setCustomerMobile(data.getCustomerMobile());
        cust.setCustomerName(data.getCustomerName());
        cust.setAddress1(data.getAddress1());
        cust.setAddress2(data.getAddress2());
        cust.setCustomerCity(data.getCustomerCity());
        cust.setCompanyName(data.getCompanyName());
        cust.setCustomerState(data.getCustomerState());
        cust.setCustomerGSTNumber(data.getCustomerGSTNumber());
        cust.setPinCode(data.getPinCode());
        cust.setIsFirstTimeLogin(FirstTimeLogin.YES);
        return cust;
    }
    public static Customer_Response_Body toShowCust(Tbl_Customer_details data){
        Customer_Response_Body res = new Customer_Response_Body();
        res.setCustomerEmail(data.getCustomerEmail());
        res.setCustomerId(data.getCustomerId());
        res.setCustomerMobile(data.getCustomerMobile());
        res.setActiveStatus(data.getActiveStatus());
        res.setCustomerName(data.getCustomerName());
        return res;
    }
    public static List<Customer_Response_Body> toLisCust(List<Tbl_Customer_details> data){
        return data.stream().map(Admin_Customer_Employee_Mapper::toShowCust).collect(Collectors.toList());
    }
}
