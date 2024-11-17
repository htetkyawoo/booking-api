package com.thihahtetkyaw.user.service;

import io.jsonwebtoken.Jwts;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtProvider {

    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private static final Calendar expiration = Calendar.getInstance();
    private static final String TOKEN_PREFIX = "Bearer ";

    public Authentication authenticate(String token) {
        var jwt = Jwts.parser().verifyWith(key)
                .build().parseSignedClaims(token.substring(TOKEN_PREFIX.length()));

        var username = jwt.getPayload().getSubject();
        return UsernamePasswordAuthenticationToken.authenticated(username, null, Collections.emptyList());
    }

    public String generate(Authentication authentication) {
        if (!Objects.isNull(authentication) && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            expiration.add(Calendar.MINUTE, 10);
            var token = Jwts.builder().issuer("htet-kyaw-oo").issuedAt(new Date()).expiration(expiration.getTime())
                    .signWith(key)
                    .subject(authentication.getName())
                    .claim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .compact();
            return TOKEN_PREFIX + token;
        }
        throw new AccessDeniedException("Authentication Error");
    }
}
