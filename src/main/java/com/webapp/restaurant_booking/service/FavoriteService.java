package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.Comment;
import com.webapp.restaurant_booking.models.Favorite;
import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.repo.FavoriteRepo;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FavoriteService {
    @Autowired
    FavoriteRepo favoriteRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RestaurantRepo restaurantRepo;

    public Favorite addFavorite(Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        Long restaurantId = ((Number) body.get("restaurantId")).longValue();

        Favorite existingFavorite = favoriteRepo.findByUserIdAndRestaurantId(userId, restaurantId);
        if (existingFavorite != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Restaurant is already in favorites");
        }
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        String restaurantName = restaurant.getName();

        Favorite newFavorite = new Favorite(user, restaurant, restaurantName);
        return favoriteRepo.save(newFavorite);
    }

    public List<Favorite> getAllFavorites() {
        return favoriteRepo.findAll();
    }

    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepo.findByUserId(userId);
    }

    @Transactional
    public void deleteFavorite(Long userId, Long restaurantId) {
        Favorite favorite = favoriteRepo.findByUserIdAndRestaurantId(userId, restaurantId);
        if(favorite!= null) {
            favoriteRepo.delete(favorite);
        }
        return;
    }
}
