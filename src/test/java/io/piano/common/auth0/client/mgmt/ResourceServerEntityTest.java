package io.piano.common.auth0.client.mgmt;

import io.piano.common.auth0.client.MockServer;
import io.piano.common.auth0.client.RecordedRequestMatcher;
import io.piano.common.auth0.client.mgmt.filter.ResourceServersFilter;
import io.piano.common.auth0.json.mgmt.ResourceServer;
import io.piano.common.auth0.json.mgmt.ResourceServersPage;
import io.piano.common.auth0.json.mgmt.Scope;
import io.piano.common.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResourceServerEntityTest extends BaseMgmtEntityTest {


    @Test
    public void shouldListResourceServerWithoutFilter() throws Exception {
        Request<ResourceServersPage> request = api.resourceServers().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_RESOURCE_SERVERS_LIST, 200);
        ResourceServersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/resource-servers"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListResourceServerWithPage() throws Exception {
        ResourceServersFilter filter = new ResourceServersFilter().withPage(23, 5);
        Request<ResourceServersPage> request = api.resourceServers().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_RESOURCE_SERVERS_LIST, 200);
        ResourceServersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/resource-servers"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("page", "23"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListResourceServerWithTotals() throws Exception {
        ResourceServersFilter filter = new ResourceServersFilter().withTotals(true);
        Request<ResourceServersPage> request = api.resourceServers().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_RESOURCE_SERVERS_PAGED_LIST, 200);
        ResourceServersPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/resource-servers"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getLength(), is(14));
        assertThat(response.getTotal(), is(14));
        assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldListResourceServers() throws Exception {
        @SuppressWarnings("deprecation")
        Request<List<ResourceServer>> request = api.resourceServers().list();

        assertThat(request, is(notNullValue()));
        server.jsonResponse(MockServer.MGMT_RESOURCE_SERVERS_LIST, 200);

        List<ResourceServer> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/resource-servers"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldUpdateResourceServer() throws Exception {
        ResourceServer resourceServer = new ResourceServer("https://api.my-company.com/api/v2/");
        resourceServer.setId("23445566abab");
        resourceServer.setName("Some API");
        resourceServer.setScopes(Collections.singletonList(new Scope("update:something")));
        resourceServer.setSigningAlgorithm("RS256");
        resourceServer.setSigningSecret("secret");
        resourceServer.setAllowOfflineAccess(false);
        resourceServer.setEnforcePolicies(true);
        resourceServer.setSkipConsentForVerifiableFirstPartyClients(false);
        resourceServer.setTokenDialect("access_token");
        resourceServer.setTokenLifetime(0);
        resourceServer.setVerificationLocation("verification_location");
        Request<ResourceServer> request = api.resourceServers()
                .update("23445566abab", resourceServer);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("PATCH", "/api/v2/resource-servers/23445566abab"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(12));
        assertThat(body, hasEntry("identifier", "https://api.my-company.com/api/v2/"));

        assertThat(response.getIdentifier(), is("https://api.my-company.com/api/v2/"));
    }

    @Test
    public void shouldGetResourceServerById() throws Exception {
        Request<ResourceServer> request = api.resourceServers()
                .get("23445566abab");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/resource-servers/23445566abab"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response.getIdentifier(), is("https://api.my-company.com/api/v2/"));
    }

    @Test
    public void shouldCreateResourceServer() throws Exception {
        Request<ResourceServer> request = api.resourceServers()
                .create(new ResourceServer("https://api.my-company.com/api/v2/"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_RESOURCE_SERVER, 200);
        ResourceServer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("POST", "/api/v2/resource-servers"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("identifier", "https://api.my-company.com/api/v2/"));

        assertThat(response.getIdentifier(), is("https://api.my-company.com/api/v2/"));
    }

    @Test
    public void shouldDeleteResourceServer() throws Exception {
        Request<Void> request = api.resourceServers()
                .delete("23445566abab");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("DELETE", "/api/v2/resource-servers/23445566abab"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
    }
}
