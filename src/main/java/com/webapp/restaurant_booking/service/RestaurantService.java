package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.RestaurantTable;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.repo.TableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;

import java.util.*;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private TableRepo tableRepo;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    public Optional<Restaurant> getSingleRestaurant(long id) {
        return restaurantRepo.findById(id);
    }

    public boolean removeRestaurant(long id) {
        if (restaurantRepo.existsById(id)) {
            restaurantRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Restaurant updateRestaurant(long id, Map<String, Object> body) {
        Restaurant current = restaurantRepo.findById(id).orElse(null);
        if (current != null) {
            current.setName((String) body.get("name"));
            current.setAddress((String) body.get("address"));

            List<Map<String, Object>> tablesData = (List<Map<String, Object>>) body.get("tables");
            Set<RestaurantTable> restaurantTables = new HashSet<>();
            for (Map<String, Object> tableData : tablesData) {
                RestaurantTable table = new RestaurantTable();
                table.setName((String) tableData.get("name"));
                table.setAvailable((Boolean) tableData.get("available"));
                table.setRestaurant(current);
                restaurantTables.add(table);
            }
            current.setTables(restaurantTables);

            restaurantRepo.save(current);
        }
        return current;
    }

    public Restaurant addRestaurant(Map<String, Object> body) {
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
