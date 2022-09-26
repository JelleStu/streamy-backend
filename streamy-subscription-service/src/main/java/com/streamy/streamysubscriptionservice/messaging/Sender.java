package com.streamy.streamysubscriptionservice.messaging;

import com.streamy.streamysubscriptionservice.StreamySubscriptionServiceApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Sender implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(StreamySubscriptionServiceApplication.jsonMessageConverter);
    }

    @Override
    public void run(String... args) {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(StreamySubscriptionServiceApplication.topicExchangeName, "streamy.userservice", "Hello from the user service!");
    }

    public void sendMessage(MessageObject messageObject){

    }
}
