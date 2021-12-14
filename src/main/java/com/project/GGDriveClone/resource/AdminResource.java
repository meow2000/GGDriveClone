package com.project.GGDriveClone.resource;

        import com.project.GGDriveClone.entity.UserEntity;
        import com.project.GGDriveClone.repository.UserRepository;
        import com.project.GGDriveClone.service.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
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


//    @PostMapping(value = "/add")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<UserEntity> addUser(@RequestParam(value = "name") String name, @RequestParam(value = "email")
//            String email, @RequestParam(value = "password") String password) {
//        UserEntity user = new UserEntity();
//        user.setEmail(name);
//        user.setEmail(password);
//        user.setEmail(email);
//        userService.saveUser(user);
//        return ResponseEntity.ok().body(user);
//    }


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

//    @PutMapping("/{id}")
//    public UserEntity update_user(@PathVariable("id") Long id, @Valid @RequestBody UserEntity user) {
//        user.setId(id);
//        userService.saveUser(user);
//    }
//


//    @RequestMapping(value = "/save", method  = RequestMethod.POST)
//    public String save(UserEntity user) {
//
//        userService.saveUser(user);
//        return "redirect:/";
//    }
//
//    @RequestMapping(value = "/delete", method = RequestMethod.GET)
//    public String deleteUser(@RequestBody Long userId, Model model) {
//        userService.deleteUser(userId);
//        return "redirect:/";
//    }
}
