package br.com.brenoborges.finflow.modules.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.brenoborges.finflow.exceptions.InvalidTokenException;
import br.com.brenoborges.finflow.exceptions.UserNotFoundException;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;
import br.com.brenoborges.finflow.providers.JWTUserProvider;
import jakarta.mail.internet.MimeMessage;

@Service
public class ResetPasswordUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUserProvider jwtUserProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${url.backend}")
    private String urlBackEnd;

    private final JavaMailSender mailSender;

    public ResetPasswordUseCase(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void emailMessage(String email, String resetLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("noreply@email.com");
            helper.setTo(email);
            helper.setSubject("Redefinição de senha");

            String htmlContent = "<p>Clique " + "<a href=\"" + resetLink + "\">aqui</a>"
                    + " para redefinir sua senha."
                    + "<p>Você tem até 2 minutos para redefinir a senha.</p>"
                    + "<p>Caso expire o prazo, solicite o reset de senha novamente.</p>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao enviar o e-mail", e);
        }
    }

    public void sendEmail(String email) {
        UserEntity user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuário não encontrado no sistema!");
                });

        if (!user.isActive()) {
            throw new UsernameNotFoundException("Usuário inativo!");
        }

        String resetLink = urlBackEnd + "/user/resetPassword?token="
                + user.getResetPasswordToken()
                + "&email=" + email;

        emailMessage(email, resetLink);
    }

    public void resetPassword(String token, String email, String password) {

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

        user.setPassword(passwordEncoder.encode(password));
        user.setResetPasswordToken(null);
        this.userRepository.save(user);
    }

}
