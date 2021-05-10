package nl.daedalus.engine.scene;

import nl.daedalus.engine.events.Event;

public interface EventProcessor {
    void onEvent(Event e);
}
