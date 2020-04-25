package uk.co.edbrook.async.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Subscription {
    private UUID id;
    private String name;
    private String email;
}
