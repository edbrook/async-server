package uk.co.edbrook.async.domain;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class CreateSubscriptionEvent extends ApplicationEvent {

    private UUID requestId;

    public CreateSubscriptionEvent(UUID requestId, Subscription subscription) {
        super(subscription);
        this.requestId = requestId;
    }

    public Subscription getSubscription() {
        return (Subscription) this.getSource();
    }

    public UUID getRequestId() {
        return requestId;
    }

    public static CreateSubscriptionEvent of(UUID requestId, Subscription subscription) {
        return new CreateSubscriptionEvent(requestId, subscription);
    }
}
