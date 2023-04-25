package com.mailSender.MailSender.service.interfaces;

import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;

public interface SendEmail {
    void sendMail(final SetApproveRecipeMessageDto setApproveRecipeMessageDto);
}
