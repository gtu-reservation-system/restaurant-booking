package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RestaurantApiController {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @GetMapping(value = "/restaurants")
    public List<Restaurant> getAllRestaurants(){
        return restaurantRepo.findAll();
    }

    @GetMapping(value = "/restaurant/{id}")
    public Restaurant getSingleRestaurant(@PathVariable("id") long id){
        return restaurantRepo.findById(id).get();
    }

    @DeleteMapping(value = "/restaurant/remove/{id}")
    public boolean removeRestaurant(@PathVariable("id") long id){
        if(!restaurantRepo.findById(id).equals(Optional.empty())){
            restaurantRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @PutMapping(value = "/restaurant/update/{id}")
    public Restaurant updateRestaurant(@PathVariable("id") long id, @RequestBody Map<String, String> body){
        Restaurant current= restaurantRepo.findById(id).get();
        current.setName(body.get("name"));
        restaurantRepo.save(current);
        return current;
    }

    @PostMapping(value = "/restaurant/add")
    public Restaurant addRestaurant(@RequestBody Map<String, String> body){
        String name= body.get("name");
        Restaurant newRestaurant= new Restaurant(name);
        return restaurantRepo.save(newRestaurant);
    }
}
