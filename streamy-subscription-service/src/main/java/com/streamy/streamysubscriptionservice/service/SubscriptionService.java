package com.streamy.streamysubscriptionservice.service;

import com.streamy.streamysubscriptionservice.StreamySubscriptionServiceApplication;
import com.streamy.streamysubscriptionservice.messaging.MessageObject;
import com.streamy.streamysubscriptionservice.model.UserSubscription;
import com.streamy.streamysubscriptionservice.repository.ISubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubscriptionService {

    @Autowired
    ISubscriptionRepository subscriptionRepository;

    @RabbitListener(queues = StreamySubscriptionServiceApplication.queueName)
    public void listenForMessage(MessageObject messageObject){
        System.out.println("Expected a messageobject, got a " + messageObject.toString());
        if (messageObject.getMessageAction() == null || messageObject.getUserID() == null)
            return;
        UserSubscription subscription = new UserSubscription(messageObject.getUserID(), messageObject.getIban(), true);
        switch (messageObject.getMessageAction()){
                case DELETE:
                    deleteSubscription(messageObject.getUserID());
                    break;
                case ADD:
                    addNewSubscription(subscription);
                    break;
                case UPDATE:
                    saveSubscription(subscription);
                default:
                    return;
         }
    }

    public void saveSubscription(UserSubscription subscription) {
        if (getSubscriptionByID(subscription.getId()) != null){
            subscriptionRepository.save(subscription);
            log.info("Subscription {} is updated", subscription.getId());
        }
    }

    public UserSubscription getSubscriptionByID(UUID id) {
        Optional<UserSubscription> userSubscription = subscriptionRepository.findById(id);
        return userSubscription.orElse(null);
    }

    public boolean addNewSubscription(UserSubscription subscription){
        try {
            subscriptionRepository.save(subscription);
            log.info("Iban number saved {}", subscription.getIbanNumber());
            return true;
        }
        catch (Exception e){
            log.error(e.toString());
            log.error("Could not save subscription {}", subscription.getId());
            return false;
        }
    }

    public boolean deleteSubscription(UUID userID){
        UserSubscription userSubscription = getSubscriptionByID(userID);
        if (userSubscription != null){
            subscriptionRepository.delete(userSubscription);
            log.info("Subscrition deleted from user {}", userSubscription.getId());
            return true;
        }
        else{
            log.info("Could not delete subscription from user {}", userID.toString());
            return false;
        }
    }

}
