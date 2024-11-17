package com.thihahtetkyaw.packages.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PurchaseDto {

    @JsonProperty(value = "package-id", required = true)
    private long packageId;
    @NotEmpty(message = "Please enter card details.")
    @NotBlank(message = "Please enter card details.")
    @JsonProperty("card-detail")
    private String cardDetail;
}
