package com.mailSender.MailSender.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;
import com.mailSender.MailSender.DTO.SetFavoriteRecipeMessageDto;
import com.mailSender.MailSender.scheduling.ApproveRecipeJobFactory;
import com.rabbitmq.client.MessageProperties;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.jms.Message;
import java.text.ParseException;

@Configuration
public class ApproveRecipeServiceActivator {
    @Bean
    public MessageChannel mqttInputChannelSetApproveRecipe() {
        return new DirectChannel();
    }

    @Autowired
    private ApproveRecipeJobFactory approveRecipeJobFactory;
    @Bean
    public MessageProducer inboundSetApproveRecipe() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "guest_receiver",
                        "setApproveRecipe");

        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannelSetApproveRecipe());
        return adapter;
    }

    @Autowired
    MqttPahoClientFactory mqttClientFactory;

    @ServiceActivator(inputChannel = "mqttInputChannelSetApproveRecipe")

    public MessageHandler setApproveRecipeHandler() {
        return message -> {
            String payload= (String) message.getPayload();
            ObjectMapper mapper = new ObjectMapper();
            SetApproveRecipeMessageDto setApproveRecipeMessageDto;
            try {
                setApproveRecipeMessageDto = mapper.readValue(payload, SetApproveRecipeMessageDto.class);
                approveRecipeJobFactory.scheduleEmailSending(setApproveRecipeMessageDto);
            } catch (JsonProcessingException | SchedulerException e) {
                e.printStackTrace();
                System.out.println("silly json -> " + payload);
            }

        };
    }


}
