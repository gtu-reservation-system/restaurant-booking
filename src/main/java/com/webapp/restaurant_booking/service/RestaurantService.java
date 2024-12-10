package com.webapp.restaurant_booking.service;

import com.webapp.restaurant_booking.models.Restaurant;
import com.webapp.restaurant_booking.models.RestaurantTable;
import com.webapp.restaurant_booking.repo.RestaurantRepo;
import com.webapp.restaurant_booking.repo.TableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashSet;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private TableRepo tableRepo;

    @Autowired
    private PhotoService photoService;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    public Optional<Restaurant> getSingleRestaurant(long id) {
        return restaurantRepo.findById(id);
    }

    public boolean removeRestaurant(long id) {
        if (restaurantRepo.existsById(id)) {
            restaurantRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Restaurant loginRestaurant(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        Optional<Restaurant> restaurantOpt = restaurantRepo.findByEmail(email);
        Restaurant restaurant = restaurantOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email"));

        if (!restaurant.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return restaurant;
    }

    public Restaurant updateRestaurant(long id, Map<String, Object> body) {
        Restaurant current = restaurantRepo.findById(id).orElse(null);
        if (current != null) {
            current.setName((String) body.get("name"));
            current.setAddress((String) body.get("address"));

            current.setPhoneNumber((String) body.get("phoneNumber"));
            current.setEmail((String) body.get("email"));
            current.setPassword((String) body.get("password"));
            current.setOperatingHours((String) body.get("operatingHours"));

            current.setBirthdayParty((Boolean) body.get("birthdayParty"));
            current.setAnniversary((Boolean) body.get("anniversary"));
            current.setJobMeeting((Boolean) body.get("jobMeeting"));
            current.setProposal((Boolean) body.get("proposal"));

            current.setAdditionalCondition((String) body.get("additionalCondition"));
            current.setWebsiteLink((String) body.get("websiteLink"));

            List<Map<String, Object>> tablesData = (List<Map<String, Object>>) body.get("tables");
            Set<RestaurantTable> restaurantTables = new HashSet<>();
            for (Map<String, Object> tableData : tablesData) {
                RestaurantTable table = new RestaurantTable();
                table.setName((String) tableData.get("name"));
                table.setAvailable((Boolean) tableData.get("available"));
                table.setCapacity(((Number) tableData.get("capacity")).intValue());
                table.setRestaurant(current);
                restaurantTables.add(table);
            }
            current.setTables(restaurantTables);

            if (body.containsKey("tags")) {
                List<String> tags = (List<String>) body.get("tags");
                current.setTags(new HashSet<>(tags));
            }

            restaurantRepo.save(current);
        }
        return current;
    }

    public Restaurant addRestaurant(Map<String, Object> body) {
        String name = (String) body.get("name");
        String address = (String) body.get("address");

        String phoneNumber = (String) body.get("phoneNumber");
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        String operatingHours = (String) body.get("operatingHours");

        Boolean birthdayParty = (Boolean) body.get("birthdayParty");
        Boolean anniversary = (Boolean) body.get("anniversary");
        Boolean jobMeeting = (Boolean) body.get("jobMeeting");
        Boolean proposal = (Boolean) body.get("proposal");

        String additionalCondition = (String) body.get("additionalCondition");
        String websiteLink = (String) body.get("websiteLink");

        List<Map<String, Object>> tablesData = (List<Map<String, Object>>) body.get("tables");
        List<String> tags = body.get("tags") != null ? (List<String>) body.get("tags") : new ArrayList<>();

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(name);
        newRestaurant.setAddress(address);

        newRestaurant.setPhoneNumber(phoneNumber);
        newRestaurant.setEmail(email);
        newRestaurant.setPassword(password);
        newRestaurant.setOperatingHours(operatingHours);

        newRestaurant.setBirthdayParty(birthdayParty);
        newRestaurant.setAnniversary(anniversary);
        newRestaurant.setJobMeeting(jobMeeting);
        newRestaurant.setProposal(proposal);

        newRestaurant.setAdditionalCondition(additionalCondition);
        newRestaurant.setWebsiteLink(websiteLink);

        newRestaurant.setTags(new HashSet<>(tags));

        Set<RestaurantTable> restaurantTables = new HashSet<>();
        for (Map<String, Object> tableData : tablesData) {
            String tableName = (String) tableData.get("name");
            boolean available = (Boolean) tableData.get("available");
            int capacity = ((Number) tableData.get("capacity")).intValue();

            RestaurantTable restaurantTable = new RestaurantTable();
            restaurantTable.setName(tableName);
            restaurantTable.setAvailable(available);
            restaurantTable.setCapacity(capacity);
            restaurantTable.setRestaurant(newRestaurant);

            restaurantTables.add(restaurantTable);
        }
        newRestaurant.setTables(restaurantTables);

        return restaurantRepo.save(newRestaurant);
    }

    public Restaurant addRestaurantWithPhotos(Map<String, Object> body, List<MultipartFile> photos) throws IOException {
        Restaurant newRestaurant = addRestaurant(body);

        if (photos != null && !photos.isEmpty()) {
            List<String> photoPaths = photoService.uploadMultiplePhotos(photos);
            newRestaurant.setPhotoPaths(photoPaths);
            restaurantRepo.save(newRestaurant);
        }

        return newRestaurant;
    }

    public Restaurant addRestaurantPhotos(Long restaurantId, List<MultipartFile> newPhotos) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        List<String> newPhotoPaths = photoService.uploadMultiplePhotos(newPhotos);

        if (restaurant.getPhotoPaths() == null) {
            restaurant.setPhotoPaths(new ArrayList<>());
        }
        restaurant.getPhotoPaths().addAll(newPhotoPaths);

        return restaurantRepo.save(restaurant);
    }

    public Restaurant removeRestaurantPhotos(Long restaurantId, List<Integer> photoIndexes) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        List<String> photoPaths = restaurant.getPhotoPaths();

        List<String> photosToRemove = photoIndexes.stream()
                .map(photoPaths::get)
                .collect(Collectors.toList());

        photoService.deletePhotos(photosToRemove);

        photoPaths.removeAll(photosToRemove);
        restaurant.setPhotoPaths(photoPaths);

        return restaurantRepo.save(restaurant);
    }

    public Restaurant replaceRestaurantPhotos(Long restaurantId, List<MultipartFile> newPhotos) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        if (restaurant.getPhotoPaths() != null && !restaurant.getPhotoPaths().isEmpty()) {
            photoService.deletePhotos(restaurant.getPhotoPaths());
        }

        List<String> newPhotoPaths = photoService.uploadMultiplePhotos(newPhotos);
        restaurant.setPhotoPaths(newPhotoPaths);

        return restaurantRepo.save(restaurant);
    }

    public List<byte[]> getAllRestaurantPhotos(Long restaurantId) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        List<String> photoPaths = restaurant.getPhotoPaths();

        return photoPaths.stream()
                .map(path -> {
                    try {
                        return photoService.getPhoto(path);
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading photo");
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Restaurant> searchRestaurantsByTag(String tag) {
        return restaurantRepo.findByTag(tag);
    }

    public List<Restaurant> searchRestaurantsByName(String name) {
        return restaurantRepo.findByNameContainingIgnoreCase(name);
    }

    public List<Restaurant> searchRestaurantsByMenuItem(String menuItemName) {
        return restaurantRepo.findByMenuItemNameContainingIgnoreCase(menuItemName);
    }

    public Restaurant addRestaurantWithLogoAndPhotos(Map<String, Object> body, MultipartFile logoPhoto, List<MultipartFile> photos) throws IOException {
        Restaurant newRestaurant = addRestaurant(body);

        if (logoPhoto != null && !logoPhoto.isEmpty()) {
            String logoPath = photoService.uploadPhoto(logoPhoto);
            newRestaurant.setLogoPhotoPath(logoPath);
        }

        if (photos != null && !photos.isEmpty()) {
            List<String> photoPaths = photoService.uploadMultiplePhotos(photos);
            newRestaurant.setPhotoPaths(photoPaths);
        }

        return restaurantRepo.save(newRestaurant);
    }

    public Restaurant updateRestaurantLogo(Long restaurantId, MultipartFile logoPhoto) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        if (restaurant.getLogoPhotoPath() != null) {
            photoService.deletePhoto(restaurant.getLogoPhotoPath());
        }

        String logoPath = photoService.uploadPhoto(logoPhoto);
        restaurant.setLogoPhotoPath(logoPath);

        return restaurantRepo.save(restaurant);
    }

    public byte[] getRestaurantLogo(Long restaurantId) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        if (restaurant.getLogoPhotoPath() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Logo not found");
        }

        return photoService.getPhoto(restaurant.getLogoPhotoPath());
    }

    public Restaurant deleteLogo(Long restaurantId) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        if (restaurant.getLogoPhotoPath() != null) {
            photoService.deletePhoto(restaurant.getLogoPhotoPath());
            restaurant.setLogoPhotoPath(null);
            return restaurantRepo.save(restaurant);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No logo found to delete");
    }
}
