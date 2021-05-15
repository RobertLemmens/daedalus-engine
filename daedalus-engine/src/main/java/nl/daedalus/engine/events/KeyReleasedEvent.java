package nl.daedalus.engine.events;

public class KeyReleasedEvent extends Event {
    private static final String NAME = "KeyReleasedEvent";
    private int keyCode;

    public KeyReleasedEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    @Override
    public Event.EventType getType() {
        return EventType.KeyReleased;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
