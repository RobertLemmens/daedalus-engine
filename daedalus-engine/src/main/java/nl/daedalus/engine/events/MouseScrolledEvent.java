package nl.daedalus.engine.events;

public class MouseScrolledEvent extends Event {

    private float xOffset;
    private float yOffset;

    public MouseScrolledEvent(float xoffset, float yoffset) {
        this.xOffset = xoffset;
        this.yOffset = yoffset;
    }

    @Override
    public EventType getType() {
        return EventType.MouseScrolled;
    }

    @Override
    public String getName() {
        return EventType.MouseScrolled.name();
    }


    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
