package com.ebonilla.product_service.infrastructure.adapters.input.rest.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class JwtTestUtil {

    private final SecretKey secretKey;

    // Crear JWT valido
    public String generateJwtValid(String username, List<String> roles){

        String scope = String.join(" ", roles);

        return Jwts.builder()
                .setSubject(username)
                .claim("scope", scope)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Crear JWT expirado
    public String generatedTokenExpired(String username, List<String> roles){
        String scope = String.join(" ", roles);

        return Jwts.builder()
                .setSubject(username)
                .claim("scope", scope)
                .setIssuedAt(new Date(System.currentTimeMillis() - 7200000))
                .setExpiration(new Date(System.currentTimeMillis() - 3600000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Metodo para crear JWT con llave secreta diferente
    public String generatedTokenWithInvalidSecretKey(String username, List<String> roles){
        String scope = String.join(" ", roles);
        SecretKey wrongKey = Keys.hmacShaKeyFor("EstaEsOtraClaveSecretaParaEstosTestDeIntegracion".getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(username)
                .claim("scope", scope)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(wrongKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
