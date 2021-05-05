package nl.daedalus.engine.renderer;

public class RenderData {

    private VertexArray vertexArray;
    private Shader shader;
    private Texture texture;

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
}
