package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Comment;
import com.webapp.restaurant_booking.models.Favorite;
import com.webapp.restaurant_booking.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("api/favorites")
@RestController
public class FavoriteApiController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping()
    public ResponseEntity<Favorite> addFavorite(@RequestBody Map<String, Object> body) {
        Favorite savedFavorite = favoriteService.addFavorite(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFavorite);
    }

    @GetMapping
    public List<Favorite> getAllFavorites() {
        return favoriteService.getAllFavorites();
    }

    @GetMapping("/{userId}")
    public List<Favorite> getFavoritesByUser(@PathVariable("userId") Long userId) {
        return favoriteService.getFavoritesByUser(userId);
    }

    @DeleteMapping("/{userId}/{restaurantId}")
    public ResponseEntity<Void> deleteFavorite(
            @PathVariable("userId") Long userId,
            @PathVariable("restaurantId") Long restaurantId) {
        favoriteService.deleteFavorite(userId, restaurantId);
        return ResponseEntity.noContent().build();
    }
}
