package nl.daedalus.engine.renderer;

import nl.daedalus.engine.math.Vec4f;

public class RenderData {

    public final static int maxQuads = 10000;
    public final static int maxVertices = maxQuads * 4;
    public final static int maxIndices = maxQuads * 6;

    public QuadVertex[] quadVertices = new QuadVertex[maxVertices];
    public int quadIndexCount = 0;
    public int quadCount = 0;


    private VertexArray vertexArray;
    private VertexBuffer vertexBuffer;
    private Shader shader;
    private Texture texture;

    public Vec4f[] quadVertexPositions = {
            new Vec4f(-0.5f, -0.5f, 0.0f, 1.0f),
            new Vec4f(0.5f, -0.5f, 0.0f, 1.0f),
            new Vec4f(0.5f, 0.5f, 0.0f, 1.0f),
            new Vec4f(-0.5f, 0.5f, 0.0f, 1.0f)
    };

    public RenderData() {
    }

    public VertexArray getVertexArray() {
        return vertexArray;
    }

    public void setVertexArray(VertexArray vertexArray) {
        this.vertexArray = vertexArray;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public VertexBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public void setVertexBuffer(VertexBuffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }
}
