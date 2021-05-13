package nl.daedalus.engine.events;

public class KeyPressedEvent extends Event{

    private static final String NAME = "KeyPressedEvent";
    private int keyCode;

    public KeyPressedEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    @Override
    public EventType getType() {
        return EventType.KeyPressed;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
