package nl.codebulb.engine.renderer.opengl;

import nl.codebulb.engine.math.Vector4f;
import nl.codebulb.engine.renderer.RendererBackend;
import nl.codebulb.engine.renderer.VertexArray;

import static org.lwjgl.opengl.GL11.*;

public class OpenGLRendererBackend extends RendererBackend {

    @Override
    public void drawIndexed(VertexArray vertexArray) {
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void setClearColor(Vector4f color) {
        glClearColor(color.r(), color.g(), color.b(), color.a());
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }
}
