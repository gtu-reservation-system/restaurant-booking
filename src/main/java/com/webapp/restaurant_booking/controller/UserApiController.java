package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/users")
@RestController
public class UserApiController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping()
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @GetMapping(value = "/{id}")
    public User getSingleUser(@PathVariable("id") long id){
        return userRepo.findById(id).get();
    }

    @DeleteMapping(value = "/{id}")
    public boolean removeUser(@PathVariable("id") long id){
        if(!userRepo.findById(id).equals(Optional.empty())){
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @PutMapping(value = "/{id}")
    public User updateUser(@PathVariable("id") long id, @RequestBody Map<String, String> body){
        User current= userRepo.findById(id).get();
        current.setName(body.get("name"));
        current.setEmail(body.get("email"));
        current.setPassword(body.get("password"));
        userRepo.save(current);
        return current;
    }

    @PostMapping()
    public User addUser(@RequestBody Map<String, String> body){
        String name= body.get("name");
        String email= body.get("email");
        String password= body.get("password");
        User newUser= new User(name, email, password);
        return userRepo.save(newUser);
    }
}