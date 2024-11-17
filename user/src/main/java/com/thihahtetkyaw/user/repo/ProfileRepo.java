package com.thihahtetkyaw.user.repo;

import com.thihahtetkyaw.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<Profile, String> {

    Profile findById(long authUserId);
}
