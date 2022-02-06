package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.DTO.ResponseCase;
import com.project.GGDriveClone.DTO.ServerResponseDto;
import com.project.GGDriveClone.entity.PlanEntity;
import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.repository.PlanRepository;
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

    @Autowired
    private PlanRepository planRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userService.getAllUser();
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServerResponseDto> getUser(@PathVariable("id") Long id) {
        UserEntity user =  userRepository.findUserEntityById(id);
        if (user == null) {
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.INVALID_USER_ID));
        }
        return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS, user));
    }


    @PostMapping("/add")
    public boolean createUser(@Valid @RequestBody UserEntity user) {
        if (user.getName() == null || user.getPassword() == null || user.getEmail() == null) {
            return false;
        }
        if (userService.checkAccountNotExists(user.getName(), user.getEmail())) {
            Long pid = new Long(1);
            PlanEntity plan = userService.findPlan(pid);
            user.setPlan(plan);
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setStorage(0l);
            userService.saveUser(user);
            return true;
        }
        return false;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ServerResponseDto> deleteTodo(@PathVariable(name = "id") Long id) {
        if (userService.findUser(id) == null) {
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.INVALID_USER_ID));
        }
        userService.deleteUser(id);
        return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ServerResponseDto> updateUser(
            @PathVariable(value = "id") Long id, @Valid @RequestBody UserEntity userDetail) {
        UserEntity user = userRepository.findUserEntityById(id);
        if (user == null) {
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.INVALID_USER_ID));
        }
        String name = userDetail.getName();
        String email = userDetail.getEmail();
        if (userService.checkAccountExists(name, email)) {
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.EXISTED_NAME_OR_EMAIL));
        }
        user.setEmail(email);
        user.setName(name);
        user.setEnabled(true);
        userService.saveUser(user);
        return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS, user));

    }

    @PutMapping("/plan")
    public ResponseEntity<ServerResponseDto> updatePlan(@RequestParam(name = "pid") Long pid, @RequestParam(name = "id") Long id) {
        UserEntity user = userService.findUser(id);
        PlanEntity plan = userService.findPlan(pid);
        if (user != null && plan != null) {
            user.setPlan(plan);
            userService.saveUser(user);
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS, user));
        }

        else {
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.INVALID_USER_ID_OR_PID));
        }
    }

    @GetMapping("/getAllPlan")
    public ResponseEntity<ServerResponseDto> getAllPlan(){
        List<PlanEntity> planEntities = planRepository.findAll();
        if(planEntities.size() == 0){
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.NO_PLAN_FOUND));
        }
        else {
            return ResponseEntity.ok(new ServerResponseDto(ResponseCase.SUCCESS, planEntities));
        }
    }

}
