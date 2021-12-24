package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.entity.AccessControlEntity;
import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.security.CustomUserDetails;
import com.project.GGDriveClone.service.AccessControlService;
import com.project.GGDriveClone.service.FileService;
import com.project.GGDriveClone.service.UserService;
import com.project.GGDriveClone.util.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class FileResource {


    @Autowired
    private ServletContext servletContext;

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AccessControlService accessControlService;

    @PostMapping("/createFolder")
    public String createFolder(@RequestBody String folderName){ return FILE_DIRECTORY+ folderName+ "/";}

    //Upload new file with request param MultipartFile
    @PostMapping("/uploadFile")
    public FileEntity fileUpload(@AuthenticationPrincipal CustomUserDetails currentUser, @RequestBody MultipartFile file) throws IOException {

        File myFile = new File(FILE_DIRECTORY + file.getOriginalFilename());
        myFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();

        // Update storage when add a new File
        UserEntity userEntity = userService.findUser(currentUser.getUserId());

        if (userEntity.getStorage() + file.getSize() < userEntity.getPlan().getMax_storage()){
            userEntity.setStorage(userEntity.getStorage() + file.getSize());
        } else {
            return null;
        }
        userService.saveUser(userEntity);

        return fileService.addFile(currentUser.getUserId(),file.getSize(), file.getOriginalFilename(), file.getContentType(), myFile.getPath());

    }

    // Download file with request param String = filename
    @GetMapping("/downloadFile")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        File file = new File(FILE_DIRECTORY + "/" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    // Share file with other user use other_user_id and object_id
    @PostMapping("/shareFile")
    public AccessControlEntity shareFile(@RequestParam Long uid, @RequestParam Long oid){
        AccessControlEntity accessControlEntity = new AccessControlEntity(uid, oid);
        return accessControlService.addAccessControlEntity(accessControlEntity);
    }

    // List file created by current user
    @GetMapping("/listFile")
    public List<FileEntity> showAllFileByUserCreated(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return fileService.findFilesByUser(currentUser.getUserId());
    }

    // List file deleted by current user
    @GetMapping("/trash")
    public List<FileEntity> showAllFileDeletedByUserCreated(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return fileService.findFilesDeletedByUser(currentUser.getUserId());
    }

    // List file shared with current user
    @GetMapping("/shareWithMe")
    public List<FileEntity> showAllFileSharedByUserID(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return fileService.findFilesSharedByUser(currentUser.getUserId());
    }

    // Delete file (Move to trash)
    @GetMapping("/deleteFile")
    public FileEntity moveFileToTrash(@RequestParam Long oid){
        return fileService.moveToTrash(oid);
    }
}