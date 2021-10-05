package io.piano.common.auth0.client.mgmt;

import io.piano.common.auth0.client.MockServer;
import io.piano.common.auth0.client.RecordedRequestMatcher;
import io.piano.common.auth0.json.mgmt.userblocks.UserBlocks;
import io.piano.common.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserBlocksEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnGetUserBlocksByIdentifierWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'identifier' cannot be null!");
        api.userBlocks().getByIdentifier(null);
    }

    @Test
    public void shouldGetUserBlocksByIdentifier() throws Exception {
        Request<UserBlocks> request = api.userBlocks().getByIdentifier("username");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_BLOCKS, 200);
        UserBlocks response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/user-blocks"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("identifier", "username"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetUserBlocksWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.userBlocks().get(null);
    }

    @Test
    public void shouldGetUserBlocks() throws Exception {
        Request<UserBlocks> request = api.userBlocks().get("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_BLOCKS, 200);
        UserBlocks response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/user-blocks/1"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteUserBlocksByIdentifierWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'identifier' cannot be null!");
        api.userBlocks().deleteByIdentifier(null);
    }

    @Test
    public void shouldDeleteUserBlocksByIdentifier() throws Exception {
        Request request = api.userBlocks().deleteByIdentifier("username");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_BLOCKS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("DELETE", "/api/v2/user-blocks"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("identifier", "username"));
    }

    @Test
    public void shouldThrowOnDeleteUserBlocksWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.userBlocks().delete(null);
    }

    @Test
    public void shouldDeleteUserBlocks() throws Exception {
        Request request = api.userBlocks().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_BLOCKS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("DELETE", "/api/v2/user-blocks/1"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
    }
}
