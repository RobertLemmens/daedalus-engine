package nl.daedalus.engine.renderer;

import nl.daedalus.engine.events.WindowResizeEvent;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.camera.OrthographicCamera;

public final class Renderer {

    private static RendererBackend backend;

    private static RenderData renderData;

    private static RenderData dynamicRenderData;

    public static void init() {
        backend = RendererBackend.create();
        renderData = new RenderData();
        renderData.setVertexArray(VertexArray.create());
        QuadVertex v1 = new QuadVertex();
        v1.setPosition(new Vec4f(-1.0f, -1.0f, 1.0f, 1.0f));
        v1.setTexCoords(new Vec2f(0.0f, 0.0f));
        QuadVertex v2 = new QuadVertex();
        v2.setPosition(new Vec4f(1.0f, -1.0f, 1.0f, 1.0f));
        v2.setTexCoords(new Vec2f(1.0f, 0.0f));
        QuadVertex v3 = new QuadVertex();
        v3.setPosition(new Vec4f(1.0f, 1.0f, 1.0f, 1.0f));
        v3.setTexCoords(new Vec2f(1.0f, 1.0f));
        QuadVertex v4 = new QuadVertex();
        v4.setPosition(new Vec4f(-1.0f, 1.0f, 1.0f, 1.0f));
        v4.setTexCoords(new Vec2f(0.0f, 1.0f));

//        float[] vertices = {
//                -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
//                 0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f,
//                 0.5f,  0.5f, 0.0f, 1.0f, 1.0f, 1.0f,
//                -0.5f,  0.5f, 0.0f, 1.0f, 0.0f, 1.0f
//        };

        float[] vertices = QuadVertex.concatAll(v1.asFloat(), v2.asFloat(), v3.asFloat(), v4.asFloat());

        VertexBuffer vertexBuffer = VertexBuffer.create(vertices);
        BufferLayout layout = new BufferLayout();
        layout.addElement("a_position", Shader.Datatype.FLOAT4);
        layout.addElement("a_texcoord", Shader.Datatype.FLOAT2);
        vertexBuffer.setLayout(layout);
        renderData.getVertexArray().addVertexBuffer(vertexBuffer);

        int[] squareIndices = {0,1,2,2,3,0};
        IndexBuffer squareIndexBuffer = IndexBuffer.create(squareIndices);
        renderData.getVertexArray().addIndexBuffer(squareIndexBuffer);
        renderData.setShader(Shader.create("shaders/Texture.glsl"));


        /// ------------ dynamic test --------------- ///
        dynamicRenderData = new RenderData();
        dynamicRenderData.setVertexArray(VertexArray.create());
        dynamicRenderData.setVertexBuffer(VertexBuffer.create(RenderData.maxVertices * (Shader.Datatype.FLOAT4.getSize() + Shader.Datatype.FLOAT4.getSize() + Shader.Datatype.FLOAT2.getSize())));
        BufferLayout dynamicLayout = new BufferLayout();
        dynamicLayout.addElement("a_position", Shader.Datatype.FLOAT4);
        dynamicLayout.addElement("a_texcoord", Shader.Datatype.FLOAT2);
        dynamicRenderData.getVertexBuffer().setLayout(dynamicLayout);
        dynamicRenderData.getVertexArray().addVertexBuffer(dynamicRenderData.getVertexBuffer());

        int[] dynamicIndexes = new int[RenderData.maxIndices];
        int offset = 0;
        for (int i = 0; i < RenderData.maxIndices; i+=6) {
            dynamicIndexes[i + 0] = offset + 0;
            dynamicIndexes[i + 1] = offset + 1;
            dynamicIndexes[i + 2] = offset + 2;

            dynamicIndexes[i + 3] = offset + 2;
            dynamicIndexes[i + 4] = offset + 3;
            dynamicIndexes[i + 5] = offset + 0;

            offset += 4;
        }
        IndexBuffer dynamicIndexBuffer = IndexBuffer.create(dynamicIndexes);
        dynamicRenderData.getVertexArray().addIndexBuffer(dynamicIndexBuffer);

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

        ///--- dynamic draw ---///
        dynamicRenderData.quadIndexCount = 0;
        dynamicRenderData.quadCount = 0;
    }

    public static void end() {
        // draw everything
        float[] verts = QuadVertex.concatAll(dynamicRenderData.quadVertices[0].asFloat(),
                dynamicRenderData.quadVertices[1].asFloat(),
                dynamicRenderData.quadVertices[2].asFloat(),
                dynamicRenderData.quadVertices[3].asFloat());
        // set the data
        float[] allVerts = new float[0];
        for (int i = 0; i < dynamicRenderData.quadCount * 4; i++) { // gather all the floats
            allVerts = QuadVertex.concatAll(allVerts, dynamicRenderData.quadVertices[i].asFloat());
        }

        dynamicRenderData.getVertexBuffer().setData(verts);
        dynamicRenderData.getVertexArray().bind();
        backend.drawIndexed(dynamicRenderData.getVertexArray(), dynamicRenderData.quadIndexCount);
    }

    public static void drawQuad(float x, float y, Mat4f scale, float rotation, Texture texture) {
        drawQuad(new Vec3f(x, y, 0.0f), scale, rotation, texture);
    }

    public static void drawQuad(Vec3f position, Mat4f scale, float rotation, Texture texture) {
        renderData.getShader().bind();
        texture.bind(0);
        Mat4f transform = Mat4f.translate(position).multiply(scale);

//        renderData.getVertexArray().bind();
        //backend.drawIndexed(renderData.getVertexArray(), 0);
//        dynamicRenderData.getVertexArray().bind();
//        backend.drawIndexed(dynamicRenderData.getVertexArray(), 6);

        Vec2f[] texCoords = {
                new Vec2f(0.0f, 0.0f),
                new Vec2f(1.0f, 0.0f),
                new Vec2f(1.0f, 1.0f),
                new Vec2f(0.0f, 1.0f)
        };

        // create some quads
        for (int i = 0; i < 4; i++) {
            QuadVertex vertex = new QuadVertex(); //TODO maken we teveel garbage hier? Misschien is een default initialized quadvertex array een beter idee.
            dynamicRenderData.quadVertices[i] = vertex; // Init
            dynamicRenderData.quadVertices[i].setPosition(transform.multiply(dynamicRenderData.quadVertexPositions[i]));
            dynamicRenderData.quadVertices[i].setTexCoords(texCoords[i]);
        }

        dynamicRenderData.quadIndexCount += 6;
        dynamicRenderData.quadCount++;

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
        backend.drawIndexed(vertexArray, 0);
    }

    public static void onWindowResize(WindowResizeEvent e) {
        backend.setViewport(0, 0, e.getWidth(), e.getHeight());
    }

}
