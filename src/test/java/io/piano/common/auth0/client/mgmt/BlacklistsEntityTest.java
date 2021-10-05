package io.piano.common.auth0.client.mgmt;

import io.piano.common.auth0.client.MockServer;
import io.piano.common.auth0.client.RecordedRequestMatcher;
import io.piano.common.auth0.json.mgmt.Token;
import io.piano.common.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlacklistsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnGetBlacklistedTokensWithNullAudience() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'audience' cannot be null!");
        api.blacklists().getBlacklist(null);
    }

    @Test
    public void shouldGetBlacklistedTokens() throws Exception {
        Request<List<Token>> request = api.blacklists().getBlacklist("myapi");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_BLACKLISTED_TOKENS_LIST, 200);
        List<Token> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/blacklists/tokens"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("aud", "myapi"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyBlacklistedTokens() throws Exception {
        Request<List<Token>> request = api.blacklists().getBlacklist("myapi");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMPTY_LIST, 200);
        List<Token> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Token.class)));
    }

    @Test
    public void shouldThrowOnBlacklistTokensWithNullToken() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'token' cannot be null!");
        api.blacklists().blacklistToken(null);
    }

    @Test
    public void shouldBlacklistToken() throws Exception {
        Request request = api.blacklists().blacklistToken(new Token("id"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_BLACKLISTED_TOKENS_LIST, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("POST", "/api/v2/blacklists/tokens"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("jti", "id"));
    }
}
