package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.DTO.FileDto;
import com.project.GGDriveClone.DTO.ResponseCase;
import com.project.GGDriveClone.DTO.ServerResponseDto;
import com.project.GGDriveClone.convert.FileConvert;
import com.project.GGDriveClone.entity.AccessControlEntity;
import com.project.GGDriveClone.entity.FileEntity;
import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.security.CustomUserDetails;
import com.project.GGDriveClone.service.AccessControlService;
import com.project.GGDriveClone.service.FileService;
import com.project.GGDriveClone.service.UserService;
import com.project.GGDriveClone.util.MediaTypeUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class FileResource {

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @Autowired
    private FileConvert fileConvert;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AccessControlService accessControlService;

    @GetMapping("/getUsername")
    public String getUsername(@RequestParam Long uid){
        UserEntity userEntity = userService.findUser(uid);
        if (userEntity != null){
            return userEntity.getName();
        }
        return "";
    }

    @PostMapping("/createFolder")
    public String createFolder(@RequestBody String folderName) {
        return FILE_DIRECTORY + "/"+ folderName + "/";
    }

    //Upload new file with request param MultipartFile
    @PostMapping("/uploadFile")
    public FileDto fileUpload(@AuthenticationPrincipal CustomUserDetails currentUser,
                              @RequestBody MultipartFile file) throws IOException {

        // Update storage when add a new File
        UserEntity userEntity = userService.findUser(currentUser.getUserId());

        String message = "Upload successful!\n";
        // Check file exits
        FileEntity fileEntity0 = fileService.findFile(file.getOriginalFilename());
        if(fileEntity0 != null){
            message = "Filename existed!";
            return fileConvert.convertToFileDto(new FileEntity(), message);
        }

        // Check if file < storage
        if (userEntity.getStorage() + file.getSize() < userEntity.getPlan().getMax_storage()) {
            userEntity.setStorage(userEntity.getStorage() + file.getSize());
            userService.saveUser(userEntity);
        } else {
            message = "You have reach max storage! Contact us to upgrade your limit\n";
            return fileConvert.convertToFileDto(new FileEntity(currentUser.getUserId(),
                    file.getSize(),
                    file.getOriginalFilename(),
                    file.getContentType()), message);
        }

        // Create new file and store to server
        File myFile = new File(FILE_DIRECTORY + file.getOriginalFilename());
        myFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();

        FileEntity fileEntity = fileService.addFile(currentUser.getUserId(), file.getSize(),
                file.getOriginalFilename(), file.getContentType(), myFile.getPath());

        return fileConvert.convertToFileDto(fileEntity, message);

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
    public ResponseEntity<ServerResponseDto> shareFile(@RequestParam String username, @RequestParam Long oid) {
        UserEntity userEntity= userService.findUser(username);
        FileEntity fileEntity = fileService.findFile(oid);
        if(userEntity == null || fileEntity == null){
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.USER_OR_FILE_NOT_EXIST));
        }
        AccessControlEntity accessControlEntity0 = accessControlService.findByUidAndOid(userEntity.getId(), oid);
        if(accessControlEntity0 != null){
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.ACCESS_CONTROL_EXIST));
        }
        AccessControlEntity accessControlEntity1 = new AccessControlEntity(userEntity.getId(), oid);
        AccessControlEntity accessControlEntity2 = accessControlService.addAccessControlEntity(accessControlEntity1);
        return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS, accessControlEntity2));
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
    @DeleteMapping("/deleteFile")
    public void moveFileToTrash(@RequestParam Long oid) {
        FileEntity fileEntity = fileService.findFile(oid);
        if (fileEntity == null) {
            System.out.println("Cannot find this file with oid: " + oid + "\n");
            return;
        }
        fileService.moveToTrash(fileEntity);
    }

    @PutMapping("/undoDelete")
    public void undoDelete(@RequestParam Long oid) {
        FileEntity fileEntity = fileService.findFile(oid);
        if (fileEntity == null) {
            System.out.println("Cannot find this file with oid: " + oid + "\n");
            return;
        }
        fileService.undoDelete(fileEntity);
    }

    @DeleteMapping("/completedDelete")
    public void completedDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                @RequestParam Long oid) {
        fileService.completedDelete(customUserDetails.getUserId(), oid);
    }

    @GetMapping("/getStorage")
    public Long getStorage(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return userService.findUser(customUserDetails.getUserId()).getStorage();
    }

    @GetMapping("/getCurrentUser")
    public UserEntity getCurrentUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return userService.findUser(customUserDetails.getUserId());
    }

    @GetMapping("/getRecent")
    public List<FileEntity> getRecentFile(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return fileService.findRecentFile(customUserDetails.getUserId());
    }

    @PostMapping("/star")
    public boolean starFile(@RequestParam Long oid){
        return fileService.starFile(oid);
    }

    @PostMapping("/unstar")
    public boolean unstarFile(@RequestParam Long oid){
        return fileService.unstarFile(oid);
    }

    @GetMapping("/getStar")
    public List<FileEntity> getStarFile(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return fileService.findStarFile(customUserDetails.getUserId());
    }

    @GetMapping("/search")
    public ResponseEntity<ServerResponseDto> search (@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                     @RequestParam String keyword){
        List<FileEntity> fileEntities = fileService.search(customUserDetails.getUserId(), keyword);
        if(fileEntities.size() == 0){
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.NO_RESULT_FOUND));
        }
        return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS, fileEntities));
    }
}