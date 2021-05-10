package nl.daedalus.engine.renderer;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.renderer.opengl.OpenGLFrameBuffer;

public abstract class FrameBuffer {

    public abstract void bind();
    public abstract void unbind();
    public abstract void dispose();
    public abstract void resize(int width, int height);
    public abstract void resize(Vec2f size);
    public abstract int getColorAttachmentId();
    public abstract Vec2f getSize();

    public static FrameBuffer create(Vec2f size) {
        return switch(Constants.BACKEND) {
            case NONE -> null;
            case OPENGL -> new OpenGLFrameBuffer(size);
            case VULKAN -> null;
        };
    }
}
