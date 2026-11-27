package fr.diginamic.hello_tp1.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Génère et lit les tokens JWT signés avec une clé HMAC.
 */
@Component 
public class JwtUtil {
    private static final String SECRET= "secret-secret-secret-secret-secret-secret";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * Génère un JWT valable 15 minutes.
     *
     * @param username utilisateur à mettre dans le token
     * @return le token signé
     */
    public String generateToken(String username) {
        return Jwts.builder().subject(username).issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
            .signWith(key)
            .compact();
    }

    /**
     * Vérifie la signature et la date d'expiration du token, puis renvoie son username.
     *
     * @param token JWT reçu du client
     * @return le username contenu dans le token
     * @throws io.jsonwebtoken.JwtException si le token est invalide ou expiré
     */
    public String extractUsername(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
        .parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
}



