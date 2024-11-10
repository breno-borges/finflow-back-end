package br.com.brenoborges.finflow.utils.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String email, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("noreply@email.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true); // A String deve ter conteúdo HTML

            mailSender.send(message);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao enviar o e-mail", e);
        }
    }
}
