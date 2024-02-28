package com.ultrasoft.ultracomplaint.service;

import com.ultrasoft.ultracomplaint.entity.Tbl_Complaints;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface Engineer_Service {

    List<Tbl_Complaints> getAllComplaints(String authToken, ComplainStatus status) throws Exception;

    Tbl_Complaints replyOnComplaint(MultipartFile file,String complaintId, String remarks, String authToken) throws Exception;

    Tbl_Complaints resolveComplaint(String complaintId,String authToken) throws Exception;

    Tbl_Complaints getSingleComplaint(String complaintId,String authToken) throws Exception;

    List<Tbl_Complaints> getComplaintPendingOrResolved(String authToken, String status) throws Exception;

    List<Tbl_Complaints> getComplaintByStatus(String authToken, ComplainStatus status) throws Exception;

    Map<String, Integer> getAllCount(String authToken) throws Exception;

    Tbl_Engineer_Details changePassword(String authToken, String currentPassword, String newPassword) throws Exception;

    Tbl_Complaints createComplaint(String authToken, String customerId, String remarks, MultipartFile file, String complaintCategory) throws Exception;
}
