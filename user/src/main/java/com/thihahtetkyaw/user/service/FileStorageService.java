package com.thihahtetkyaw.user.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public interface FileStorageService {
    String save(MultipartFile photo, String id, String storagePath);

    default String getExtension(MultipartFile file) {
        var array = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        return array[array.length - 1];
    }
}
