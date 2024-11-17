package com.thihahtetkyaw.packages.service;

import com.thihahtetkyaw.packages.dto.PageableDto;
import com.thihahtetkyaw.packages.entity.Packages;
import com.thihahtetkyaw.packages.mapping.PackageMapper;
import com.thihahtetkyaw.packages.repo.PackagesRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PackagesService {

    private final PackagesRepo repo;
    private final PackageMapper packageMapper;

    public PageableDto<Packages> findPackages(Pageable pageable) {
        return packageMapper.toPageable(repo.findAll(pageable));
    }

    public Packages findById(long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("No such package with %d.".formatted(id)));
    }
}
