package br.com.brenoborges.finflow.providers;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTTokenDecodedProvider {

    public DecodedJWT tokenDecoded(String token, String secretKey) {
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.require(algorithm)
                .build()
                .verify(token);
    }
}
