package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/api/users")
@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public User getSingleUser(@PathVariable("id") long id) {
        return userService.getSingleUser(id).orElse(null);
    }

    @DeleteMapping(value = "/{id}")
    public boolean removeUser(@PathVariable("id") long id) {
        return userService.removeUser(id);
    }

    @PutMapping(value = "/{id}")
    public User updateUser(@PathVariable("id") long id, @RequestBody Map<String, String> body) {
        return userService.updateUser(id, body);
    }

    @PostMapping
    public User addUser(@RequestBody Map<String, String> body) {
        return userService.addUser(body);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> body) {
        String identifier = body.get("emailOrPhone");
        String password = body.get("password");

        User user = userService.login(identifier, password);
        return ResponseEntity.ok(user);
    }
}
