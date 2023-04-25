package com.mailSender.MailSender.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailSender.MailSender.DTO.DeleteFavoriteRecipeMessageDto;
import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;
import com.mailSender.MailSender.DTO.SetFavoriteRecipeMessageDto;
import com.mailSender.MailSender.scheduling.ApproveRecipeJobFactory;
import com.mailSender.MailSender.scheduling.FavoriteRecipeNotifyJobFactory;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;

import java.text.ParseException;
import java.util.Objects;

@Configuration
public class MessageProcessorConfig {
    @Bean
    public MessageProducer inbound(@Autowired MessageChannel mqttInputChannel) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "setFavoriteRecipe",
                        "setFavoriteRecipe", "deleteFavoriteRecipe", "setApproveRecipe");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(0);
        adapter.setOutputChannel(mqttInputChannel);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler deleteFavoriteRecipeHandler(
            @Autowired FavoriteRecipeNotifyJobFactory favoriteRecipeNotifyJobFactory,
            @Autowired ApproveRecipeJobFactory approveRecipeJobFactory
    ) {
        return message -> {
            MessageHeaders messageHeaders = message.getHeaders();
            System.out.println(message);
            if (messageHeaders.containsKey("mqtt_receivedTopic")) {
                ObjectMapper mapper = new ObjectMapper();
                String topic = Objects.requireNonNull(messageHeaders.get("mqtt_receivedTopic")).toString();
                try {
                    switch (topic) {
                        case "deleteFavoriteRecipe" -> {
                            DeleteFavoriteRecipeMessageDto deleteFavoriteRecipeMessageDto =
                                    mapper.readValue(message.getPayload().toString(),
                                            DeleteFavoriteRecipeMessageDto.class);
                            if (deleteFavoriteRecipeMessageDto.getEmail()!=null){
                            favoriteRecipeNotifyJobFactory.deleteScheduledFavoriteRecipeNotifying(deleteFavoriteRecipeMessageDto);}
                            else{
                                System.out.println("wrong email +");
                            }
                        }
                        case "setFavoriteRecipe" -> {
                            SetFavoriteRecipeMessageDto setFavoriteRecipeMessageDto
                                    = mapper.readValue(message.getPayload().toString(), SetFavoriteRecipeMessageDto.class);
                            if (setFavoriteRecipeMessageDto.getEmail()!=null){
                                favoriteRecipeNotifyJobFactory.scheduleFavoriteRecipeNotifying(setFavoriteRecipeMessageDto);}
                            else{
                                System.out.println("wrong email +");
                            }

                        }
                        case "setApproveRecipe" -> {
                            SetApproveRecipeMessageDto setApproveRecipeMessageDto
                                    = mapper.readValue(message.getPayload().toString(), SetApproveRecipeMessageDto.class);
                            if (setApproveRecipeMessageDto.getEmailTo()!=null){
                                approveRecipeJobFactory.scheduleEmailSending(setApproveRecipeMessageDto);}
                            else{
                                System.out.println("wrong email +");
                            }

                        }
                        default -> {
                            System.out.printf("Wrong topic: %s%n", topic);
                        }
                    }
                } catch (JsonProcessingException | SchedulerException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }

        };

    }


}
