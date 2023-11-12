package com.tallerwebi.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

    private JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("eparking.unlam@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendMimeMessage(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("eparking.unlam@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);  // true indicates HTML format

            // Enviar el mensaje
            emailSender.send(mimeMessage);

            System.out.println("Correo enviado con éxito.");
    }

}