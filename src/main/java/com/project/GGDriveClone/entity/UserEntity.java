package com.project.GGDriveClone.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Timestamp getLast_login_at() {
        return last_login_at;
    }

    public void setLast_login_at(Timestamp last_login_at) {
        this.last_login_at = last_login_at;
    }

    public boolean isIs_deleteted() {
        return is_deleteted;
    }

    public void setIs_deleteted(boolean is_deleteted) {
        this.is_deleteted = is_deleteted;
    }

    private Timestamp created_time;

    private Timestamp updated_time;

    private  Timestamp last_login_at;

    private boolean is_deleteted;
}
