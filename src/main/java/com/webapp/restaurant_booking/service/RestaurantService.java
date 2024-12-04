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
import java.util.stream.Collectors;

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
                table.setCapacity(((Number) tableData.get("capacity")).intValue());
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
            int capacity = ((Number) tableData.get("capacity")).intValue();

            RestaurantTable restaurantTable = new RestaurantTable();
            restaurantTable.setName(tableName);
            restaurantTable.setAvailable(available);
            restaurantTable.setCapacity(capacity);
            restaurantTable.setRestaurant(newRestaurant);

            restaurantTables.add(restaurantTable);
        }
        newRestaurant.setTables(restaurantTables);

        return restaurantRepo.save(newRestaurant);
    }

    public Restaurant addRestaurantWithPhotos(Map<String, Object> body, List<MultipartFile> photos) throws IOException {
        Restaurant newRestaurant = addRestaurant(body);

        if (photos != null && !photos.isEmpty()) {
            List<String> photoPaths = photoService.uploadMultiplePhotos(photos);
            newRestaurant.setPhotoPaths(photoPaths);
            restaurantRepo.save(newRestaurant);
        }

        return newRestaurant;
    }

    public Restaurant addRestaurantPhotos(Long restaurantId, List<MultipartFile> newPhotos) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        List<String> newPhotoPaths = photoService.uploadMultiplePhotos(newPhotos);

        if (restaurant.getPhotoPaths() == null) {
            restaurant.setPhotoPaths(new ArrayList<>());
        }
        restaurant.getPhotoPaths().addAll(newPhotoPaths);

        return restaurantRepo.save(restaurant);
    }

    public Restaurant removeRestaurantPhotos(Long restaurantId, List<Integer> photoIndexes) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        List<String> photoPaths = restaurant.getPhotoPaths();

        List<String> photosToRemove = photoIndexes.stream()
                .map(photoPaths::get)
                .collect(Collectors.toList());

        photoService.deletePhotos(photosToRemove);

        photoPaths.removeAll(photosToRemove);
        restaurant.setPhotoPaths(photoPaths);

        return restaurantRepo.save(restaurant);
    }

    public Restaurant replaceRestaurantPhotos(Long restaurantId, List<MultipartFile> newPhotos) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        if (restaurant.getPhotoPaths() != null && !restaurant.getPhotoPaths().isEmpty()) {
            photoService.deletePhotos(restaurant.getPhotoPaths());
        }

        List<String> newPhotoPaths = photoService.uploadMultiplePhotos(newPhotos);
        restaurant.setPhotoPaths(newPhotoPaths);

        return restaurantRepo.save(restaurant);
    }

    public List<byte[]> getAllRestaurantPhotos(Long restaurantId) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        List<String> photoPaths = restaurant.getPhotoPaths();

        return photoPaths.stream()
                .map(path -> {
                    try {
                        return photoService.getPhoto(path);
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading photo");
                    }
                })
                .collect(Collectors.toList());
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
