package nl.daedalus.engine.renderer.opengl;

import nl.daedalus.engine.DaedalusLogger;
import nl.daedalus.engine.renderer.RendererContext;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Initialize our opengl specifics.
 */
public class OpenGLRendererContext extends RendererContext {

    private long window;
    public OpenGLRendererContext(long window) {
        this.window = window;
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        int major = glGetInteger(GL_MAJOR_VERSION);
        int minor = glGetInteger(GL_MINOR_VERSION);
        String vendor = glGetString(GL_VENDOR);
        String renderer = glGetString(GL_RENDERER);
        String version = glGetString(GL_VERSION);

        DaedalusLogger.info("Graphics vendor     [" + vendor + "]");
        DaedalusLogger.info("Graphics renderer   [" + renderer + "]");
        DaedalusLogger.info("Graphics version    [" + version + "]");
        DaedalusLogger.info("OpenGL version: " + major + "." + minor);
    }

    @Override
    public void setVsync(boolean vsync) {
        glfwSwapInterval(1);
    }

    @Override
    public void swapBuffers() {
        glfwSwapBuffers(window); // swap the color buffers
    }
}
