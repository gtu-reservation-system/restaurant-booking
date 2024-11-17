package com.webapp.restaurant_booking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
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

    @ElementCollection
    @CollectionTable(name = "restaurant_photos", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "photo_path")
    private List<String> photoPaths = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<RestaurantTable> restaurantTables;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Reservation> reservations;

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

    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
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
