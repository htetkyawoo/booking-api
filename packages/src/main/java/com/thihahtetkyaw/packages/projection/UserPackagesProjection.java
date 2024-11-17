package com.thihahtetkyaw.packages.projection;

import com.thihahtetkyaw.packages.entity.Packages;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserPackagesProjection
        (
                Packages packages,
                LocalDateTime startTime,
                int remainingCredits
        ) {
}
