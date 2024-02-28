package com.ultrasoft.ultracomplaint.service;

import com.ultrasoft.ultracomplaint.entity.Tbl_Complaints;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.requestbody.Customer_Request_Body;
import com.ultrasoft.ultracomplaint.responsebody.Customer_Response_Body;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface CustomerService {

    Tbl_Customer_details changeCurrentPassword(String currentPassword,String newPassword, String authToken) throws Exception;

    Tbl_Complaints createComplaint(MultipartFile file, String remarks, String authToken, String complainCategory) throws Exception;

    List<Tbl_Complaints> getAllComplaints(String authToken) throws Exception;

    Tbl_Complaints getSingleComplaint(String complaintId,String authToke) throws Exception;

    Tbl_Complaints replyOnComplaint(MultipartFile file,String complaintId, String remarks, String authToken) throws Exception;

    Customer_Response_Body createNewCustomer(Customer_Request_Body data) throws Exception;

    Tbl_Complaints closeComplaint(String authToken, String compliantId) throws Exception;

    List<Tbl_Complaints> getPendingResolvedComplaints(String authToken, String status) throws Exception;

    List<Tbl_Complaints> getComplaintByStatus(String authToken, ComplainStatus status) throws Exception;

    Map<String, Integer> getCountByStatus(String authToken) throws Exception;

    Object forgotPassword(String mobileNumber, String password, UserRoles roles) throws Exception;
}
