package nl.daedalus.engine.core;

import nl.daedalus.engine.events.Event;

public interface DaedalusLoop {

    void onInit();
    void onUpdate(float dt);
    void onEvent(Event e);
    void onExit();

}
