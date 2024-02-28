package com.ultrasoft.ultracomplaint.serviceimpl;

import com.google.gson.Gson;
import com.ultrasoft.ultracomplaint.entity.Tbl_Admin_Users;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.entity.Tbl_login_Session;
import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.mapper.Login_Session_Mapper;
import com.ultrasoft.ultracomplaint.repo.Admin_Repo;
import com.ultrasoft.ultracomplaint.repo.Customer_Repo;
import com.ultrasoft.ultracomplaint.repo.Engineer_Repo;
import com.ultrasoft.ultracomplaint.repo.Login_Session_Repo;
import com.ultrasoft.ultracomplaint.requestbody.Login_Request_Body;
import com.ultrasoft.ultracomplaint.service.Login_Session_Service;
import com.ultrasoft.ultracomplaint.utility.EmailTokenPojo;
import com.ultrasoft.ultracomplaint.utility.GenerateEncryptedToken;
import com.ultrasoft.ultracomplaint.utility.SendEmailTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
public class Login_Session_Service_Impl implements Login_Session_Service {

    private final Login_Session_Repo session;

    private final Admin_Repo adminRepo;

    private final Engineer_Repo engRepo;

    private final Customer_Repo custRepo;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${url.ultrasoft.forgotpassword}")
    private String url;

    @Autowired
    private SendEmailTo mail;

    public Login_Session_Service_Impl(Login_Session_Repo session,
                                      Admin_Repo adminRepo, Engineer_Repo engRepo,
                                      Customer_Repo custRepo) {
        this.session = session;
        this.adminRepo = adminRepo;
        this.engRepo = engRepo;
        this.custRepo = custRepo;
    }

