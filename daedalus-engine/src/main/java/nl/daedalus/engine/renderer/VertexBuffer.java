package nl.daedalus.engine.renderer;

import nl.daedalus.engine.Constants;
import nl.daedalus.engine.renderer.opengl.OpenGLVertexBuffer;

public abstract class VertexBuffer {

    public static VertexBuffer create(float[] vertices) {
        return switch (Constants.BACKEND) {
            case NONE -> null;
            case OPENGL -> new OpenGLVertexBuffer(vertices);
            case VULKAN -> null;
        };
    }

    public abstract void bind();
    public abstract void unbind();
    public abstract void dispose();
    public abstract void setLayout(BufferLayout bufferLayout);
    public abstract BufferLayout getLayout();
}
