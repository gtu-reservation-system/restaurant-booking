package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.MenuItem;
import com.webapp.restaurant_booking.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RequestMapping("/api/restaurants")
@RestController
public class MenuItemApiController {
    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/{restaurantId}/menu-items")
    public ResponseEntity<MenuItem> addMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody Map<String, Object> body) {
        MenuItem menuItem = menuItemService.addMenuItem(restaurantId, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItem);
    }

    @GetMapping("/{restaurantId}/menu-items")
    public List<MenuItem> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        return menuItemService.getMenuItemsByRestaurant(restaurantId);
    }

    @PutMapping("/menu-items/{menuItemId}")
    public MenuItem updateMenuItem(
            @PathVariable Long menuItemId,
            @RequestBody Map<String, Object> body) {
        return menuItemService.updateMenuItem(menuItemId, body);
    }

    @DeleteMapping("/menu-items/{menuItemId}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long menuItemId) {
        menuItemService.deleteMenuItem(menuItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/menu-items/tag")
    public List<MenuItem> searchMenuItemsByTag(@RequestParam String tag) {
        return menuItemService.searchMenuItemsByTag(tag);
    }

    @GetMapping("/menu-items/name")
    public List<MenuItem> searchMenuItemsByName(@RequestParam String name) {
        return menuItemService.searchMenuItemsByName(name);
    }

    @GetMapping("/menu-items")
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @GetMapping("/menu-items/tags")
    public Set<String> getAllMenuItemTags() {
        return menuItemService.getAllMenuItemTags();
    }

    @GetMapping("/tags")
    public Set<String> getAllRestaurantTags() {
        return menuItemService.getAllRestaurantTags();
    }
}