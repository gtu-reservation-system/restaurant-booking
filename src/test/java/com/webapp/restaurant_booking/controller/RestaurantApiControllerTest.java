package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.RestaurantTable;
import com.webapp.restaurant_booking.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantApiControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantApiController restaurantApiController;

    private Restaurant restaurant;
    private Map<String, Object> restaurantBody;

    @BeforeEach
    void setUp() {
        Set<RestaurantTable> tables = new HashSet<>();
        RestaurantTable table = new RestaurantTable();
        table.setName("Table 1");
        table.setAvailable(true);
        tables.add(table);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test Street");
        restaurant.setTables(tables);
        restaurant.setTags(new HashSet<>(Arrays.asList("Fine Dining", "Casual")));

        restaurantBody = new HashMap<>();
        restaurantBody.put("name", "Test Restaurant");
        restaurantBody.put("address", "123 Test Street");
        restaurantBody.put("tables", Collections.singletonList(
                Map.of("name", "Table 1", "available", true)
        ));
        restaurantBody.put("tags", Arrays.asList("Fine Dining", "Casual"));
    }

    @Test
    void getAllRestaurants_Success() {
        List<Restaurant> restaurants = Collections.singletonList(restaurant);
        when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        List<Restaurant> result = restaurantApiController.getAllRestaurants();

        assertEquals(restaurants, result);
        verify(restaurantService).getAllRestaurants();
    }

    @Test
    void getSingleRestaurant_Success() {
        when(restaurantService.getSingleRestaurant(1L)).thenReturn(Optional.of(restaurant));

        Restaurant result = restaurantApiController.getSingleRestaurant(1L);

        assertEquals(restaurant, result);
        verify(restaurantService).getSingleRestaurant(1L);
    }

    @Test
    void getSingleRestaurant_NotFound() {
        when(restaurantService.getSingleRestaurant(1L)).thenReturn(Optional.empty());

        Restaurant result = restaurantApiController.getSingleRestaurant(1L);

        assertNull(result);
        verify(restaurantService).getSingleRestaurant(1L);
    }

    @Test
    void removeRestaurant_Success() {
        when(restaurantService.removeRestaurant(1L)).thenReturn(true);

        boolean result = restaurantApiController.removeRestaurant(1L);

        assertTrue(result);
        verify(restaurantService).removeRestaurant(1L);
    }

    @Test
    void updateRestaurant_Success() {
        when(restaurantService.updateRestaurant(1L, restaurantBody)).thenReturn(restaurant);

        Restaurant result = restaurantApiController.updateRestaurant(1L, restaurantBody);

        assertEquals(restaurant, result);
        verify(restaurantService).updateRestaurant(1L, restaurantBody);
    }

    @Test
    void addRestaurant_Success() {
        when(restaurantService.addRestaurant(restaurantBody)).thenReturn(restaurant);

        Restaurant result = restaurantApiController.addRestaurant(restaurantBody);

        assertEquals(restaurant, result);
        verify(restaurantService).addRestaurant(restaurantBody);
    }

    @Test
    void uploadRestaurantPhoto_Success() throws IOException {
        MultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        when(restaurantService.uploadRestaurantPhoto(1L, mockFile)).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantApiController.uploadRestaurantPhoto(1L, mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurant, response.getBody());
        verify(restaurantService).uploadRestaurantPhoto(1L, mockFile);
    }

    @Test
    void getRestaurantPhoto_Success() throws IOException {
        byte[] mockPhotoBytes = "test photo content".getBytes();

        when(restaurantService.getRestaurantPhoto(1L, 0)).thenReturn(mockPhotoBytes);

        ResponseEntity<byte[]> response = restaurantApiController.getRestaurantPhoto(1L, 0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
        assertArrayEquals(mockPhotoBytes, response.getBody());
        verify(restaurantService).getRestaurantPhoto(1L, 0);
    }

    @Test
    void searchRestaurantsByTag_Success() {
        List<Restaurant> restaurants = Collections.singletonList(restaurant);
        when(restaurantService.searchRestaurantsByTag("Fine Dining")).thenReturn(restaurants);

        List<Restaurant> result = restaurantApiController.searchRestaurantsByTag("Fine Dining");

        assertEquals(restaurants, result);
        verify(restaurantService).searchRestaurantsByTag("Fine Dining");
    }

    @Test
    void searchRestaurantsByName_Success() {
        List<Restaurant> restaurants = Collections.singletonList(restaurant);
        when(restaurantService.searchRestaurantsByName("Test")).thenReturn(restaurants);

        List<Restaurant> result = restaurantApiController.searchRestaurantsByName("Test");

        assertEquals(restaurants, result);
        verify(restaurantService).searchRestaurantsByName("Test");
    }

    @Test
    void searchRestaurantsByMenuItem_Success() {
        List<Restaurant> restaurants = Collections.singletonList(restaurant);
        when(restaurantService.searchRestaurantsByMenuItem("Pizza")).thenReturn(restaurants);

        List<Restaurant> result = restaurantApiController.searchRestaurantsByMenuItem("Pizza");

        assertEquals(restaurants, result);
        verify(restaurantService).searchRestaurantsByMenuItem("Pizza");
    }
}