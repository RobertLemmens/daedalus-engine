package nl.daedalus.engine.renderer;

import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.data.QuadVertex;

import java.util.List;

public class RenderData {

    private final int maxQuads = 10000;
    private final int maxVertices = maxQuads * 4;
    private final int maxIndices = maxQuads * 6;
    private final int maxTextureSlots = 32;
    private int textureSlotIndex = 1;

    private VertexArray vertexArray;
    private Shader shader;
    private Texture texture;

    private Texture[] textureSlots = new Texture[maxTextureSlots];
    private Vec4f[] quadVertexPositions = new Vec4f[4];

    private int quadIndexCount = 0;

    private int vertexPointer = 0;
    private QuadVertex[] quadVertexes;

    public RenderData() {
    }

    public int getVertexPointer() {
        return vertexPointer;
    }

    public void setVertexPointer(int vertexPointer) {
        this.vertexPointer = vertexPointer;
    }

    public void incrementVertexPointer() {
        this.vertexPointer++;
    }

    public int getMaxQuads() {
        return maxQuads;
    }

    public int getMaxVertices() {
        return maxVertices;
    }

    public int getMaxIndices() {
        return maxIndices;
    }

    public int getMaxTextureSlots() {
        return maxTextureSlots;
    }

    public int getTextureSlotIndex() {
        return textureSlotIndex;
    }

    public void setTextureSlotIndex(int textureSlotIndex) {
        this.textureSlotIndex = textureSlotIndex;
    }

    public int getQuadIndexCount() {
        return quadIndexCount;
    }

    public void setQuadIndexCount(int quadIndexCount) {
        this.quadIndexCount = quadIndexCount;
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

    public Texture[] getTextureSlots() {
        return textureSlots;
    }

    public void setTextureSlots(Texture[] textureSlots) {
        this.textureSlots = textureSlots;
    }

    public QuadVertex[] getQuadVertexes() {
        return quadVertexes;
    }

    public void setQuadVertexes(QuadVertex[] quadVertexes) {
        this.quadVertexes = quadVertexes;
    }

    public Vec4f[] getQuadVertexPositions() {
        return quadVertexPositions;
    }

    public void setQuadVertexPositions(Vec4f[] quadVertexPositions) {
        this.quadVertexPositions = quadVertexPositions;
    }
}
