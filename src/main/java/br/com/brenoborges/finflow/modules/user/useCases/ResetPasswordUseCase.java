package br.com.brenoborges.finflow.modules.user.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

            String htmlContent = "<p>Clique no link para redefinir sua senha: "
                    + "<a href=\"" + resetLink + "\">Clique Aqui</a></p>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Erro:");
            System.out.println(e.getMessage());
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

        String resetLink = "http://localhost:8080/user/resetPassword?token=" + user.getResetPasswordToken();

        emailMessage(email, resetLink);
    }

    public void resetPassword(String token, String password) {

        String idUser = this.jwtUserProvider.validationToken(token).getSubject();

        UserEntity user = this.userRepository.findById(UUID.fromString(idUser))
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        user.setPassword(passwordEncoder.encode(password));
        user.setResetPasswordToken(null);
        this.userRepository.save(user);
    }

}
