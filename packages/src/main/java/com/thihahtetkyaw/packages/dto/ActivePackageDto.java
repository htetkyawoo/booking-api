package com.thihahtetkyaw.packages.dto;

import java.math.BigDecimal;

public record ActivePackageDto(
         long id,
         String name,
         BigDecimal price,
         int credits,
         int validDay,
         int countryId
) {
}
