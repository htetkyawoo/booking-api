package com.thihahtetkyaw.packages.repo;

import com.thihahtetkyaw.packages.dto.ActivePackageDto;
import com.thihahtetkyaw.packages.entity.Packages;
import com.thihahtetkyaw.packages.entity.UserPackages;
import com.thihahtetkyaw.packages.entity.embedded.UserPackagesId;
import com.thihahtetkyaw.packages.projection.UserPackagesProjection;
import jakarta.persistence.EntityResult;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserPackagesRepo extends JpaRepository<UserPackages, UserPackagesId> {

    @Query("""
            select new com.thihahtetkyaw.packages.projection.UserPackagesProjection(p, up.startTime, up.remainingCredits)
             from UserPackages up join Packages p on p.id = up.userPackageId.packageId where up.userPackageId.authUserId = ?1
            """)
    List<UserPackagesProjection> findAllUserPackages(long userId);

    Optional<UserPackages> findByUserPackageId(UserPackagesId id);
}
