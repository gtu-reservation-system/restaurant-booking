package com.webapp.restaurant_booking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class PhotoService {

    @Value("${upload.dir}")
    private String uploadDir;

    public String uploadPhoto(MultipartFile file) throws IOException {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);
        return uniqueFileName;
    }

    public byte[] getPhoto(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(filename);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filename);
        }
        return Files.readAllBytes(filePath);
    }
}
