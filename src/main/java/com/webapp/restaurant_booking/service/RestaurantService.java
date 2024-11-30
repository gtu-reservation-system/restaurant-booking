package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.RestaurantTable;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.repo.TableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashSet;

import java.util.*;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private TableRepo tableRepo;

    @Autowired
    private PhotoService photoService;

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

            if (body.containsKey("tags")) {
                List<String> tags = (List<String>) body.get("tags");
                current.setTags(new HashSet<>(tags));
            }

            restaurantRepo.save(current);
        }
        return current;
    }

    public Restaurant addRestaurant(Map<String, Object> body) {
        String name = (String) body.get("name");
        String address = (String) body.get("address");

        List<Map<String, Object>> tablesData = (List<Map<String, Object>>) body.get("tables");
        List<String> tags = body.get("tags") != null ? (List<String>) body.get("tags") : new ArrayList<>();

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(name);
        newRestaurant.setAddress(address);
        newRestaurant.setTags(new HashSet<>(tags));

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

    public Restaurant uploadRestaurantPhoto(Long restaurantId, MultipartFile file) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        String filePath = photoService.uploadPhoto(file);
        if (restaurant.getPhotoPaths() == null) {
            restaurant.setPhotoPaths(new ArrayList<>());
        }
        restaurant.getPhotoPaths().add(filePath);
        restaurantRepo.save(restaurant);
        return restaurant;
    }

    public byte[] getRestaurantPhoto(Long restaurantId, int index) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        List<String> photoPaths = restaurant.getPhotoPaths();
        if (index < 0 || index >= photoPaths.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found");
        }

        String filePath = photoPaths.get(index);
        return photoService.getPhoto(filePath);
    }

    public List<Restaurant> searchRestaurantsByTag(String tag) {
        return restaurantRepo.findByTag(tag);
    }

    public List<Restaurant> searchRestaurantsByName(String name) {
        return restaurantRepo.findByNameContainingIgnoreCase(name);
    }

    public List<Restaurant> searchRestaurantsByMenuItem(String menuItemName) {
        return restaurantRepo.findByMenuItemNameContainingIgnoreCase(menuItemName);
    }
}
