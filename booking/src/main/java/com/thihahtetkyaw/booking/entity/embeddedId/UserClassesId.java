package com.thihahtetkyaw.booking.entity.embeddedId;

import com.thihahtetkyaw.booking.entity.Classes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserClassesId {

    @Column(name = "auth_user_id")
    private long userId;
    private long classId;
}
