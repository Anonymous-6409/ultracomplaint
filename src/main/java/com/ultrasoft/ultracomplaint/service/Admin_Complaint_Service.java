package com.ultrasoft.ultracomplaint.service;

import com.ultrasoft.ultracomplaint.entity.Tbl_Complaints;
import com.ultrasoft.ultracomplaint.entity.Tbl_Complaints_Chats;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public interface Admin_Complaint_Service {

    List<Tbl_Complaints> getAllComplaints(String authToken, ComplainStatus status) throws Exception;

    Tbl_Complaints replyOnComplaint(MultipartFile file, String complaintId, String remarks, String authToken, ComplainStatus status) throws Exception;

    Tbl_Complaints closeComplaint(String complaintId,String authToken) throws Exception;

    Tbl_Complaints assignedToEngineer(String complaintId, String engineerId, String authToken) throws Exception;

    long countComplaint(ComplainStatus status,String authToken) throws Exception;

    Map<String, String> getAssignedDetails(String authToken, String complaintId) throws Exception;

    ComplainStatus getComplaintStatus(String authToken, String complaintId) throws Exception;

    List<Tbl_Complaints> getAllResolvedComplaints(String authToken) throws Exception;

    List<Tbl_Complaints> getAllPendingResolvedComplaints(String authToken, String status) throws Exception;

    List<Tbl_Complaints> getComplaintByStatus(String authToken, ComplainStatus status) throws Exception;

    Tbl_Complaints updateComplaint(String authToken, String complaintId, ComplainStatus status) throws Exception;

    Object getComplaint(String authToken, String id) throws Exception;

    Tbl_Complaints getComplaintByID(String authToken, String complaintId) throws Exception;

    Tbl_Complaints reAssignEngineer(String authToken, String engineerId, String compliantId) throws Exception;

    Map<String, Integer> getAllCount(String authToken) throws Exception;

    void getDataFromExcel(MultipartFile file, String authToken) throws Exception;

    String getAssetDataFromExcel(MultipartFile file, String authToken) throws Exception;

    ByteArrayInputStream generateCustomerExcel() throws Exception;

    ByteArrayInputStream generateAssetExcel(String customerId) throws Exception;

    int getAllPendingResolvedCount(String authToken, String status) throws Exception;

    List<Tbl_Complaints_Chats> getChatbyId(String authToken, String complaintId) throws Exception;

    ByteArrayInputStream getAllAdminExcel() throws Exception;

    ByteArrayInputStream getAllEngineerExcel() throws Exception;

    Tbl_Complaints createComplaint(String authToken, String customerId, String remarks, MultipartFile file, String complaintCategory) throws Exception;
}
