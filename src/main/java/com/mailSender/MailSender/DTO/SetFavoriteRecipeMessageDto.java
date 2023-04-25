package com.mailSender.MailSender.DTO;

import lombok.Data;

@Data

public class SetFavoriteRecipeMessageDto {
    private String text;
    private String title;
    private String email;
}
