package com.thihahtetkyaw.packages.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPackagesId {

    @Column(name = "auth_user_id")
    private long authUserId;
    @Column(name = "package_id")
    private long packageId;
}
