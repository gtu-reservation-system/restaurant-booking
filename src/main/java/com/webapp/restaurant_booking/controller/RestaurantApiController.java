package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/{id}/photo")
    public ResponseEntity<Restaurant> uploadRestaurantPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Restaurant updatedRestaurant = restaurantService.uploadRestaurantPhoto(id, file);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @GetMapping(value = "/{id}/photo/{index}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getRestaurantPhoto(@PathVariable Long id, @PathVariable int index) throws IOException {
        byte[] photo = restaurantService.getRestaurantPhoto(id, index);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photo);
    }
}
