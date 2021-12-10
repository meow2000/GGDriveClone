package com.project.GGDriveClone.resource;

        import com.project.GGDriveClone.entity.UserEntity;
        import com.project.GGDriveClone.service.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;
        import java.util.Optional;
        import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/admin")
public class AdminResouce {
    @Autowired
    private UserService userService;
    private List<UserEntity> userList = new CopyOnWriteArrayList<>();



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserEntity> index() {
        List<UserEntity> users = userService.getAllUser();
        return users;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {

        userList.add(user);
        // Trả về response với STATUS CODE = 200 (OK)
        // Body sẽ chứa thông tin về đối tượng user vừa được tạo.
        return ResponseEntity.ok().body(user);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editUser(@RequestBody Long userId, Model model) {
        Optional<UserEntity> userEdit = userService.findUserById(userId);
        userEdit.ifPresent(user -> model.addAttribute("user", user));
        return "editUser";
    }

    @RequestMapping(value = "/save", method  = RequestMethod.POST)
    public String save(UserEntity user) {

        userService.saveUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteUser(@RequestBody Long userId, Model model) {
        userService.deleteUser(userId);
        return "redirect:/";
    }
}
