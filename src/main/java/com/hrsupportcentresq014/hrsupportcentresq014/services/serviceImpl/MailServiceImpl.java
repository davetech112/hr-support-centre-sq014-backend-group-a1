package com.hrsupportcentresq014.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.hrsupportcentresq014.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void sendMailTest(String senderEmail,String messageSubject, String messageBody) throws MessagingException {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper messageController = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            messageController.setFrom(email);
            messageController.setTo(senderEmail);
            messageController.setSubject(messageSubject);
            messageController.setText(messageBody);

            javaMailSender.send(message);
    }

    @Override
    public void sendAccountActivation(String receiverEmail, String activationMessage) throws MessagingException {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageController = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            messageController.setTo(receiverEmail);
            messageController.setSubject("Welcome to H.R.M.S ");
            messageController.setText(activationMessage);
            messageController.setFrom(email);

            javaMailSender.send(message);

    }

    @Override
    public void passwordReset(String resetUrl, String receiverEmail) throws MessagingException {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageController = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            messageController.setTo(receiverEmail);
            messageController.setSubject("Password Reset");
            messageController.setText(resetUrl);
            messageController.setFrom(email);

            javaMailSender.send(message);

    }
}
