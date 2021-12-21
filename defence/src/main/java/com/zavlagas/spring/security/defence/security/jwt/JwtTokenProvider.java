package com.zavlagas.spring.security.defence.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zavlagas.spring.security.defence.security.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/* TODO - >Introduce Variables*/
/* TODO -> Change Algorithm*/

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(UserPrincipal userPrincipal) {
        String[] claims = getClaimsFromUser(userPrincipal);
        String jwt = JWT.create()
                .withIssuer(AppJwt.Properties.getIssuer())
                .withAudience(AppJwt.Properties.getAudience())
                .withIssuedAt(AppJwt.Properties.ISSUED_AT)
                .withSubject(AppJwt.Properties.getSubject())
                .withArrayClaim(AppJwt.Properties.getAuthorities(), claims)
                .withExpiresAt(AppJwt.Properties.EXPIRED_AT)
                .sign(getJwtAlgorithm());
        return jwt;
    }

    public Set<GrantedAuthority> getAuthorities(String token) {
        return Arrays
                .stream(getClaimsFromToken(token))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public Authentication getAuthentication(String username, Set<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities
                );
        return usernamePasswordAuthToken;
    }

    public boolean isTokenValid(String username, String token) {
        JWTVerifier verifier = getJwtVerifier();
        return StringUtils.isNoneEmpty(username) && !isTokenExpired(verifier, token);
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expirationDate = verifier.verify(token).getExpiresAt();
        return expirationDate.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {

        JWTVerifier jwtVerifier = getJwtVerifier();
        return jwtVerifier
                .verify(token)
                .getClaim(AppJwt.Properties.getAuthorities())
                .asArray(String.class);


    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        Set<String> authorities = userPrincipal
                .getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toSet());
        return (authorities.toArray(new String[0]));
    }

    private JWTVerifier getJwtVerifier() {
        JWTVerifier verifier;
        try {
            verifier = JWT
                    .require(getJwtAlgorithm())
                    .withIssuer(AppJwt.Properties.getIssuer()).build();
        } catch (JWTVerificationException exception) {

            throw new JWTVerificationException(AppJwt.Message.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }


    public String getSubject(String token) {
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getSubject();
    }


    public Algorithm getJwtAlgorithm() {
        return Algorithm.HMAC512(secret);
    }

}
