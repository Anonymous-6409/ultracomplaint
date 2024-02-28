package com.ultrasoft.ultracomplaint.controller;

import com.ultrasoft.ultracomplaint.entity.Tbl_Complaints;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.requestbody.Customer_Request_Body;
import com.ultrasoft.ultracomplaint.responsebody.APIResponseBody;
import com.ultrasoft.ultracomplaint.responsebody.Customer_Response_Body;
import com.ultrasoft.ultracomplaint.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController

@RequestMapping("customer")
@Tag(name = "CUSTOMER CONTROLLER")
@CrossOrigin("*")
public class Customer_Controller {

    private final CustomerService customer;

    public Customer_Controller(CustomerService customer) {
        this.customer = customer;
    }

    @PostMapping(value = "/create/complaint",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, name = "Create new complaint")
    public ResponseEntity<APIResponseBody> createComplain(
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String remark,
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String complainCategory
            ){
        try {
            Tbl_Complaints rsponse = customer.createComplaint(file, remark, authToken, complainCategory);
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint created successfully",
                    rsponse
            ), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/reply/complaint",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,name = "Reply on complaint")
    public ResponseEntity<APIResponseBody> replyComplain(
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String remark,
            @RequestParam String complainId,
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
    ){
        try {
            Tbl_Complaints rsponse = customer.replyOnComplaint(file,complainId,remark,authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "Replied successfully",
                    rsponse
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getall/complaint",name = "Get all complaint")
    public ResponseEntity<APIResponseBody> getallComplaint(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
    )
    {
        try {
            List<Tbl_Complaints> rsponse = customer.getAllComplaints(authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "All Complaint fetched successfully",
                    rsponse
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/complaint/{complaintId}",name = "Get complaint")
    public ResponseEntity<APIResponseBody> getallComplaintSingle(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @PathVariable String complaintId
    )
    {
        try {
            Tbl_Complaints rsponse = customer.getSingleComplaint(complaintId,authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint details fetched successfully",
                    rsponse
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/signup/customer")
    public ResponseEntity<APIResponseBody> createCustomer(@RequestBody Customer_Request_Body data) throws Exception{
        try {
            Customer_Response_Body res = customer.createNewCustomer(data);
            return new ResponseEntity<>(new APIResponseBody("Customer Created",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/close/complaint")
    public ResponseEntity<APIResponseBody> closeComplaint(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String complaintId
    ) throws Exception{
        try {
            return new ResponseEntity<>(new APIResponseBody("Complaint Closed",
                    customer.closeComplaint(authToken,complaintId)), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getall/complaint/pending/resolved",name = "Get all pending or resolved complaint")
    public ResponseEntity<APIResponseBody> getAllComplaintPendingResolves(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String status
    )
    {
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "All Complaint fetched successfully",
                    customer.getPendingResolvedComplaints(authToken, status)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getall/complaint/bystatus",name = "Get all complaint by status")
    public ResponseEntity<APIResponseBody> getallComplaintByStatus(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam ComplainStatus status
            )
    {
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "All Complaint fetched successfully",
                    customer.getComplaintByStatus(authToken, status)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/change/password", name = "Change Password")
    public ResponseEntity<APIResponseBody> changePassword(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam(name = "Current Password") String currentPassword,
            @RequestParam(name = "New Password") String newPassword
    ){
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Password changed successfully",
                    customer.changeCurrentPassword(currentPassword, newPassword, authToken)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getall/count")
    public ResponseEntity<APIResponseBody> getAllCount(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
    )
    {
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Count Fetch Successfully",
                    customer.getCountByStatus(authToken)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/forgot/password")
    public ResponseEntity<APIResponseBody> forgotPassword(@RequestParam String mobileNumber,
                                                          @RequestParam String password,
                                                          @RequestParam UserRoles role)
    {
        try {
            return new ResponseEntity<>(new APIResponseBody("Password Changed Successfully", customer.forgotPassword(mobileNumber, password, role)), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

}
