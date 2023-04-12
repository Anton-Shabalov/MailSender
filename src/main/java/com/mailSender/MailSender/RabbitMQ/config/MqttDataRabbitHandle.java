package com.mailSender.MailSender.RabbitMQ.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mailSender.MailSender.DTO.Message;
import com.mailSender.MailSender.scheduling.EmailJobFactory;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.quartz.SchedulerException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.support.GenericMessage;

import java.io.UnsupportedEncodingException;


@Slf4j
@Component
@RabbitListener(queues = "topic.mqtt.EmailSend")
public class MqttDataRabbitHandle {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EmailJobFactory emailJobFactory;
    @RabbitHandler
    public void process(byte[] ms) throws UnsupportedEncodingException, JsonProcessingException, SchedulerException {
        String jsonString = new String(ms, "UTF-8");
        Message messageMail=objectMapper.readValue(jsonString, Message.class);
        emailJobFactory.ScheduleEmailSending(messageMail);
    }
}
