package com.zavlagas.spring.security.defence.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zavlagas.spring.security.defence.security.SecurityConstants;
import com.zavlagas.spring.security.defence.security.models.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPointExceptionHandler extends Http403ForbiddenEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        HttpResponse httpResponse = new HttpResponse(
                forbidden.value(),
                forbidden,
                forbidden.getReasonPhrase().toUpperCase(),
                SecurityConstants.Messages.FORBIDDEN_MESSAGE
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(forbidden.value());

        OutputStream responseObject = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseObject, httpResponse);
        responseObject.flush();
    }
}
