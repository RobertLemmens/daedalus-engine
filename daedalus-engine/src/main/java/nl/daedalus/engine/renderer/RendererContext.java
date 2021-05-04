package nl.daedalus.engine.renderer;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.renderer.opengl.OpenGLRendererContext;

public abstract class RendererContext {

    public static RendererContext create(long window) {
        switch (Constants.BACKEND) {
            case OPENGL: return new OpenGLRendererContext(window);
            case VULKAN: throw new IllegalStateException("Vulkan renderer is not supported yet");
            case NONE: throw new IllegalArgumentException("None renderer is not supported yet");
        }
        return null;
    }

    public abstract void setVsync(boolean vsync);
    public abstract void swapBuffers();


}
