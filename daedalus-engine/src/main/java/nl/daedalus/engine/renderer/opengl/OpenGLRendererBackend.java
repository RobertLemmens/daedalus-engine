package nl.daedalus.engine.renderer.opengl;

import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.RendererBackend;
import nl.daedalus.engine.renderer.VertexArray;

import static org.lwjgl.opengl.GL11.*;

public class OpenGLRendererBackend extends RendererBackend {

    public OpenGLRendererBackend() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void drawIndexed(VertexArray vertexArray, int indexCount) {
        int count = indexCount == 0 ? vertexArray.getIndexBuffer().getCount() : indexCount;
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void setClearColor(Vec4f color) {
        glClearColor(color.r(), color.g(), color.b(), color.a());
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    @Override
    public void setViewport(int x, int y, int width, int height) {
        glViewport(x, y, width, height);
    }
}
