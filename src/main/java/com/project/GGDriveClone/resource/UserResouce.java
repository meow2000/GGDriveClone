package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Controller
public class UserResouce {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<UserEntity> users = userService.getAllUser();

        model.addAttribute("users", users);

        return "index";
    }

    @RequestMapping(value = "/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserEntity());
        return "addUser";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam("id") Long userId, Model model) {
        Optional<UserEntity> userEdit = userService.findUserById(userId);
        userEdit.ifPresent(user -> model.addAttribute("user", user));
        return "editUser";
    }

    @RequestMapping(value = "save", method  = RequestMethod.POST)
    public String save(UserEntity user) {
        userService.saveUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") Long userId, Model model) {
        userService.deleteUser(userId);
        return "redirect:/";
    }
}
