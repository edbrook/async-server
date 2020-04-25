package uk.co.edbrook.async.domain;

import java.util.UUID;

public interface SubscriptionTrigger {
    void subscribe(UUID requestId, Subscription subscription);
}
