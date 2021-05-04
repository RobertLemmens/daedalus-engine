package nl.daedalus.engine.renderer;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.renderer.opengl.OpenGLIndexBuffer;

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
