package com.thihahtetkyaw.packages.entity;

import com.thihahtetkyaw.packages.entity.embedded.UserPackagesId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPackages {

    @EmbeddedId
    private UserPackagesId userPackageId;
    private LocalDateTime startTime;
    private int remainingCredits;

}
