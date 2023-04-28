/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

/**
 *
 * @author eqima
 */
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import io.jsonwebtoken.*;

import java.util.Date;

public class TookenManager {

    private static String apikey = "eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJFcXVpbWEiLCJuYW1lIjoiT3N0aWUiLCJpYXQiOjE2NDc5OTk5MDB9.9VOy-ejlbQEbRWaaodK9gCu2pt_pFa1wR6wgatAhaeZQjRTBhMhKUM0BssvhdUhJ";

    @SuppressWarnings("deprecation")
    public String createJWT(String id, String issuer, String subject, long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS384;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apikey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    @SuppressWarnings("deprecation")
    public Claims parseJWT(String jwt) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(apikey))
                    .parseClaimsJws(jwt).getBody();
        } catch (SignatureException e) {
        }
        return claims;
    }
}
