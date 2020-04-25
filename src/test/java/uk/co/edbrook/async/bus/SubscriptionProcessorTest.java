package uk.co.edbrook.async.bus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.co.edbrook.async.domain.CreateSubscriptionEvent;
import uk.co.edbrook.async.domain.Subscription;
import uk.co.edbrook.async.domain.SubscriptionResponse;

import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionProcessorTest {

    private static final UUID REQ_ID = UUID.fromString("12345678-1234-5678-9ABC-123456789ABC");
    private static final String NAME = "MOCK_NAME";
    private static final String EMAIL = "MOCK_EMAIL";
    private static final String SUBSCRIPTION_ID = "U0VDUkVUOjpNT0NLX05BTUU6Ok1PQ0tfRU1BSUw6OkVORA==";

    private static final String MOCK_URL = "http://mock-url.com/async/result";
    private static final URI MOCK_URI = URI.create(MOCK_URL);

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UriResolver uriResolver;

    @Captor
    private ArgumentCaptor<SubscriptionResponse> subscriptionResponse;

    @InjectMocks
    private SubscriptionProcessor processor;

    @Test
    void onApplicationEvent() {
        // Given
        var subscription = Subscription.builder()
                .name(NAME)
                .email(EMAIL)
                .build();

        var event = CreateSubscriptionEvent.of(REQ_ID, subscription);

        when(uriResolver.resolveForId(any(UUID.class)))
                .thenReturn(MOCK_URI);

        when(restTemplate.postForEntity(any(URI.class), any(SubscriptionResponse.class), any()))
                .thenReturn(ResponseEntity.noContent().build());

        // When
        processor.onApplicationEvent(event);

        // Then
        verify(uriResolver).resolveForId(REQ_ID);
        verify(restTemplate).postForEntity(eq(MOCK_URI), subscriptionResponse.capture(), eq(Void.class));
        verifyNoMoreInteractions(uriResolver, restTemplate);

        var response = subscriptionResponse.getValue();
        assertThat(response.getRequestId()).isEqualTo(REQ_ID);
        assertThat(response.getSubscriptionId()).isEqualTo(SUBSCRIPTION_ID);
    }
}