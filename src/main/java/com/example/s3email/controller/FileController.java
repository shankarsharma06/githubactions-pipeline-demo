package com.example.s3email.controller;

import com.example.s3email.service.EmailService;
import com.example.s3email.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class FileController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-file")
    public String sendFileFromS3(@RequestParam String fileName, @RequestParam String email) {
        // Download file from S3
		File file = s3Service.downloadFile(fileName);

		// Send the file as an email attachment
		try {
			emailService.sendFileWithEmail(email, "File from S3", "Here is your file.", file);
		} catch (jakarta.mail.MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "File sent to " + email;
    }
}
