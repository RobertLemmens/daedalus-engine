package nl.codebulb.engine.renderer;

import nl.codebulb.engine.Constants;
import nl.codebulb.engine.renderer.opengl.OpenGLIndexBuffer;

public abstract class IndexBuffer {

    public static IndexBuffer create(int[] indices) {
        return switch(Constants.BACKEND) {
            case NONE -> null;
            case OPENGL -> new OpenGLIndexBuffer(indices);
            case VULKAN -> null;
        };
    }

    public abstract void bind();
    public abstract void unbind();
    public abstract void dispose();
    public abstract int getCount();
}
