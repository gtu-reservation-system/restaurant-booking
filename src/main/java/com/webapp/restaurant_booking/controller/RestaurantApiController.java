package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RequestMapping("/api/restaurants")
@RestController
public class RestaurantApiController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping()
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping(value = "/{id}")
    public Restaurant getSingleRestaurant(@PathVariable("id") long id) {
        return restaurantService.getSingleRestaurant(id).orElse(null);
    }

    @DeleteMapping(value = "/{id}")
    public boolean removeRestaurant(@PathVariable("id") long id) {
        return restaurantService.removeRestaurant(id);
    }

    @PutMapping(value = "/{id}")
    public Restaurant updateRestaurant(@PathVariable("id") long id, @RequestBody Map<String, Object> body) {
        return restaurantService.updateRestaurant(id, body);
    }

    @PostMapping()
    public Restaurant addRestaurant(@RequestBody Map<String, Object> body) {
        return restaurantService.addRestaurant(body);
    }
}
