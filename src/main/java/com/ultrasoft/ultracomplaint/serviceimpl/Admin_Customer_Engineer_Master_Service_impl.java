package com.ultrasoft.ultracomplaint.serviceimpl;

import com.ultrasoft.ultracomplaint.entity.Tbl_Admin_Users;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import com.ultrasoft.ultracomplaint.enums.FirstTimeLogin;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.mapper.Admin_Customer_Employee_Mapper;
import com.ultrasoft.ultracomplaint.repo.Admin_Repo;
import com.ultrasoft.ultracomplaint.repo.Customer_Repo;
import com.ultrasoft.ultracomplaint.repo.Engineer_Repo;
import com.ultrasoft.ultracomplaint.requestbody.*;
import com.ultrasoft.ultracomplaint.responsebody.Admin_Response_Body;
import com.ultrasoft.ultracomplaint.responsebody.Engineer_Response_Body;
import com.ultrasoft.ultracomplaint.service.Admin_Customer_Engineer_Master_Service;
import com.ultrasoft.ultracomplaint.service.Login_Session_Service;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class Admin_Customer_Engineer_Master_Service_impl implements Admin_Customer_Engineer_Master_Service {

    private final Admin_Repo adminRepo;

    private final Engineer_Repo engRepo;

    private final Customer_Repo custRepo;

    private final Login_Session_Service session;

    public Admin_Customer_Engineer_Master_Service_impl(Admin_Repo adminRepo,
                                                       Engineer_Repo engRepo,
                                                       Customer_Repo custRepo,
                                                       Login_Session_Service session) {
        this.adminRepo = adminRepo;
        this.engRepo = engRepo;
        this.custRepo = custRepo;
        this.session = session;
    }

    @Override
    public Admin_Response_Body createNewAdmin(Admin_Request_Body data, String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (data.getName()==null || data.getName().equals("")){
                throw new Exception("Name is required");
            }
            if (data.getPhoneNumber()==null || data.getPhoneNumber().equals("")){
                throw new Exception("Mobile number is required");
            }
            Tbl_Admin_Users user = adminRepo.findByPhoneNumber(data.getPhoneNumber());
            if (user!=null){
                throw new Exception("Mobile number already exists");
            }
            if (data.getEmailId()!=null){
                Tbl_Admin_Users admin = adminRepo.findByEmailId(data.getEmailId());
                if (admin!=null){
                    throw new Exception("Email already exists");
                }
            }
            Tbl_Admin_Users users = adminRepo.save(Admin_Customer_Employee_Mapper.todbAdmin(data,
                    adminRepo.count()));
            return Admin_Customer_Employee_Mapper.toShowAdmin(users);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Customer_details createNewCustomer(Customer_Request_Body data, String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (data.getCustomerState()==null){
                throw new Exception("State must not be null");
            }
            if (data.getAddress1()==null){
                throw new Exception("Address must not be null");
            }
            if (data.getPinCode()==null || data.getPinCode().equals("") || data.getPinCode().length()!=6){
                throw new Exception("Invalid Pin code");
            }
            if (data.getCompanyName()==null || data.getCompanyName().equals("")){
                throw new Exception("Company Name is required");
            }
            if (data.getCustomerCity()==null || data.getCustomerCity().equals("")){
                throw new Exception("City is required");
            }
            Tbl_Customer_details customerDetails = custRepo.findByCustomerMobile(data.getCustomerMobile());
            if (customerDetails != null){
                throw new Exception("Mobile number already exists.");
            }
            if (data.getCustomerEmail()!=null && !data.getCustomerEmail().equals("")){
                Tbl_Customer_details customerDetails2 = custRepo.findByCustomerEmail(data.getCustomerEmail());
                if (customerDetails2!=null){
                    throw new Exception("Email already exists");
                }
            }
            Tbl_Customer_details customerDetails1 = Admin_Customer_Employee_Mapper.todbCust(data,
                    custRepo.count());
            custRepo.save(customerDetails1);
            return customerDetails1;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Engineer_Response_Body createNewEngineer(Engineer_Request_Body data, String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (data.getEngineerName()==null || data.getEngineerName().equals("")){
                throw new Exception("Engineer name is required");
            }
            if (data.getEngineerMobile()==null || data.getEngineerMobile().equals("")){
                throw new Exception("Engineer mobile is required");
            }
            Tbl_Engineer_Details user = engRepo.findByEngineerMobile(data.getEngineerMobile());
            if (user!=null){
                throw new Exception("Mobile number already exists");
            }
            Tbl_Engineer_Details user1 = engRepo.findByEngineerEmailId(data.getEngineerEmailId());
            if (user1!=null){
                throw new Exception("Email already exists");
            }
            Tbl_Engineer_Details users = engRepo.save(Admin_Customer_Employee_Mapper.todbEng(data,
                    engRepo.count()));
            return Admin_Customer_Employee_Mapper.toShowEng(users);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Admin_Response_Body> getAllAdminUser(String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            List<Tbl_Admin_Users> users = adminRepo.findAllByOrderByCreatedDateDesc();
//            Collections.sort(users, Tbl_Admin_Users.compareByCreatedDate().reversed());
            if(users.isEmpty()){
                throw new Exception("No record found");
            }
            else{
                return Admin_Customer_Employee_Mapper.toListAdmin(users);
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Customer_details> getAllCustomer(String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            List<Tbl_Customer_details> users = custRepo.findAllByOrderByCreatedDateDesc();
//            Collections.sort(users, Tbl_Customer_details.compareByCreatedDate().reversed());
            if(users.isEmpty()){
                throw new Exception("No record found");
            }
            else{
                return users;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Engineer_Details> getAllEngineer(String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            List<Tbl_Engineer_Details> users = engRepo.findAllByOrderByCreatedDateDesc();
//            Collections.sort(users, Tbl_Engineer_Details.compareByCreatedDate().reversed());
            if(users.isEmpty()){
                throw new Exception("No record found");
            }
            else{
                return users;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void blockAdmin_Customer_Engineer(UserRoles role, String id, String authToken, ActiveStaus status) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if(role.equals(UserRoles.ADMIN)){
                Tbl_Admin_Users admin  = adminRepo.findByAdminId(id);
                if(admin != null){
                    admin.setActiveStatus(status);
                    adminRepo.save(admin);
                }
                else{
                    throw new Exception("Invalid Admin Id");
                }
            }
            else if(role.equals(UserRoles.CUSTOMER)){
                Tbl_Customer_details admin  = custRepo.findByCustomerId(id);
                if(admin != null){
                    admin.setActiveStatus(status);
                    custRepo.save(admin);
                }
                else{
                    throw new Exception("Invalid Admin Id");
                }

            }
            else if(role.equals(UserRoles.ENGINEER)){
                Tbl_Engineer_Details admin  = engRepo.findByEngineerId(id);
                if(admin != null){
                    admin.setActiveStatus(status);
                    engRepo.save(admin);
                }
                else{
                    throw new Exception("Invalid Admin Id");
                }

            }
            else{
                throw new Exception("Invalid role");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Admin_Users changePassword(String authToken, String currentPassword, String newPassword) throws Exception {
        try {
            Tbl_Admin_Users adminUsers = session.getSessionForAdmin(authToken);
            if (adminUsers.getPassword().equals(Sha256PasswordHash.generateSHA256Hash(currentPassword))){
                if (newPassword.length()<8){
                    throw new Exception("Password must contain at least 8 characters");
                }
                else if (newPassword.length()>16){
                    throw new Exception("Password must not contain more than 16 characters");
                }
                adminUsers.setPassword(Sha256PasswordHash.generateSHA256Hash(newPassword));
                adminRepo.save(adminUsers);
                return adminUsers;
            }
            else {
                throw new Exception("Invalid current password");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Customer_details updateCustomer(Customer_Request_Body data, String authToken, String customerId) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (data.getCustomerState()==null){
                throw new Exception("State must not be null");
            }
            if (data.getAddress1()==null){
                throw new Exception("Address must not be null");
            }
            if (data.getPinCode()==null || data.getPinCode().equals("") || data.getPinCode().length()!=6){
                throw new Exception("Invalid Pin code");
            }
            if (data.getCompanyName()==null || data.getCompanyName().equals("")){
                throw new Exception("Company Name is required");
            }
            if (data.getCustomerCity()==null || data.getCustomerCity().equals("")){
                throw new Exception("City is required");
            }
            Tbl_Customer_details cust = custRepo.findByCustomerId(customerId);
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
            cust.setUpdatedDate(new Date());
            custRepo.save(cust);
            return cust;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Customer_details getCustomerById(String authToken, String id) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            Tbl_Customer_details customerDetails = custRepo.findByCustomerId(id);
            if (customerDetails==null){
                throw new Exception("Invalid Customer Id");
            }
            return customerDetails;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Object updateAdmin(String authToken, AdminUpdateRequestBody body) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            Tbl_Admin_Users adminUsers = adminRepo.findByAdminId(body.getId());
            if (adminUsers==null){
                throw new Exception("Invalid Admin Id");
            }
            adminUsers.setName(body.getName());
            adminUsers.setEmailId(body.getEmailId());
            adminUsers.setName(body.getName());
            adminUsers.setPhoneNumber(body.getPhoneNumber());
            adminUsers.setUpdatedDate(new Date());
            adminRepo.save(adminUsers);
            return adminUsers;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Object updateEngineer(String authToken, EngineerUpdateRequestBody body) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            Tbl_Engineer_Details engineerDetails = engRepo.findByEngineerId(body.getEngineerId());
            if (engineerDetails==null){
                throw new Exception("Invalid Engineer Id");
            }
            engineerDetails.setEngineerMobile(body.getEngineerMobile());
            engineerDetails.setEngineerName(body.getEngineerName());
            engineerDetails.setEngineerEmailId(body.getEngineerEmailId());
            engineerDetails.setEngineerWorkDiscription(body.getEngineerWorkDiscription());
            engineerDetails.setUpdatedDate(new Date());
            engRepo.save(engineerDetails);
            return body;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
