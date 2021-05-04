package nl.daedalus.engine.renderer;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.renderer.opengl.OpenGLTexture;

public abstract class Texture {

    public static Texture create(String path) {
        return switch (Constants.BACKEND) {
            case NONE -> null;
            case OPENGL -> new OpenGLTexture(path);
            case VULKAN -> null;
        };
    }

    public abstract int getWidth();
    public abstract int getHeight();

    public abstract void bind(int slot);
    public abstract void unbind();

}
