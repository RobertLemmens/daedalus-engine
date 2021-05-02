package nl.codebulb.engine.renderer;

import nl.codebulb.engine.Constants;
import nl.codebulb.engine.renderer.opengl.OpenGLVertexArray;

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
