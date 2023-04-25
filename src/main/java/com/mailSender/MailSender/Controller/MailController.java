//package com.mailSender.MailSender.Controller;
//
//import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;
//import com.mailSender.MailSender.DTO.Response;
//import com.mailSender.MailSender.scheduling.ApproveRecipeJobFactory;
//import jakarta.validation.Valid;
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/mail")
//public class MailController {
//
//
//    @Autowired
//    private ApproveRecipeJobFactory approveRecipeJobFactory;
//
//    @PostMapping("/sendMail")
//    public ResponseEntity<Response> sendMail(@Valid @RequestBody SetApproveRecipeMessageDto setApproveRecipeMessageDto) throws SchedulerException {
//        approveRecipeJobFactory.scheduleEmailSending(setApproveRecipeMessageDto);
//        return ResponseEntity.ok(new Response(""));
//    }
//
//}
