package io.piano.common.auth0.json.mgmt.client;

import io.piano.common.auth0.json.mgmt.PageDeserializer;
import io.piano.common.auth0.client.mgmt.ClientsEntity;

import java.util.List;

/**
 * Parses a given paged response into their {@link ClientsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see ClientsEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class ClientsPageDeserializer extends PageDeserializer<ClientsPage, Client> {

    ClientsPageDeserializer() {
        super(Client.class, "clients");
    }

    @Override
    protected ClientsPage createPage(List<Client> items) {
        return new ClientsPage(items);
    }

    @Override
    protected ClientsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Client> items) {
        return new ClientsPage(start, length, total, limit, items);
    }

}
