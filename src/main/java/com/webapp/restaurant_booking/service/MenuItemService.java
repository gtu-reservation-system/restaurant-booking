package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.MenuItem;
import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.repo.MenuItemRepo;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuItemService {
    @Autowired
    private MenuItemRepo menuItemRepository;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Transactional
    public MenuItem addMenuItem(Long restaurantId, Map<String, Object> body) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        String name = (String) body.get("name");
        String description = (String) body.get("description");
        BigDecimal price = new BigDecimal(body.get("price").toString());

        Set<String> tags = (body.get("tags") != null) ? ((List<String>) body.get("tags")).stream().collect(Collectors.toSet()) : Set.of();

        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        menuItem.setTags(tags);
        menuItem.setRestaurant(restaurant);

        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Transactional
    public MenuItem updateMenuItem(Long menuItemId, Map<String, Object> body) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found"));

        if (body.containsKey("name")) {
            menuItem.setName((String) body.get("name"));
        }
        if (body.containsKey("description")) {
            menuItem.setDescription((String) body.get("description"));
        }
        if (body.containsKey("price")) {
            menuItem.setPrice(new BigDecimal(body.get("price").toString()));
        }
        if (body.containsKey("tags")) {
            menuItem.setTags(((List<String>) body.get("tags")).stream().collect(Collectors.toSet()));
        }

        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(Long menuItemId) {
        if (!menuItemRepository.existsById(menuItemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found");
        }
        menuItemRepository.deleteById(menuItemId);
    }

    public List<MenuItem> searchMenuItemsByTag(String tag) {
        return menuItemRepository.findByTag(tag);
    }

    public List<MenuItem> searchMenuItemsByName(String name) {
        return menuItemRepository.findByNameContainingIgnoreCase(name);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public Set<String> getAllMenuItemTags() {
        List<MenuItem> allMenuItems = menuItemRepository.findAll();
        return allMenuItems.stream()
                .flatMap(menuItem -> menuItem.getTags().stream())
                .collect(Collectors.toSet());
    }

    public Set<String> getAllRestaurantTags() {
        List<Restaurant> allRestaurants = restaurantRepo.findAll();
        return allRestaurants.stream()
                .flatMap(restaurant -> restaurant.getTags().stream())
                .collect(Collectors.toSet());
    }
}