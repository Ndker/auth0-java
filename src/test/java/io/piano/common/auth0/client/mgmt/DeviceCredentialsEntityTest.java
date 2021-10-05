package io.piano.common.auth0.client.mgmt;

import io.piano.common.auth0.client.MockServer;
import io.piano.common.auth0.client.RecordedRequestMatcher;
import io.piano.common.auth0.client.mgmt.filter.DeviceCredentialsFilter;
import io.piano.common.auth0.json.mgmt.DeviceCredentials;
import io.piano.common.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeviceCredentialsEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldListDeviceCredentials() throws Exception {
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/device-credentials"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListDeviceCredentialsWithClientId() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withClientId("client_23");
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/device-credentials"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("client_id", "client_23"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListDeviceCredentialsWithUserId() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withUserId("user_23");
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/device-credentials"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("user_id", "user_23"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }


    @Test
    public void shouldListDeviceCredentialsWithType() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withType("public_key");
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/device-credentials"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("type", "public_key"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }


    @Test
    public void shouldListDeviceCredentialsWithFields() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withFields("some,random,fields", true);
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/device-credentials"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("fields", "some,random,fields"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyDeviceCredentials() throws Exception {
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMPTY_LIST, 200);
        List<DeviceCredentials> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(DeviceCredentials.class)));
    }

    @Test
    public void shouldThrowOnCreateDeviceCredentialsWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'device credentials' cannot be null!");
        api.deviceCredentials().create(null);
    }

    @Test
    public void shouldCreateDeviceCredentials() throws Exception {
        Request<DeviceCredentials> request = api.deviceCredentials().create(new DeviceCredentials("device", "public_key", "val123", "id123", "clientId"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_DEVICE_CREDENTIALS, 200);
        DeviceCredentials response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("POST", "/api/v2/device-credentials"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(5));
        assertThat(body, hasEntry("device_name", "device"));
        assertThat(body, hasEntry("type", "public_key"));
        assertThat(body, hasEntry("value", "val123"));
        assertThat(body, hasEntry("device_id", "id123"));
        assertThat(body, hasEntry("client_id", "clientId"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteDeviceCredentialsWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'device credentials id' cannot be null!");
        api.deviceCredentials().delete(null);
    }

    @Test
    public void shouldDeleteDeviceCredentials() throws Exception {
        Request request = api.deviceCredentials().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_DEVICE_CREDENTIALS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("DELETE", "/api/v2/device-credentials/1"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
    }
}
