package nl.daedalus.engine.renderer.opengl;

import nl.daedalus.engine.renderer.Shader;
import nl.daedalus.engine.renderer.BufferElement;
import nl.daedalus.engine.renderer.IndexBuffer;
import nl.daedalus.engine.renderer.VertexArray;
import nl.daedalus.engine.renderer.VertexBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL20.*;
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
        int index = 0;
        for(BufferElement element : buffer.getLayout().getElements()) {
            glEnableVertexAttribArray(index);
            glVertexAttribPointer(index, element.getComponentCount(), getOpenGLType(element.datatype()), element.normalized(), buffer.getLayout().getStride(), element.offset());
            index++;
        }

//        glEnableVertexAttribArray(0);
//        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
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

    public static int getOpenGLType(Shader.Datatype datatype) {
        return switch (datatype) {
            case FLOAT, FLOAT2, FLOAT3, FLOAT4 -> GL_FLOAT;
            case MAT3, MAT4 -> GL_FLOAT;
            case INT, INT4, INT2, INT3 -> GL_INT;
            case BOOL -> GL_BOOL;
        };
    }
}
