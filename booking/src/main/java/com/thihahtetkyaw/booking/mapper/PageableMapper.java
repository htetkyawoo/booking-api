package com.thihahtetkyaw.booking.mapper;

import com.thihahtetkyaw.booking.dto.PageableDto;
import org.springframework.data.domain.Page;

public interface PageableMapper<S, DTO> {

    PageableDto<DTO> toPageable(Page<S> page);
}
