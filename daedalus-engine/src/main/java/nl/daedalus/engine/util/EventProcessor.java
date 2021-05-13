package nl.daedalus.engine.util;

import nl.daedalus.engine.events.Event;

public interface EventProcessor {
    void onEvent(Event e);
}
