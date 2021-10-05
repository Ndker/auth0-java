package io.piano.common.auth0.json.mgmt.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.piano.common.auth0.client.mgmt.ActionsEntity;

import java.util.List;

/**
 * Represents the request body when updating a trigger's action bindings.
 * @see ActionsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BindingsUpdateRequest {

    @JsonProperty("bindings")
    private List<BindingUpdate> bindingUpdates;

    /**
     * Creates a new instance
     *
     * @param bindingUpdates a list of binding updates
     */
    @JsonCreator
    public BindingsUpdateRequest(@JsonProperty("bindings") List<BindingUpdate> bindingUpdates) {
        this.bindingUpdates = bindingUpdates;
    }

    /**
     * @return the binding updates associated with this instance.
     */
    public List<BindingUpdate> getBindingUpdates() {
        return bindingUpdates;
    }
}
