package com.thihahtetkyaw.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

public record ClassesDto (
    long id,
    String name,
    int cost,
    LocalDateTime startTime,
    LocalDateTime endTime,
    int totalSlot,
    long occupiedSlot,
    int countryId
){}
