package com.hrsupportcentresq014.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.hrsupportcentresq014.services.MailService;
import com.hrsupportcentresq014.hrsupportcentresq014.services.ThymeleafService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender javaMailSender;
    private ThymeleafService thymeleafService;

    @Value("${spring.mail.username}")
    private String email;
    public MailServiceImpl(JavaMailSender javaMailSender, ThymeleafService thymeleafService) {
        this.javaMailSender = javaMailSender;
        this.thymeleafService = thymeleafService;
    }
    @Override
    public void sendMailTest(String fullName, String subject, String senderEmail, String text,String messageSubject) throws MessagingException {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper messageController = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            messageController.setFrom(email);
            messageController.setTo(senderEmail);
//            messageController.setCc(sendMailDto.getCc());
            messageController.setSubject(subject);
            Map<String, Object> variables = new HashMap<>();
            variables.put("fullName", fullName);
            variables.put("message_body", text);
            variables.put("message_subject", messageSubject);
            messageController.setText(thymeleafService.createContent("mail_sender_test.html", variables), true);

            javaMailSender.send(message);
    }

    @Override
    public void sendAccountActivation(String firstname, String lastname, String activated_email, String activated_password) throws MessagingException {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageController = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            messageController.setTo(activated_email);
            messageController.setSubject("Welcome to HRMS " + firstname);
            Map<String, Object> variables = new HashMap<>();
            variables.put("first_name", firstname);
            variables.put("last_name", lastname);
            variables.put("email", activated_email);
            variables.put("password", activated_password);
            variables.put("fullName", firstname + " " + lastname);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            variables.put("date", simpleDateFormat.format(new Date()));

            messageController.setText(thymeleafService.createContent("welcome_new_users.html", variables), true);
            messageController.setFrom(email);

            javaMailSender.send(message);

    }

    @Override
    public void passwordReset(String fullName, String resetUrl, String altEmail) throws MessagingException {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageController = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            messageController.setTo(altEmail);
            messageController.setSubject("Forgot Password");
            Map<String, Object> variables = new HashMap<>();
            variables.put("email", altEmail);
            variables.put("fullName", fullName);
            variables.put("reset_url", resetUrl);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            variables.put("date", simpleDateFormat.format(new Date()));

            messageController.setText(thymeleafService.createContent("forgot_password.html", variables), true);
            messageController.setFrom(email);

            javaMailSender.send(message);

    }
}
