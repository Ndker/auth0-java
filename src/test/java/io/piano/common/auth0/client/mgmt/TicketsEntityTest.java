package io.piano.common.auth0.client.mgmt;

import io.piano.common.auth0.client.MockServer;
import io.piano.common.auth0.client.RecordedRequestMatcher;
import io.piano.common.auth0.json.mgmt.tickets.EmailVerificationTicket;
import io.piano.common.auth0.json.mgmt.tickets.PasswordChangeTicket;
import io.piano.common.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TicketsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnCreateEmailVerificationTicketWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email verification ticket' cannot be null!");
        api.tickets().requestEmailVerification(null);
    }

    @Test
    public void shouldCreateEmailVerificationTicket() throws Exception {
        EmailVerificationTicket ticket = new EmailVerificationTicket("uid123");
        ticket.setIncludeEmailInRedirect(true);
        ticket.setTTLSeconds(42);

        Request<EmailVerificationTicket> request = api.tickets().requestEmailVerification(ticket);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMAIL_VERIFICATION_TICKET, 200);
        EmailVerificationTicket response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("POST", "/api/v2/tickets/email-verification"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("user_id", "uid123"));
        assertThat(body, hasEntry("includeEmailInRedirect", true));
        assertThat(body, hasEntry("ttl_sec", 42));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreatePasswordChangeTicketWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'password change ticket' cannot be null!");
        api.tickets().requestPasswordChange(null);
    }

    @Test
    public void shouldCreatePasswordChangeTicket() throws Exception {
        Request<PasswordChangeTicket> request = api.tickets().requestPasswordChange(new PasswordChangeTicket("uid123"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_PASSWORD_CHANGE_TICKET, 200);
        PasswordChangeTicket response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("POST", "/api/v2/tickets/password-change"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("user_id", "uid123"));

        assertThat(response, is(notNullValue()));
    }
}
