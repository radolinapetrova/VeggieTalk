package com.veggietalk.account_service.service;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

public class JwtDecoder {

    public static JWTClaimsSet decodeJwtToken(String jwtToken) {
        try {
            JWT jwt = JWTParser.parse(jwtToken);
            return jwt.getJWTClaimsSet();

        } catch (Exception e) {
            System.out.println("Error parsing JWT token: " + e.getMessage());
            return null;
        }
    }
}
