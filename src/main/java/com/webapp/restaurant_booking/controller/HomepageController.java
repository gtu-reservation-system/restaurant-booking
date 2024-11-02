package com.webapp.restaurant_booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/")
@RestController
public class HomepageController {

    @GetMapping
    public String getHomepage() {
        return "Welcome";
    }
}
