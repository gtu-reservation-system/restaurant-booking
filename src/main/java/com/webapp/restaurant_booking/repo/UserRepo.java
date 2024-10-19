package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
