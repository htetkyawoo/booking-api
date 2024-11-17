package com.thihahtetkyaw.user.service;

import com.thihahtetkyaw.user.dto.AuthUserDto;
import com.thihahtetkyaw.user.entity.AuthUser;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    public boolean sendVerifyEmail(AuthUser authUser) {
        authUser.setVerified(true);
        return true;
    }
}
