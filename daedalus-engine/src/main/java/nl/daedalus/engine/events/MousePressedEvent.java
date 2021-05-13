package nl.daedalus.engine.events;

import nl.daedalus.engine.math.Vec2f;

public class MousePressedEvent extends Event{

    private static final String NAME = "MousePressedEvent";

    private int button;

    public MousePressedEvent(int button) {
        this.button = button;
    }

    public int getButton() {
        return button;
    }

    @Override
    public EventType getType() {
        return EventType.MouseButtonPressed;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
