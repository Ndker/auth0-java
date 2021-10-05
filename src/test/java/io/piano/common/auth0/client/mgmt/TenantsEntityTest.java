package io.piano.common.auth0.client.mgmt;

import io.piano.common.auth0.client.MockServer;
import io.piano.common.auth0.client.RecordedRequestMatcher;
import io.piano.common.auth0.client.mgmt.filter.FieldsFilter;
import io.piano.common.auth0.json.mgmt.tenants.Tenant;
import io.piano.common.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TenantsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldGetTenantSettings() throws Exception {
        Request<Tenant> request = api.tenants().get(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_TENANT, 200);
        Tenant response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/tenants/settings"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetTenantSettingsWithFields() throws Exception {
        FieldsFilter filter = new FieldsFilter().withFields("some,random,fields", true);
        Request<Tenant> request = api.tenants().get(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_TENANT, 200);
        Tenant response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/tenants/settings"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("fields", "some,random,fields"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateTenantSettingsWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'tenant' cannot be null!");
        api.tenants().update(null);
    }

    @Test
    public void shouldUpdateTenantSettings() throws Exception {
        Request<Tenant> request = api.tenants().update(new Tenant());
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_TENANT, 200);
        Tenant response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("PATCH", "/api/v2/tenants/settings"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }
}
