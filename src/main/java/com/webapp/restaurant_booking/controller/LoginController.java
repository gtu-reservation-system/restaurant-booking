package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RequestMapping("/api/login")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<?> universalLogin(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Object loginResult = loginService.login(email, password);
        return ResponseEntity.ok(loginResult);
    }
}