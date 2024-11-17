package com.thihahtetkyaw.user.mapper;

import com.thihahtetkyaw.user.dto.AuthUserDto;
import com.thihahtetkyaw.user.entity.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthUserMapper {

    AuthUser toEntity(AuthUserDto authUserDto);

    @Mapping(target = "password", ignore = true, defaultValue = "null")
    AuthUserDto toDto(AuthUser authUser);
}
