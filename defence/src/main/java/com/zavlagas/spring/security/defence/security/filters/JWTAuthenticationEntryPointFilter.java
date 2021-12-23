package com.zavlagas.spring.security.defence.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zavlagas.spring.security.defence.entities.User;

import com.zavlagas.spring.security.defence.security.UserPrincipal;
import com.zavlagas.spring.security.defence.security.cookies.AppCookie;
import com.zavlagas.spring.security.defence.security.jwt.JwtTokenProvider;
import com.zavlagas.spring.security.defence.security.services.LoginAttemptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;


public class JWTAuthenticationEntryPointFilter extends UsernamePasswordAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(JWTAuthenticationEntryPointFilter.class);
    private JwtTokenProvider jwtProvider;
    private AuthenticationManager authenticationManager;
    private LoginAttemptService loginAttemptService;


    public JWTAuthenticationEntryPointFilter(AuthenticationManager authenticationManager,
                                             JwtTokenProvider jwtProvider,
                                             LoginAttemptService loginAttemptService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.loginAttemptService = loginAttemptService;
        setFilterProcessesUrl("/api/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {


        User creds = getUserFromRequest(request);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        new HashSet<>()
                )
        );


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserPrincipal user = (UserPrincipal) authResult.getPrincipal();
        if (authResult.isAuthenticated()) {
            this.loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
            String token = jwtProvider
                    .generateJwtToken(user);
            response.addCookie(createHttpCookie(token));
            
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        UserPrincipal deniedUser = (UserPrincipal) request.getUserPrincipal();
        this.loginAttemptService.addUserToLoginAttemptCache(deniedUser.getUsername());
    }

    private Cookie createHttpCookie(String token) {
        Cookie httpCookie = new Cookie(AppCookie.Properties.name, token);
        httpCookie.setHttpOnly(AppCookie.Properties.isHttpOnly);
        httpCookie.setDomain(AppCookie.Properties.domain);
        httpCookie.setMaxAge(AppCookie.Properties.maxAge);

        return httpCookie;

    }

    private User getUserFromRequest(HttpServletRequest request) {
        User user = null;
        try {
            user = new ObjectMapper().readValue(
                    request.getInputStream(), User.class
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

}
