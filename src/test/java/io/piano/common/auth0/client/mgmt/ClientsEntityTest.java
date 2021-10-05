package io.piano.common.auth0.client.mgmt;

import io.piano.common.auth0.client.MockServer;
import io.piano.common.auth0.client.RecordedRequestMatcher;
import io.piano.common.auth0.client.mgmt.filter.ClientFilter;
import io.piano.common.auth0.json.mgmt.client.Client;
import io.piano.common.auth0.json.mgmt.client.ClientsPage;
import io.piano.common.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClientsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListClientsWithoutFilter() throws Exception {
        Request<ClientsPage> request = api.clients().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENTS_LIST, 200);
        ClientsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/clients"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClientsWithPage() throws Exception {
        ClientFilter filter = new ClientFilter().withPage(23, 5);
        Request<ClientsPage> request = api.clients().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENTS_LIST, 200);
        ClientsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/clients"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("page", "23"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClientsWithTotals() throws Exception {
        ClientFilter filter = new ClientFilter().withTotals(true);
        Request<ClientsPage> request = api.clients().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENTS_PAGED_LIST, 200);
        ClientsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/clients"));
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
    public void shouldListClientsWithFields() throws Exception {
        ClientFilter filter = new ClientFilter().withFields("some,random,fields", true);
        Request<ClientsPage> request = api.clients().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENTS_PAGED_LIST, 200);
        ClientsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/clients"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("fields", "some,random,fields"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClientsWithAdditionalProperties() throws Exception {
        ClientFilter filter = new ClientFilter()
                .withAppType("regular_web,native")
                .withIsFirstParty(true)
                .withIsGlobal(true);
        Request<ClientsPage> request = api.clients().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENTS_LIST, 200);
        ClientsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/clients"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("app_type", "regular_web,native"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("is_first_party", "true"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasQueryParameter("is_global", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListClients() throws Exception {
        @SuppressWarnings("deprecation")
        Request<List<Client>> request = api.clients().list();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENTS_LIST, 200);
        List<Client> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/clients"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyClients() throws Exception {
        @SuppressWarnings("deprecation")
        Request<List<Client>> request = api.clients().list();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_EMPTY_LIST, 200);
        List<Client> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Client.class)));
    }

    @Test
    public void shouldThrowOnGetClientWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        api.clients().get(null);
    }

    @Test
    public void shouldGetClient() throws Exception {
        Request<Client> request = api.clients().get("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENT, 200);
        Client response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("GET", "/api/v2/clients/1"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateClientWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client' cannot be null!");
        api.clients().create(null);
    }

    @Test
    public void shouldCreateClient() throws Exception {
        Request<Client> request = api.clients().create(new Client("My Application"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENT, 200);
        Client response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("POST", "/api/v2/clients"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "My Application"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteClientWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        api.clients().delete(null);
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        Request request = api.clients().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENT, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("DELETE", "/api/v2/clients/1"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateClientWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        api.clients().update(null, new Client("name"));
    }

    @Test
    public void shouldThrowOnUpdateClientWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client' cannot be null!");
        api.clients().update("clientId", null);
    }

    @Test
    public void shouldUpdateClient() throws Exception {
        Request<Client> request = api.clients().update("1", new Client("My Application"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENT, 200);
        Client response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("PATCH", "/api/v2/clients/1"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = MockServer.bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "My Application"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnRotateClientSecretWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'client id' cannot be null!");
        api.clients().rotateSecret(null);
    }

    @Test
    public void shouldRotateClientSecret() throws Exception {
        Request<Client> request = api.clients().rotateSecret("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_CLIENT, 200);
        Client response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasMethodAndPath("POST", "/api/v2/clients/1/rotate-secret"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Type", "application/json"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Content-Length", "0"));
        MatcherAssert.assertThat(recordedRequest, RecordedRequestMatcher.hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

}
