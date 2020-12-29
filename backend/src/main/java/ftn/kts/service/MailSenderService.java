package ftn.kts.service;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class MailSenderService {

    private JavaMailSender emailSender;

    @Value("${registration.link}")
    private String link;

    @Autowired
    public MailSenderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    private Future<SimpleMailMessage> sendEmail(String recipient, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(content);
        emailSender.send(message);
        return new AsyncResult<>(message);
    }

    @Async
    public Future<SimpleMailMessage> confirmRegistration(String username, String key) {
        String content = "Welcome to CultYourself!\nYou’re just one click away from getting started with CultYourself. " +
                "\nPlease follow this link to activate your account:\n"
                + link + key +
                "\nOnce your account is activated, you can start using all of CultYourself features" +
                "\n\nYou’re receiving this email because you recently created a new CultYourself account or added a new email address. " +
                "If this wasn’t you, please ignore this email.";
        return sendEmail(username, "Activate Your CultYourself Account Now", content);

    }

}
