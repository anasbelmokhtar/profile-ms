package com.algonquin.profile.controllers;

import com.algonquin.profile.model.Credentials;
import com.algonquin.profile.model.User;
import com.algonquin.profile.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProfileController {
    @Autowired
    UserService userService;

    public ProfileController(){

    }
    @PostMapping("/recipe-book/registration")
    public void createUser(@RequestBody User s) throws SQLException {
        System.out.println(s.toString());
        userService.register(s);

    }
    @GetMapping(value = "/recipe-book/registration/{token}")
    public int validateUser(@PathVariable String token){
        return userService.validateUser(token);
    }
    @PostMapping("/users/authenticate")
    public User login(@RequestBody Credentials cs) throws SQLException {

        User user = userService.login(cs);

        return user;
    }
    @PostMapping("/recipe-book/password-reset")
    public int changePassword(@RequestBody Credentials cs) {
        return userService.changePassword(cs);
    }
}
