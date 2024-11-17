package com.thihahtetkyaw.user.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thihahtetkyaw.user.dto.GeneralDto;
import com.thihahtetkyaw.user.entity.AuthUser;
import com.thihahtetkyaw.user.exception.VerificationException;
import com.thihahtetkyaw.user.service.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenGenerateFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Basic ")){
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication.getPrincipal() instanceof AuthUser authUser){
                System.out.println(authUser.isVerified());
            }
            if (authentication.getPrincipal() instanceof AuthUser authUser && authUser.isVerified()) {
                var token = Map.of("token", jwtProvider.generate(authentication));
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(token));
            }else {
                var dto = GeneralDto.withAuthenticationFail("Authentication Failure", "Please verify your account and try again.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(dto));
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }
}
