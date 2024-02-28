package com.ultrasoft.ultracomplaint.serviceimpl;

import com.google.gson.Gson;
import com.ultrasoft.ultracomplaint.entity.*;
import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import com.ultrasoft.ultracomplaint.enums.FirstTimeLogin;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.mapper.Complain_Mapper;
import com.ultrasoft.ultracomplaint.repo.*;
import com.ultrasoft.ultracomplaint.requestbody.Data;
import com.ultrasoft.ultracomplaint.service.Admin_Complaint_Service;
import com.ultrasoft.ultracomplaint.service.Login_Session_Service;
import com.ultrasoft.ultracomplaint.utility.Push_Notification_Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Admin_Complaint_Service_impl implements Admin_Complaint_Service {

    private final Login_Session_Service session;

    private final Chat_Repo chatR;

    private final Engineer_Repo engRepo;

    private final Customer_Repo customerRepo;

    private final Asset_Category_Repo assetCategoryRepo;

    private final Asset_Type_Repo assetTypeRepo;

    private final Asset_Status_Repo assetStatusRepo;

    private final Asset_Details_Repo asset_Details_Repo;

    private final Admin_Repo admin_Repo;

    private final Chat_Repo complaintRepo;

    private final Push_Notification_Service pushNotificationService;

    public Admin_Complaint_Service_impl(Login_Session_Service session, Chat_Repo chatR, Engineer_Repo engRepo, Customer_Repo customerRepo, Asset_Category_Repo assetCategoryRepo, Asset_Type_Repo assetTypeRepo, Asset_Status_Repo assetStatusRepo,
                                        Asset_Details_Repo asset_Details_Repo,
                                        Admin_Repo admin_Repo, Chat_Repo complaintRepo, Push_Notification_Service pushNotificationService) {
        this.session = session;
        this.chatR = chatR;
        this.engRepo = engRepo;
        this.customerRepo = customerRepo;
        this.assetCategoryRepo = assetCategoryRepo;
        this.assetTypeRepo = assetTypeRepo;
        this.assetStatusRepo = assetStatusRepo;
        this.asset_Details_Repo = asset_Details_Repo;
        this.admin_Repo = admin_Repo;
        this.complaintRepo = complaintRepo;
        this.pushNotificationService = pushNotificationService;
    }


    @Override
    public List<Tbl_Complaints> getAllComplaints(String authToken, ComplainStatus status) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            List<Tbl_Complaints> allcomp = chatR.findByStatusOrderByCreatedDateDesc(status);
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
    public Tbl_Complaints replyOnComplaint(MultipartFile file, String complaintId, String remarks, String authToken, ComplainStatus status) throws Exception {
        try {
            Tbl_Admin_Users admin = session.getSessionForAdmin(authToken);
            Tbl_Complaints ncomp = chatR.findByComplainId(complaintId);
            if(ncomp != null){
                if(status!=null || status.equals("")){
                    Tbl_Complaints complaints = Complain_Mapper.replyByAdmin(ncomp,admin, remarks, file);
                    if (status.equals(ComplainStatus.UN_ASSIGNED)){
                        complaints.setStatus(ComplainStatus.UN_ASSIGNED);
                        complaints.setAssignedToEngineer(null);
                        complaints.setAssignedByAdmin(null);
                    }
                    else {
                        complaints.setStatus(status);
                    }
                    chatR.save(complaints);
                    return ncomp;
                }
                chatR.save(Complain_Mapper.replyByAdmin(ncomp,admin, remarks, file));
                Data data = new Data();
                data.setText("Admin Replied on your Complaint, Click to check details");
                data.setTitle(ncomp.getComplainId() + " " + ncomp.getCreatedDate().toString());
                data.setStyle("BigText");
                data.setNumber(ncomp.getCreatedByCUstomer().getCustomerMobile());
//                pushNotificationService.sendPushNotificationToCustomer(data);
//                if (ncomp.getAssignedToEngineer()!=null){
//                    data.setNumber(ncomp.getAssignedToEngineer().getEngineerMobile());
//                    pushNotificationService.sendPushNotification(data);
//                }
                return ncomp;
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
    public Tbl_Complaints closeComplaint(String complaintId, String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            Tbl_Complaints ncomp = chatR.findByComplainId(complaintId);
            if(ncomp != null){
                if(ncomp.getStatus().equals(ComplainStatus.CLOSED)){
                    throw new Exception("Complain is already closed");
                }
                else{
                    ncomp.setStatus(ComplainStatus.CLOSED);
                    ncomp.setTicketClosedDate(new Date());
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
    public Tbl_Complaints assignedToEngineer(String complaintId, String engineerId,String authToken) throws Exception {
        try {
            Tbl_Admin_Users admin = session.getSessionForAdmin(authToken);
            Tbl_Complaints ncomp = chatR.findByComplainId(complaintId);
            Tbl_Engineer_Details eng = engRepo.findByEngineerId(engineerId);
            if(ncomp != null && eng != null){
                if(ncomp.getStatus().equals(ComplainStatus.CLOSED)){
                    throw new Exception("Complain is already closed");
                }
                else{
                    Tbl_Complaints response = Complain_Mapper.assignTOEngineer(ncomp,admin,eng);
                    chatR.save(response);
                    if (eng.getNotificationId()!=null){
                        Data body = new Data();
                        body.setNumber(eng.getEngineerMobile());
                        body.setStyle("BigText");
                        body.setTitle("Complaint Assigned To You");
                        body.setText(ncomp.getComplainId()+" "+ncomp.getCreatedDate().toString());
                        pushNotificationService.sendPushNotification(body);
                    }
                    return response;
                }
            }
            else{
                throw new Exception("Invalid complaint/engineer details");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public long countComplaint(ComplainStatus status,String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            long daat = chatR.countByStatus(status);
            return daat;
        }
        catch (Exception e){
            throw new Exception("Invalid complaint id");
        }
    }

    @Override
    public Map<String, String> getAssignedDetails(String authToken, String complaintId) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            Tbl_Complaints complaints = chatR.findByComplainId(complaintId);
            Map<String, String> map = new HashMap<>();
            String AssignedTo = complaints.getAssignedToEngineer().getEngineerName();
            String AssignedBy = complaints.getAssignedByAdmin().getName();
            map.put("AssignedBy",AssignedBy);
            map.put("AssignedTo",AssignedTo);
            return map;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ComplainStatus getComplaintStatus(String authToken, String complaintId) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            Tbl_Complaints complaints = chatR.findByComplainId(complaintId);
            return complaints.getStatus();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Complaints> getAllResolvedComplaints(String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            List<Tbl_Complaints> allResolved = chatR.findByStatus(ComplainStatus.RESOLVED);
            List<Tbl_Complaints> allClosed = chatR.findByStatus(ComplainStatus.CLOSED);
            allResolved.addAll(allClosed);
            if(allResolved.isEmpty() && allClosed.isEmpty()){
                throw new Exception("No record found");
            }
            else{
                return allResolved;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Complaints> getAllPendingResolvedComplaints(String authToken, String status) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (status.equals("PENDING")){
                List<Tbl_Complaints> allResolved = chatR.findByStatus(ComplainStatus.IN_PROGRESS);
                List<Tbl_Complaints> allClosed = chatR.findByStatus(ComplainStatus.UN_ASSIGNED);
                allResolved.addAll(allClosed);
                Collections.sort(allResolved, Tbl_Complaints.compareByCreatedDate());
                if(allResolved.isEmpty() && allClosed.isEmpty()){
                    throw new Exception("No record found");
                }
                else{
                    return allResolved;
                }
            }
            else if (status.equals("RESOLVED")){
                List<Tbl_Complaints> allResolved = chatR.findByStatus(ComplainStatus.RESOLVED);
                List<Tbl_Complaints> allClosed = chatR.findByStatus(ComplainStatus.CLOSED);
                allResolved.addAll(allClosed);
                Collections.sort(allResolved, Tbl_Complaints.compareByUpdatedDate());
                if(allResolved.isEmpty() && allClosed.isEmpty()){
                    throw new Exception("No record found");
                }
                else{
                    return allResolved;
                }
            }
            else {
                throw new Exception("Invalid status");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Complaints> getComplaintByStatus(String authToken, ComplainStatus status) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (status.equals(ComplainStatus.CLOSED)){
                List<Tbl_Complaints> allComplaints = chatR.findByStatus(status);
                Collections.sort(allComplaints, Tbl_Complaints.compareByCreatedDate());
                if(allComplaints.isEmpty()){
                    throw new Exception("No record found");
                }
                return allComplaints;
            }
            else if (status.equals(ComplainStatus.UN_ASSIGNED)){
                List<Tbl_Complaints> allComplaints = chatR.findByStatus(status);
                Collections.sort(allComplaints, Tbl_Complaints.compareByCreatedDate());
                if(allComplaints.isEmpty()){
                    throw new Exception("No record found");
                }
                return allComplaints;
            }
            else if (status.equals(ComplainStatus.IN_PROGRESS)){
                List<Tbl_Complaints> allComplaints = chatR.findByStatus(status);
                Collections.sort(allComplaints, Tbl_Complaints.compareByCreatedDate());
                if(allComplaints.isEmpty()){
                    throw new Exception("No record found");
                }
                return allComplaints;
            }
            else {
                List<Tbl_Complaints> allComplaints = chatR.findByStatus(status);
                if(allComplaints.isEmpty()){
                    throw new Exception("No record found");
                }
                return allComplaints;
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints updateComplaint(String authToken, String complaintId, ComplainStatus status) throws Exception {
        try {
            Tbl_Admin_Users admin = session.getSessionForAdmin(authToken);
            Tbl_Complaints ncomp = chatR.findByComplainId(complaintId);
            if(ncomp != null){
//                if (ncomp.getStatus().equals(ComplainStatus.IN_PROGRESS)){
//                    throw new Exception("Complaint is already "+ComplainStatus.IN_PROGRESS.name());
//                }
                if (ncomp.getStatus().equals(status)){
                    throw new Exception("Complaint is already "+status.name());
                }
//                if(ncomp.getStatus().equals(ComplainStatus.RESOLVED)){
//                    ncomp.setStatus(status);
//                    chatR.save(ncomp);
//                    return ncomp;
//                }
//                else{
//                    throw new Exception("Complaint is "+ncomp.getStatus());
//                }
                if (status.equals(ComplainStatus.UN_ASSIGNED)){
                    ncomp.setStatus(status);
                    ncomp.setAssignedToEngineer(null);
                }
                else {
                    ncomp.setStatus(status);
                }
                ncomp.setUpdatedDate(new Date());
                chatR.save(ncomp);
                return ncomp;
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
    public Object getComplaint(String authToken, String id) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (id.contains("ULTCOMP")){
                Tbl_Complaints complaints = chatR.findByComplainId(id);
                if (complaints==null){
                    throw new Exception("Invalid Complaint Id");
                }
                return complaints;
            }
            else if (id.contains("@")){
                List<Tbl_Complaints> complaints = chatR.findByCreatedByCUstomer(customerRepo.findByCustomerEmail(id));
                if (complaints.isEmpty()){
                    throw new Exception("No records found");
                }
                return complaints;
            }
            else if (id.matches("\\d+")){
                List<Tbl_Complaints> complaints = chatR.findByCreatedByCUstomer(customerRepo.findByCustomerMobile(id));
                if (complaints == null){
                    throw new Exception("No records found");
                }
                return complaints;
            }
            else {
                throw new Exception("No records found");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints getComplaintByID(String authToken, String complaintId) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (complaintId.contains("ULTCOMP")){
                Tbl_Complaints complaints = chatR.findByComplainId(complaintId);
                if (complaints==null){
                    throw new Exception("No records found");
                }
                return complaints;
            }
            else {
                throw new Exception("Invalid Complaint Id");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints reAssignEngineer(String authToken, String engineerId, String compliantId) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            Tbl_Complaints ncomp = chatR.findByComplainId(compliantId);
            Tbl_Engineer_Details engineerDetails = engRepo.findByEngineerId(engineerId);
            ncomp.setAssignedToEngineer(engineerDetails);
            return ncomp;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Map<String, Integer> getAllCount(String authToken) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            List<Tbl_Complaints> unassigned = chatR.findByStatus(ComplainStatus.UN_ASSIGNED);
            List<Tbl_Complaints> inprogress = chatR.findByStatus(ComplainStatus.IN_PROGRESS);
            List<Tbl_Complaints> resolved = chatR.findByStatus(ComplainStatus.RESOLVED);
            List<Tbl_Complaints> closed = chatR.findByStatus(ComplainStatus.CLOSED);
            Map<String, Integer> map = new HashMap<>();
            map.put("UN_ASSIGNED", unassigned.size());
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
    public void getDataFromExcel(MultipartFile file, String authToken) throws Exception {
        try{
            session.getSessionForAdmin(authToken);
            dataToDb(file.getInputStream());
//            Workbook workbook = new XSSFWorkbook(file.getInputStream());
//
//            Sheet sheet = workbook.getSheetAt(0);
//
//            List<Tbl_Customer_details> customerDetails = new ArrayList<>();
//            int rowHeading = 0;
//            long id = customerRepo.count();
//            int rows = sheet.getPhysicalNumberOfRows();
//            System.out.println(rows);
//            for (Row row : sheet) {
//                if (rowHeading==0 || row.getCell(rowHeading).getStringCellValue().equals("")){
//                    rowHeading++;
//                    continue;
//                }
//                int colIndex=0;
//                Tbl_Customer_details customerDetails1 = null;
//                if (row.getCell(9)!=null){
//                    customerDetails1 = customerRepo.findByCustomerId(row.getCell(9).getStringCellValue());
//                }
//                if (customerDetails1==null){
//                    customerDetails1 = new Tbl_Customer_details();
//                    customerDetails1.setIsFirstTimeLogin(FirstTimeLogin.YES);
//                    customerDetails1.setActiveStatus(ActiveStaus.ACTIVE);
//                    if (id<9) {
//                        customerDetails1.setCustomerId("C000"+String.valueOf(id +1));
//                    }
//                    else if (id<99) {
//                        customerDetails1.setCustomerId("C00"+String.valueOf(id +1));
//                    }
//                    else if (id<999) {
//                        customerDetails1.setCustomerId("C0"+String.valueOf(id +1));
//                    }
//                    else {
//                        customerDetails1.setCustomerId("C"+String.valueOf(id +1));
//                    }
//                    id++;
//                }
//                for (Cell cell : row) {
//                    switch (colIndex){
//                        case 0:
//                            if (cell==null){
//                                customerDetails1.setCustomerName(null);
//                            }
//                            else {
//                                customerDetails1.setCustomerName(cell.getStringCellValue());
//                            }
//                            colIndex++;
//                            break;
//                        case 1:
//                            if (cell==null){
//                                customerDetails1.setCompanyName("");
//                            }
//                            else {
//                                customerDetails1.setCompanyName(cell.getStringCellValue());
//                            }
//                            colIndex++;
//                            break;
//                        case 2:
//                            if (cell==null){
////                                customerDetails1.setAddress1(null);
//                                throw new Exception("Address 1 is required");
//                            }
//                            else {
//                                customerDetails1.setAddress1(cell.getStringCellValue());
//                            }
//                            colIndex++;
//                            break;
//                        case 3:
//                            if (cell==null){
//                                customerDetails1.setAddress2(null);
//                            }
//                            else {
//                                customerDetails1.setAddress2(cell.getStringCellValue());
//                            }
//                            colIndex++;
//                            break;
//                        case 4:
//                            if (cell==null){
//                                customerDetails1.setCustomerCity(null);
//                            }
//                            else {
//                                customerDetails1.setCustomerCity(cell.getStringCellValue());
//                            }
//                            colIndex++;
//                            break;
//                        case 5:
//                            if (cell==null){
//                                customerDetails1.setCustomerState(null);
//                            }
//                            else {
//                                customerDetails1.setCustomerState(cell.getStringCellValue());
//                            }
//                            colIndex++;
//                            break;
//                        case 6:
//                            if (cell==null){
//                                customerDetails1.setPinCode(null);
//                            }
//                            else {
//                                customerDetails1.setPinCode(String.valueOf((long) cell.getNumericCellValue()));
//                            }
//                            colIndex++;
//                            break;
//                        case 7:
//                            if (cell==null){
//                                customerDetails1.setCustomerGSTNumber(null);
//                            }
//                            else {
//                                customerDetails1.setCustomerGSTNumber(cell.getStringCellValue());
//                            }
//                            colIndex++;
//                            break;
//                        case 8:
//                            if (cell==null){
//                                throw new Exception("Mobile Number is required");
//                            }
//                            customerDetails1.setCustomerMobile(String.valueOf((long) cell.getNumericCellValue()));
//                            colIndex++;
//                            break;
//                        case 9:
//                            if (cell!=null && !cell.getStringCellValue().equals("")){
//                                customerDetails1.setCustomerEmail(cell.getStringCellValue());
//                            }
//                            colIndex++;
//                            break;
//                        case 10:
//                            if (cell!=null && !cell.getStringCellValue().equals("")){
//                                customerDetails1.setCustomerId(cell.getStringCellValue());
//                            }
//                            colIndex++;
//                            break;
//                        default:
//                            break;
//                    }
//                }
//                customerDetails1.setCreatedDate(new Date());
//                System.out.println(new Gson().toJson(customerDetails1));
//                customerDetails.add(customerDetails1);
//            }
//
//            customerRepo.saveAll(customerDetails);
//
//            workbook.close();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private void dataToDb(InputStream inputStream) throws Exception{
        try {
            Workbook workbook = null;
            List<Tbl_Customer_details> list = new ArrayList<>();
            workbook = WorkbookFactory.create(inputStream);
            workbook.forEach(sheet -> {
                int index = 0;
                long id = customerRepo.count();
                for (Row row : sheet){
                    if(index++==0){
                        continue;
                    }
                    Tbl_Customer_details customerDetails = null;
                    if (row.getCell(10)!=null){
                        customerDetails = customerRepo.findByCustomerId(row.getCell(10).getStringCellValue());
                        if (customerDetails==null){
                            customerDetails = new Tbl_Customer_details();
                        }
                    }else {
                        customerDetails = new Tbl_Customer_details();
                        if (id<9) {
                            customerDetails.setCustomerId("C000"+String.valueOf(id +1));
                        }
                        else if (id<99) {
                            customerDetails.setCustomerId("C00"+String.valueOf(id +1));
                        }
                        else if (id<999) {
                            customerDetails.setCustomerId("C0"+String.valueOf(id +1));
                        }
                        else {
                            customerDetails.setCustomerId("C"+String.valueOf(id +1));
                        }
                        id++;
                    }
                    if (row.getCell(0)!=null){
                        customerDetails.setCustomerName(row.getCell(0).getStringCellValue());
                    }
                    if (row.getCell(1)!=null){
                        customerDetails.setCompanyName(row.getCell(1).getStringCellValue());
                    }
                    if (row.getCell(2)!=null){
                        customerDetails.setAddress1(row.getCell(2).getStringCellValue());
                    }
                    if (row.getCell(3)!=null){
                        customerDetails.setAddress2(row.getCell(3).getStringCellValue());
                    }
                    if (row.getCell(4)!=null){
                        customerDetails.setCustomerCity(row.getCell(4).getStringCellValue());
                    }
                    if (row.getCell(5)!=null){
                        customerDetails.setCustomerState(row.getCell(5).getStringCellValue());
                    }
                    if (row.getCell(6)!=null){
                        customerDetails.setPinCode(String.valueOf(row.getCell(6).getNumericCellValue()));
                    }
                    if (row.getCell(7)!=null){
                        customerDetails.setCustomerGSTNumber(row.getCell(7).getStringCellValue());
                    }
                    if (row.getCell(8)!=null){
                        customerDetails.setCustomerMobile(String.valueOf((long) row.getCell(8).getNumericCellValue()));
                    }
                    if (row.getCell(9)!=null){
                        customerDetails.setCustomerEmail(row.getCell(9).getStringCellValue());
                    }
                    customerDetails.setActiveStatus(ActiveStaus.ACTIVE);
                    customerDetails.setCreatedDate(new Date());
                    customerDetails.setUpdatedDate(new Date());
                    list.add(customerDetails);
                    System.out.println(new Gson().toJson(customerDetails));
                }
                customerRepo.saveAll(list);
            });
            workbook.close();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getAssetDataFromExcel(MultipartFile file, String authToken) throws Exception {
        try{
            session.getSessionForAdmin(authToken);
            Workbook workbook = new XSSFWorkbook(file.getInputStream());

            Sheet sheet = workbook.getSheetAt(0);
            List<Tbl_Asset_Details> assetDetails = new ArrayList<>();
            int rowHeading = 0;
            long assets = asset_Details_Repo.count();
            for (Row row : sheet) {
                if (rowHeading==0){
                    rowHeading++;
                    continue;
                }

                Tbl_Asset_Details assetDetail = null;
                if (row.getCell(1)!=null){
                    Tbl_Asset_Details assetDetails2 = asset_Details_Repo.findByAssetId(row.getCell(1).getStringCellValue());
                    if (assetDetails2!=null){
                        assetDetail= assetDetails2;
                    }
                }
                else {
                    assetDetail = new Tbl_Asset_Details();
                    assetDetail.setAssetId("ASTD"+(assets++));
                }
                int colIndex=0;
                if (row.getCell(0)==null){
                    throw new Exception("Customer Id cannot be null");
                }
                Tbl_Customer_details customerDetails = customerRepo.findByCustomerId(row.getCell(0).getStringCellValue());
                if (customerDetails==null){
                   throw new Exception("User "+row.getCell(0).getStringCellValue()+" not found.");
                }

                for (Cell cell : row) {
                    switch (colIndex){
                        case 0:
                            if (row.getCell(0)==null){
                                throw new Exception("Customer Code is required");
                            }
                            if (row.getCell(0).getStringCellValue()==null){
                                throw new Exception("Customer Code is required");
                            }
                            assetDetail.setCustomerDetails(customerDetails);
                            colIndex++;
                            break;
                        case 1:
                            if (row.getCell(1)==null){
                                colIndex++;
                                break;
                            }
//                            Tbl_Asset_Details assetDetails1 = asset_Details_Repo.findByAssetId(cell.getStringCellValue());
//                            if (assetDetails1==null){
//                                throw new Exception("Invalid Asset Id");
//                            }
//                            assetDetail.setAssetId(cell.getStringCellValue());
                            colIndex++;
                            break;
                        case 2:
                            if (row.getCell(2)==null){
                                throw new Exception("Asset Category is required");
                            }
                            Tbl_Asset_Category assetCategory = assetCategoryRepo.findByAssetCategoryName(row.getCell(2).getStringCellValue());
                            if (assetCategory==null){
                                assetCategory = new Tbl_Asset_Category();
                                assetCategory.setAssetCategoryName(row.getCell(2).getStringCellValue());
                                assetCategory.setCreatedDate(new Date());
                                assetCategoryRepo.save(assetCategory);
                            }
                            assetDetail.setAssetCategory(assetCategory);
                            colIndex++;
                            break;
                        case 3:
                            if (row.getCell(3)==null){
                                throw new Exception("Asset Type is required");
                            }
                            Tbl_Asset_Type assetType = assetTypeRepo.findByAssetTypeName(row.getCell(3).getStringCellValue());
                            if (assetType==null){
                                assetType = new Tbl_Asset_Type();
                                assetType.setAssetTypeId("ASTY"+(assetTypeRepo.count()));
                                assetType.setCreatedDate(new Date());
                                assetType.setAssetTypeName(row.getCell(2).getStringCellValue());
                                assetTypeRepo.save(assetType);
                            }
                            else {
                                assetType.setAssetTypeName(row.getCell(3).getStringCellValue());
                            }
                            assetDetail.setAssetType(assetType);
                            colIndex++;
                            break;
                        case 4:
                            if (row.getCell(4)==null){
                                throw new Exception("Asset Details is required");
                            }
                            assetDetail.setAssetDetails(row.getCell(4).getStringCellValue());
                            colIndex++;
                            break;
                        case 5:
                            try {
                                assetDetail.setSerialNo(String.valueOf((int) row.getCell(5).getNumericCellValue()));
                            }
                            catch (Exception e){
                                assetDetail.setSerialNo(row.getCell(5).getStringCellValue());
                            }
                            colIndex++;
                            break;
                        case 6:
                            if (row.getCell(6)==null){
                                throw new Exception("Asset Status is required");
                            }
                            Tbl_Asset_Status assetStatus = assetStatusRepo.findByAssetStatusName(row.getCell(6).getStringCellValue());
                            if (assetStatus==null){
                                assetStatus = new Tbl_Asset_Status();
                                assetStatus.setCreatedDate(new Date());
                                assetStatus.setAssetStatusName(row.getCell(6).getStringCellValue());
                                assetStatus.setAssetStatusId("ASTS"+ assetStatusRepo.count());
                                assetStatusRepo.save(assetStatus);
                            }
                            assetDetail.setAssetStatus(assetStatus);
                            colIndex++;
                            break;
                        case 7:
                            if (row.getCell(7)==null){
                                assetDetail.setExpiryDate(null);
                            }
                            else{
                                try {
                                    assetDetail.setExpiryDate(row.getCell(7).getDateCellValue());
                                }
                                catch (Exception e){
                                    try {
                                        assetDetail.setExpiryDate(new SimpleDateFormat("dd/MM/yyyy").parse(row.getCell(7).getStringCellValue()));
                                    }
                                    catch (Exception exception){
                                        assetDetail.setExpiryDate(null);
                                    }
                                }
                            }
                            colIndex++;
                            break;
                        default:
                            break;
                    }
                }
                assetDetail.setCreatedDate(new Date());
                assetDetails.add(assetDetail);
            }

            asset_Details_Repo.saveAll(assetDetails);
            System.out.println("Data saved");
            workbook.close();

            return "Successful";
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream generateCustomerExcel() throws Exception {
        String[] heading = {"Customer Name", "Company Name", "Address1", "Address2", "City", "State", "Pin code", "GST Number", "Contact Number","Email Id", "Customer Code","Status", "Created Date", "Updated Date"};
        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("CustomerDetails");
            Row headerRow = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i=0; i<heading.length; i++){
                Cell cell =headerRow.createCell(i);
                cell.setCellValue(heading[i]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowIndex = 1;
            List<Tbl_Customer_details> assetDetails = customerRepo.findAll();
            for (Tbl_Customer_details assetDetails1 : assetDetails){
                Row row = sheet.createRow(rowIndex++);
                //Name
                if (assetDetails1.getCustomerName()==null){
                    row.createCell(0).setCellValue("");
                }
                else {
                    row.createCell(0).setCellValue(assetDetails1.getCustomerName());
                }
                //Company Name
                if (assetDetails1.getCompanyName()==null){
                    row.createCell(1).setCellValue("");
                }
                else {
                    row.createCell(1).setCellValue(assetDetails1.getCompanyName());
                }
                //Address 1
                if (assetDetails1.getAddress1()==null){
                    row.createCell(2).setCellValue("");
                }
                else {
                    row.createCell(2).setCellValue(assetDetails1.getAddress1());
                }
                //Address 2
                if (assetDetails1.getAddress2()==null){
                    row.createCell(3).setCellValue("");
                }
                else {
                    row.createCell(3).setCellValue(assetDetails1.getAddress2());
                }
                //City
                if (assetDetails1.getCustomerCity()==null){
                    row.createCell(4).setCellValue("");
                }
                else {
                    row.createCell(4).setCellValue(assetDetails1.getCustomerCity());
                }
                //State
                if (assetDetails1.getCustomerState()==null){
                    row.createCell(5).setCellValue("");
                }
                else {
                    row.createCell(5).setCellValue(assetDetails1.getCustomerState());
                }
                //Pin Code
                if (assetDetails1.getPinCode()==null){
                    row.createCell(6).setCellValue("");
                }
                else {
                    row.createCell(6).setCellValue(Integer.parseInt(assetDetails1.getPinCode()));
                }
                //GST Number
                if (assetDetails1.getCustomerGSTNumber()==null){
                    row.createCell(7).setCellValue("");
                }
                else {
                    row.createCell(7).setCellValue(String.valueOf(assetDetails1.getCustomerGSTNumber()));
                }
                //Mobile
                if (assetDetails1.getCustomerMobile()==null){
                    row.createCell(8).setCellValue("");
                }
                else {
                    row.createCell(8).setCellValue(assetDetails1.getCustomerMobile());
                }
                //Email Id
                if (assetDetails1.getCustomerEmail()==null){
                    row.createCell(9).setCellValue("");
                }
                else {
                    row.createCell(9).setCellValue(assetDetails1.getCustomerEmail());
                }
                //Customer Id
                if (assetDetails1.getCustomerId()==null){
                    row.createCell(10).setCellValue("");
                }
                else {
                    row.createCell(10).setCellValue(assetDetails1.getCustomerId());
                }
                //Active Status
                if (assetDetails1.getActiveStatus()==null){
                    row.createCell(11).setCellValue("");
                }
                else {
                    row.createCell(11).setCellValue(assetDetails1.getActiveStatus().name());
                }
                //Created Date
                if (assetDetails1.getCreatedDate()==null){
                    row.createCell(12).setCellValue("");
                }
                else {
                    row.createCell(12).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(assetDetails1.getCreatedDate()));
                }
                //Updated Date
                if (assetDetails1.getUpdatedDate()==null){
                    row.createCell(13).setCellValue("");
                }
                else {
                    row.createCell(13).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(assetDetails1.getUpdatedDate()));
                }
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream generateAssetExcel(String customerId) throws Exception {
        String[] heading = {"Customer code","Asset Id","Asset Category","Asset Type","Asset Details","Serial No.", "Status", "Expiry Date", "Created Date", "Updated Date"};
        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("AssetDetails");
            Row headerRow = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i=0; i<heading.length; i++){
                Cell cell =headerRow.createCell(i);
                cell.setCellValue(heading[i]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowIndex = 1;
            Tbl_Customer_details customerDetails = customerRepo.findByCustomerId(customerId);
            if (customerDetails==null){
                throw new Exception("Invalid Customer Id");
            }
            List<Tbl_Asset_Details> assetDetails = asset_Details_Repo.findByCustomerDetails(customerDetails);
            for (Tbl_Asset_Details assetDetails1 : assetDetails){
                Row row = sheet.createRow(rowIndex++);
                if (assetDetails1.getCustomerDetails()==null){
                    row.createCell(0).setCellValue("");
                }
                else {
                    row.createCell(0).setCellValue(assetDetails1.getCustomerDetails().getCustomerId());
                }
                if (assetDetails1.getAssetId()==null){
                    row.createCell(1).setCellValue("");
                }
                else {
                    row.createCell(1).setCellValue(assetDetails1.getAssetId());
                }
                if (assetDetails1.getAssetCategory()==null){
                    row.createCell(2).setCellValue("");
                }
                else {
                    row.createCell(2).setCellValue(assetDetails1.getAssetCategory().getAssetCategoryName());
                }
                if (assetDetails1.getAssetType()==null){
                    row.createCell(3).setCellValue("");
                }
                else {
                    row.createCell(3).setCellValue(assetDetails1.getAssetType().getAssetTypeName());
                }
                if (assetDetails1.getAssetDetails()==null || assetDetails1.getAssetDetails().equals("")){
                    row.createCell(4).setCellValue("");
                }
                else {
                    row.createCell(4).setCellValue(assetDetails1.getAssetDetails());
                }
                if (assetDetails1.getSerialNo()==null){
                    row.createCell(5).setCellValue("");
                }
                else {
                    row.createCell(5).setCellValue(Integer.parseInt(assetDetails1.getSerialNo()));
                }
                if (assetDetails1.getAssetStatus()==null){
                    row.createCell(6).setCellValue("");
                }
                else {
                    row.createCell(6).setCellValue(assetDetails1.getAssetStatus().getAssetStatusName());
                }
                if (assetDetails1.getExpiryDate()==null){
                    row.createCell(7).setCellValue("");
                }
                else {
                    row.createCell(7).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(assetDetails1.getExpiryDate()));
                }
                if (assetDetails1.getCreatedDate()==null){
                    row.createCell(8).setCellValue("");
                }
                else {
                    row.createCell(8).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(assetDetails1.getCreatedDate()));
                }
                if (assetDetails1.getUpdatedDate()==null){
                    row.createCell(9).setCellValue("");
                }
                else {
                    row.createCell(9).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(assetDetails1.getUpdatedDate()));
                }
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public int getAllPendingResolvedCount(String authToken, String status) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (status.equals("PENDING")){
                List<Tbl_Complaints> pending = chatR.findByStatus(ComplainStatus.UN_ASSIGNED);
                List<Tbl_Complaints> pending1 = chatR.findByStatus(ComplainStatus.IN_PROGRESS);
                return pending1.size() + pending.size();
            }
            else if (status.equals("RESOLVED")){
                List<Tbl_Complaints> pending = chatR.findByStatus(ComplainStatus.RESOLVED);
                List<Tbl_Complaints> pending1 = chatR.findByStatus(ComplainStatus.CLOSED);
                return pending1.size() + pending.size();
            }
            else {
                throw new Exception("Invalid status");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Tbl_Complaints_Chats> getChatbyId(String authToken, String complaintId) throws Exception {
        try {
            session.getSessionForAdmin(authToken);
            if (complaintId==null){
                throw new Exception("ComplaintId is required");
            }
            Tbl_Complaints complaints = chatR.findByComplainId(complaintId);
            return complaints.getComplaintChats();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream getAllAdminExcel() throws Exception {
        String[] heading = {"Admin id","Name","Phone number","Email","Status"};
        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("AdminDetails");
            Row headerRow = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i=0; i<heading.length; i++){
                Cell cell =headerRow.createCell(i);
                cell.setCellValue(heading[i]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowIndex = 1;
            List<Tbl_Admin_Users> assetDetails = admin_Repo.findAll();
            for (Tbl_Admin_Users assetDetails1 : assetDetails){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(assetDetails1.getAdminId());
                row.createCell(1).setCellValue(assetDetails1.getName());
                row.createCell(2).setCellValue(assetDetails1.getPhoneNumber());
                row.createCell(3).setCellValue(assetDetails1.getEmailId());
                row.createCell(4).setCellValue(assetDetails1.getActiveStatus().toString());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream getAllEngineerExcel() throws Exception {
        String[] heading = {"Engineer id","Name","Phone number","Email","Work description","Status"};
        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("EngineerDetails");
            Row headerRow = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i=0; i<heading.length; i++){
                Cell cell =headerRow.createCell(i);
                cell.setCellValue(heading[i]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowIndex = 1;
            List<Tbl_Engineer_Details> assetDetails = engRepo.findAll();
            for (Tbl_Engineer_Details assetDetails1 : assetDetails){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(assetDetails1.getEngineerId());
                row.createCell(1).setCellValue(assetDetails1.getEngineerName());
                row.createCell(2).setCellValue(assetDetails1.getEngineerMobile());
                row.createCell(3).setCellValue(assetDetails1.getEngineerEmailId());
                row.createCell(4).setCellValue(assetDetails1.getEngineerWorkDiscription());
                row.createCell(5).setCellValue(assetDetails1.getActiveStatus().toString());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Tbl_Complaints createComplaint(String authToken, String customerId, String remarks, MultipartFile file, String complaintCategory) throws Exception {
        try {
            if (customerId==null || customerId.equals("")){
                throw new Exception("Customer Id is required");
            }
            session.getSessionForAdmin(authToken);
            Tbl_Customer_details customerDetails = customerRepo.findByCustomerId(customerId);
            if (customerDetails==null){
                throw new Exception("Invalid Customer Id");
            }
            Tbl_Asset_Category assetCategory = assetCategoryRepo.findByAssetCategoryName(complaintCategory);
            if (assetCategory == null){
                throw new Exception("Invalid Asset Category");
            }
            Tbl_Complaints comp = Complain_Mapper.toDB(file,customerDetails, remarks, complaintRepo.count()+1, assetCategory);
            comp.getComplaintChats().get(0).setRole(UserRoles.ADMIN);
            comp =  complaintRepo.save(comp);
            return comp;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
