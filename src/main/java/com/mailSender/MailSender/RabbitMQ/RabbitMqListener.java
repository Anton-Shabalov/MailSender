package com.mailSender.MailSender.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {


    @RabbitListener(queues = "queueForEmail")
    public void worker1(String str) {
        System.out.println(str);
    }


}
