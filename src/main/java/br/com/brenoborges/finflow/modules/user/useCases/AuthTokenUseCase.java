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
import br.com.brenoborges.finflow.modules.user.dtos.TokenDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;
import br.com.brenoborges.finflow.providers.JWTExpirationTime;

@Service
public class AuthTokenUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTExpirationTime jwtExpirationTime;

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

    public TokenDTO loginToken(LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        UserEntity user = this.userRepository.findByEmail(loginRequestDTO.email())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuário ou senha incorreta!");
                });

        if (!user.isActive()) {
            throw new UsernameNotFoundException("Usuário inativo!");
        }

        boolean passwordMatches = this.passwordEncoder.matches(loginRequestDTO.password(), user.getPassword());

        if (!passwordMatches) {
            throw new UsernameNotFoundException("Usuário ou senha incorreta!");
        }

        return TokenDTO.builder()
                .token(token(user, this.jwtExpirationTime.getExpirationTimeLoginInMinutes()))
                .expiresIn(expiresIn(this.jwtExpirationTime.getExpirationTimeLoginInMinutes()).toEpochMilli())
                .build();
    }

    public void forgotPassword(String email)
            throws AuthenticationException {
        UserEntity user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuário não encontrado no sistema!");
                });

        if (!user.isActive()) {
            throw new UsernameNotFoundException("Usuário inativo!");
        }

        TokenDTO token = TokenDTO.builder()
                .token(token(user, this.jwtExpirationTime.getExpirationTimeResetPasswordInMinutes()))
                .expiresIn(expiresIn(this.jwtExpirationTime.getExpirationTimeResetPasswordInMinutes()).toEpochMilli())
                .build();

        user.setResetPasswordToken(token.getToken());

        this.userRepository.save(user);
    }
}
