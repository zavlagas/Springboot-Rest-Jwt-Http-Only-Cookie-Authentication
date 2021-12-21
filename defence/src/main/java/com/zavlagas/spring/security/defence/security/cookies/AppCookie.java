package com.zavlagas.spring.security.defence.security.cookies;

public class AppCookie {

    public static class Properties {
        public static final String name = "Jwt-Cookie";
        public static final boolean isHttpOnly = true;
        public static final String domain = "com.zavlagas.spring.security";
        public static final int maxAge = 86400;
    }
}
