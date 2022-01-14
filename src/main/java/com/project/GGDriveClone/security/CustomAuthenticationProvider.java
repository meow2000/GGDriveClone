package com.project.GGDriveClone.security;

import com.project.GGDriveClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("hedspi2a"));
        System.out.println(bCryptPasswordEncoder.matches("hedspi2a", "$2a$10$aCX7m45HKrJ/6jWiCgAGIulBV2iE3CELTrTEeF3ir08Ft3sOOLi9y"));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if(username == null || password == null) return null;
        boolean isEnable = false;
        try{
            isEnable = userService.findUser(username).isEnabled();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        CustomUserDetails userDetails;
        userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        System.out.println("Userdetail:" + userDetails.toString());
        if(userDetails != null){
            if (passwordEncoder.matches(password, userDetails.getPassword()) && isEnable) {
                return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            } else {
                return null;
            }
        }
        else return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
