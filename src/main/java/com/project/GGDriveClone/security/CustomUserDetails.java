package com.project.GGDriveClone.security;

import com.project.GGDriveClone.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private String name;
    private String role;

    private UserEntity user;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             Long userId, String name) {
        super(username, password, authorities);
        this.userId = userId;
        this.name = name;
    }



    public CustomUserDetails(String username, String password,
                             boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities, Long userId, String name) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
