package com.streamy.streamysubscriptionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("subscriptions")
public class UserSubscription {
    @Id
    private UUID id;
    @Indexed(unique = true)
    private String ibanNumber;
    private boolean isActive;

}
