package com.zavlagas.spring.security.defence.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zavlagas.spring.security.defence.entities.User;

import com.zavlagas.spring.security.defence.security.UserPrincipal;
import com.zavlagas.spring.security.defence.security.cookies.AppCookie;
import com.zavlagas.spring.security.defence.security.jwt.JwtTokenProvider;
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


    public JWTAuthenticationEntryPointFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {

        try {
            User creds = new ObjectMapper().readValue(
                    request.getInputStream(), User.class
            );
            logger.info(" -> attemptAuthentication :  Username :" + creds.getUsername()
                    + " + Password : " + creds.getPassword());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new HashSet<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        logger.info("-> successfulAuthentication :" + authResult);
        if (authResult.isAuthenticated()) {
            String token = jwtProvider
                    .generateJwtToken((UserPrincipal) authResult.getPrincipal());
            response.addCookie(createHttpCookie(token));
        }

    }


    private Cookie createHttpCookie(String token) {
        Cookie httpCookie = new Cookie(AppCookie.Properties.name, token);
        httpCookie.setHttpOnly(AppCookie.Properties.isHttpOnly);
        httpCookie.setDomain(AppCookie.Properties.domain);
        httpCookie.setMaxAge(AppCookie.Properties.maxAge);

        return httpCookie;

    }

}
