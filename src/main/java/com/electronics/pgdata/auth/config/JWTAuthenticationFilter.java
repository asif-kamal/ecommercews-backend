package com.electronics.pgdata.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailService;
    private final JWTTokenHelper jwtTokenHelper;

    public JWTAuthenticationFilter(UserDetailsService userDetailService, JWTTokenHelper jwtTokenHelper) {
        this.userDetailService = userDetailService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (null == authHeader || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String authToken = jwtTokenHelper.getToken(request);
            if (null != authToken) {
                String username = jwtTokenHelper.getUsernameFromToken(authToken);
                if (null != username) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
