package com.ultrasoft.ultracomplaint.entity;

import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

@Entity(name = "Tbl_Complaints")
@DynamicInsert
@DynamicUpdate
public class Tbl_Complaints {

    @Id
    private String complainId;

    private ComplainStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @Fetch(value = FetchMode.SELECT)
    private Tbl_Asset_Category assetCategory;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @Fetch(value=FetchMode.SELECT)
    private Tbl_Admin_Users assignedByAdmin;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @Fetch(value=FetchMode.SELECT)
    private Tbl_Engineer_Details assignedToEngineer;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @Fetch(value=FetchMode.SELECT)
    private Tbl_Customer_details createdByCUstomer;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="complainId")
    @Fetch(value=FetchMode.SELECT)
    private List<Tbl_Complaints_Chats> complaintChats;

    @Column(name= "createddate", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name= "ticketcloseddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ticketClosedDate;

    @Column(name= "engineerassigneddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date engineerAssignedDate;

    @Column(name = "resolveddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resolvedDate;

    @Column(name= "updateddate")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

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

    public Tbl_Admin_Users getAssignedByAdmin() {
        return assignedByAdmin;
    }

    public void setAssignedByAdmin(Tbl_Admin_Users assignedByAdmin) {
        this.assignedByAdmin = assignedByAdmin;
    }

    public Tbl_Engineer_Details getAssignedToEngineer() {
        return assignedToEngineer;
    }

    public void setAssignedToEngineer(Tbl_Engineer_Details assignedToEngineer) {
        this.assignedToEngineer = assignedToEngineer;
    }

    public Tbl_Customer_details getCreatedByCUstomer() {
        return createdByCUstomer;
    }

    public void setCreatedByCUstomer(Tbl_Customer_details createdByCUstomer) {
        this.createdByCUstomer = createdByCUstomer;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Tbl_Complaints_Chats> getComplaintChats() {
        return complaintChats;
    }

    public void setComplaintChats(List<Tbl_Complaints_Chats> complaintChats) {
        this.complaintChats = complaintChats;
    }

    public Date getTicketClosedDate() {
        return ticketClosedDate;
    }

    public void setTicketClosedDate(Date ticketClosedDate) {
        this.ticketClosedDate = ticketClosedDate;
    }

    public Date getEngineerAssignedDate() {
        return engineerAssignedDate;
    }

    public void setEngineerAssignedDate(Date engineerAssignedDate) {
        this.engineerAssignedDate = engineerAssignedDate;
    }

    public Tbl_Asset_Category getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(Tbl_Asset_Category assetCategory) {
        this.assetCategory = assetCategory;
    }

    public Date getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Date resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public static Comparator<Tbl_Complaints> compareByCreatedDate() {
        return Comparator.comparing(Tbl_Complaints::getCreatedDate).reversed();
    }

    public static Comparator<Tbl_Complaints> compareByTicketClosedDate() {
        return Comparator.comparing(Tbl_Complaints::getTicketClosedDate).reversed();
    }

    public static Comparator<Tbl_Complaints> compareByUpdatedDate() {
        return Comparator.comparing(Tbl_Complaints::getUpdatedDate).reversed();
    }

    public static Comparator<Tbl_Complaints> compareByAssignedDate() {
        return Comparator.comparing(Tbl_Complaints::getEngineerAssignedDate).reversed();
    }

    public static Comparator<Tbl_Complaints> compareByResolvedDate() {
        return Comparator.comparing(Tbl_Complaints::getResolvedDate).reversed();
    }

}
