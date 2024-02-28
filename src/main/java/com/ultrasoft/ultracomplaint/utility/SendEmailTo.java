package com.ultrasoft.ultracomplaint.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class SendEmailTo {
    @Value("${report.internet.address}")
    private String internetAddress;

    @Autowired
    private JavaMailSender emailSender;

    public void sendMessage(
            String to,
            String subject, String text) throws Exception {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("UltraSoft<support@ultrasoft.com>");
            helper.setTo(InternetAddress.parse(to));
            helper.setSubject(subject);
            helper.setText(text);
            helper.setSentDate(new Date());
            helper.setText(text, true);
            emailSender.send(message);
            System.out.println("Mail sent");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
