package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.RestaurantTable;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.repo.TableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/restaurants")
@RestController
public class RestaurantApiController {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private TableRepo tableRepo;

    @GetMapping()
    public List<Restaurant> getAllRestaurants(){
        return restaurantRepo.findAll();
    }

    @GetMapping(value = "/{id}")
    public Restaurant getSingleRestaurant(@PathVariable("id") long id){
        return restaurantRepo.findById(id).get();
    }

    @DeleteMapping(value = "/{id}")
    public boolean removeRestaurant(@PathVariable("id") long id){
        if(!restaurantRepo.findById(id).equals(Optional.empty())){
            restaurantRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @PutMapping(value = "/{id}")
    public Restaurant updateRestaurant(@PathVariable("id") long id, @RequestBody Map<String, Object> body){
        Restaurant current= restaurantRepo.findById(id).get();
        current.setName((String) body.get("name"));
        current.setAddress((String) body.get("address"));
        current.setTables((Set<RestaurantTable>) body.get("tables"));
        restaurantRepo.save(current);
        return current;
    }

    @PostMapping()
    public Restaurant addRestaurant(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String address = (String) body.get("address");

        List<Map<String, Object>> tablesData = (List<Map<String, Object>>) body.get("tables");

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(name);
        newRestaurant.setAddress(address);

        Set<RestaurantTable> restaurantTables = new HashSet<>();
        for (Map<String, Object> tableData : tablesData) {
            String tableName = (String) tableData.get("name");
            boolean available = (Boolean) tableData.get("available");

            RestaurantTable restaurantTable = new RestaurantTable();
            restaurantTable.setName(tableName);
            restaurantTable.setAvailable(available);
            restaurantTable.setRestaurant(newRestaurant);

            restaurantTables.add(restaurantTable);
        }
        newRestaurant.setTables(restaurantTables);

        return restaurantRepo.save(newRestaurant);
    }
}
