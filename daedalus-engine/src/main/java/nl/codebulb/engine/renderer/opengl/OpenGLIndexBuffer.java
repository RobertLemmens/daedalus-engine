package nl.codebulb.engine.renderer.opengl;

import nl.codebulb.engine.renderer.IndexBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class OpenGLIndexBuffer extends IndexBuffer {

    private final int id;
    private final int[] indices;

    public OpenGLIndexBuffer(int[] indices) {
        id = glGenBuffers();
        this.indices = indices;
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    @Override
    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    @Override
    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void dispose() {
        glDeleteBuffers(id);
    }

    @Override
    public int getCount() {
        return indices.length;
    }
}
