package org.mehdi.chatsocket.service;

import io.jsonwebtoken.Claims;
import org.mehdi.chatsocket.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String generateToken(User user);
    SecretKey getSignInKey();

    Claims extractAllClaims(String token);
    <T> T extractClaim(String token, Function<Claims,T> resolver);
    String extractUsername(String token);
    boolean isValid(String token, User user);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);
}
