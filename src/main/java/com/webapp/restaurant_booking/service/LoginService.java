package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    public Object login(String email, String password) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password for user");
        }

        Optional<Restaurant> restaurantOpt = restaurantRepo.findByEmail(email);
        if (restaurantOpt.isPresent()) {
            Restaurant restaurant = restaurantOpt.get();
            if (restaurant.getPassword().equals(password)) {
                return restaurant;
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password for restaurant");
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email");
    }
}