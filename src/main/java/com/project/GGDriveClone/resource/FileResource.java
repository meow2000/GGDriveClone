package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.service.FileService;
import com.project.GGDriveClone.ultil.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;

@RestController
public class FileResource {


    @Autowired
    private ServletContext servletContext;

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

    @GetMapping("/downloadFile")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam(name = "filename") String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        File file = new File(FILE_DIRECTORY + "/" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}