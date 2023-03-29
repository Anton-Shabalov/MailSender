package com.mailSender.MailSender.service.interfaces;

import com.mailSender.MailSender.DTO.Message;

public interface SendEmail {
    void sendMail(final Message message);
}
