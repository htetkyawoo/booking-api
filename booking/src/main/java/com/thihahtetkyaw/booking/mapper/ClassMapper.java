package com.thihahtetkyaw.booking.mapper;

import com.thihahtetkyaw.booking.dto.ClassesDto;
import com.thihahtetkyaw.booking.dto.PageableDto;
import com.thihahtetkyaw.booking.entity.Classes;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClassMapper extends PageableMapper<ClassesDto, ClassesDto> {

    @Mappings({
            @Mapping(source = "content", target = "data"),
            @Mapping(source = "number", target = "currentPage")
    })
    PageableDto<ClassesDto> toPageable(Page<ClassesDto> page);
}
