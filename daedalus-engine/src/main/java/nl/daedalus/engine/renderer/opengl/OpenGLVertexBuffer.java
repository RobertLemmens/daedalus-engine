package nl.daedalus.engine.renderer.opengl;

import nl.daedalus.engine.renderer.BufferLayout;
import nl.daedalus.engine.renderer.RenderData;
import nl.daedalus.engine.renderer.VertexBuffer;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class OpenGLVertexBuffer extends VertexBuffer {

    private final int id;
    private BufferLayout bufferLayout;

    public OpenGLVertexBuffer(float[] vertices) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    /*
        VBO voor dynamic drawing
     */
    public OpenGLVertexBuffer(int size) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW); // size moet max vertices * vertex size zijn om te passen.
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

    @Override
    public void setLayout(BufferLayout bufferLayout) {
        this.bufferLayout = bufferLayout;
    }

    @Override
    public void setData(float[] vertices) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
    }

    @Override
    public BufferLayout getLayout() {
        return bufferLayout;
    }
}
