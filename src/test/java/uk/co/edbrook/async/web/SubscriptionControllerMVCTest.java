package uk.co.edbrook.async.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.edbrook.async.domain.Subscription;
import uk.co.edbrook.async.domain.SubscriptionTrigger;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {SubscriptionController.class})
class SubscriptionControllerMVCTest {

    private static final String REQUEST_ID = "00000001-aaaa-bbbb-cccc-100000000001";
    private static final String EMAIL = "an@email.address.com";
    private static final String NAME = "aname";

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    @Captor
    private ArgumentCaptor<Subscription> subscriptionCaptor;

    @MockBean
    private SubscriptionTrigger subscriptionTrigger;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        doNothing().when(subscriptionTrigger)
                .subscribe(any(UUID.class), any(Subscription.class));
    }

    @Test
    void subscribe() throws Exception {
        mockMvc.perform(post("/api/subscribe")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"requestId\":\"" + REQUEST_ID + "\"," +
                        "\"name\":\"" + NAME + "\"," +
                        "\"email\":\"" + EMAIL + "\"" +
                        "}"))
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));

        verify(subscriptionTrigger).subscribe(uuidCaptor.capture(), subscriptionCaptor.capture());
        verifyNoMoreInteractions(subscriptionTrigger);

        var uuid = uuidCaptor.getValue();
        var subscription = subscriptionCaptor.getValue();

        var expectedUuid = UUID.fromString(REQUEST_ID);
        var expectedSubscription = Subscription.builder().name(NAME).email(EMAIL).build();

        assertThat(uuid).isEqualTo(expectedUuid);
        assertThat(subscription).isEqualTo(expectedSubscription);
    }
}