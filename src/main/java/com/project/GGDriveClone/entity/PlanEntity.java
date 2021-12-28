package com.project.GGDriveClone.entity;


import com.project.GGDriveClone.entity.UserEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "plans")
public class PlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long cost;
    private Long max_storage;
    private Timestamp createdTime;
    private Timestamp updatedTime;


    public Long getMax_storage() {
        return max_storage;
    }

    public void setMax_storage(Long max_storage) {
        this.max_storage = max_storage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
