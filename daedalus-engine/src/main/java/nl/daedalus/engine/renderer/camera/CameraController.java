package nl.daedalus.engine.renderer.camera;

import nl.daedalus.engine.events.Event;

public abstract class CameraController {

    public abstract void onUpdate(float dt);
    public abstract void onEvent(Event e);

}
