package nl.codebulb.engine.renderer;

import nl.codebulb.engine.Constants;
import nl.codebulb.engine.math.Vec4f;
import nl.codebulb.engine.renderer.opengl.OpenGLRendererBackend;
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
