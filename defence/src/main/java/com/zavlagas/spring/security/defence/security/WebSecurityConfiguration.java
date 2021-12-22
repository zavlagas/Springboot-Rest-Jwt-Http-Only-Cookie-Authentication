package com.zavlagas.spring.security.defence.security;

import com.zavlagas.spring.security.defence.security.filters.JWTAuthenticationEntryPointFilter;
import com.zavlagas.spring.security.defence.security.filters.JWTAuthorizationEntryPointFilter;
import com.zavlagas.spring.security.defence.security.exceptions.JwtAccessDeniedExceptionHandler;
import com.zavlagas.spring.security.defence.security.exceptions.JwtAuthenticationEntryPointExceptionHandler;
import com.zavlagas.spring.security.defence.security.jwt.JwtTokenProvider;
import com.zavlagas.spring.security.defence.security.services.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private UserDetailsService userDetailsService;
    private JwtTokenProvider jwtTokenProvider;
    private JwtAccessDeniedExceptionHandler jwtAccessDeniedExceptionHandler;
    private JwtAuthenticationEntryPointExceptionHandler jwtAuthenticationEntryPointExceptionHandler;
    private LoginAttemptService loginAttemptService;

    @Autowired
    public WebSecurityConfiguration(
            UserDetailsService userDetailsService,
            JwtTokenProvider jwtTokenProvider,
            JwtAccessDeniedExceptionHandler jwtAccessDeniedExceptionHandler,
            JwtAuthenticationEntryPointExceptionHandler jwtAuthenticationEntryPointExceptionHandler,
            LoginAttemptService loginAttemptService
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationEntryPointExceptionHandler = jwtAuthenticationEntryPointExceptionHandler;
        this.jwtAccessDeniedExceptionHandler = jwtAccessDeniedExceptionHandler;
        this.loginAttemptService = loginAttemptService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests().antMatchers(SecurityConstants.PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedExceptionHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPointExceptionHandler)
                .and()
                .addFilter(new JWTAuthenticationEntryPointFilter(authenticationManagerBean(), jwtTokenProvider, loginAttemptService))
                .addFilter(new JWTAuthorizationEntryPointFilter(authenticationManagerBean()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList(SecurityConstants.ALLOWED_CORS));
        corsConfiguration.setAllowedMethods(Arrays.asList(SecurityConstants.ALLOWED_HTTP_METHOD_CALLS));
        corsConfiguration.setAllowedHeaders(Arrays.asList(SecurityConstants.ALLOWED_HEADERS));
        corsConfiguration.setExposedHeaders(Arrays.asList(SecurityConstants.EXPOSED_HEADERS));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
