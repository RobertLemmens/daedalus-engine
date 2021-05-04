package nl.daedalus.engine.renderer;

import nl.daedalus.engine.Constants;
import nl.daedalus.engine.renderer.opengl.OpenGLVertexArray;

public abstract class VertexArray {

    public static VertexArray create() {
        return switch (Constants.BACKEND) {
            case NONE -> null;
            case OPENGL -> new OpenGLVertexArray();
            case VULKAN -> null;
        };
    }

    public abstract void bind();
    public abstract void unbind();
    public abstract void addVertexBuffer(VertexBuffer buffer);
    public abstract void addIndexBuffer(IndexBuffer buffer);
    public abstract VertexBuffer getVertexBuffer();
    public abstract IndexBuffer getIndexBuffer();
}
