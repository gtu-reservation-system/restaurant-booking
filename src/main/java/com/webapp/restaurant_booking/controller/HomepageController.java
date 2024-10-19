package com.webapp.restaurant_booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomepageController {

    @GetMapping(value = "/")
    public String getHomepage() {
        return "Welcome";
    }
}
