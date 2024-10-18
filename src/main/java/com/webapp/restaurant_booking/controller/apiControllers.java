package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.RestaurantBookingApplication;
import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class apiControllers {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @GetMapping(value = "/")
    public String getWelcome(){
        return "Welcome";
    }

    @GetMapping(value = "/restaurants")
    public List<Restaurant> getRestaurants(){
        return restaurantRepo.findAll();
    }
}
