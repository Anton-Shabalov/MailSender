package com.mailSender.MailSender.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailSender.MailSender.DTO.DeleteFavoriteRecipeMessageDto;
import com.mailSender.MailSender.DTO.SetFavoriteRecipeMessageDto;
import com.mailSender.MailSender.scheduling.ApproveRecipeJobFactory;
import com.mailSender.MailSender.scheduling.FavoriteRecipeNotifyJobFactory;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.text.ParseException;

@Configuration
public class FavoriteRecipeServiceActivator {
    @Autowired
    MessageChannel mqttInputChannel;

    @Autowired FavoriteRecipeNotifyJobFactory favoriteRecipeNotifyJobFactory;
    @Bean
    public MessageProducer inboundSetFavoriteRecipe() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "setFavoriteRecipe",
                        "setFavoriteRecipe");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(0);
        adapter.setOutputChannel(mqttInputChannel);
        return adapter;
    }

    @Bean
    public MessageProducer inboundDeleteFavoriteRecipe() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "deleteFavoriteRecipe",
                        "deleteFavoriteRecipe");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(0);
        adapter.setOutputChannel(mqttInputChannel);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler deleteFavoriteRecipeHandler() {
                return message -> {
                    System.out.println("deleteFavoriteRecipeHandler");
            String payload= (String) message.getPayload();
                    System.out.println(payload);
            ObjectMapper mapper = new ObjectMapper();
            DeleteFavoriteRecipeMessageDto deleteFavoriteRecipeMessageDto;
            try {
                deleteFavoriteRecipeMessageDto = mapper.readValue(payload, DeleteFavoriteRecipeMessageDto.class);
                favoriteRecipeNotifyJobFactory.deleteScheduledFavoriteRecipeNotifying(deleteFavoriteRecipeMessageDto);
            } catch (JsonProcessingException | SchedulerException e) {
                e.printStackTrace();
                System.out.println("silly json -> " + payload);
            }
        };

    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler setFavoriteRecipeHandler() {
        return message -> {

            System.out.println("setFavoriteRecipeHandler");
            String payload= (String) message.getPayload();
            System.out.println(payload);
            ObjectMapper mapper = new ObjectMapper();
            SetFavoriteRecipeMessageDto setFavoriteRecipeMessageDto;
            try {
                setFavoriteRecipeMessageDto = mapper.readValue(payload, SetFavoriteRecipeMessageDto.class);
                System.out.println(payload);
                System.out.println(setFavoriteRecipeMessageDto.getText()+ setFavoriteRecipeMessageDto.getEmail()+ setFavoriteRecipeMessageDto.getTitle()+" -> "+message.getHeaders());
                favoriteRecipeNotifyJobFactory.scheduleFavoriteRecipeNotifying(setFavoriteRecipeMessageDto);
            } catch (JsonProcessingException | SchedulerException | ParseException e) {
                e.printStackTrace();
                System.out.println("silly json -> " + payload);
            }
        };
    }
//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannelSetFavoriteRecipe")
//    public MessageHandler setFavoriteRecipeHandler() {
//        return message -> {
//            String payload= (String) message.getPayload();
//            ObjectMapper mapper = new ObjectMapper();
//            SetFavoriteRecipeMessageDto setFavoriteRecipeMessageDto;
//            try {
//                setFavoriteRecipeMessageDto = mapper.readValue(payload, SetFavoriteRecipeMessageDto.class);
//                System.out.println(payload);
//                System.out.println(setFavoriteRecipeMessageDto.getText()+ setFavoriteRecipeMessageDto.getEmail()+ setFavoriteRecipeMessageDto.getTitle()+" -> "+message.getHeaders());
//                favoriteRecipeNotifyJobFactory.scheduleFavoriteRecipeNotifying(setFavoriteRecipeMessageDto);
//            } catch (JsonProcessingException | SchedulerException | ParseException e) {
//                System.out.println("silly json -> " + payload);
//            }
//        };
//    }

//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannelDeleteFavoriteRecipe")
//    public MessageHandler deleteFavoriteRecipeHandler() {
//        return message -> {
//            String payload= (String) message.getPayload();
//            ObjectMapper mapper = new ObjectMapper();
//            DeleteFavoriteRecipeMessageDto deleteFavoriteRecipeMessageDto;
//            try {
//                deleteFavoriteRecipeMessageDto = mapper.readValue(payload, DeleteFavoriteRecipeMessageDto.class);
//                favoriteRecipeNotifyJobFactory.deleteScheduledFavoriteRecipeNotifying(deleteFavoriteRecipeMessageDto);
//            } catch (JsonProcessingException | SchedulerException e) {
////                e.printStackTrace();
//                System.out.println("silly json -> " + payload);
//            }
//        };
//    }

}
