package com.project.GGDriveClone.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "objects")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long size;

    private String name;

    private String type;

    private String path;

    private Timestamp created_time;

    private Timestamp updated_time;

    private boolean is_deleteted;
    @Lob
    private byte[] data;

    public FileEntity(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
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

    public Timestamp getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Timestamp created_time) {
        this.created_time = created_time;
    }

    public Timestamp getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Timestamp updated_time) {
        this.updated_time = updated_time;
    }

    public boolean isIs_deleteted() {
        return is_deleteted;
    }

    public void setIs_deleteted(boolean is_deleteted) {
        this.is_deleteted = is_deleteted;
    }
}
