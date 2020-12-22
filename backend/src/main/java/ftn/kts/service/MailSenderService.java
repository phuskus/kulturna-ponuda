package ftn.kts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSender;

@Component
public class MailSenderService {

    private JavaMailSender emailSender;

    @Value("${registration.link}")
    private String link;

    @Autowired
    public MailSenderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    private void sendEmail(String recipient, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(content);
        emailSender.send(message);
    }

    @Async
    public void confirmRegistration(String username, String key) {
        String content = "Welcome to CultYourself!\nYou’re just one click away from getting started with CultYourself. " +
                "\nPlease follow this link to activate your account:\n"
                + link + key +
                "\nOnce your account is activated, you can start using all of CultYourself features" +
                "\n\nYou’re receiving this email because you recently created a new CultYourself account or added a new email address. " +
                "If this wasn’t you, please ignore this email.";
        sendEmail(username, "Activate Your CultYourself Account Now", content);

    }

}
