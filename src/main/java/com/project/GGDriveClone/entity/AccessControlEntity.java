package com.project.GGDriveClone.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "access_control_list")
public class AccessControlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;

    private Long uid;

    private Long oid;

    private Timestamp createdTime;

    private Timestamp updatedTime;

    public AccessControlEntity(Long uid, Long oid) {
        this.uid = uid;
        this.oid = oid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }
}
