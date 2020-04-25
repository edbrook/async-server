package uk.co.edbrook.async.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SubscriptionResponse {
    UUID requestId;
    String subscriptionId;
}
