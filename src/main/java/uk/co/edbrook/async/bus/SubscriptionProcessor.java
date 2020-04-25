package uk.co.edbrook.async.bus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.co.edbrook.async.domain.CreateSubscriptionEvent;
import uk.co.edbrook.async.domain.SubscriptionResponse;

import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
class SubscriptionProcessor implements ApplicationListener<CreateSubscriptionEvent> {

    private static final String SECRET = "SECRET";

    private final RestTemplate asyncResponseTemplate;
    private final UriResolver uriResolver;

    @Override
    public void onApplicationEvent(CreateSubscriptionEvent event) {
        var requestId = event.getRequestId();

        var subscription = event.getSubscription();
        var subscriptionId = generateId(subscription.getName(), subscription.getEmail());

        var asyncResponse = SubscriptionResponse.builder()
                .requestId(requestId)
                .subscriptionId(subscriptionId)
                .build();

        var uri = uriResolver.resolveForId(requestId);

        log.info("Sending response: {} to {}", asyncResponse, uri);
        asyncResponseTemplate.postForEntity(uri, asyncResponse, Void.class);
    }

    private String generateId(String name, String email) {
        String data = String.format("%s::%s::%s::END", SECRET, name, email);
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
}
