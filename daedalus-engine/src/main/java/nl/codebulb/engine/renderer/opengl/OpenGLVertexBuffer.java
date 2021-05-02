package nl.codebulb.engine.renderer.opengl;

import nl.codebulb.engine.renderer.VertexBuffer;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class OpenGLVertexBuffer extends VertexBuffer {

    private final int id;

    public OpenGLVertexBuffer(float[] vertices) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    @Override
    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    @Override
    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void dispose() {
        glDeleteBuffers(id);
    }
}
