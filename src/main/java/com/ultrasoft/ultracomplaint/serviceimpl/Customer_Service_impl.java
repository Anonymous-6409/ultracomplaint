package com.ultrasoft.ultracomplaint.serviceimpl;

import com.ultrasoft.ultracomplaint.entity.*;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.mapper.Admin_Customer_Employee_Mapper;
import com.ultrasoft.ultracomplaint.mapper.Complain_Mapper;
import com.ultrasoft.ultracomplaint.repo.*;
import com.ultrasoft.ultracomplaint.requestbody.Customer_Request_Body;
import com.ultrasoft.ultracomplaint.requestbody.Data;
import com.ultrasoft.ultracomplaint.requestbody.PushNotificationRequest;
import com.ultrasoft.ultracomplaint.responsebody.Customer_Response_Body;
import com.ultrasoft.ultracomplaint.service.CustomerService;
import com.ultrasoft.ultracomplaint.service.Login_Session_Service;
import com.ultrasoft.ultracomplaint.utility.Push_Notification_Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Customer_Service_impl implements CustomerService {

    private final Chat_Repo complRepo;

    private final Customer_Repo custRepo;

    private final Login_Session_Service session;

    private final Asset_Category_Repo assetRepo;
    private final Engineer_Repo engineer_Repo;
    private final Admin_Repo admin_Repo;

    private final Push_Notification_Service pushNotificationService;

    public Customer_Service_impl(Chat_Repo complRepo, Customer_Repo custRepo, Login_Session_Service session, Asset_Category_Repo assetRepo,
                                 Engineer_Repo engineer_Repo,
                                 Admin_Repo admin_Repo, Push_Notification_Service pushNotificationService) {
        this.complRepo = complRepo;
        this.custRepo = custRepo;
        this.session = session;
        this.assetRepo = assetRepo;
        this.engineer_Repo = engineer_Repo;
        this.admin_Repo = admin_Repo;
        this.pushNotificationService = pushNotificationService;
    }

    @Override
    public Tbl_Customer_details changeCurrentPassword(String currentPassword,String newPassword, String authToken) throws Exception {
        try {
            Tbl_Customer_details cust = session.getSessionForCustomer(authToken);
            if(cust.getPassword().equals(Sha256PasswordHash.generateSHA256Hash(currentPassword))){
                if (newPassword.length()<8){
                    throw new Exception("Password must contain at least 8 characters");
                }
                else if (newPassword.length()>16){
                    throw new Exception("Password must not contain more than 16 characters");
                }
                cust.setPassword(Sha256PasswordHash.generateSHA256Hash(newPassword));
                custRepo.save(cust);
                return cust;
            }
            else{
                throw new Exception("Incorrect current password");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints createComplaint(MultipartFile file, String remarks, String authToken, String complainCategory) throws Exception {
        try {
            Tbl_Customer_details cust = session.getSessionForCustomer(authToken);
            Tbl_Asset_Category assetCategory = assetRepo.findByAssetCategoryName(complainCategory);
            if (assetCategory == null){
                throw new Exception("Invalid Asset Category");
            }
            Tbl_Complaints comp = Complain_Mapper.toDB(file,cust, remarks, complRepo.count()+1, assetCategory);
            comp =  complRepo.save(comp);
            PushNotificationRequest request = new PushNotificationRequest();
            request.setText(cust.getCompanyName()+" "+ cust.getCreatedDate().toString());
            request.setTitle("New Complaint Found");
            request.setStyle("BigText");
            pushNotificationService.sendPushNotificationToToken(request);
            return comp;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Complaints> getAllComplaints(String authToken) throws Exception {
        try {
            Tbl_Customer_details cust = session.getSessionForCustomer(authToken);
            List<Tbl_Complaints> data = complRepo.findByCreatedByCUstomer(cust);
            if(data.isEmpty()){
                throw new Exception("No record found");
            }
            else{
                return data;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints getSingleComplaint(String complaintId, String authToke) throws Exception {
        try {
            Tbl_Customer_details cust = session.getSessionForCustomer(authToke);
            Tbl_Complaints data = complRepo.findByComplainIdAndCreatedByCUstomer(complaintId,cust);
//            Tbl_Complaints data = complRepo.findByComplainId(complaintId);
            if(data != null){
                return data;
            }
            else{
                throw new Exception("Invalid complaint Id");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints replyOnComplaint(MultipartFile file, String complaintId, String remarks, String authToken) throws Exception {
        try {
            Tbl_Customer_details cust = session.getSessionForCustomer(authToken);
            Tbl_Complaints comp = complRepo.findByComplainIdAndCreatedByCUstomer(complaintId, cust);
            if(comp != null){
                if(comp.getStatus().equals(ComplainStatus.CLOSED)){
                    throw new Exception("Complain is already closed");
                }
                else{
                    comp = Complain_Mapper.replyByCustomer(comp,remarks, file);
                    complRepo.save(comp);
                    Data data = new Data();
                    data.setText(cust.getCompanyName()+" replied on his complaint " + comp.getComplainId());
                    data.setTitle("Customer replied on a Complaint");
                    data.setStyle("BigText");
                    data.setNumber(comp.getAssignedToEngineer().getEngineerMobile());
                    pushNotificationService.sendPushNotification(data);
                    return comp;
                }
            }
            else{
                throw new Exception("Invalid complaint id");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Customer_Response_Body createNewCustomer(Customer_Request_Body data) throws Exception {
        try {
            if (data.getCustomerState()==null){
                throw new Exception("State must not be null");
            }
            if (data.getAddress1()==null){
                throw new Exception("Address must not be null");
            }
            if (data.getPinCode()== null || data.getPinCode().equals("") || data.getPinCode().length()!=6){
                throw new Exception("Invalid State code");
            }
            if (data.getCompanyName()==null || data.getCompanyName().equals("")){
                throw new Exception("Company Name is required");
            }
            if (data.getCustomerCity()==null || data.getCustomerCity().equals("")){
                throw new Exception("City is required");
            }
            if (data.getCustomerEmail()!=null){
                Tbl_Customer_details customerDetails = custRepo.findByCustomerEmail(data.getCustomerEmail());
                if (customerDetails!=null){
                    throw new Exception("Email already exists");
                }
            }
            Tbl_Customer_details customerDetails = custRepo.findByCustomerMobile(data.getCustomerMobile());
            if (customerDetails!=null){
                throw new Exception("Mobile number already exists");
            }
            Tbl_Customer_details users = custRepo.save(Admin_Customer_Employee_Mapper.todbCust(data,
                    custRepo.count()));
            return Admin_Customer_Employee_Mapper.toShowCust(users);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints closeComplaint(String authToken, String compliantId) throws Exception {
        try {
            Tbl_Customer_details cust = session.getSessionForCustomer(authToken);
            Tbl_Complaints comp = complRepo.findByComplainId(compliantId);
            if(comp != null){
                if(comp.getStatus().equals(ComplainStatus.CLOSED)){
                    throw new Exception("Complain is already closed");
                }
                else{
                    comp.setStatus(ComplainStatus.CLOSED);
                    comp.setTicketClosedDate(new Date());
                    complRepo.save(comp);
                    return comp;
                }
            }
            else{
                throw new Exception("Invalid complaint id");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Complaints> getPendingResolvedComplaints(String authToken, String status) throws Exception {
        try {
            Tbl_Customer_details cust = session.getSessionForCustomer(authToken);
            if (status.equals("PENDING")){
                List<Tbl_Complaints> inprogress = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.IN_PROGRESS,cust);
                List<Tbl_Complaints> unassigned = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.UN_ASSIGNED,cust);
                if(inprogress.isEmpty() && unassigned.isEmpty()){
                    throw new Exception("No record found");
                }
                else{
                    inprogress.addAll(unassigned);
                    return inprogress;
                }
            }
            else if (status.equals("RESOLVED")){
                List<Tbl_Complaints> resolved = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.RESOLVED,cust);
                List<Tbl_Complaints> closed = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.CLOSED,cust);
                if(resolved.isEmpty() && closed.isEmpty()){
                    throw new Exception("No record found");
                }
                else{
                    closed.addAll(resolved);
                    return closed;
                }
            }
            else {
                throw new Exception("Invalid Status");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Complaints> getComplaintByStatus(String authToken, ComplainStatus status) throws Exception {
        try {
            Tbl_Customer_details cust = session.getSessionForCustomer(authToken);
//            if (status.equals(ComplainStatus.UN_ASSIGNED)){
//                throw new Exception("Status cannot be Unassigned for Customer");
//            }
//            if (status.equals(ComplainStatus.IN_PROGRESS)){
//                List<Tbl_Complaints> complaints = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.UN_ASSIGNED,cust);
//                List<Tbl_Complaints> complaints1 = complRepo.findByStatusAndCreatedByCUstomer(status,cust);
//                complaints.addAll(complaints1);
//                if(complaints.isEmpty()){
//                    throw new Exception("No record found");
//                }
//                return complaints;
//            }
            List<Tbl_Complaints> complaints = complRepo.findByStatusAndCreatedByCUstomer(status,cust);
            complaints.sort(Tbl_Complaints.compareByCreatedDate());
            if(complaints.isEmpty()){
                throw new Exception("No record found");
            }
            return complaints;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Map<String, Integer> getCountByStatus(String authToken) throws Exception {
        try {
            Tbl_Customer_details cust = session.getSessionForCustomer(authToken);
//            if (status.equals(ComplainStatus.IN_PROGRESS)){
//                List<Tbl_Complaints> complaints = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.IN_PROGRESS, cust);
//                List<Tbl_Complaints> complaints1 = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.UN_ASSIGNED, cust);
//                complaints.addAll(complaints1);
//                if(complaints.isEmpty()){
//                    throw new Exception("No record found");
//                }
//                return complaints.size();
//            }
//            else if (status.equals(ComplainStatus.RESOLVED)){
//                List<Tbl_Complaints> complaints = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.IN_PROGRESS, cust);
//                if(complaints.isEmpty()){
//                    throw new Exception("No record found");
//                }
//                return complaints.size();
//            }
//            else if (status.equals(ComplainStatus.CLOSED)){
//                List<Tbl_Complaints> complaints = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.IN_PROGRESS, cust);
//                if(complaints.isEmpty()){
//                    throw new Exception("No record found");
//                }
//                return complaints.size();
//            }
//            else {
//                throw new Exception("Invalid status! Status must be In Progress, Resolved or Closed");
//            }
            List<Tbl_Complaints> inprogress = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.IN_PROGRESS,cust);
            List<Tbl_Complaints> unassigned = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.UN_ASSIGNED,cust);
            List<Tbl_Complaints> resolved = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.RESOLVED,cust);
            List<Tbl_Complaints> closed = complRepo.findByStatusAndCreatedByCUstomer(ComplainStatus.CLOSED,cust);

            Map<String, Integer> map = new HashMap<>();
            map.put(ComplainStatus.IN_PROGRESS.name(),inprogress.size());
            map.put(ComplainStatus.CLOSED.name(), closed.size());
            map.put(ComplainStatus.RESOLVED.name(), resolved.size());
            map.put(ComplainStatus.UN_ASSIGNED.name(), unassigned.size());
            return map;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Object forgotPassword(String mobileNumber, String password, UserRoles roles) throws Exception {
        try {
            if (roles.equals(UserRoles.CUSTOMER)){
                Tbl_Customer_details customerDetails = custRepo.findByCustomerMobile(mobileNumber);
                if (customerDetails==null){
                    throw new Exception("Invalid Mobile Number");
                }
                customerDetails.setPassword(Sha256PasswordHash.generateSHA256Hash(password));
                customerDetails.setUpdatedDate(new Date());
                custRepo.save(customerDetails);
                return mobileNumber;
            } else if (roles.equals(UserRoles.ENGINEER)) {
                Tbl_Engineer_Details engineerDetails = engineer_Repo.findByEngineerMobile(mobileNumber);
                if (engineerDetails==null){
                    throw new Exception("Invalid Mobile Number");
                }
                engineerDetails.setPassword(Sha256PasswordHash.generateSHA256Hash(password));
                engineerDetails.setUpdatedDate(new Date());
                engineer_Repo.save(engineerDetails);
                return mobileNumber;
            } else if (roles.equals(UserRoles.ADMIN)) {
                Tbl_Admin_Users adminUsers = admin_Repo.findByPhoneNumber(mobileNumber);
                if (adminUsers==null){
                    throw new Exception("Invalid Mobile Number");
                }
                adminUsers.setPassword(Sha256PasswordHash.generateSHA256Hash(password));
                adminUsers.setUpdatedDate(new Date());
                admin_Repo.save(adminUsers);
                return mobileNumber;
            }
            else {
                throw new Exception("Invalid User Type");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
