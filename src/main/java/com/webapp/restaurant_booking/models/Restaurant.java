package com.webapp.restaurant_booking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
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

    @Column
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String operatingHours;

    @Column
    private Boolean birthdayParty;

    @Column
    private Boolean anniversary;

    @Column
    private Boolean jobMeeting;

    @Column
    private Boolean proposal;

    @Column
    private String additionalCondition;

    @Column
    private String logoPhotoPath;

    @Column
    private String websiteLink;

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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MenuItem> menuItems = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "restaurant_tags", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    public Restaurant() {}

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public Boolean getBirthdayParty() {
        return birthdayParty;
    }

    public void setBirthdayParty(Boolean birthdayParty) {
        this.birthdayParty = birthdayParty;
    }

    public Boolean getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(Boolean anniversary) {
        this.anniversary = anniversary;
    }

    public Boolean getJobMeeting() {
        return jobMeeting;
    }

    public void setJobMeeting(Boolean jobMeeting) {
        this.jobMeeting = jobMeeting;
    }

    public Boolean getProposal() {
        return proposal;
    }

    public void setProposal(Boolean proposal) {
        this.proposal = proposal;
    }

    public String getAdditionalCondition() {
        return additionalCondition;
    }

    public void setAdditionalCondition(String additionalCondition) {
        this.additionalCondition = additionalCondition;
    }

    public String getLogoPhotoPath() {
        return logoPhotoPath;
    }

    public void setLogoPhotoPath(String logoPhotoPath) {
        this.logoPhotoPath = logoPhotoPath;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
