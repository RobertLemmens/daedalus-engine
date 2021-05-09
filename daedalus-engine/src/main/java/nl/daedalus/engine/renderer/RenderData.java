package nl.daedalus.engine.renderer;

import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.texture.Texture;

public class RenderData {

    public final static int maxQuads = 10000;
    public final static int maxVertices = maxQuads * 4;
    public final static int maxIndices = maxQuads * 6;
    public final static int maxTextures = 32;

    public QuadVertex[] quadVertices = new QuadVertex[maxVertices];
    public int quadIndexCount = 0;
    public int quadCount = 0;
    public int textureIndex = 0;

    public Texture[] textures = new Texture[maxTextures];

    public Vec2f[] defaultTexCoords = {
            new Vec2f(0.0f, 0.0f),
            new Vec2f(1.0f, 0.0f),
            new Vec2f(1.0f, 1.0f),
            new Vec2f(0.0f, 1.0f)
    };

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
