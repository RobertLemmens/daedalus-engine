package nl.daedalus.engine.renderer;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.renderer.opengl.OpenGLVertexBuffer;

import java.nio.FloatBuffer;

public abstract class VertexBuffer {

    public static VertexBuffer create(float[] vertices) {
        return switch (Constants.BACKEND) {
            case NONE -> null;
            case OPENGL -> new OpenGLVertexBuffer(vertices);
            case VULKAN -> null;
        };
    }

    public static VertexBuffer create(int size) {
        return switch (Constants.BACKEND) {
            case NONE -> null;
            case OPENGL -> new OpenGLVertexBuffer(size);
            case VULKAN -> null;
        };
    }

    public abstract void bind();
    public abstract void unbind();
    public abstract void dispose();
    public abstract void setLayout(BufferLayout bufferLayout);
    public abstract BufferLayout getLayout();
    public abstract void setData(FloatBuffer data, int size);
}
