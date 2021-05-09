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

    public static Texture create(int width, int height) {
        return switch (Constants.BACKEND) {
            case NONE -> null;
            case OPENGL -> new OpenGLTexture(width, height);
            case VULKAN -> null;
        };
    }
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract boolean equals(Texture other);
    public abstract String getPath();

    public abstract void setData(int data);

    public abstract void bind(int slot);
    public abstract void unbind();

}
