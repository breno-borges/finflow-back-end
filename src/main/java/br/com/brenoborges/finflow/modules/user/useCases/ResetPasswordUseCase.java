package br.com.brenoborges.finflow.modules.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.brenoborges.finflow.exceptions.InvalidTokenException;
import br.com.brenoborges.finflow.exceptions.UserNotFoundException;
import br.com.brenoborges.finflow.modules.user.dtos.ResetPasswordDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;
import br.com.brenoborges.finflow.providers.JWTUserProvider;
import br.com.brenoborges.finflow.utils.email.EmailService;

@Service
public class ResetPasswordUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUserProvider jwtUserProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${url.frontend}")
    private String urlFrontend;

    private final EmailService emailService;

    public ResetPasswordUseCase(EmailService emailService) {
        this.emailService = emailService;
    }

    public void resetPasswordEmail(String email) {
        UserEntity user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuário não encontrado no sistema!");
                });

        if (!user.isActive()) {
            throw new UsernameNotFoundException("Usuário inativo!");
        }

        String resetLink = urlFrontend + "/auth/passwordRecover/changePassword?token="
                + user.getResetPasswordToken()
                + "&email=" + email;

        emailService.sendEmailResetPassword(email, resetLink);
    }

    public void resetPassword(String token, String email, ResetPasswordDTO resetPasswordDTO) {

        UserEntity user = this.userRepository
                .findByEmail(email).orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        DecodedJWT tokenDecoded = this.jwtUserProvider.validationToken(token);

        if (tokenDecoded == null) {
            user.setResetPasswordToken(null);
            this.userRepository.save(user);
            throw new InvalidTokenException();
        }

        if (user.getResetPasswordToken() == null) {
            throw new InvalidTokenException();
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.newPassword()));
        user.setResetPasswordToken(null);
        this.userRepository.save(user);
    }

}