    @Override
    public Tbl_login_Session generateSessionAdmin(Login_Request_Body data, String notificationId) throws Exception {
        try {
            if (data.getUserRole().equals(UserRoles.ADMIN)){
                System.out.println("ADMIN LOGIN");
                if (data.getUsername()==null){
                    throw new Exception("Username is required");
                }
                if (data.getPassword()==null){
                    throw new Exception("Password is required");
                }
                System.out.println(Sha256PasswordHash.generateSHA256Hash(data.getPassword()));
                Tbl_Admin_Users admin = adminRepo.findByPhoneNumberAndPassword(data.getUsername(), Sha256PasswordHash.generateSHA256Hash(data.getPassword()));
                if(admin != null){
                    System.out.println(admin.getPhoneNumber());
                    if(admin.getActiveStatus().equals(ActiveStaus.INACTIVE)){
                        throw new Exception("Please contact super admin");
                    }
                    else{
                        if (notificationId!=null && !notificationId.equals("")){
                            admin.setNotificationId(notificationId);
                            adminRepo.save(admin);
                        }
                        return session.save(Login_Session_Mapper.loginAsAdmin(admin));
                    }
                }
                else{
                    throw new Exception("Invalid username or password");
                }
            }
            else if(data.getUserRole().equals(UserRoles.ENGINEER)){
                System.out.println("ENGINEER LOGIN");
                Tbl_Engineer_Details eng = engRepo.findByEngineerMobileAndPassword(data.getUsername(), Sha256PasswordHash.generateSHA256Hash(data.getPassword()));
                if(eng != null){
                    if(eng.getActiveStatus().equals(ActiveStaus.INACTIVE)){
                        throw new Exception("Please contact admin");
                    }
                    else{
                        if (notificationId!=null && !notificationId.equals("")){
                            eng.setNotificationId(notificationId);
                            engRepo.save(eng);
                        }
                        return session.save(Login_Session_Mapper.loginAsEmloyee(eng));
                    }
                }
                else{
                    throw new Exception("Invalid username or password");
                }
            }
            else if(data.getUserRole().equals(UserRoles.CUSTOMER)){
                System.out.println("CUSTOMER LOGIN");
                Tbl_Customer_details cust = custRepo.findByCustomerMobileAndPassword(data.getUsername(), Sha256PasswordHash.generateSHA256Hash(data.getPassword()));
                if(cust != null){
                    if(cust.getActiveStatus().equals(ActiveStaus.INACTIVE)){
                        throw new Exception("Please contact admin");
                    }
                    else{
                        if (notificationId!=null && !notificationId.equals("")){
                            cust.setNotificationId(notificationId);
                            custRepo.save(cust);
                        }
                        return session.save(Login_Session_Mapper.loginAsCustomer(cust));
                    }
                }
                else{
                    throw new Exception("Invalid username or password");
                }
            }
            else{
                throw new Exception("Invalid username");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Admin_Users getSessionForAdmin(String authToken) throws Exception {
        try {
            Tbl_login_Session ses = session.findByAuthToken(authToken);
            if(ses != null){
                if(ses.getAdmin() != null && ses.getRole().equals(UserRoles.ADMIN)){
                    return ses.getAdmin();
                }
                else{
                    throw new Exception("Access denied");
                }
            }
            else{
                throw new Exception("Invalid session");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Customer_details getSessionForCustomer(String authToken) throws Exception {
        try {
            Tbl_login_Session ses = session.findByAuthToken(authToken);
            if(ses != null){
                if(ses.getCustomer() != null && ses.getRole().equals(UserRoles.CUSTOMER)){
                    return ses.getCustomer();
                }
                else{
                    throw new Exception("Access denied");
                }
            }
            else{
                throw new Exception("Invalid session");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Engineer_Details getSessionForEng(String authToken) throws Exception {
        try {
            Tbl_login_Session ses = session.findByAuthToken(authToken);
            if(ses != null){
                if(ses.getEngineer() != null && ses.getRole().equals(UserRoles.ENGINEER)){
                    return ses.getEngineer();
                }
                else{
                    throw new Exception("Access denied");
                }
            }
            else{
                throw new Exception("Invalid session");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    @Modifying
    public void logout(String authToken) throws Exception {
        try {
            Tbl_login_Session ses = session.findByAuthToken(authToken);
            if(ses != null){
                session.delete(ses);
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Object chenagePassword(String email, UserRoles type) throws Exception {
        try {
            if (email==null){
                throw new Exception("Email is required");
            }
            if (type==null){
                throw new Exception("Type is required");
            }
            if (type.equals(UserRoles.ADMIN)){
                Tbl_Admin_Users adminUsers = adminRepo.findByEmailId(email);
                if (adminUsers==null){
                    throw new Exception("Email Not Found! Please Contact Admin.");
                }
                EmailTokenPojo tokenPojo = new EmailTokenPojo();
                tokenPojo.setEmail(adminUsers.getEmailId());
                tokenPojo.setType(type);
                String link = url+"?token="+GenerateEncryptedToken.encrypt(new Gson().toJson(tokenPojo));
                changePasswordLink(adminUsers.getEmailId(), "Forgot Password", adminUsers.getName(), link);
                return "Mail Sent";
            }
            else if (type.equals(UserRoles.CUSTOMER)){
                Tbl_Customer_details customerDetails = custRepo.findByCustomerEmail(email);
                if (customerDetails==null){
                    throw new Exception("Email Not Found! Please Contact Admin.");
                }
                EmailTokenPojo tokenPojo = new EmailTokenPojo();
                tokenPojo.setType(type);
                tokenPojo.setEmail(customerDetails.getCustomerEmail());
                String link = url+"?token="+GenerateEncryptedToken.encrypt(new Gson().toJson(tokenPojo));
                changePasswordLink(customerDetails.getCustomerEmail(), "Forgot Password", customerDetails.getCustomerName(), link);
                return "Mail Sent";
            }
            else if (type.equals(UserRoles.ENGINEER)){
                Tbl_Engineer_Details engineerDetails = engRepo.findByEngineerEmailId(email);
                if (engineerDetails==null){
                    throw new Exception("Email Not Found! Please Contact Admin.");
                }
                EmailTokenPojo tokenPojo = new EmailTokenPojo();
                tokenPojo.setEmail(email);
                tokenPojo.setType(type);
                String link = url+"?token="+GenerateEncryptedToken.encrypt(new Gson().toJson(tokenPojo));
                changePasswordLink(engineerDetails.getEngineerEmailId(), "Forgot Password", engineerDetails.getEngineerName(), link);
                return "Mail Sent";
            }
            else {
                throw new Exception("Invalid User Type");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Object resetPassword(String email, String password, String body) throws Exception {
        try {
            if (email==null){
                throw new Exception("Email is required");
            }
            if (password==null){
                throw new Exception("Password is required");
            }
            if (body==null){
                throw new Exception("Email Token is required");
            }
            String encData = GenerateEncryptedToken.decrypt(body.replaceAll(" ","+"));
            EmailTokenPojo pojo = new Gson().fromJson(encData, EmailTokenPojo.class);
            if (pojo.getEmail()==null || pojo.getType()==null){
                throw new Exception("Invalid Token Id");
            }
            if (pojo.getType().equals(UserRoles.CUSTOMER)){
                Tbl_Customer_details customerDetails = custRepo.findByCustomerEmail(pojo.getEmail());
                if (customerDetails==null){
                    throw new Exception("Invalid Email Id, Please Contact Admin.");
                }
                customerDetails.setPassword(Sha256PasswordHash.generateSHA256Hash(password));
                custRepo.save(customerDetails);
                return "Password Changed Successfully";
            }
            else if (pojo.getType().equals(UserRoles.ENGINEER)){
                Tbl_Engineer_Details engineerDetails = engRepo.findByEngineerEmailId(pojo.getEmail());
                if (engineerDetails==null){
                    throw new Exception("Invalid Email Id, Please Contact Admin.");
                }
                engineerDetails.setPassword(Sha256PasswordHash.generateSHA256Hash(password));
                engRepo.save(engineerDetails);
                return "Password Changed Successfully";
            } else if (pojo.getType().equals(UserRoles.ADMIN)) {
                Tbl_Admin_Users adminUsers = adminRepo.findByEmailId(pojo.getEmail());
                if (adminUsers==null){
                    throw new Exception("Invalid Email Id, Please Contact Super Admin.");
                }
                adminUsers.setPassword(Sha256PasswordHash.generateSHA256Hash(password));
                return "Password Changed Successfully";
            } else {
                throw new Exception("Invalid User Type.");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void changePasswordLink(String to, String subject, String name, String link) throws Exception{
        try {
            Map<String, Object> invoice = new HashMap<>();
            invoice.put("name", name);
            invoice.put("link", link);
//            System.out.println(invoice);
            Context context = new Context();
            context.setVariables(invoice);
//            System.out.println(new Gson().toJson(context));
            String htmlContent = templateEngine.process("forgotpasswordmail", context);
            mail.sendMessage(to, subject, htmlContent);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
