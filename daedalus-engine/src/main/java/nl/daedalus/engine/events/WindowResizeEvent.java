package nl.daedalus.engine.events;

public class WindowResizeEvent extends Event{

    private int width;
    private int height;

    public WindowResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public EventType getType() {
        return EventType.WindowResized;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String getName() {
        return EventType.WindowResized.name();
    }
}
