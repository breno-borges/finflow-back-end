package br.com.brenoborges.finflow.modules.user.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.brenoborges.finflow.modules.user.dtos.LoginRequestDTO;
import br.com.brenoborges.finflow.modules.user.dtos.AccessTokenDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;

@Service
public class AuthTokenUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Instant expiresIn(long minutes) {
        return Instant.now().plus(Duration.ofMinutes(minutes));
    }

    private String token(UserEntity user, long minutes) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create().withIssuer("finflow")
                .withSubject(user.getIdUser().toString())
                .withExpiresAt(expiresIn(minutes))
                .sign(algorithm);
    }

    public AccessTokenDTO loginToken(LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        UserEntity user = this.userRepository.findByEmail(loginRequestDTO.email())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuário ou senha incorreta!");
                });

        boolean passwordMatches = this.passwordEncoder.matches(loginRequestDTO.password(), user.getPassword());

        if (!passwordMatches) {
            throw new UsernameNotFoundException("Usuário ou senha incorreta!");
        }

        AccessTokenDTO loginToken = AccessTokenDTO.builder()
                .accessToken(token(user, 30))
                .expiresIn(expiresIn(30).toEpochMilli())
                .build();

        return loginToken;
    }
}
