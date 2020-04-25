package uk.co.edbrook.async.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements SubscriptionTrigger {

    private final ApplicationEventPublisher publisher;

    @Override
    public void subscribe(UUID requestId, Subscription subscription) {
        var event = CreateSubscriptionEvent.of(requestId, subscription);
        publisher.publishEvent(event);
    }
}
