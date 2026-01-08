package org.neurogine.librarymanagementbackend.controller;

import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.User;
import org.neurogine.librarymanagementbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    /*public UserController(UserService userService) {
        this.userService = userService;
    }*/


    // Register user
    @PostMapping("/register")
    public User register(@RequestBody User user){
        //validation logic
        if(user.getUserName() == null || user.getUserName().isBlank()){
            throw new RuntimeException("Username Cannot be Empty");
        }
        // Password Check
        if(!user.getPassword().equals(user.getRepeatPassword())){
            throw  new RuntimeException("Password do not match");
        }

        return userService.register(user);
    }

    // Login user (temporary - Jwt later)
    @PostMapping("/login")
    public boolean login(@RequestBody User user){
        return userService.login(user);
    }

    // Get All users
    @GetMapping
    public List<User> list(){
        return userService.findAll();
    }

    // get By id
    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id){
        return userService.findById(id);
    }

    // delete user
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    // Search user by keyword
    @GetMapping("/search")
    public List<User> search(@RequestParam String keyword) {
        return userService.findByKeyword(keyword);
    }

    // Change password
    @PutMapping("/{id}/password")
    public boolean changePassword( @PathVariable Integer id, @RequestParam String newPassword) {
        return userService.changePassword(id, newPassword);
    }



}
