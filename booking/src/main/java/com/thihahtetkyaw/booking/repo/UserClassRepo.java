package com.thihahtetkyaw.booking.repo;

import com.thihahtetkyaw.booking.entity.Classes;
import com.thihahtetkyaw.booking.entity.UserClasses;
import com.thihahtetkyaw.booking.entity.embeddedId.UserClassesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UserClassRepo extends JpaRepository<UserClasses, UserClassesId> {

    @Query("""
            select c from UserClasses uc join Classes c on uc.id.classId = c.id
             where uc.id.userId = ?1
            """)
    List<Classes> findByUserId(Long userId);

    boolean existsById(UserClassesId id);

    @Query("select count(uc) > 0 from UserClasses uc join Classes c on uc.id.classId = c.id where uc.id.userId = ?1 and (c.endTime between ?2 and ?3)")
    boolean hasClassBetween(long userId, LocalDateTime start, LocalDateTime end);
}
