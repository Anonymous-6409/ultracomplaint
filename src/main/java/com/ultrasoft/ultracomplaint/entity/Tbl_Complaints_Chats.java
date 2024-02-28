package com.ultrasoft.ultracomplaint.entity;

import com.ultrasoft.ultracomplaint.enums.UserRoles;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "Tbl_Complaints_Chats")
@DynamicInsert
@DynamicUpdate
public class Tbl_Complaints_Chats implements Comparable<Tbl_Complaints_Chats> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String remark;

    private String attachment;

    private UserRoles role;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @Fetch(value= FetchMode.SELECT)
    private Tbl_Admin_Users admindetails;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @Fetch(value=FetchMode.SELECT)
    private Tbl_Engineer_Details engineerDetails;

    @Column(name= "createddate", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Tbl_Admin_Users getAdmindetails() {
        return admindetails;
    }

    public void setAdmindetails(Tbl_Admin_Users admindetails) {
        this.admindetails = admindetails;
    }

    public Tbl_Engineer_Details getEngineerDetails() {
        return engineerDetails;
    }

    public void setEngineerDetails(Tbl_Engineer_Details engineerDetails) {
        this.engineerDetails = engineerDetails;
    }

    @Override
    public int compareTo(Tbl_Complaints_Chats o) {
        if (this.createdDate.equals(o.getCreatedDate())) {
            return 0;
        }
        else if (this.createdDate.after(o.getCreatedDate())) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
