package com.thihahtetkyaw.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thihahtetkyaw.user.entity.AuthUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResetDto {
    @NotEmpty(message = "Please enter your email.")
    @NotBlank(message = "Please enter your email.")
    private String email;

    @NotEmpty(message = "Please enter new password.")
    @NotBlank(message = "Please enter new password.")
    private String password;

    @NotEmpty(message = "Please enter old password.")
    @NotBlank(message = "Please enter old password.")
    @JsonProperty(value = "old-password")
    private String oldPassword;
}
