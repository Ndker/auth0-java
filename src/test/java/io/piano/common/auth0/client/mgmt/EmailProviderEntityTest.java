package io.piano.common.auth0.client.mgmt;

import io.piano.common.auth0.client.MockServer;
import io.piano.common.auth0.client.RecordedRequestMatcher;
import io.piano.common.auth0.client.mgmt.filter.FieldsFilter;
import io.piano.common.auth0.json.mgmt.emailproviders.EmailProvider;
import io.piano.common.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmailProviderEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldGetEmailProvider() throws Exception {
        Request<EmailProvider> request = api.emailProvider().get(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/emails/provider"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetEmailProviderWithFields() throws Exception {
        FieldsFilter filter = new FieldsFilter().withFields("some,random,fields", true);
        Request<EmailProvider> request = api.emailProvider().get(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/emails/provider"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("fields", "some,random,fields"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnSetupEmailProviderWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email provider' cannot be null!");
        api.emailProvider().setup(null);
    }

    @Test
    public void shouldSetupEmailProvider() throws Exception {
        Request<EmailProvider> request = api.emailProvider().setup(new EmailProvider("provider"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("POST", "/api/v2/emails/provider"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "provider"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldDeleteEmailProvider() throws Exception {
        Request request = api.emailProvider().delete();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMAIL_PROVIDER, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("DELETE", "/api/v2/emails/provider"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateEmailProviderWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email provider' cannot be null!");
        api.emailProvider().update(null);
    }

    @Test
    public void shouldUpdateEmailProvider() throws Exception {
        Request<EmailProvider> request = api.emailProvider().update(new EmailProvider("name"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("PATCH", "/api/v2/emails/provider"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "name"));

        assertThat(response, is(notNullValue()));
    }
}
