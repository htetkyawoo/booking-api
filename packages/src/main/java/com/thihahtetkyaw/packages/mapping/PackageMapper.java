package com.thihahtetkyaw.packages.mapping;

import com.thihahtetkyaw.packages.dto.PageableDto;
import com.thihahtetkyaw.packages.entity.Packages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PackageMapper extends PageableMapper<Packages, Packages>{

    @Mappings({
            @Mapping(source = "content", target = "data"),
            @Mapping(source = "number", target = "currentPage")
    })
    PageableDto<Packages> toPageable(Page<Packages> page);
}
