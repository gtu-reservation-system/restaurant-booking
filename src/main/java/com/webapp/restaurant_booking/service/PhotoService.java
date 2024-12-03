package com.webapp.restaurant_booking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Value("${upload.path}")
    private String uploadPath;

    public String uploadPhoto(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot upload empty file");
        }
        Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        Path targetLocation = uploadDir.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }

    public byte[] getPhoto(String filename) throws IOException {
        Path filePath = Paths.get(uploadPath).resolve(filename).normalize();

        if (!Files.exists(filePath)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found");
        }
        return Files.readAllBytes(filePath);
    }

    public void deletePhoto(String filename) throws IOException {
        Path filePath = Paths.get(uploadPath).resolve(filename).normalize();

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }

    public List<String> uploadMultiplePhotos(List<MultipartFile> files) throws IOException {
        List<String> uploadedFilenames = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = uploadPhoto(file);
            uploadedFilenames.add(filename);
        }

        return uploadedFilenames;
    }

    public void deletePhotos(List<String> filenames) throws IOException {
        for (String filename : filenames) {
            deletePhoto(filename);
        }
    }
}