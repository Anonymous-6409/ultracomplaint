package com.ultrasoft.ultracomplaint.serviceimpl;

import com.ultrasoft.ultracomplaint.entity.Tbl_Asset_Category;
import com.ultrasoft.ultracomplaint.entity.Tbl_Complaints;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.mapper.Complain_Mapper;
import com.ultrasoft.ultracomplaint.repo.Asset_Category_Repo;
import com.ultrasoft.ultracomplaint.repo.Chat_Repo;
import com.ultrasoft.ultracomplaint.repo.Customer_Repo;
import com.ultrasoft.ultracomplaint.repo.Engineer_Repo;
import com.ultrasoft.ultracomplaint.requestbody.Data;
import com.ultrasoft.ultracomplaint.service.Engineer_Service;
import com.ultrasoft.ultracomplaint.service.Login_Session_Service;
import com.ultrasoft.ultracomplaint.utility.Push_Notification_Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class Engineer_Service_impl implements Engineer_Service {

    private final Login_Session_Service session;

    private final Chat_Repo chatR;

    private final Engineer_Repo repo;

    private final Customer_Repo customerRepo;

    private final Asset_Category_Repo assetCategoryRepo;

    private final Push_Notification_Service pushNotificationService;


    public Engineer_Service_impl(Login_Session_Service session, Chat_Repo chatR, Engineer_Repo repo, Customer_Repo customerRepo, Asset_Category_Repo assetCategoryRepo, Push_Notification_Service pushNotificationService) {
        this.session = session;
        this.chatR = chatR;
        this.repo = repo;
        this.customerRepo = customerRepo;
        this.assetCategoryRepo = assetCategoryRepo;
        this.pushNotificationService = pushNotificationService;
    }

    @Override
    public List<Tbl_Complaints> getAllComplaints(String authToken, ComplainStatus status) throws Exception {
        try {
            Tbl_Engineer_Details eng = session.getSessionForEng(authToken);
            if (status == ComplainStatus.UN_ASSIGNED){
                throw new Exception("Invalid Status");
            }
            List<Tbl_Complaints> allcomp = chatR.findByAssignedToEngineerAndStatus(eng, status);
            if(allcomp.isEmpty()){
                throw new Exception("No record found");
            }
            else{
                return allcomp;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints replyOnComplaint(MultipartFile file, String complaintId, String remarks, String authToken) throws Exception {
        try {
            Tbl_Engineer_Details eng = session.getSessionForEng(authToken);
            Tbl_Complaints ncomp = chatR.findByComplainIdAndAssignedToEngineer(complaintId, eng);
            if(ncomp != null){
                if(ncomp.getStatus().equals(ComplainStatus.CLOSED)){
                    throw new Exception("Complain is already closed");
                }
                else{
                    chatR.save(Complain_Mapper.replyByEngineer(ncomp,eng, remarks, file));
                    Data data = new Data();
                    data.setTitle("Engineer replied on your Complaint");
                    data.setText("Click here to view your Complaint");
                    data.setNumber(ncomp.getCreatedByCUstomer().getCustomerMobile());
                    if (ncomp.getCreatedByCUstomer().getNotificationId()!=null){
                        pushNotificationService.sendPushNotificationToCustomer(data);
                    }
                    return ncomp;
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
    public Tbl_Complaints resolveComplaint(String complaintId,String authToken) throws Exception {
        try {
            Tbl_Engineer_Details eng = session.getSessionForEng(authToken);
            Tbl_Complaints ncomp = chatR.findByComplainId(complaintId);
            if(ncomp != null){
                if(ncomp.getStatus().equals(ComplainStatus.CLOSED)){
                    throw new Exception("Complaint already closed");
                }
                else{
                    ncomp.setStatus(ComplainStatus.RESOLVED);
                    ncomp.setResolvedDate(new Date());
                    chatR.save(ncomp);
                    return ncomp;
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
    public Tbl_Complaints getSingleComplaint(String complaintId, String authToken) throws Exception {
        try {
            Tbl_Engineer_Details eng = session.getSessionForEng(authToken);
            Tbl_Complaints ncomp = chatR.findByComplainIdAndAssignedToEngineer(complaintId, eng);
            if(ncomp != null){
                return ncomp;
            }
            else{
                throw new Exception("Invalid complaint code");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Complaints> getComplaintPendingOrResolved(String authToken, String status) throws Exception {
        try {
            Tbl_Engineer_Details eng = session.getSessionForEng(authToken);
            if (status.equals("PENDING")){
                List<Tbl_Complaints> unassigned = chatR.findByAssignedToEngineerAndStatus(eng,ComplainStatus.UN_ASSIGNED);
                List<Tbl_Complaints> assigned = chatR.findByAssignedToEngineerAndStatus(eng,ComplainStatus.IN_PROGRESS);
                if (unassigned.isEmpty() && assigned.isEmpty()){
                    throw new Exception("No record found");
                }
                unassigned.addAll(assigned);
                Collections.sort(unassigned, Tbl_Complaints.compareByAssignedDate().reversed());
                return unassigned;
            }
            else if (status.equals("RESOLVED")){
                List<Tbl_Complaints> resolved = chatR.findByAssignedToEngineerAndStatus(eng,ComplainStatus.RESOLVED);
                List<Tbl_Complaints> closed = chatR.findByAssignedToEngineerAndStatus(eng,ComplainStatus.CLOSED);
                if (resolved.isEmpty() && closed.isEmpty()){
                    throw new Exception("No record found");
                }
                resolved.addAll(closed);
                Collections.sort(resolved, Tbl_Complaints.compareByResolvedDate().reversed());
                return resolved;
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
        try{
            Tbl_Engineer_Details eng = session.getSessionForEng(authToken);
            if (status.equals(ComplainStatus.UN_ASSIGNED)){
                throw new Exception("Invalid Status Un Assigned");
            }
            List<Tbl_Complaints> complaints = chatR.findByAssignedToEngineerAndStatus(eng,status);
            if (complaints.isEmpty()){
                throw new Exception("No records found");
            }
            return complaints;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Map<String, Integer> getAllCount(String authToken) throws Exception {
        try {
            Tbl_Engineer_Details eng = session.getSessionForEng(authToken);
            List<Tbl_Complaints> inprogress = chatR.findByAssignedToEngineerAndStatus(eng,ComplainStatus.IN_PROGRESS);
            List<Tbl_Complaints> resolved = chatR.findByAssignedToEngineerAndStatus(eng, ComplainStatus.RESOLVED);
            List<Tbl_Complaints> closed = chatR.findByAssignedToEngineerAndStatus(eng, ComplainStatus.CLOSED);
            Map<String, Integer> map = new HashMap<>();
            map.put("IN_PROGRESS", inprogress.size());
            map.put("RESOLVED", resolved.size());
            map.put("CLOSED", closed.size());
            return map;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Engineer_Details changePassword(String authToken, String currentPassword, String newPassword) throws Exception {
        try{
            Tbl_Engineer_Details eng = session.getSessionForEng(authToken);
            if (eng.getPassword().equals(Sha256PasswordHash.generateSHA256Hash(currentPassword))){
                if (newPassword.length()<8){
                    throw new Exception("Password must contain at least 8 characters");
                }
                else if (newPassword.length()>16){
                    throw new Exception("Password must not contain more than 16 characters");
                }
                eng.setPassword(Sha256PasswordHash.generateSHA256Hash(newPassword));
                repo.save(eng);
                return eng;
            }
            else {
                throw new Exception("Incorrect current password");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints createComplaint(String authToken, String customerId, String remarks, MultipartFile file, String complaintCategory) throws Exception {
        try {
            if (customerId==null){
                throw new Exception("Customer Id is required");
            }
            session.getSessionForEng(authToken);
            Tbl_Customer_details customerDetails = customerRepo.findByCustomerId(customerId);
            if (customerDetails==null){
                throw new Exception("Invalid Customer Id");
            }
            Tbl_Asset_Category assetCategory = assetCategoryRepo.findByAssetCategoryName(complaintCategory);
            if (assetCategory == null){
                throw new Exception("Invalid Asset Category");
            }
            Tbl_Complaints comp = Complain_Mapper.toDB(file,customerDetails, remarks, chatR.count()+1, assetCategory);
            comp.getComplaintChats().get(0).setRole(UserRoles.ENGINEER);
            comp =  chatR.save(comp);
            return comp;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
