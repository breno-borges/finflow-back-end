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

    private String subject;
    private String text;

    public void sendEmailResetPassword(String email, String resetLink) {

        this.subject = "Redefinição de senha";

        this.text = "<p>Clique " + "<a href=\"" + resetLink + "\">aqui</a>"
                + " para redefinir sua senha."
                + "<p>Você tem até 5 minutos para redefinir a senha.</p>"
                + "<p>Caso expire o prazo, solicite o reset de senha novamente.</p>";

        sendEmail(email, subject, text);

    }

    private void sendEmail(String email, String subject, String text) {
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
