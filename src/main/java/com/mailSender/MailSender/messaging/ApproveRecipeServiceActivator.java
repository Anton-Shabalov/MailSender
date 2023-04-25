package com.mailSender.MailSender.messaging;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;
import com.mailSender.MailSender.scheduling.ApproveRecipeJobFactory;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@EnableIntegration

@Configuration
public class ApproveRecipeServiceActivator {

    @Autowired
    ApproveRecipeJobFactory approveRecipeJobFactory;
//
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public MessageHandler setApproveRecipeHandler() {
//        return message -> {
//            String payload = (String) message.getPayload();
//            ObjectMapper mapper = new ObjectMapper();
//            SetApproveRecipeMessageDto setApproveRecipeMessageDto;
//            try {
//                System.out.println(payload);
//                setApproveRecipeMessageDto = mapper.readValue(payload, SetApproveRecipeMessageDto.class);
//                approveRecipeJobFactory.scheduleEmailSending(setApproveRecipeMessageDto);
//            } catch (JsonProcessingException | SchedulerException e) {
////                e.printStackTrace();
//                System.out.println("silly json -> " + payload);
//            }
//
//        };
//    }


}
