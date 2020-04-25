package uk.co.edbrook.async.web;

import lombok.Value;
import uk.co.edbrook.async.domain.Subscription;

import java.util.UUID;

@Value
public class SubscriptionRequest {
    UUID requestId;
    String name;
    String email;

    public Subscription toSubscription() {
        return Subscription.builder()
                .name(getName())
                .email(getEmail())
                .build();
    }
}
