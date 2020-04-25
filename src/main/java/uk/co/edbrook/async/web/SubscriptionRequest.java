package uk.co.edbrook.async.web;

import lombok.Value;

@Value
public class SubscriptionRequest {
    String requestId;
    String name;
    String email;
}
