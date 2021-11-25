package com.project.GGDriveClone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(){return "admin";}
}