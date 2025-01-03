package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
	List<User> findByNameContainingIgnoreCase(String name);
}
