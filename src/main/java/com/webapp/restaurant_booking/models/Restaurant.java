package com.webapp.restaurant_booking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RestaurantTable> restaurantTables;

    public Restaurant() {
    }

    public Restaurant(long id, String name, String address, Set<RestaurantTable> restaurantTables) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.restaurantTables = restaurantTables;
    }

    public Restaurant(String name, String address, Set<RestaurantTable> restaurantTables) {
        this.name = name;
        this.address = address;
        this.restaurantTables = restaurantTables;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<RestaurantTable> getTables() {
        return restaurantTables;
    }

    public void setTables(Set<RestaurantTable> restaurantTables) {
        this.restaurantTables = restaurantTables;
    }
}
