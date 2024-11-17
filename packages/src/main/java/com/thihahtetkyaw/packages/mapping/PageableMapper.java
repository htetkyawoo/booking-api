package com.thihahtetkyaw.packages.mapping;


import com.thihahtetkyaw.packages.dto.PageableDto;
import org.springframework.data.domain.Page;

public interface PageableMapper<S, DTO> {

    PageableDto<DTO> toPageable(Page<S> page);
}
