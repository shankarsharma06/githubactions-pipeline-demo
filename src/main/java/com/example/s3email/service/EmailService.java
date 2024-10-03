package com.example.s3email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;  // Updated import
import jakarta.mail.internet.MimeMessage;  // Updated import

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendFileWithEmail(String to, String subject, String body, File file) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        FileSystemResource fileResource = new FileSystemResource(file);
        helper.addAttachment(file.getName(), fileResource);

        javaMailSender.send(message);
    }
}
