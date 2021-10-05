package io.piano.common.auth0.json.mgmt.logevents;

import io.piano.common.auth0.json.mgmt.PageDeserializer;
import io.piano.common.auth0.client.mgmt.LogEventsEntity;

import java.util.List;

/**
 * Parses a given paged response into their {@link LogEventsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see LogEventsEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class LogEventsPageDeserializer extends PageDeserializer<LogEventsPage, LogEvent> {

    protected LogEventsPageDeserializer() {
        super(LogEvent.class, "logs");
    }

    @Override
    protected LogEventsPage createPage(List<LogEvent> items) {
        return new LogEventsPage(items);
    }

    @Override
    protected LogEventsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<LogEvent> items) {
        return new LogEventsPage(start, length, total, limit, items);
    }
}
