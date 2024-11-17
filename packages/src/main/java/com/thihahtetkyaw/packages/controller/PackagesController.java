package com.thihahtetkyaw.packages.controller;

import com.thihahtetkyaw.packages.dto.PageableDto;
import com.thihahtetkyaw.packages.entity.Packages;
import com.thihahtetkyaw.packages.service.PackagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/packages")
public class PackagesController {

    private final PackagesService packagesService;

    @GetMapping
    public PageableDto<Packages> get(
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> size
    ){
        var pageNumber = page.filter(n -> n > 0).map(n -> n - 1).orElse(0);
        var pageSize = size.filter(n -> n > 0).orElse(10);
        return packagesService.findPackages(PageRequest.of(pageNumber, pageSize));
    }
}
