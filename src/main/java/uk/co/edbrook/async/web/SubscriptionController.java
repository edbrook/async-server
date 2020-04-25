package uk.co.edbrook.async.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.edbrook.async.domain.SubscriptionTrigger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionTrigger subscriptionTrigger;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionRequest request) {
        var requestId = request.getRequestId();
        var subscription = request.toSubscription();

        subscriptionTrigger.subscribe(requestId, subscription);

        return ResponseEntity.accepted()
                .build();
    }
}
