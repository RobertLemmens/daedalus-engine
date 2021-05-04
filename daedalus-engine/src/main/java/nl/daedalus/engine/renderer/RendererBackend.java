package nl.daedalus.engine.renderer;

import nl.daedalus.engine.Constants;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.opengl.OpenGLRendererBackend;
public abstract class RendererBackend {

    public enum Backends {
        NONE, OPENGL, VULKAN
    }

    public static RendererBackend create() {
        switch (Constants.BACKEND) {
            case OPENGL: return new OpenGLRendererBackend();
            case VULKAN: throw new IllegalArgumentException("Vulkan renderer is not supported yet");
            case NONE: throw new IllegalArgumentException("None renderer is not supported yet");
        }
        return null;
    }

    public abstract void drawIndexed(VertexArray vertexArray);
    public abstract void setClearColor(Vec4f color);
    public abstract void clear();

}
