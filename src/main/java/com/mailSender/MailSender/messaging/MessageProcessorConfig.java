package com.mailSender.MailSender.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailSender.MailSender.DTO.DeleteFavoriteRecipeMessageDto;
import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;
import com.mailSender.MailSender.DTO.SetFavoriteRecipeMessageDto;
import com.mailSender.MailSender.scheduling.ApproveRecipeJobFactory;
import com.mailSender.MailSender.scheduling.FavoriteRecipeNotifyJobFactory;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MessageProcessorConfig {
    @Bean
    public MessageProducer inbound(@Autowired MessageChannel mqttInputChannel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                "tcp://localhost:1883", "setFavoriteRecipe", "setFavoriteRecipe", "deleteFavoriteRecipe", "setApproveRecipe");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(0);
        adapter.setOutputChannel(mqttInputChannel);
        return adapter;
    }

    private final MessageValidator messageValidator = new MessageValidator();

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler deleteFavoriteRecipeHandler(@Autowired FavoriteRecipeNotifyJobFactory favoriteRecipeNotifyJobFactory, @Autowired ApproveRecipeJobFactory approveRecipeJobFactory) {
        return message -> {
            MessageHeaders messageHeaders = message.getHeaders();
            log.info("Message received:\n{}",message);
            if (messageHeaders.containsKey("mqtt_receivedTopic")) {
                ObjectMapper mapper = new ObjectMapper();
                String topic = Objects.requireNonNull(messageHeaders.get("mqtt_receivedTopic")).toString();
                String payload = message.getPayload().toString();
                messageValidator.validateViaSchema(payload, topic);
                try {
                    switch (topic) {
                        case "deleteFavoriteRecipe" -> {
                            DeleteFavoriteRecipeMessageDto deleteFavoriteRecipeMessageDto =
                                    mapper.readValue(message.getPayload().toString(), DeleteFavoriteRecipeMessageDto.class);
                            favoriteRecipeNotifyJobFactory.deleteScheduledFavoriteRecipeNotifying(deleteFavoriteRecipeMessageDto);
                        }
                        case "setFavoriteRecipe" -> {
                            SetFavoriteRecipeMessageDto setFavoriteRecipeMessageDto =
                                    mapper.readValue(message.getPayload().toString(), SetFavoriteRecipeMessageDto.class);

                            favoriteRecipeNotifyJobFactory.scheduleFavoriteRecipeNotifying(setFavoriteRecipeMessageDto);
                        }
                        case "setApproveRecipe" -> {
                            SetApproveRecipeMessageDto setApproveRecipeMessageDto =
                                    mapper.readValue(message.getPayload().toString(), SetApproveRecipeMessageDto.class);
                            approveRecipeJobFactory.scheduleEmailSending(setApproveRecipeMessageDto);
                        }
                        default ->
                            log.warn("Wrong topic: {}", topic);

                    }

                } catch (ParseException | JsonProcessingException | SchedulerException e) {
                    log.warn("Wrong topic: {}", e.getMessage());
                }
            }

        };

    }


}
