package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.RestaurantTable;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.repo.TableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RestaurantApiController {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private TableRepo tableRepo;

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
    public Restaurant updateRestaurant(@PathVariable("id") long id, @RequestBody Map<String, Object> body){
        Restaurant current= restaurantRepo.findById(id).get();
        current.setName((String) body.get("name"));
        current.setAddress((String) body.get("address"));
        current.setTables((Set<RestaurantTable>) body.get("tables"));
        restaurantRepo.save(current);
        return current;
    }

    @PostMapping(value = "/restaurant/add")
    public Restaurant addRestaurant(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String address = (String) body.get("address");

        // Retrieve table data from the request body
        List<Map<String, Object>> tablesData = (List<Map<String, Object>>) body.get("tables");

        // Create a new Restaurant object
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(name);
        newRestaurant.setAddress(address);

        // Convert table data to Table objects and associate them with the restaurant
        Set<RestaurantTable> restaurantTables = new HashSet<>();
        for (Map<String, Object> tableData : tablesData) {
            String tableName = (String) tableData.get("name");
            boolean available = (Boolean) tableData.get("available");

            RestaurantTable restaurantTable = new RestaurantTable();
            restaurantTable.setName(tableName);  // Set table name
            restaurantTable.setAvailable(available);  // Set availability
            restaurantTable.setRestaurant(newRestaurant);  // Associate table with the restaurant

            restaurantTables.add(restaurantTable);
        }

        // Set the tables for the restaurant
        newRestaurant.setTables(restaurantTables);

        // Save the restaurant (and cascade-save the tables)
        return restaurantRepo.save(newRestaurant);
    }
}
