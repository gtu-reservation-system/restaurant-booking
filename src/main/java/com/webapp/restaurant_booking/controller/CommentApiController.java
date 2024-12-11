package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Comment;
import com.webapp.restaurant_booking.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/api/comments")
@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Map<String, Object> body) {
        try {
            if (!body.containsKey("restaurantId")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant ID is required");
            }
            if (!body.containsKey("userId")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required");
            }
            if (!body.containsKey("comment")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment is required");
            }
            if (!body.containsKey("rating")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating is required");
            }

            Comment savedComment = commentService.addComment(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error processing comment: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping(value = "/{id}")
    public Comment getSingleComment(@PathVariable("id") long id) {
        return commentService.getSingleComment(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
    }

    @DeleteMapping(value = "/{id}")
    public boolean removeComment(@PathVariable("id") long id) {
        return commentService.removeComment(id);
    }

    @GetMapping("/user/{userId}")
    public List<Comment> getCommentsByUser(@PathVariable("userId") Long userId) {
        return commentService.getCommentsByUser(userId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Comment> getCommentsByRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        return commentService.getCommentsByRestaurant(restaurantId);
    }
}