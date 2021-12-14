package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserService service;


    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@Valid @RequestBody UserEntity user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
       service.register(user,  getSiteURL(request));
       System.out.println("abccc");
       return ResponseEntity.ok(user);
    }

//    @PostMapping("/process_register")
//    public String processRegister(UserEntity user, HttpServletRequest request)
//            throws UnsupportedEncodingException, MessagingException {
//        service.register(user, getSiteURL(request));
//        return "register_success";
//    }

//    @GetMapping("/users")
//    public String listUsers(Model model) {
//        List<UserEntity> listUsers = service.listAll();
//        model.addAttribute("listUsers", listUsers);
//
//        return "users";
//    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (service.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}
