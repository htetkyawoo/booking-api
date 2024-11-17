package com.thihahtetkyaw.packages.controller;

import com.thihahtetkyaw.packages.dto.PurchaseDto;
import com.thihahtetkyaw.packages.dto.UserPackagesDto;
import com.thihahtetkyaw.packages.entity.UserPackages;
import com.thihahtetkyaw.packages.entity.embedded.UserPackagesId;
import com.thihahtetkyaw.packages.service.UserPackagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/{id}/packages")
public class UserPackagesController {

    private final UserPackagesService userPackagesService;

    @GetMapping
    public List<UserPackagesDto> get(@PathVariable long id) {
        return userPackagesService.findUserPackages(id);
    }

    @PostMapping
    public UserPackages purchase(@PathVariable long id, @RequestBody @Validated PurchaseDto purchaseDto, BindingResult bindingResult) {
        return userPackagesService.purchase(new UserPackagesId(id, purchaseDto.getPackageId()), purchaseDto.getCardDetail());
    }
}
