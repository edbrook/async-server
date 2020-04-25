package uk.co.edbrook.async.bus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
public class ResponseUriResolver implements UriResolver {

    @Value("${remote.url:http://localhost:8080/api/result}")
    String url;

    @Override
    public URI resolveForId(UUID id) {
        return URI.create(url);
    }
}
