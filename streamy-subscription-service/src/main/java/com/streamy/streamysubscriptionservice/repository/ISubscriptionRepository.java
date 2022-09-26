package com.streamy.streamysubscriptionservice.repository;

import com.streamy.streamysubscriptionservice.model.UserSubscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface ISubscriptionRepository extends MongoRepository<UserSubscription, UUID> {
}
