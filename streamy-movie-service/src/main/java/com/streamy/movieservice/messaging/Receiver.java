package com.streamy.movieservice.messaging;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver implements MessageListener {

    @Override
    public void onMessage(Message message) {
        byte[] body = message.getBody();
        System.out.println(new String(body));

    }

}
