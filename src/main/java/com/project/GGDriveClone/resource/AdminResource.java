package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.repository.UserRepository;
import com.project.GGDriveClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminResource {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userService.getAllUser();
        return users;
    }

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable("id") Long id) {
        return userRepository.findUserEntityById(id);
    }


    @PostMapping("/add")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity user) {
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity updateUser(
            @PathVariable(value = "id") Long id, @Valid @RequestBody UserEntity userDetail) {

        UserEntity user = userRepository.findUserEntityById(id);
        user.setEmail(userDetail.getEmail());
        user.setName(userDetail.getName());
        user.setEnabled(true);
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/verify")
    public boolean verifyUser(@Param("code") String code) {
        return userService.verify(code);
    }


}
