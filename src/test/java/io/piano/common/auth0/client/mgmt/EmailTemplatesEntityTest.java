package io.piano.common.auth0.client.mgmt;

import io.piano.common.auth0.client.MockServer;
import io.piano.common.auth0.client.RecordedRequestMatcher;
import io.piano.common.auth0.json.mgmt.EmailTemplate;
import io.piano.common.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("RedundantThrows")
public class EmailTemplatesEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldGetEmailTemplate() throws Exception {
        Request<EmailTemplate> request = api.emailTemplates().get("welcome_email");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMAIL_TEMPLATE, 200);
        EmailTemplate response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/email-templates/welcome_email"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetEmailTemplateWithNullName() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'template name' cannot be null!");
        api.emailTemplates().get(null);
    }

    @Test
    public void shouldCreateEmailTemplate() throws Exception {
        EmailTemplate template = new EmailTemplate();
        template.setName("welcome_email");
        template.setBody("Welcome!!");
        template.setFrom("auth0.com");
        template.setSubject("Welcome");
        template.setSyntax("liquid");
        template.setEnabled(true);

        Request<EmailTemplate> request = api.emailTemplates().create(template);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMAIL_PROVIDER, 200);
        EmailTemplate response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("POST", "/api/v2/email-templates"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(6));
        assertThat(body, hasEntry("template", "welcome_email"));
        assertThat(body, hasEntry("body", "Welcome!!"));
        assertThat(body, hasEntry("from", "auth0.com"));
        assertThat(body, hasEntry("syntax", "liquid"));
        assertThat(body, hasEntry("subject", "Welcome"));
        assertThat(body, hasEntry("enabled", true));
        assertThat(body, not(hasKey("resultUrl")));
        assertThat(body, not(hasKey("urlLifetimeInSeconds")));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateEmailTemplateWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'template' cannot be null!");
        api.emailTemplates().create(null);
    }

    @Test
    public void shouldPatchEmailTemplate() throws Exception {
        EmailTemplate template = new EmailTemplate();
        template.setBody("<html>New</html>");
        template.setUrlLifetimeInSeconds(123);
        template.setResultUrl("https://somewhere.com");

        Request<EmailTemplate> request = api.emailTemplates().update("welcome_email", template);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMAIL_PROVIDER, 200);
        EmailTemplate response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("PATCH", "/api/v2/email-templates/welcome_email"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(4));
        assertThat(body, hasEntry("resultUrl", "https://somewhere.com"));
        assertThat(body, hasEntry("body", "<html>New</html>"));
        assertThat(body, hasEntry("syntax", "liquid"));
        assertThat(body, hasEntry("urlLifetimeInSeconds", 123));
        assertThat(body, not(hasKey("template")));
        assertThat(body, not(hasKey("from")));
        assertThat(body, not(hasKey("enabled")));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnPatchEmailTemplateWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'template' cannot be null!");
        api.emailTemplates().update("welcome_email", null);
    }

    @Test
    public void shouldThrowOnPatchEmailTemplateWithNullName() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'template name' cannot be null!");
        api.emailTemplates().update(null, new EmailTemplate());
    }
}
