package com.zavlagas.spring.security.defence.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zavlagas.spring.security.defence.security.SecurityConstants;
import com.zavlagas.spring.security.defence.security.models.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAccessDeniedExceptionHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        HttpResponse httpResponse = new HttpResponse(
                unauthorized.value(),
                unauthorized,
                unauthorized.getReasonPhrase().toUpperCase(),
                SecurityConstants.Messages.ACCESS_DENIED_MESSAGE
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(unauthorized.value());

        OutputStream responseObject = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseObject, httpResponse);
        responseObject.flush();
    }
}
