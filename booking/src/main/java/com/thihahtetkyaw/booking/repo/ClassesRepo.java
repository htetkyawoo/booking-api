package com.thihahtetkyaw.booking.repo;

import com.thihahtetkyaw.booking.dto.ClassesDto;
import com.thihahtetkyaw.booking.entity.Classes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassesRepo extends JpaRepository<Classes, Long> {

    @Query("""
            select new com.thihahtetkyaw.booking.dto.ClassesDto(c.id, c.name, c.cost, c.startTime, c.endTime, c.totalSlot, count(uc), c.countryId )
            from Classes c left join UserClasses uc on uc.id.classId = c.id group by c.id, c.name, c.cost, c.startTime, c.endTime, c.totalSlot, c.countryId
            """)
    Page<ClassesDto> findClasses(Pageable pageable);

    @Query("select count(c) from Classes c")
    long countById(long classId);

    List<Classes> findByStartTimeBetween(LocalDateTime from, LocalDateTime to);
}
