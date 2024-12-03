package com.webapp.restaurant_booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.service.PhotoService;
import com.webapp.restaurant_booking.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/api/restaurants")
@RestController
public class RestaurantApiController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private PhotoService photoService;

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

    @PostMapping(value = "/with-photos")
    public ResponseEntity<Restaurant> addRestaurantWithPhotos(
            @RequestPart("restaurant") String restaurantJson,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> body = mapper.readValue(restaurantJson, Map.class);

        Restaurant restaurant = restaurantService.addRestaurantWithPhotos(body, photos);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping(value = "/{id}/photos", consumes = "multipart/form-data")
    public ResponseEntity<Restaurant> addRestaurantPhotos(
            @PathVariable Long id,
            @RequestPart("photos") List<MultipartFile> photos
    ) throws IOException {
        Restaurant updatedRestaurant = restaurantService.addRestaurantPhotos(id, photos);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{id}/photos")
    public ResponseEntity<Restaurant> removeRestaurantPhotos(
            @PathVariable Long id,
            @RequestBody List<Integer> photoIndexes
    ) throws IOException {
        Restaurant updatedRestaurant = restaurantService.removeRestaurantPhotos(id, photoIndexes);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @PutMapping(value = "/{id}/photos", consumes = "multipart/form-data")
    public ResponseEntity<Restaurant> replaceRestaurantPhotos(
            @PathVariable Long id,
            @RequestPart("photos") List<MultipartFile> photos
    ) throws IOException {
        Restaurant updatedRestaurant = restaurantService.replaceRestaurantPhotos(id, photos);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @GetMapping("/{id}/photos")
    public ResponseEntity<List<String>> getAllRestaurantPhotos(@PathVariable Long id) {
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        List<String> photoPaths = restaurant.getPhotoPaths();

        if (photoPaths == null || photoPaths.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(photoPaths);
    }

    @GetMapping("/tag")
    public List<Restaurant> searchRestaurantsByTag(@RequestParam String tag) {
        return restaurantService.searchRestaurantsByTag(tag);
    }

    @GetMapping("/name")
    public List<Restaurant> searchRestaurantsByName(@RequestParam String name) {
        return restaurantService.searchRestaurantsByName(name);
    }

    @GetMapping("/menu-item")
    public List<Restaurant> searchRestaurantsByMenuItem(@RequestParam String menuItemName) {
        return restaurantService.searchRestaurantsByMenuItem(menuItemName);
    }
}
