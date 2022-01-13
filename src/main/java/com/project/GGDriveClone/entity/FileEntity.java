package com.project.GGDriveClone.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "objects")
public class FileEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "access_control_list",
            joinColumns = @JoinColumn(name = "oid"),
            inverseJoinColumns = @JoinColumn(name = "uid"))
    private Set<UserEntity> userEntities = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uid;

    private Long size;

    private String name;

    private String type;

    private String path;

    private Timestamp createdTime;

    private Timestamp updatedTime;

    private boolean isStar;

    private boolean isDeleted;

    public FileEntity(Long uid, Long size, String name, String type, String path) {
        this.uid = uid;
        this.size = size;
        this.name = name;
        this.type = type;
        this.path = path;
    }

    public FileEntity(Long uid, Long size, String name, String type) {
        this.uid = uid;
        this.size = size;
        this.name = name;
        this.type = type;
    }

    public FileEntity() {

    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "userEntities=" + userEntities +
                ", id=" + id +
                ", uid=" + uid +
                ", size=" + size +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", path='" + path + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
