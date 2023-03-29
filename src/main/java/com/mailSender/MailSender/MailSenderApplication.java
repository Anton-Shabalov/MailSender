package com.mailSender.MailSender;

import com.mailSender.MailSender.DTO.Message;
import com.mailSender.MailSender.service.DefaultMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailSenderApplication.class, args);

	}

}
