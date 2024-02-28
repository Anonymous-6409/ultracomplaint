package com.ultrasoft.ultracomplaint.responsebody;

import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import java.util.Date;
import java.util.List;

public class ComplaintResponse {

    private String complainId;

    private ComplainStatus status;

    private String assignedByAdminName;

    private String assignedByAdminId;

    private String assignedToEngineerName;

    private String assignedToEngineerId;

    private String createdByCustomerId;

    private String createdByCustomerName;

    private Date createdDate;

    private List<ChatDetails> chats;

    public String getComplainId() {
        return complainId;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId;
    }

    public ComplainStatus getStatus() {
        return status;
    }

    public void setStatus(ComplainStatus status) {
        this.status = status;
    }

    public String getAssignedByAdminName() {
        return assignedByAdminName;
    }

    public void setAssignedByAdminName(String assignedByAdminName) {
        this.assignedByAdminName = assignedByAdminName;
    }

    public String getAssignedByAdminId() {
        return assignedByAdminId;
    }

    public void setAssignedByAdminId(String assignedByAdminId) {
        this.assignedByAdminId = assignedByAdminId;
    }

    public String getAssignedToEngineerName() {
        return assignedToEngineerName;
    }

    public void setAssignedToEngineerName(String assignedToEngineerName) {
        this.assignedToEngineerName = assignedToEngineerName;
    }

    public String getAssignedToEngineerId() {
        return assignedToEngineerId;
    }

    public void setAssignedToEngineerId(String assignedToEngineerId) {
        this.assignedToEngineerId = assignedToEngineerId;
    }

    public String getCreatedByCustomerId() {
        return createdByCustomerId;
    }

    public void setCreatedByCustomerId(String createdByCustomerId) {
        this.createdByCustomerId = createdByCustomerId;
    }

    public String getCreatedByCustomerName() {
        return createdByCustomerName;
    }

    public void setCreatedByCustomerName(String createdByCustomerName) {
        this.createdByCustomerName = createdByCustomerName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<ChatDetails> getChats() {
        return chats;
    }

    public void setChats(List<ChatDetails> chats) {
        this.chats = chats;
    }

    public static class ChatDetails{

        private String remarks;

        private String attachment;

        private UserRoles messageBy;

        private String adminName;

        private String adminId;

        private String engName;

        private String engId;

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public UserRoles getMessageBy() {
            return messageBy;
        }

        public void setMessageBy(UserRoles messageBy) {
            this.messageBy = messageBy;
        }

        public String getAdminName() {
            return adminName;
        }

        public void setAdminName(String adminName) {
            this.adminName = adminName;
        }

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public String getEngName() {
            return engName;
        }

        public void setEngName(String engName) {
            this.engName = engName;
        }

        public String getEngId() {
            return engId;
        }

        public void setEngId(String engId) {
            this.engId = engId;
        }
    }
}
