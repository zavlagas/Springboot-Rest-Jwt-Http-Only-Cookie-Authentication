package com.zavlagas.spring.security.defence.security.filters;

import com.zavlagas.spring.security.defence.security.cookies.AppCookie;
import com.zavlagas.spring.security.defence.security.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

@Component
public class JWTAuthorizationEntryPointFilter extends BasicAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(JWTAuthorizationEntryPointFilter.class);

    private JwtTokenProvider jwtTokenProvider;

    public JWTAuthorizationEntryPointFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        boolean isCookieExist = findIfHttpCookieExist(request);
        if (!isCookieExist) {
            filterChain.doFilter(request, response);
        }

        Authentication authentication = getAuthentication(request, collectTheCookie(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }


    private Authentication getAuthentication(HttpServletRequest request, Cookie httpCookie) {
        String token = httpCookie.getValue();
        String username = jwtTokenProvider.getSubject(token);

        if (jwtTokenProvider.isTokenValid(username, token)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            Set<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);

            return jwtTokenProvider.getAuthentication(username, authorities, request);
        }
        return null;
    }

    private Cookie collectTheCookie(HttpServletRequest request) {
        return Arrays
                .stream(request.getCookies())
                .filter(isCookieTheSame())
                .findFirst().get();
    }

    private boolean findIfHttpCookieExist(HttpServletRequest request) {
        return (Arrays.stream(request.getCookies())
                .anyMatch(isCookieTheSame()));
    }

    private Predicate<Cookie> isCookieTheSame() {
        return cookie -> cookie.getName().equals(AppCookie.Properties.name);
    }


}
