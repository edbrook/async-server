package uk.co.edbrook.async.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class SubscriptionService implements SubscriptionTrigger {

    private final ApplicationEventPublisher publisher;

    @Override
    public void subscribe(UUID requestId, Subscription subscription) {
        log.info("Process subscription: {}, RequestID: {}", subscription, requestId);
        var event = CreateSubscriptionEvent.of(requestId, subscription);
        publisher.publishEvent(event);
    }
}
