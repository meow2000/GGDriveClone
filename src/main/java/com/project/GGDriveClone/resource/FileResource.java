package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class FileResource {

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;
    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    public FileEntity fileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        File myFile = new File(FILE_DIRECTORY + file.getOriginalFilename());
        myFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();

        return fileService.addFile(file.getSize(), file.getOriginalFilename(), file.getContentType(), myFile.getPath());

    }

    @GetMapping("/home")
    public List<FileEntity> showAllFileByUser(@RequestParam("user_id") Long id) {
        UserEntity userEntity = new UserEntity(id);
        return fileService.findAllByUser(userEntity);
    }

    @GetMapping("/downloadFile/{id}")
    public void downloafFile(@RequestParam("id") Long id) {
        fileService.findFile(id);
    }
}