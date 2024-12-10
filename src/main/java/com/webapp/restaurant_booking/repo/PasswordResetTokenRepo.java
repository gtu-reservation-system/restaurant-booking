package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.PasswordResetToken;
import com.webapp.restaurant_booking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
}