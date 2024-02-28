package com.ultrasoft.ultracomplaint.controller;

import com.ultrasoft.ultracomplaint.entity.Tbl_Complaints;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import com.ultrasoft.ultracomplaint.mapper.Complain_Mapper;
import com.ultrasoft.ultracomplaint.responsebody.APIResponseBody;
import com.ultrasoft.ultracomplaint.service.Engineer_Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("engineer")
@Tag(name = "ENGINEER CONTROLLER")
@CrossOrigin("*")
public class Engineer_Controller {

    private final Engineer_Service service;

    public Engineer_Controller(Engineer_Service service) {
        this.service = service;
    }

    @GetMapping(value = "/get/all/complaint",name = "Get all complaint")
    public ResponseEntity<APIResponseBody> getallComplaints(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam ComplainStatus status
    )
    {
        try {
            List<Tbl_Complaints> response = service.getAllComplaints(authToken, status);
            return new ResponseEntity<>(new APIResponseBody(
                    "All Complaint fetched successfully",
                    response
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/eng/complaint/{complaintId}",name = "Get by complaint id")
    public ResponseEntity<APIResponseBody> getallComplaintsSingleEng(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @PathVariable String complaintId
    )
    {
        try {
            Tbl_Complaints response = service.getSingleComplaint(complaintId, authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint details fetched successfully",
                    response
            ), HttpStatus.OK);
        }
        catch (Exception e){
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
            Tbl_Complaints response = service.replyOnComplaint(file,complainId,remark,authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "Replied successfully",
                    response
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/resolve/complaint", name = "Resolve complaint")
    public ResponseEntity<APIResponseBody> resolveComplain(
            @RequestParam String complainId,
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
    ){
        try {
            Tbl_Complaints response = service.resolveComplaint(complainId,authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint Resolved successfully",
                    response
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/all/complaint/pending/resolved",name = "Get all pending or resolved complaint")
    public ResponseEntity<APIResponseBody> getAllComplaintsPendingOrResolved(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String status
    )
    {
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "All Complaint fetched successfully",
                    service.getComplaintPendingOrResolved(authToken, status)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/all/complaint/status",name = "Get all complaint by status")
    public ResponseEntity<APIResponseBody> getAllComplaintsByStatus(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam ComplainStatus status
    )
    {
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "All Complaint fetched successfully",
                    service.getComplaintByStatus(authToken, status)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getall/count", name = "Get all engineer complaint count")
    public ResponseEntity<APIResponseBody> getAllCount(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
    ){
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint Found Successfully",
                    service.getAllCount(authToken)
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
            Tbl_Engineer_Details response = service.changePassword(authToken,currentPassword,newPassword);
            return new ResponseEntity<>(new APIResponseBody(
                    "Password changed successfully",
                    response
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/create/complaint",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, name = "Create Complaint Engineer")
    public ResponseEntity<APIResponseBody> createComplaint(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String remarks,
            @RequestParam String customerId,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String complaintCategory
    ){
        try {
            Tbl_Complaints complaints = service.createComplaint(authToken,customerId, remarks, file, complaintCategory);
            return new ResponseEntity<>(new APIResponseBody("Complaint Created Successfully", complaints),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }
}
