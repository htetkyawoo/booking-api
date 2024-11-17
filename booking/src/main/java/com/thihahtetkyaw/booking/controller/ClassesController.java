package com.thihahtetkyaw.booking.controller;

import com.thihahtetkyaw.booking.dto.ClassesDto;
import com.thihahtetkyaw.booking.dto.PageableDto;
import com.thihahtetkyaw.booking.service.ClassesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classes")
public class ClassesController {

    private final ClassesService classesService;

    @GetMapping
    public PageableDto<ClassesDto> findClass(
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> size
    ){
        var pageNumber = page.filter(n -> n > 0).map(n -> n - 1).orElse(0);
        var pageSize = size.filter(n -> n > 0).orElse(10);
        return  classesService.findClasses(PageRequest.of(pageNumber, pageSize));
    }
}
