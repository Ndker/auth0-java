package io.piano.common.auth0.json.mgmt;

import io.piano.common.auth0.json.JsonMatcher;
import io.piano.common.auth0.json.JsonTest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScopeTest extends JsonTest<Scope> {
    private static final String SCOPE_JSON = "src/test/resources/mgmt/scope.json";

    @Test
    public void serialize() throws Exception {
        Scope scope = new Scope("read:client_grants");
        scope.setDescription("Read Client Grants");
        String json = toJSON(scope);

        MatcherAssert.assertThat(json, JsonMatcher.hasEntry("value", "read:client_grants"));
        assertThat(json, JsonMatcher.hasEntry("description", "Read Client Grants"));
    }

    @Test
    public void deserialize() throws Exception {
        Scope deserialized = fromJSON(readTextFile(SCOPE_JSON), Scope.class);

        assertThat(deserialized.getValue(), is("read:client_grants"));
        assertThat(deserialized.getDescription(), is("Read Client Grants"));
    }
}
