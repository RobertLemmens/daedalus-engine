package nl.daedalus.engine.renderer;

import nl.daedalus.engine.events.WindowResizeEvent;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.camera.OrthographicCamera;
import nl.daedalus.engine.renderer.data.QuadVertex;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public final class Renderer {

    private static RendererBackend backend;

    private static RenderData renderData;

    public static void init() {
        backend = RendererBackend.create();
        renderData = new RenderData();
        renderData.setVertexArray(VertexArray.create());

        // TODO get real quad size
        VertexBuffer vertexBuffer = VertexBuffer.create(renderData.getMaxVertices() * (4 + 3 + 2));
        BufferLayout layout = new BufferLayout();
        layout.addElement("a_position", Shader.Datatype.FLOAT3);
        layout.addElement("a_color", Shader.Datatype.FLOAT4);
        layout.addElement("a_texcoord", Shader.Datatype.FLOAT2);
        layout.addElement("a_texindex", Shader.Datatype.FLOAT);
        layout.addElement("a_tilingfactor", Shader.Datatype.FLOAT);
        vertexBuffer.setLayout(layout);
        renderData.getVertexArray().addVertexBuffer(vertexBuffer);

        // init to max vertices for now
        renderData.setQuadVertexes(new QuadVertex[renderData.getMaxVertices()]);

        int[] quadIndices = new int[renderData.getMaxIndices()];

        int offset = 0;
        for (int i = 0; i < renderData.getMaxIndices(); i += 6) {
            quadIndices[i + 0] = offset += 0;
            quadIndices[i + 1] = offset += 1;
            quadIndices[i + 2] = offset += 2;
            quadIndices[i + 3] = offset += 2;
            quadIndices[i + 4] = offset += 3;
            quadIndices[i + 5] = offset += 0;

            offset += 4;
        }
        IndexBuffer squareIndexBuffer = IndexBuffer.create(quadIndices);
        renderData.getVertexArray().addIndexBuffer(squareIndexBuffer);

        int[] samplers = new int[renderData.getMaxTextureSlots()];
        for ( int i = 0; i < renderData.getMaxTextureSlots(); i++) {
            samplers[i] = i;
        }
        renderData.setShader(Shader.create("shaders/Texture.glsl"));
        renderData.getShader().bind();
        renderData.getShader().setIntArray("u_texture", samplers);

        renderData.getQuadVertexPositions()[0] = new Vec4f(-0.5f, -0.5f, 0.0f, 1.0f);
        renderData.getQuadVertexPositions()[1] = new Vec4f(0.5f, -0.5f, 0.0f, 1.0f);
        renderData.getQuadVertexPositions()[2] = new Vec4f(0.5f, 0.5f, 0.0f, 1.0f);
        renderData.getQuadVertexPositions()[3] = new Vec4f(-0.5f, 0.5f, 0.0f, 1.0f);

    }

    public static void setClearColor(Vec4f color){
        backend.setClearColor(color);
    }

    public static void clear() {
        backend.clear();
    }

    //todo temp
    private static Mat4f viewProjectionMatrix;

    public static void begin(OrthographicCamera orthographicCamera) {
        viewProjectionMatrix = orthographicCamera.getViewProjectionMatrix();
        renderData.getShader().bind();
        renderData.getShader().uploadUniformMat4("u_view_projection", viewProjectionMatrix);
        renderData.setQuadIndexCount(0); //todo overbodig?
        renderData.setTextureSlotIndex(1);
        renderData.setVertexPointer(0);
    }

    public static void end() {
        // draw everything
        FloatBuffer buffer = BufferUtils.createFloatBuffer((3+4+2+2) * (renderData.getQuadVertexes().length));
        for (int i = 0; i < renderData.getVertexPointer(); i++) {
            buffer.put(renderData.getQuadVertexes()[i].toBuffer());
        }
        buffer.flip();
        renderData.getVertexArray().getVertexBuffer().setData(buffer, renderData.getQuadVertexes().length);
        for (int i = 0; i < renderData.getTextureSlotIndex(); i++) {
            renderData.getTextureSlots()[i].bind(i);
        }
        backend.drawIndexed(renderData.getVertexArray(), renderData.getQuadIndexCount());
    }

    public static void drawQuad(float x, float y, Mat4f scale, float rotation, Texture texture) {
        drawQuad(new Vec3f(x, y, 0.0f), scale, rotation, texture);
    }

    public static Vec4f color = new Vec4f(1.0f, 1.0f, 1.0f, 1.0f);

    public static void drawQuad(Vec3f position, Mat4f scale, float rotation, Texture texture) {

        Mat4f transform = Mat4f.translate(position).multiply(scale);

        float[][] texCoords = {
                {0.0f, 0.0f},
                {1.0f, 0.0f},
                {1.0f, 1.0f},
                {0.0f, 1.0f}
        };

        renderData.getTextureSlots()[0] = texture;

        for (int i = 0; i < 4; i++) {
            renderData.getQuadVertexes()[renderData.getVertexPointer()] = new QuadVertex();
            renderData.getQuadVertexes()[renderData.getVertexPointer()].setPosition(transform.multiply(renderData.getQuadVertexPositions()[i]).getVec3());
            renderData.getQuadVertexes()[renderData.getVertexPointer()].setColor(color);
            renderData.getQuadVertexes()[renderData.getVertexPointer()].setTexCoord(new Vec2f(texCoords[i]));
            renderData.getQuadVertexes()[renderData.getVertexPointer()].setTexIndex(0);
            renderData.getQuadVertexes()[renderData.getVertexPointer()].setTilingFactor(1);
            renderData.incrementVertexPointer();
        }

        renderData.setQuadIndexCount(renderData.getQuadIndexCount() + 6); //todo maak gwn public
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

    public static void onWindowResize(WindowResizeEvent e) {
        backend.setViewport(0, 0, e.getWidth(), e.getHeight());
    }

}
