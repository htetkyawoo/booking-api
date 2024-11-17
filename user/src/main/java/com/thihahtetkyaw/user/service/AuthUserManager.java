package com.thihahtetkyaw.user.service;

import com.thihahtetkyaw.user.dto.AuthUserDto;
import com.thihahtetkyaw.user.dto.ResetDto;
import com.thihahtetkyaw.user.entity.AuthUser;
import com.thihahtetkyaw.user.exception.PasswordMissMatchException;
import com.thihahtetkyaw.user.mapper.AuthUserMapper;
import com.thihahtetkyaw.user.repo.AuthUserRepo;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthUserManager implements UserDetailsService {

    private final AuthUserRepo repo;
    private final AuthUserMapper mapper;
    private final PasswordEncoder encoder;
    private final MailService mailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  repo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional // This is for setting verify to true from mock function.
    public AuthUserDto save(AuthUserDto dto) {
        var user = repo.save(mapper.toEntity(dto));
        mailService.sendVerifyEmail(user);
        return mapper.toDto(user);
    }

    public AuthUserDto updatePassword(ResetDto resetDto) {
        var account = findByEmail(resetDto.getEmail());
        if(encoder.matches(resetDto.getOldPassword(), account.getPassword())) {
            account.setPassword(encoder.encode(resetDto.getPassword()));
            return mapper.toDto(repo.save(account));
        }
        throw new PasswordMissMatchException("Please enter a correct old Password.");
    }

    private AuthUser findByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public AuthUserDto findById(long id) {
        return repo.findById(id).map(mapper::toDto).orElseThrow(() -> new UsernameNotFoundException(String.valueOf(id)));
    }
}
