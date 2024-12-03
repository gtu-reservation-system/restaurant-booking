package com.webapp.restaurant_booking.controller;

import com.webapp.restaurant_booking.models.User;
import com.webapp.restaurant_booking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserApiControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserApiController userApiController;

    private User testUser;
    private Map<String, String> userBody;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "John Doe", "john.doe@example.com", "password123", "1234567890");
        testUser.setRole("user");

        userBody = new HashMap<>();
        userBody.put("fullName", "John Doe");
        userBody.put("email", "john.doe@example.com");
        userBody.put("password", "password123");
        userBody.put("phoneNumber", "1234567890");
    }

    @Test
    void getAllUsers_Success() {
        List<User> users = Collections.singletonList(testUser);
        when(userService.getAllUsers()).thenReturn(users);

        List<User> result = userApiController.getAllUsers();

        assertEquals(users, result);
        verify(userService).getAllUsers();
    }

    @Test
    void getSingleUser_Exists() {
        when(userService.getSingleUser(1L)).thenReturn(Optional.of(testUser));

        User result = userApiController.getSingleUser(1L);

        assertEquals(testUser, result);
        verify(userService).getSingleUser(1L);
    }

    @Test
    void getSingleUser_NotExists() {
        when(userService.getSingleUser(1L)).thenReturn(Optional.empty());

        User result = userApiController.getSingleUser(1L);

        assertNull(result);
        verify(userService).getSingleUser(1L);
    }

    @Test
    void removeUser_Success() {
        when(userService.removeUser(1L)).thenReturn(true);

        boolean result = userApiController.removeUser(1L);

        assertTrue(result);
        verify(userService).removeUser(1L);
    }

    @Test
    void removeUser_Failure() {
        when(userService.removeUser(1L)).thenReturn(false);

        boolean result = userApiController.removeUser(1L);

        assertFalse(result);
        verify(userService).removeUser(1L);
    }

    @Test
    void updateUser_Success() {
        when(userService.updateUser(1L, userBody)).thenReturn(testUser);

        User result = userApiController.updateUser(1L, userBody);

        assertEquals(testUser, result);
        verify(userService).updateUser(1L, userBody);
    }

    @Test
    void addUser_Success() {
        when(userService.addUser(userBody)).thenReturn(testUser);

        User result = userApiController.addUser(userBody);

        assertEquals(testUser, result);
        verify(userService).addUser(userBody);
    }

    @Test
    void loginUser_Success() {
        Map<String, String> loginBody = new HashMap<>();
        loginBody.put("emailOrPhone", "john.doe@example.com");
        loginBody.put("password", "password123");

        when(userService.login("john.doe@example.com", "password123")).thenReturn(testUser);

        ResponseEntity<?> response = userApiController.loginUser(loginBody);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(testUser, response.getBody());
        verify(userService).login("john.doe@example.com", "password123");
    }
}