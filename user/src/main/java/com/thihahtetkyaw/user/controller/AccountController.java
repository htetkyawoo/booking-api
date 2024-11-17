package com.thihahtetkyaw.user.controller;

import com.thihahtetkyaw.user.dto.AuthUserDto;
import com.thihahtetkyaw.user.dto.ResetDto;
import com.thihahtetkyaw.user.entity.AuthUser;
import com.thihahtetkyaw.user.service.AuthUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final PasswordEncoder passwordEncoder;
    private final AuthUserManager authUserManager;


    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody @Validated(AuthUser.AccountWithPassword.class) AuthUserDto authUserDto, BindingResult bindingResult) {
        authUserDto.setPassword(passwordEncoder.encode(authUserDto.getPassword()));
        var dto =  authUserManager.save(authUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public AuthUserDto get(@PathVariable long id) {
        return authUserManager.findById(id);
    }

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Validated ResetDto resetDto, BindingResult bindingResult) {
        authUserManager.updatePassword(resetDto);
        return ResponseEntity.ok().body("Successfully changed password.");
    }


}
