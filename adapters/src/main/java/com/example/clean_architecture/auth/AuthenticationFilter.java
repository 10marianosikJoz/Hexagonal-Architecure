package com.example.clean_architecture.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

class AuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final TokenFacade tokenFacade;

    AuthenticationFilter(final UserDetailsService userDetailsService,
                         final TokenFacade tokenFacade) {

        this.userDetailsService = userDetailsService;
        this.tokenFacade = tokenFacade;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER))
                .map(authHeader -> authHeader.substring(BEARER.length()))
                .ifPresent(token -> {
                    if (SecurityContextHolder.getContext().getAuthentication() != null) {
                        return;
                    }

                    var username = tokenFacade.getUsernameFromToken(token);
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    if (tokenFacade.isValidForUser(token, userDetails)) {
                        var authentication = new UsernamePasswordAuthenticationToken(userDetails,
                                                                                     null,
                                                                                     userDetails.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                });

        filterChain.doFilter(request, response);
    }
}