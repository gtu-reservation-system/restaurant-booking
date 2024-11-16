package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getSingleUser(long id) {
        return userRepo.findById(id);
    }

    public boolean removeUser(long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public User updateUser(long id, Map<String, String> body) {
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isPresent()) {
            User current = userOpt.get();
            String password = body.get("password");
            if (password.length() < 3 || password.length() > 12) {
                throw new IllegalArgumentException("Password must be between 3 and 12 characters.");
            }
            current.setName(body.get("name"));
            current.setEmail(body.get("email"));
            current.setPassword(password);
            return userRepo.save(current);
        }
        return null;
    }

    public User addUser(Map<String, String> body) {
        String name = body.get("fullName");
        String email = body.get("email");
        String password = body.get("password");
		String number = body.get("phoneNumber");

		Optional<User> existingUser  = userRepo.findByEmail(email);
		if (existingUser .isPresent()) {
			throw new IllegalArgumentException("Email is already in use.");
		}
        if (password.length() < 3 || password.length() > 12) {
            throw new IllegalArgumentException("Password must be between 3 and 12 characters.");
        }
        User newUser = new User(name, email, password, number);
        return userRepo.save(newUser);
    }

    public User login(String email, String password) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        User current = userOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email"));

        if (!current.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
        return current;
    }
}
