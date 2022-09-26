package streamy.userservice.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import streamy.userservice.StreamyUserserviceApplication;

@Component
public class Sender{

    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

/*    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        MessageObject message = new MessageObject();
        message.setMessageAction(MessageAction.DELETE);
        //message.setUserID("test");
        rabbitTemplate.convertAndSend(StreamyUserserviceApplication.topicExchangeName, "streamy.subscriptionservice", message);
    }*/

    public void SendMessage(MessageObject messageObject){
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(StreamyUserserviceApplication.topicExchangeName, "streamy.subscriptionservice", messageObject);

    }
}