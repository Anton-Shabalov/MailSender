package com.mailSender.MailSender.RabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailSender.MailSender.DTO.Message;
import com.mailSender.MailSender.scheduling.EmailJobFactory;
import org.quartz.SchedulerException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EmailJobFactory emailJobFactory;

    @RabbitListener(queues = "queueForEmail")
    public void mailNotificationListener(String jsonMessage) throws JsonProcessingException, SchedulerException {
        Message message=objectMapper.readValue(jsonMessage, Message.class);
        emailJobFactory.ScheduleEmailSending(message);
    }


}
