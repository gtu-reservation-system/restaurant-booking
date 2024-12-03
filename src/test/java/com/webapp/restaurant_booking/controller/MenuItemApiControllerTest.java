package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.MenuItem;
import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.service.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuItemApiControllerTest {

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private MenuItemApiController menuItemApiController;

    private MenuItem menuItem;
    private Map<String, Object> menuItemBody;

    @BeforeEach
    void setUp() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("Test Pizza");
        menuItem.setDescription("Description of test pizza");
        menuItem.setPrice(new BigDecimal("12.99"));
        menuItem.setRestaurant(restaurant);

        Set<String> tags = new HashSet<>(Arrays.asList("Italian", "Vegetarian"));
        menuItem.setTags(tags);

        menuItemBody = new HashMap<>();
        menuItemBody.put("name", "Test Pizza");
        menuItemBody.put("description", "Description of test pizza");
        menuItemBody.put("price", 12.99);
        menuItemBody.put("tags", new ArrayList<>(tags));
    }

    @Test
    void addMenuItem_Success() {
        when(menuItemService.addMenuItem(eq(1L), eq(menuItemBody))).thenReturn(menuItem);

        ResponseEntity<MenuItem> response = menuItemApiController.addMenuItem(1L, menuItemBody);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(menuItem, response.getBody());
        verify(menuItemService).addMenuItem(1L, menuItemBody);
    }

    @Test
    void getMenuItemsByRestaurant_Success() {
        List<MenuItem> menuItems = Collections.singletonList(menuItem);
        when(menuItemService.getMenuItemsByRestaurant(1L)).thenReturn(menuItems);

        List<MenuItem> result = menuItemApiController.getMenuItemsByRestaurant(1L);

        assertEquals(menuItems, result);
        verify(menuItemService).getMenuItemsByRestaurant(1L);
    }

    @Test
    void updateMenuItem_Success() {
        when(menuItemService.updateMenuItem(1L, menuItemBody)).thenReturn(menuItem);

        MenuItem result = menuItemApiController.updateMenuItem(1L, menuItemBody);

        assertEquals(menuItem, result);
        verify(menuItemService).updateMenuItem(1L, menuItemBody);
    }

    @Test
    void deleteMenuItem_Success() {
        doNothing().when(menuItemService).deleteMenuItem(1L);

        ResponseEntity<Void> response = menuItemApiController.deleteMenuItem(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(menuItemService).deleteMenuItem(1L);
    }

    @Test
    void searchMenuItemsByTag_Success() {
        List<MenuItem> menuItems = Collections.singletonList(menuItem);
        when(menuItemService.searchMenuItemsByTag("Italian")).thenReturn(menuItems);

        List<MenuItem> result = menuItemApiController.searchMenuItemsByTag("Italian");

        assertEquals(menuItems, result);
        verify(menuItemService).searchMenuItemsByTag("Italian");
    }

    @Test
    void searchMenuItemsByName_Success() {
        List<MenuItem> menuItems = Collections.singletonList(menuItem);
        when(menuItemService.searchMenuItemsByName("Pizza")).thenReturn(menuItems);

        List<MenuItem> result = menuItemApiController.searchMenuItemsByName("Pizza");

        assertEquals(menuItems, result);
        verify(menuItemService).searchMenuItemsByName("Pizza");
    }

    @Test
    void getAllMenuItems_Success() {
        List<MenuItem> menuItems = Collections.singletonList(menuItem);
        when(menuItemService.getAllMenuItems()).thenReturn(menuItems);

        List<MenuItem> result = menuItemApiController.getAllMenuItems();

        assertEquals(menuItems, result);
        verify(menuItemService).getAllMenuItems();
    }

    @Test
    void getAllMenuItemTags_Success() {
        Set<String> expectedTags = new HashSet<>(Arrays.asList("Italian", "Vegetarian"));
        when(menuItemService.getAllMenuItemTags()).thenReturn(expectedTags);

        Set<String> result = menuItemApiController.getAllMenuItemTags();

        assertEquals(expectedTags, result);
        verify(menuItemService).getAllMenuItemTags();
    }

    @Test
    void getAllRestaurantTags_Success() {
        Set<String> expectedTags = new HashSet<>(Arrays.asList("Fine Dining", "Casual"));
        when(menuItemService.getAllRestaurantTags()).thenReturn(expectedTags);

        Set<String> result = menuItemApiController.getAllRestaurantTags();

        assertEquals(expectedTags, result);
        verify(menuItemService).getAllRestaurantTags();
    }
}