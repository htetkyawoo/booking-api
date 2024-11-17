package com.thihahtetkyaw.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thihahtetkyaw.user.entity.AuthUser;
import com.thihahtetkyaw.user.entity.Profile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthUserDto {

    private Long id;

    @NotEmpty(message = "Please enter your email.", groups = {AuthUser.AccountWithoutPassword.class, AuthUser.AccountWithPassword.class})
    @NotBlank(message = "Please enter your email.", groups = {AuthUser.AccountWithoutPassword.class, AuthUser.AccountWithPassword.class})
    @JsonProperty(required = true)
    private String email;

    @NotEmpty(message = "Please enter your name.", groups = {AuthUser.AccountWithoutPassword.class, AuthUser.AccountWithPassword.class})
    @NotBlank(message = "Please enter your name.", groups = {AuthUser.AccountWithoutPassword.class, AuthUser.AccountWithPassword.class})
    @JsonProperty(required = true)
    private String password;

    @NotEmpty(message = "Please enter password." , groups = {AuthUser.AccountWithPassword.class})
    @NotBlank(message = "Please enter password.", groups = {AuthUser.AccountWithPassword.class})
    @JsonProperty(required = true)
    private String name;

    @NotNull(message = "Please select your gender.", groups = {AuthUser.AccountWithPassword.class, AuthUser.AccountWithoutPassword.class})
    private AuthUser.Gender gender;

    private boolean isVerified;
}
