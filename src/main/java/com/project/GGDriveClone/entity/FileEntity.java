package com.project.GGDriveClone.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "objects")
@Where(clause = "is_deleted=false")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long size;

    private String name;

    private String type;

    private String path;

    private Timestamp createdTime;

    private Timestamp updatedTime;

    private boolean isDeleteted;

    public FileEntity(Long size, String name, String type, String path) {
        this.size = size;
        this.name = name;
        this.type = type;
        this.path = path;
    }

    public FileEntity() {

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

    public boolean isDeleteted() {
        return isDeleteted;
    }

    public void setDeleteted(boolean deleteted) {
        isDeleteted = deleteted;
    }
}
