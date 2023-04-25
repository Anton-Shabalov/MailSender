package com.mailSender.MailSender.Configurations;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import jakarta.jms.JMSException;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
//@EnableJms
public class MqttListenerConfig {
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://localhost:1883"});
        options.setUserName("guest");
        options.setPassword("guest".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }
////
//    @Bean
//    @ServiceActivator(inputChannel = "mqttOutboundChannel")
//    public MessageHandler mqttOutbound(MqttPahoClientFactory mqttClientFactory) {
//        MqttPahoMessageHandler messageHandler =
//                new MqttPahoMessageHandler(MqttAsyncClient.generateClientId(), mqttClientFactory);
//        messageHandler.setAsync(false);
//        messageHandler.setDefaultTopic("favoriteRecipe");
//
//        return messageHandler;
//    }
//
//    @Bean
//    public MessageChannel mqttOutboundChannel() {
//        return new DirectChannel();
//    }
//    @Bean
//    public Queue createQueue() {
//        return new Queue("favoriteRecipe");
//    }
//
//    @Bean
//    public Binding createBindingBetweenQueueAndMqttTopic() {
//        return new Binding("favoriteRecipe", Binding.DestinationType.QUEUE, "amq.topic", "favoriteRecipe", null);
//    }
    @Bean
    public javax.jms.ConnectionFactory connectionFactory() throws JMSException {
        RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(1883);
        connectionFactory.setUsername("guest");
        connectionFactory.setVirtualHost("/");
        return   connectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }

    //TODO
//    @Bean
//    public Queue createQueueEmailBox() {
//        return new Queue("mailbox");
//    }
//
//    @Bean
//    public Binding createBindingBetweenQueueAndMqttTopicMailBox() {
//        return new Binding("mailbox", Binding.DestinationType.EXCHANGE, "jms.durable.queues", "mailbox", null);
//    }
//    @Bean
//    public JmsTemplate jmsTemplate() throws JMSException {
//        JmsTemplate template = new JmsTemplate();
//        template.setConnectionFactory(connectionFactory());
//        template.setDefaultDestinationName("mailbox");
//        return template;
//    }
//
}
