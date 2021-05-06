package nl.daedalus.engine.events;

public abstract class Event {

    public enum EventType {
        WindowResized,
        KeyPressed, KeyReleased, KeyTyped,
        MouseButtonPressed, MouseButtonReleased, MouseMoved, MouseScrolled
    }

    public abstract EventType getType();
    public abstract String getName();


}
