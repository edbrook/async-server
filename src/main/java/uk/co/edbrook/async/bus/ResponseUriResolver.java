package uk.co.edbrook.async.bus;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
public class ResponseUriResolver implements UriResolver {

    @Override
    public URI resolveForId(UUID id) {
        return URI.create("http://localhost:8080/api/result");
    }
}
