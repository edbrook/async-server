package uk.co.edbrook.async.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    private static final UUID REQ_ID = UUID.fromString("00000000-0000-0000-0000-000000001111");
    private static final String EMAIL_ADDRESS = "MOCK.EMAIL@ADDRESS.COM";
    private static final String NAME = "MOCK_NAME";

    @Mock
    private ApplicationEventPublisher publisher;

    @Captor
    private ArgumentCaptor<CreateSubscriptionEvent> subscriptionEvent;

    @InjectMocks
    private SubscriptionService service;

    @Test
    void subscribe() {
        // Given
        Subscription subscription = Subscription.builder()
                .name(NAME)
                .email(EMAIL_ADDRESS)
                .build();

        // When
        service.subscribe(REQ_ID, subscription);

        // Then
        verify(publisher).publishEvent(subscriptionEvent.capture());
        verifyNoMoreInteractions(publisher);
        assertThat(subscriptionEvent.getValue().getSource()).isEqualTo(subscription);
        assertThat(subscriptionEvent.getValue().getRequestId()).isEqualTo(REQ_ID);
    }
}