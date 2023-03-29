package com.mailSender.MailSender.Controller;

import com.mailSender.MailSender.DTO.Message;
import com.mailSender.MailSender.DTO.Response;
import com.mailSender.MailSender.service.interfaces.SendEmail;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
public class MailController {
    @Autowired
    SendEmail emailpush;
    @PostMapping("/sendMail")
    public ResponseEntity<Response> sendMail(@Valid @RequestBody Message message) {
        emailpush.sendMail(message);
        return ResponseEntity.ok(new Response(""));
    }

}
