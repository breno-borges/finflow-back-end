package br.com.brenoborges.finflow.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    // Metodo que converto o objeto em um JSON.
    public static String objectToJSON(Object object) {
        final ObjectMapper objectMapper = new ObjectMapper(); // Dependencia do Java que posso converter objetos.
                                                              // Mapear.
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID idUser, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        return JWT.create().withIssuer("finflow")
                .withSubject(idUser.toString())
                .withExpiresAt(expiresIn)
                .sign(algorithm);
    }
}
