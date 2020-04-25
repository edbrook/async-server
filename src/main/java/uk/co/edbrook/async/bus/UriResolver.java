package uk.co.edbrook.async.bus;

import java.net.URI;
import java.util.UUID;

public interface UriResolver {
    URI resolveForId(UUID id);
}
