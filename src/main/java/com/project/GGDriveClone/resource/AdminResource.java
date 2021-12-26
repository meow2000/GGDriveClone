package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.entity.PlanEntity;
import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.repository.UserRepository;
import com.project.GGDriveClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminResource {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        Long pid = new Long(1);
        PlanEntity plan = userService.findPlan(pid);
        user.setPlan(plan);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTodo(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
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

    @PutMapping("/plan")
    public UserEntity updatePlan(@RequestParam(name = "pid") Long pid, @RequestParam(name = "id") Long id) {
        UserEntity user = userService.findUser(id);
        PlanEntity plan = userService.findPlan(pid);
        user.setPlan(plan);
        userService.saveUser(user);
        return user;
    }

}
