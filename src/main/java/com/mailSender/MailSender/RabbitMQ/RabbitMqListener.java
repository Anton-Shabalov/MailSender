package com.mailSender.MailSender.RabbitMQ;

import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;
import com.mailSender.MailSender.scheduling.ApproveRecipeJobFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {
    @Autowired
    private ApproveRecipeJobFactory approveRecipeJobFactory;

//    @JmsListener(destination = "queueForEmail")
//    public void worker1(@Payload SetApproveRecipeMessageDto message) throws SchedulerException {
//        System.out.println(message);
////        emailJobFactory.scheduleEmailSending(message);
//        System.out.println(message.getEmailTo());
//        System.out.println(message.getSubject());
//        System.out.println(message.getText());
//
//    }

//    @RabbitListener(queues = "queueForEmail")
//    public void receiveMessage(SetApproveRecipeMessageDto setApproveRecipeMessageDto) {
//        System.out.println("Received <" + setApproveRecipeMessageDto + ">");
//    }



}
