package com.thihahtetkyaw.packages.mapping;

import com.thihahtetkyaw.packages.dto.UserPackagesDto;
import com.thihahtetkyaw.packages.projection.UserPackagesProjection;
import org.mapstruct.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserPackagesMapper {
    @Mappings({
            @Mapping(source = "projection.packages.id", target = "id"),
            @Mapping(source = "projection.packages.name", target = "name"),
            @Mapping(source = "projection.packages.price", target = "price"),
            @Mapping(source = "projection.packages.credits", target = "credits"),
            @Mapping(source = "projection.packages.validDay", target = "validDay"),
            @Mapping(source = "projection.packages.countryId", target = "countryId"),
    })
    UserPackagesDto toDto(UserPackagesProjection projection);

    @AfterMapping
    default void additionalFields(@MappingTarget UserPackagesDto userPackagesDto, UserPackagesProjection projection) {
        if(userPackagesDto != null && projection != null) {
            LocalDateTime lastDate = projection.startTime().plusDays(projection.packages().getValidDay());
            var expired = lastDate.isBefore(LocalDateTime.now());
            if(expired) {
                userPackagesDto.setRemainingTime(null);
            }else{
                var remaining = Duration.between(LocalDateTime.now(), lastDate);
                userPackagesDto.setRemainingTime("%s Days, %s Hours, %s Minutes".formatted(remaining.toDays(), remaining.toHours() % 24, remaining.toMinutes() % 60));
            }
            userPackagesDto.setExpired(expired);
        }
    }
}
