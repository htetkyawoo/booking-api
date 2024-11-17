package com.thihahtetkyaw.booking.service;

import com.thihahtetkyaw.booking.dto.ClassesDto;
import com.thihahtetkyaw.booking.entity.Classes;
import com.thihahtetkyaw.booking.mapper.ClassMapper;
import com.thihahtetkyaw.booking.repo.ClassesRepo;
import com.thihahtetkyaw.booking.dto.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassesService {

    private final ClassesRepo classesRepo;
    private final ClassMapper classMapper;

    public PageableDto<ClassesDto> findClasses(Pageable pageable) {
        return classMapper.toPageable(classesRepo.findClasses(pageable));
    }

    public Classes findById(long id) {
        return classesRepo.findById(id).orElseThrow(() -> new RuntimeException("Class not found"));
    }

    public List<Classes> findNewClass() {
        return classesRepo.findByStartTimeBetween(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
    }
}
