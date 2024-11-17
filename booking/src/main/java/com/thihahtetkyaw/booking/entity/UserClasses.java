package com.thihahtetkyaw.booking.entity;

import com.thihahtetkyaw.booking.entity.embeddedId.UserClassesId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserClasses {

    @EmbeddedId
    private UserClassesId id;

}
