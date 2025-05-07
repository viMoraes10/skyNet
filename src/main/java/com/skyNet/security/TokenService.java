package com.skyNet.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.skyNet.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Service class for handling token related operations.
 */
@Service
public class TokenService {

    /**
     * Secret key for token generation and validation.
     */
    @Value("${api.security.toker.secret}")
    private String secret;

    /**
     * Generates a JWT token for a given user.
     *
     * @param user The user for whom the token is to be generated.
     * @return The generated JWT token.
     * @throws RuntimeException if there is an error while generating the token.
     */
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token: ",exception);
        }
    }

    /**
     * Validates a given JWT token.
     *
     * @param token The JWT token to be validated.
     * @return The subject of the validated token.
     * @throws RuntimeException if there is an error while validating the token.
     */
    public  String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTCreationException exception) {
            throw new RuntimeException("Error while validating token: ",exception);
        }
    }

    /**
     * Generates an expiration date for the JWT token.
     *
     * @return The generated expiration date.
     */
    private Instant genExpirationDate(){
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(2);

        // Converts LocalDateTime to ZonedDateTime using the system's default timezone
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();

        return instant;
    }
}