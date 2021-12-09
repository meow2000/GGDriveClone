package com.project.GGDriveClone.security;

import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find user " + username);
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        CustomUserDetails userDetails = new CustomUserDetails(user.getName(), user.getPassword(), authorities, user.getId(), username);

        return userDetails;
    }

    public UserDetails loadUserByUserId(Long userId){
        UserEntity user = userService.findUser(userId);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find user with id:" + userId);
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        CustomUserDetails userDetails = new CustomUserDetails(user.getName(), user.getPassword(), authorities, userId, user.getEmail());

        return userDetails;
    }
}

