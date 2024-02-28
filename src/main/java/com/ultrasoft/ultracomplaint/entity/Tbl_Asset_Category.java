package com.ultrasoft.ultracomplaint.entity;

import com.ultrasoft.ultracomplaint.enums.ActiveStaus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

@Entity(name = "Tbl_Asset_Category")
@DynamicUpdate
@DynamicInsert
public class Tbl_Asset_Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String assetCategoryName;

    private ActiveStaus blockStatus;

    @Column(name= "createddate", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name= "updateddate")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public String getAssetCategoryName() {
        return assetCategoryName;
    }

    public void setAssetCategoryName(String assetCategoryName) {
        this.assetCategoryName = assetCategoryName;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ActiveStaus getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(ActiveStaus blockStatus) {
        this.blockStatus = blockStatus;
    }

    public static Comparator<Tbl_Asset_Category> compareByUpdatedDate() {
        return Comparator.comparing(Tbl_Asset_Category::getCreatedDate);
    }
}
