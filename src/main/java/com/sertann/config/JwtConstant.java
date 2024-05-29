package com.sertann.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Arrays;

public class JwtConstant {
    public static String JWT_HEADER = "Authorization";
    public static String SECRET_KEY = Arrays.toString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());



}
