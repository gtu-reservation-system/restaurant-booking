package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.Comment;
import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.repo.CommentRepo;
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
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Transactional
    public Comment addComment(Map<String, Object> body) {
        Long restaurantId = ((Number) body.get("restaurantId")).longValue();
        Long userId = ((Number) body.get("userId")).longValue();
        String comment = (String) body.get("comment");
        Integer rating = ((Number) body.get("rating")).intValue();

        if (rating < 1 || rating > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating must be between 1 and 5");
        }

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (comment == null || comment.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment cannot be empty");
        }
        if (comment.length() > 500) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment cannot exceed 500 characters");
        }

        Comment newComment = new Comment(restaurant, user, comment, rating);
        return commentRepo.save(newComment);
    }

    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    public Optional<Comment> getSingleComment(long id) {
        return commentRepo.findById(id);
    }

    @Transactional
    public boolean removeComment(long id) {
        Optional<Comment> commentOpt = commentRepo.findById(id);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();

            User user = comment.getUser();
            if (user != null) {
                user.getComments().remove(comment);
            }
            Restaurant restaurant = comment.getRestaurant();
            if (restaurant != null) {
                restaurant.getComments().remove(comment);
            }
            commentRepo.delete(comment);
            return true;
        }
        return false;
    }

    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepo.findByUserId(userId);
    }

    public List<Comment> getCommentsByRestaurant(Long restaurantId) {
        return commentRepo.findByRestaurantIdOrderByCreatedAtDesc(restaurantId);
    }
}