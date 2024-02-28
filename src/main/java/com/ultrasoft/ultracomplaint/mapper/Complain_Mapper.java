package com.ultrasoft.ultracomplaint.mapper;

import com.ultrasoft.ultracomplaint.entity.*;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.responsebody.ComplaintResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Complain_Mapper {


    public static Tbl_Complaints toDB(MultipartFile file, Tbl_Customer_details cust, String remarks, long id, Tbl_Asset_Category assetCategory) throws Exception {
        Tbl_Complaints comp = new Tbl_Complaints();
        comp.setComplainId("ULTCOMP"+String.valueOf(id + 1));
        comp.setStatus(ComplainStatus.UN_ASSIGNED);
        comp.setCreatedByCUstomer(cust);
        comp.setAssetCategory(assetCategory);
        List<Tbl_Complaints_Chats> chats = new ArrayList<>();
        Tbl_Complaints_Chats singleChat = new Tbl_Complaints_Chats();
        singleChat.setRemark(remarks);
        singleChat.setRole(UserRoles.CUSTOMER);
        if (file != null) {
            singleChat.setAttachment(UploadDocs.image(file));
        }
        chats.add(singleChat);
        comp.setComplaintChats(chats);
        return comp;
    }

    public static Tbl_Complaints assignTOEngineer(Tbl_Complaints comp,
                                                  Tbl_Admin_Users admin,
                                                  Tbl_Engineer_Details eng){
        comp.setStatus(ComplainStatus.IN_PROGRESS);
        comp.setAssignedToEngineer(eng);
        comp.setAssignedByAdmin(admin);
        comp.setEngineerAssignedDate(new Date());
        return comp;
    }

    public static Tbl_Complaints replyByAdmin(Tbl_Complaints comp, Tbl_Admin_Users admin,
                                              String remarks, MultipartFile file) throws Exception {
        Tbl_Complaints_Chats singleChat = new Tbl_Complaints_Chats();
        singleChat.setRemark(remarks);
        singleChat.setRole(UserRoles.ADMIN);
        singleChat.setAdmindetails(admin);
        if (file != null) {
            singleChat.setAttachment(UploadDocs.image(file));
        }
        comp.getComplaintChats().add(singleChat);
        return comp;
    }

    public static Tbl_Complaints replyByEngineer(Tbl_Complaints comp, Tbl_Engineer_Details admin,
                                              String remarks, MultipartFile file) throws Exception {
        Tbl_Complaints_Chats singleChat = new Tbl_Complaints_Chats();
        singleChat.setRemark(remarks);
        singleChat.setRole(UserRoles.ENGINEER);
        singleChat.setEngineerDetails(admin);
        if (file != null) {
            singleChat.setAttachment(UploadDocs.image(file));
        }
        comp.getComplaintChats().add(singleChat);
        return comp;
    }

    public static Tbl_Complaints replyByCustomer(Tbl_Complaints comp,
                                                 String remarks, MultipartFile file) throws Exception {
        Tbl_Complaints_Chats singleChat = new Tbl_Complaints_Chats();
        singleChat.setRemark(remarks);
        singleChat.setRole(UserRoles.CUSTOMER);
        if (file != null) {
            singleChat.setAttachment(UploadDocs.image(file));
        }
        comp.getComplaintChats().add(singleChat);
        return comp;
    }

    public static ComplaintResponse toShow(Tbl_Complaints complain){
        ComplaintResponse data = new ComplaintResponse();
        data.setComplainId(complain.getComplainId());
        data.setStatus(complain.getStatus());
        data.setCreatedDate(complain.getCreatedDate());
        if(complain.getStatus().equals(ComplainStatus.UN_ASSIGNED)){
            data.setCreatedByCustomerId(complain.getCreatedByCUstomer().getCustomerId());
            data.setCreatedByCustomerName(complain.getCreatedByCUstomer().getCustomerName());
        }
        else{
            data.setCreatedByCustomerId(complain.getCreatedByCUstomer().getCustomerId());
            data.setCreatedByCustomerName(complain.getCreatedByCUstomer().getCustomerName());
            data.setAssignedByAdminName(complain.getAssignedByAdmin().getAdminId());
            data.setAssignedByAdminId(complain.getAssignedByAdmin().getName());
            data.setAssignedToEngineerId(complain.getAssignedToEngineer().getEngineerId());
            data.setAssignedToEngineerName(complain.getAssignedToEngineer().getEngineerName());
        }
        data.setChats(chatList(complain.getComplaintChats()));
        return data;
    }

    public static List<ComplaintResponse> responList(List<Tbl_Complaints> data){
        return data.stream().map(Complain_Mapper::toShow).collect(Collectors.toList());
    }
    public static ComplaintResponse.ChatDetails chatDetails(Tbl_Complaints_Chats data){
        ComplaintResponse.ChatDetails chat = new ComplaintResponse.ChatDetails();
        chat.setAttachment(data.getAttachment());
        chat.setRemarks(data.getRemark());
        chat.setMessageBy(data.getRole());
        if(data.getRole().equals(UserRoles.ADMIN)){
            chat.setAdminId(data.getAdmindetails().getAdminId());
            chat.setAdminName(data.getAdmindetails().getName());
        }
        if(data.getRole().equals(UserRoles.ENGINEER)){
            chat.setEngId(data.getEngineerDetails().getEngineerId());
            chat.setEngName(data.getEngineerDetails().getEngineerName());
        }
        return chat;
    }
    public static List<ComplaintResponse.ChatDetails> chatList(List<Tbl_Complaints_Chats> data){
        return data.stream().map(Complain_Mapper::chatDetails).collect(Collectors.toList());
    }
}
