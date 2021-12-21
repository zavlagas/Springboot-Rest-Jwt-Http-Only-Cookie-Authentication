package com.zavlagas.spring.security.defence.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AppJwt {


    protected static class Security {

        public static final String TOKEN_PREFIX = "Bearer";
        public static final long EXPIRATION_TIME = 432_000_000;


    }


    protected static class Properties {

        public static final Date ISSUED_AT = new Date();
        public static final Date EXPIRED_AT = new Date(System.currentTimeMillis()
                + AppJwt.Security.EXPIRATION_TIME);

        private static String issuer = "Issuer";
        private static String subject = "Subject";
        private static String audience = "Audience";
        private static String authorities = "Authorities";


        public static String getIssuer() {
            return issuer;
        }

        public static void setIssuer(String issuer) {
            Properties.issuer = issuer;
        }

        public static String getSubject() {
            return subject;
        }

        public static void setSubject(String subject) {
            Properties.subject = subject;
        }

        public static String getAudience() {
            return audience;
        }

        public static void setAudience(String audience) {
            Properties.audience = audience;
        }

        public static String getAuthorities() {
            return authorities;
        }

        public static void setAuthorities(String authorities) {
            Properties.authorities = authorities;
        }
    }


    protected class Message {
      
        public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    }

}
