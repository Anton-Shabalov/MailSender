package com.mailSender.MailSender.service;

import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;
import com.mailSender.MailSender.service.interfaces.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultMailSender implements SendEmail {
    @Autowired
    public JavaMailSender senderMails;

    @Override
    public void sendMail(SetApproveRecipeMessageDto setApproveRecipeMessageDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(setApproveRecipeMessageDto.getEmailTo());
        simpleMailMessage.setSubject(setApproveRecipeMessageDto.getSubject());
        simpleMailMessage.setText(setApproveRecipeMessageDto.getText());
        senderMails.send(simpleMailMessage);
    }
}
