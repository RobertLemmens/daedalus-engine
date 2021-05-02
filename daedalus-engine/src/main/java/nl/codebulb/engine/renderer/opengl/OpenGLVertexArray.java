package nl.codebulb.engine.renderer.opengl;

import nl.codebulb.engine.renderer.IndexBuffer;
import nl.codebulb.engine.renderer.VertexArray;
import nl.codebulb.engine.renderer.VertexBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class OpenGLVertexArray extends VertexArray {

    private final int id;
    private VertexBuffer vertexBuffer;
    private IndexBuffer indexBuffer;

    public OpenGLVertexArray() {
        id = glGenVertexArrays();
    }

    @Override
    public void bind() {
        glBindVertexArray(id);
    }

    @Override
    public void unbind() {
        glBindVertexArray(0);
    }

    @Override
    public void addVertexBuffer(VertexBuffer buffer) {
        bind();
        buffer.bind();
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        this.vertexBuffer = buffer;
    }

    @Override
    public void addIndexBuffer(IndexBuffer buffer) {
        bind();
        buffer.bind();

        this.indexBuffer = buffer;
    }

    @Override
    public VertexBuffer getVertexBuffer() {
        return this.vertexBuffer;
    }

    @Override
    public IndexBuffer getIndexBuffer() {
        return this.indexBuffer;
    }
}
