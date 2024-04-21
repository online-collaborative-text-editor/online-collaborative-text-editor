package com.APT.online.collaborative.text.editor.Controller;

import com.APT.online.collaborative.text.editor.Model.User;
import com.APT.online.collaborative.text.editor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
        userService.updateUser(userId, user);
    }
}
