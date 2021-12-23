package com.zavlagas.spring.security.defence.security;

public class SecurityConstants {


    public static final String[] PUBLIC_URLS = {
            "/api/user/register"
    };
    public static final String[] ALLOWED_CORS = {
            "http://localhost:4200",
            "http://localhost:8081"
    };
    public static final String[] ALLOWED_HTTP_METHOD_CALLS = {
            "PUT",
            "DELETE",
            "GET",
            "POST"
    };
    public static final String[] ALLOWED_HEADERS = {
            "Origin",
            "Access-Control-Allow-Origin",
            "Content-Type",
            "Accept",
            "Set-Cookie",
            "Origin, Accept",
            "X-Requested-With",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers",
            "X-XSRF-TOKEN"
    };
    public static final String[] EXPOSED_HEADERS = {
            "Origin",
            "Content-Type",
            "Accept",
            "Set-Cookie",
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Headers",
            "Access-Control-Allow-Credentials",
            "X-XSRF-TOKEN"
    };

    public class Messages {
        public static final String FORBIDDEN_MESSAGE = "You need to Login";
        public static final String ACCESS_DENIED_MESSAGE = "You do not have permission , access denied";
    }
}
