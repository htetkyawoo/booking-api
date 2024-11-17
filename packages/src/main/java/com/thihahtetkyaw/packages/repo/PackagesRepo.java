package com.thihahtetkyaw.packages.repo;

import com.thihahtetkyaw.packages.entity.Packages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PackagesRepo extends JpaRepository<Packages, Long> {

}
