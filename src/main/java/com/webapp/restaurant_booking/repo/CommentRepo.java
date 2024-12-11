package com.webapp.restaurant_booking.repo;

import com.webapp.restaurant_booking.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);
    List<Comment> findByRestaurantId(Long restaurantId);
    List<Comment> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);
}