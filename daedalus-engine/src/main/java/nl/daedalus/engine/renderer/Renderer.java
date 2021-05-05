package nl.daedalus.engine.renderer;

import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.camera.OrthographicCamera;
import org.lwjgl.opengl.ARBTextureEnvDot3;

public final class Renderer {

    private static RendererBackend backend;

    private static RenderData renderData;

    public static void init() {
        backend = RendererBackend.create();
        renderData = new RenderData();
        renderData.setVertexArray(VertexArray.create());

        float[] vertices = {
                -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f,  0.5f, 0.0f, 1.0f, 1.0f,
                -0.5f,  0.5f, 0.0f, 0.0f, 1.0f
        };

        VertexBuffer vertexBuffer = VertexBuffer.create(vertices);
        BufferLayout layout = new BufferLayout();
        layout.addElement("a_position", Shader.Datatype.FLOAT3);
        layout.addElement("u_texcoord", Shader.Datatype.FLOAT2);
        vertexBuffer.setLayout(layout);
        renderData.getVertexArray().addVertexBuffer(vertexBuffer);

        int[] squareIndices = {0,1,2,2,3,0};
        IndexBuffer squareIndexBuffer = IndexBuffer.create(squareIndices);
        renderData.getVertexArray().addIndexBuffer(squareIndexBuffer);
        renderData.setShader(Shader.create("shaders/Texture.glsl"));

    }

    public static void setClearColor(Vec4f color){
        backend.setClearColor(color);
    }

    public static void clear() {
        backend.clear();
    }

    //temp
    private static Mat4f viewProjectionMatrix;

    public static void begin(OrthographicCamera orthographicCamera) {
        viewProjectionMatrix = orthographicCamera.getViewProjectionMatrix();
        renderData.getShader().uploadUniformMat4("u_view_projection", viewProjectionMatrix);
        // bind shader

        // bind the vertex array
    }

    public static void end() {
        // draw everything

    }

    public static void drawQuad(float x, float y, Mat4f scale, float rotation, Texture texture) {
        // update the quads
        drawQuad(new Vec3f(x, y, 0.0f), scale, rotation, texture);
    }
    public static void drawQuad(Vec3f position, Mat4f scale, float rotation, Texture texture) {
        renderData.getShader().bind();
        texture.bind(0);
        Mat4f transform = Mat4f.translate(position).multiply(scale);
        renderData.getShader().uploadUniformMat4("u_transform", transform);

        renderData.getVertexArray().bind();
        backend.drawIndexed(renderData.getVertexArray());
    }

//    public static void drawQuad(Vec3f position, Mat4f scale, float rotation, Vec4f color) {
//        renderData.getShader().bind();
//        texture.bind(0);
//        Mat4f transform = Mat4f.translate(position).multiply(scale);
//        renderData.getShader().uploadUniformMat4("u_transform", transform);
//
//        renderData.getVertexArray().bind();
//        backend.drawIndexed(renderData.getVertexArray());
//    }

    public static void drawQuad(VertexArray vertexArray, Shader shader, Mat4f transform) {
        shader.bind();
        shader.uploadUniformMat4("u_view_projection", viewProjectionMatrix);
        shader.uploadUniformMat4("u_transform", transform);

        vertexArray.bind();
        backend.drawIndexed(vertexArray);
    }

}
