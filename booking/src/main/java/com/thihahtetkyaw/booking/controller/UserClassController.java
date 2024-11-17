package com.thihahtetkyaw.booking.controller;

import com.thihahtetkyaw.booking.entity.Classes;
import com.thihahtetkyaw.booking.entity.UserClasses;
import com.thihahtetkyaw.booking.entity.embeddedId.UserClassesId;
import com.thihahtetkyaw.booking.service.UserClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{id}")
public class UserClassController {

    private final UserClassService userClassService;

    @GetMapping("classes")
    public List<Classes> getClasses(@PathVariable long id) {
        return userClassService.findByUserId(id);
    }

    @DeleteMapping("classes/{class-id}")
    public ResponseEntity<?> deleteClass(@PathVariable long id, @PathVariable("class-id") long classId) {
        if(userClassService.delete(new UserClassesId(id, classId))){
            return ResponseEntity.noContent().build();
        };
        return ResponseEntity.notFound().build();
    }

    @GetMapping("book/{class-id}")
    public UserClasses book(@PathVariable long id, @PathVariable("class-id") long classId) {
        return userClassService.book(id, classId);
    }

}
