package io.piano.common.auth0.json.mgmt;

import java.util.ArrayList;
import java.util.List;

import io.piano.common.auth0.json.JsonTest;
import io.piano.common.auth0.json.JsonMatcher;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static io.piano.common.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResourceServerTest extends JsonTest<ResourceServer> {
    private final static String RESOURCE_SERVER_JSON = "src/test/resources/mgmt/resource_server.json";

    @Test
    public void deserialize() throws Exception {
        ResourceServer deserialized = fromJSON(readTextFile(RESOURCE_SERVER_JSON), ResourceServer.class);

        assertThat(deserialized.getId(), is("23445566abab"));
        assertThat(deserialized.getName(), is("Some API"));
        assertThat(deserialized.getIdentifier(), is("https://api.my-company.com/api/v2/"));
        assertThat(deserialized.getScopes(), hasSize(2));
        assertThat(deserialized.getSigningAlgorithm(), is("RS256"));
        assertThat(deserialized.getSigningSecret(), is("secret"));
        assertThat(deserialized.isSystem(), is(true));
        assertThat(deserialized.getAllowOfflineAccess(), is(false));
        assertThat(deserialized.getSkipConsentForVerifiableFirstPartyClients(), is(false));
        assertThat(deserialized.getTokenDialect(), is("access_token"));
        assertThat(deserialized.getTokenLifetime(), is(86400));
        assertThat(deserialized.getVerificationLocation(), is("verification_location"));
    }

    @Test
    public void serialize() throws Exception {
        ResourceServer entity = new ResourceServer("https://api.my-company.com/api/v2/");
        Scope scope1 = new Scope("read:client_grants");
        scope1.setDescription("Read Client Grants");
        Scope scope2 = new Scope("create:client_grants");
        scope2.setDescription("Create Client Grants");

        List<Scope> scopes = new ArrayList<>();
        scopes.add(scope1);
        scopes.add(scope2);
        entity.setId("23445566abab");
        entity.setName("Some API");
        entity.setScopes(scopes);
        entity.setSigningAlgorithm("RS256");
        entity.setSigningSecret("secret");
        entity.setEnforcePolicies(true);
        entity.setAllowOfflineAccess(false);
        entity.setSkipConsentForVerifiableFirstPartyClients(false);
        entity.setTokenLifetime(86400);
        entity.setTokenDialect("access_token_authz");
        entity.setVerificationLocation("verification_location");

        String json = toJSON(entity);

        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("id", "23445566abab"));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("name", "Some API"));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("identifier", "https://api.my-company.com/api/v2/"));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("signing_alg", "RS256"));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("signing_secret", "secret"));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("enforce_policies", true));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("allow_offline_access", false));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("skip_consent_for_verifiable_first_party_clients", false));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("token_lifetime", 86400));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("token_dialect", "access_token_authz"));
        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("verification_location", "verification_location"));
    }
}
