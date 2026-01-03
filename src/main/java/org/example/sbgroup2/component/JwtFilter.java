package org.example.sbgroup2.component;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServletResponse;
import org.example.sbgroup2.models.User;
import org.example.sbgroup2.repositories.UserRepository;
import org.example.sbgroup2.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepo;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        String token = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(c -> c.getName().equals("token"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
//        String token = null;
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("token".equals(cookie.getName())) {
//                    token = cookie.getValue();
//                    break;
//                }
//            }
//        }

        if (token != null) {
            Claims claims = jwtService.parse(token);
            User user = null;
            try {
                user = userRepo.findById(claims.getSubject()).orElseThrow(() -> new RuntimeException("User not found"));
            } catch (Exception e) {
                // Optionally log the error
                logger.warn("User not found or error: " + e.getMessage());
            }

            if (user != null) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            // If user is null, do not set authentication, so the request continues unauthenticated
        }

        filterChain.doFilter(request, response);
    }
}

