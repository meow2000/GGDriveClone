package com.project.GGDriveClone.controller;

import com.project.GGDriveClone.entity.FileEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {
    @GetMapping("/")
    public String home(){
        return "index";
    }
}
