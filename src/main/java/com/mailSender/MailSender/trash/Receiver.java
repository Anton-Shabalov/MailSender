package com.mailSender.MailSender.trash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

//  @JmsListener(destination = "mailbox")
//  public void receiveMessage(String string) {
//    System.out.println("Received <" + string + ">");
//  }
//  private static final String MQTT_TOPIC = "test-topic";
//
//  @JmsListener(destination = "test-queue")
//  public void receiveMessag1e(String message) {
//    MqttPahoMessageHandler mqttHandler = new MqttPahoMessageHandler("test-client", mqttClientFactory);
//    mqttHandler.setDefaultTopic(MQTT_TOPIC);
//    mqttHandler.handleMessage(new GenericMessage<>(message));
//  }

  @Autowired
  private MqttPahoClientFactory mqttClientFactory;
}