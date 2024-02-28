package com.ultrasoft.ultracomplaint.controller;

import com.ultrasoft.ultracomplaint.entity.*;
import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.requestbody.*;
import com.ultrasoft.ultracomplaint.responsebody.APIResponseBody;
import com.ultrasoft.ultracomplaint.responsebody.Admin_Response_Body;
import com.ultrasoft.ultracomplaint.responsebody.Engineer_Response_Body;
import com.ultrasoft.ultracomplaint.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("admin")
@Tag(name = "ADMIN CONTROLLER")
@CrossOrigin("*")
public class Admin_Master_Controller {

    private final Admin_Customer_Engineer_Master_Service service;

    private final Asset_Category_Service categoryService;

    private final Asset_Type_Service assetType;

    private final Asset_Status_Service assetStatus;

    private final Admin_Complaint_Service adminCompService;

    public Admin_Master_Controller(Admin_Customer_Engineer_Master_Service service, Asset_Category_Service categoryService, Asset_Type_Service assetType, Asset_Status_Service assetStatus, Admin_Complaint_Service adminCompService) {
        this.service = service;
        this.categoryService = categoryService;
        this.assetType = assetType;
        this.assetStatus = assetStatus;
        this.adminCompService = adminCompService;
    }

    @PostMapping("/create/admin")
    public ResponseEntity<APIResponseBody> createNewAdmin(@RequestBody Admin_Request_Body data,
                                                      @RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            Admin_Response_Body res = service.createNewAdmin(data, authToken);
            return new ResponseEntity<>(new APIResponseBody("Admin Created",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/admin")
    public ResponseEntity<APIResponseBody> getallAdmin(@RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            List<Admin_Response_Body> res = service.getAllAdminUser(authToken);
            return new ResponseEntity<>(new APIResponseBody("All admin fetched successfully",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/create/engineer")
    public ResponseEntity<APIResponseBody> createEngineer(@RequestBody Engineer_Request_Body data,
                                                     @RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            Engineer_Response_Body res = service.createNewEngineer(data, authToken);
            return new ResponseEntity<>(new APIResponseBody("Engineer Created",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/engineer")
    public ResponseEntity<APIResponseBody> getallEngneer(@RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            List<Tbl_Engineer_Details> res = service.getAllEngineer(authToken);
            return new ResponseEntity<>(new APIResponseBody("All engineers fetched successfully",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/create/customer")
    public ResponseEntity<APIResponseBody> createCustomer(@RequestBody Customer_Request_Body data,
                                                     @RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            Tbl_Customer_details res = service.createNewCustomer(data, authToken);
            return new ResponseEntity<>(new APIResponseBody("Customer Created",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/customer")
    public ResponseEntity<APIResponseBody> getallCustomer(@RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            List<Tbl_Customer_details> res = service.getAllCustomer(authToken);
            return new ResponseEntity<>(new APIResponseBody("All customers fetched successfully",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/block/unblock/{id}")
    public ResponseEntity<APIResponseBody> getallCustomer(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @PathVariable String id,
            @RequestParam UserRoles role,
            @RequestParam ActiveStaus action) throws Exception{
        try {
            service.blockAdmin_Customer_Engineer(role, id, authToken,action);
            return new ResponseEntity<>(new APIResponseBody("user blocked/unblocked successfully",
                    "user blocked/unblocked successfully"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/create/category")
    public ResponseEntity<APIResponseBody> createCategory(@RequestBody AssetCategoryRequestBody data,
                                                          @RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            Tbl_Asset_Category res = categoryService.createAssetCategory(data, authToken);
            return new ResponseEntity<>(new APIResponseBody("Category Created",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/update/category")
    public ResponseEntity<APIResponseBody> updateCategory(@RequestBody Tbl_Asset_Category data,
                                                          @RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            Tbl_Asset_Category res = categoryService.updateAssetCategory(data, authToken);
            return new ResponseEntity<>(new APIResponseBody("Category Updated",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/category")
    public ResponseEntity<APIResponseBody> getAllCategory(
                                                         ) throws Exception{
        try {
            List<Tbl_Asset_Category> res = categoryService.getAllAssetCategory();
            return new ResponseEntity<>(new APIResponseBody("Record Fetched Successfully",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/block/category")
    public ResponseEntity<APIResponseBody> blockCategory(@RequestHeader(name = "X-AUTH-TOKEN") String authToken,
                                                         @RequestParam long id)
    {
        try {
            return new ResponseEntity<>(new APIResponseBody("Asset Category Status Changed Successfully",
                    categoryService.blockAssetCategory(authToken, id)), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/create/assettype")
    public ResponseEntity<APIResponseBody> createAssetType(@RequestBody Tbl_Asset_Type data,
                                                          @RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            Tbl_Asset_Type res = assetType.createAssetType(data, authToken);
            return new ResponseEntity<>(new APIResponseBody("Asset Type Created",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/update/assettype")
    public ResponseEntity<APIResponseBody> updateAssetType(@RequestBody Tbl_Asset_Type data,
                                                          @RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            Tbl_Asset_Type res = assetType.updateAssetType(data, authToken);
            return new ResponseEntity<>(new APIResponseBody("Asset Type Updated",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/assettype")
    public ResponseEntity<APIResponseBody> getAllAssetType(
    ) throws Exception{
        try {
            List<Tbl_Asset_Type> res = assetType.getAllAssetType();
            return new ResponseEntity<>(new APIResponseBody("Record Fetched Successfully",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }


    @PostMapping("/create/assetstaus")
    public ResponseEntity<APIResponseBody> createAssetStatus(@RequestBody Tbl_Asset_Status data,
                                                           @RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            Tbl_Asset_Status res = assetStatus.createAssetStaus(data, authToken);
            return new ResponseEntity<>(new APIResponseBody("Asset Status Created",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/update/assetstaus")
    public ResponseEntity<APIResponseBody> updateAssetStatus(@RequestBody Tbl_Asset_Status data,
                                                           @RequestHeader(name = "X-AUTH-TOKEN") String authToken) throws Exception{
        try {
            Tbl_Asset_Status res = assetStatus.updateAssetStaus(data, authToken);
            return new ResponseEntity<>(new APIResponseBody("Asset status Updated",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/assetstaus")
    public ResponseEntity<APIResponseBody> getAllAssetStatus(
    ) throws Exception{
        try {
            List<Tbl_Asset_Status> res = assetStatus.getAllAssetStaus();
            return new ResponseEntity<>(new APIResponseBody("Record Fetched Successfully",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }


    @PostMapping(value = "/reply/admin/complaint",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponseBody> replyComplainAdmin(
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String remark,
            @RequestParam String complainId,
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam(required = false) ComplainStatus status
    ){
        try {
            Tbl_Complaints rsponse = adminCompService.replyOnComplaint(file,complainId,remark,authToken,status);
            return new ResponseEntity<>(new APIResponseBody(
                    "Replied successfully",
                    rsponse
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/admin/complaint")
    public ResponseEntity<APIResponseBody> getallComplaintAdmin(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam ComplainStatus status
            )
    {
        try {
            List<Tbl_Complaints> rsponse = adminCompService.getAllComplaints(authToken,status);
            return new ResponseEntity<>(new APIResponseBody(
                    "All Complaint fetched successfully",
                    rsponse
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/close/admin/complaint")
    public ResponseEntity<APIResponseBody> closeComplaint(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String complainId
    )
    {
        try {
            Tbl_Complaints rsponse = adminCompService.closeComplaint(complainId, authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint closed successfully",
                    rsponse
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/assigntoeng/admin/complaint")
    public ResponseEntity<APIResponseBody> assignedToEngineer(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String complainId,
            @RequestParam String engineerId
    )
    {
        try {
            adminCompService.assignedToEngineer(complainId, engineerId, authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "Engineer assigned successfully",
                    "Engineer assigned successfully"
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/count/admin/complaint")
    public ResponseEntity<APIResponseBody> countComplaint(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam ComplainStatus status
    )
    {
        try {
            long rsponse = adminCompService.countComplaint(status, authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "Count fetched successfully",
                    rsponse
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/get/complaint/assigned/to/by")
    public ResponseEntity<APIResponseBody> getAssignedByAndAssignedToDetails(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String complaintId
    )
    {
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Count fetched successfully",
                    adminCompService.getAssignedDetails(authToken, complaintId)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/get/complaint/status")
    public ResponseEntity<APIResponseBody> getComplaintStatus(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String complaintId
    )
    {
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Count fetched successfully",
                    adminCompService.getComplaintStatus(authToken, complaintId)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/admin/pending/resolved/complaint")
    public ResponseEntity<APIResponseBody> getAllPendingComplaintAdmin(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String status
    )
    {
        try {
            List<Tbl_Complaints> response = adminCompService.getAllPendingResolvedComplaints(authToken,status);
            return new ResponseEntity<>(new APIResponseBody(
                    "All Complaint fetched successfully",
                    response
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/admin/resolved/complaint")
    public ResponseEntity<APIResponseBody> getAllResolvedComplaintAdmin(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
    )
    {
        try {
            List<Tbl_Complaints> response = adminCompService.getAllResolvedComplaints(authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "All Complaint fetched successfully",
                    response
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/get/allcomplaint/status")
    public ResponseEntity<APIResponseBody> getAllComplaintByStatus(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam ComplainStatus status
    )
    {
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint fetched successfully",
                    adminCompService.getComplaintByStatus(authToken, status)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/update/status/admin/complaint")
    public ResponseEntity<APIResponseBody> updateComplainAdmin(
            @RequestParam String complainId,
            @RequestParam ComplainStatus status,
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
    ){
        try {
            Tbl_Complaints response = adminCompService.updateComplaint(authToken,complainId,status);
            return new ResponseEntity<>(new APIResponseBody(
                    "Replied successfully",
                    response
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/getall/id")
    public ResponseEntity<APIResponseBody> getAllComplaintById(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String id
    ){
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint Found",
                    adminCompService.getComplaint(authToken, id)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/get/complaintId/{complaintId}")
    public ResponseEntity<APIResponseBody> getComplaintByComplaintId(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @PathVariable String complaintId
    ){
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint Found Successfully",
                    adminCompService.getComplaintByID(authToken, complaintId)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/reassign/engineer/")
    public ResponseEntity<APIResponseBody> reAssignEngineer(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String complaintId,
            @RequestParam String engineerId
    ){
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint Found Successfully",
                    adminCompService.reAssignEngineer(authToken,engineerId,complaintId)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getall/count", name = "Get all admin complaint count")
    public ResponseEntity<APIResponseBody> getAllCount(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
    ){
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Complaint Found Successfully",
                    adminCompService.getAllCount(authToken)
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
            Tbl_Admin_Users response = service.changePassword(authToken,currentPassword,newPassword);
            return new ResponseEntity<>(new APIResponseBody(
                    "Password changed successfully",
                    response
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/excel/upload", name = "Upload Customer Excel to DB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponseBody> uploadExcel(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam MultipartFile file
    ){
        try {
            adminCompService.getDataFromExcel(file, authToken);
            return new ResponseEntity<>(new APIResponseBody(
                    "Excel Uploaded Successfully",
                    "Excel Uploaded Successfully"
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/excel/asset/upload", name = "Upload Asset Details to DB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadAssetExcel(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam MultipartFile file
    ){
        try {
            return new ResponseEntity<>(new APIResponseBody(
                    "Excel Uploaded Successfully",
                    adminCompService.getAssetDataFromExcel(file, authToken)
            ), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getall/pending/resolved/count", name = "Get All Pending Resolved Complaints Count")
    public ResponseEntity<APIResponseBody> getAllPendingResolvedCount(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String status
    ){
        try{
            return new ResponseEntity<>(new APIResponseBody("Count Fetch Successfully",adminCompService.getAllPendingResolvedCount(authToken, status)),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/excel/asset/download", name = "Download Asset Excel")
    public ResponseEntity<Object> downloadAssetExcel(
            @RequestParam String customerId
    ){
        try {
            InputStreamResource file = new InputStreamResource(adminCompService.generateAssetExcel(customerId));
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + "asset_details.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/excel/customer/download", name = "Download Customer Excel")
    public ResponseEntity<Object> downloadCustomerExcel(
    ){
        try {
            InputStreamResource file = new InputStreamResource(adminCompService.generateCustomerExcel());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + "user_details.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/chatby/complaintId", name = "Get All Chats By Complaint Id")
    public ResponseEntity<APIResponseBody> getChatById(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String complaintId
    ){
        try{
            return new ResponseEntity<>(new APIResponseBody("Count Fetch Successfully",
                    adminCompService.getChatbyId(authToken, complaintId)),
                    HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/admin/excel", name = "Get All Admin In Excel")
    public ResponseEntity<Object> getAdminExcel(
    ){
        try{
            InputStreamResource inputStreamResource = new InputStreamResource(adminCompService.getAllAdminExcel());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + "all_admin_details.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(inputStreamResource);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get/engineer/excel", name = "Get All Engineer In Excel")
    public ResponseEntity<Object> getEngineerExcel(
    ){
        try{
            InputStreamResource file = new InputStreamResource(adminCompService.getAllEngineerExcel());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + "all_engineer_details.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()),HttpStatus.OK);
        }
    }

    @PostMapping("/update/customer")
    public ResponseEntity<APIResponseBody> updateCustomer(
            @RequestBody Customer_Request_Body data,
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String customerId
    ) throws Exception{
        try {
            Tbl_Customer_details res = service.updateCustomer(data, authToken, customerId);
            return new ResponseEntity<>(new APIResponseBody("Customer Updated Successfully",
                    res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/create/complaint",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, name = "Create Complaint Admin")
    public ResponseEntity<APIResponseBody> createComplaint(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken,
            @RequestParam String remarks,
            @RequestParam String customerId,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String complaintCategory
    ){
        try {
            Tbl_Complaints complaints = adminCompService.createComplaint(authToken,customerId, remarks, file, complaintCategory);
            return new ResponseEntity<>(new APIResponseBody("Complaint Created Successfully", complaints),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/get/customer/{id}")
    public ResponseEntity<APIResponseBody> getCustomerById(@RequestHeader(name = "X-AUTH-TOKEN") String authToken,
                                                           @PathVariable(name = "id") String id)
    {
        try {
            return new ResponseEntity<>(new APIResponseBody("Customer Details Fetched Successfully",
                    service.getCustomerById(authToken, id)), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/update/admin")
    public ResponseEntity<APIResponseBody> updateAdmin(@RequestHeader(name = "X-AUTH-TOKEN") String authToken,
                                                       @RequestBody AdminUpdateRequestBody body)
    {
        try {
            return new ResponseEntity<>(new APIResponseBody("Admin Updated Successfully", service.updateAdmin(authToken, body)), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/update/engineer")
    public ResponseEntity<APIResponseBody> updateEngineer(@RequestHeader(name = "X-AUTH-TOKEN") String authToken,
                                                       @RequestBody EngineerUpdateRequestBody body)
    {
        try {
            return new ResponseEntity<>(new APIResponseBody("Admin Updated Successfully", service.updateEngineer(authToken, body)), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }
}
