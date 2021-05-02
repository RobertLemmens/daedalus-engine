package nl.codebulb.engine.renderer;

import nl.codebulb.engine.Constants;
import nl.codebulb.engine.renderer.opengl.OpenGLVertexBuffer;

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
