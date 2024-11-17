package com.thihahtetkyaw.user.controller;

import com.thihahtetkyaw.user.entity.Profile;
import com.thihahtetkyaw.user.service.FileStorageService;
import com.thihahtetkyaw.user.service.ProfileService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@MultipartConfig
@RequestMapping("/api/accounts/{id}/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public String profile(@PathVariable int id) {
        return profileService.getProfile(id);
    }

    @PostMapping
    public Profile profilePost(@PathVariable int id, @RequestPart MultipartFile profile) {
        return profileService.save(id, fileStorageService.save(profile, String.valueOf(id), "static/profiles"));
    }
}
