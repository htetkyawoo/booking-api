package com.thihahtetkyaw.packages.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserPackagesDto {
    private long id;
    private String name;
    private BigDecimal price;
    private int credits;
    private int validDay;
    private int countryId;

    private LocalDateTime startTime;
    private int remainingCredits;

    private boolean expired;
    private String remainingTime;


}
