package nl.daedalus.engine.renderer.opengl;

import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.RendererBackend;
import nl.daedalus.engine.renderer.VertexArray;

import static org.lwjgl.opengl.GL11.*;

public class OpenGLRendererBackend extends RendererBackend {

    public OpenGLRendererBackend() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void drawIndexed(VertexArray vertexArray) {
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void setClearColor(Vec4f color) {
        glClearColor(color.r(), color.g(), color.b(), color.a());
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }
}
