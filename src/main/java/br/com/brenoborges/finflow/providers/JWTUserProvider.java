package br.com.brenoborges.finflow.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTUserProvider {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private JWTTokenDecodedProvider jwtTokenDecodedProvider;

    public DecodedJWT validationToken(String token) {
        try {
            return jwtTokenDecodedProvider.tokenDecoded(token, secretKey);
        } catch (Exception e) {
            return null;
        }
    }
}
