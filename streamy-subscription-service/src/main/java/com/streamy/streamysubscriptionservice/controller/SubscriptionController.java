package com.streamy.streamysubscriptionservice.controller;

import com.streamy.streamysubscriptionservice.model.UserSubscription;
import com.streamy.streamysubscriptionservice.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequestMapping("/subscription")
@RestController
public class SubscriptionController {

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/user")
    public ResponseEntity<UserSubscription> getUser(@RequestParam(value = "userid")String id) {
        if (id != null) {
            return ResponseEntity.ok().body(subscriptionService.getSubscriptionByID(UUID.fromString(id)));
        }
        else
            return ResponseEntity.badRequest().body(null);
    }


}
