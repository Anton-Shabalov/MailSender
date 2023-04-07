package com.mailSender.MailSender.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailSender.MailSender.DTO.Message;
import com.mailSender.MailSender.service.interfaces.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultMailSender implements SendEmail {
    @Autowired
    private JavaMailSender senderMails;

    @Override
    public void sendMail(Message message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(message.getEmailTo());
        simpleMailMessage.setSubject(message.getSubject());
        simpleMailMessage.setText(message.getText());
        senderMails.send(simpleMailMessage);
    }
}
