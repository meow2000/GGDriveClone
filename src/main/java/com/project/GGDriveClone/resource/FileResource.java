package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class FileResource {

    @Autowired
    private FileService fileService;

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @PostMapping("/uploadFile")
    public FileEntity fileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        File myFile = new File(FILE_DIRECTORY + file.getOriginalFilename());
        myFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();

        return fileService.addFile(file.getSize(), file.getOriginalFilename(), file.getContentType(), myFile.getPath());

    }
}