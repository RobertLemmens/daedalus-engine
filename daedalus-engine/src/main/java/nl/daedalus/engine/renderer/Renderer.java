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
        dynamicRenderData.setVertexBuffer(VertexBuffer.create(RenderData.maxVertices *
                (
                        Shader.Datatype.FLOAT4.getSize() +
                        Shader.Datatype.FLOAT4.getSize() +
                        Shader.Datatype.FLOAT2.getSize() +
                        Shader.Datatype.FLOAT.getSize()
                )));
        BufferLayout dynamicLayout = new BufferLayout();
        dynamicLayout.addElement("a_position", Shader.Datatype.FLOAT4);
        dynamicLayout.addElement("a_texcoord", Shader.Datatype.FLOAT2);
        dynamicLayout.addElement("a_texindex", Shader.Datatype.FLOAT);
        dynamicLayout.addElement("a_textilingfactor", Shader.Datatype.FLOAT);
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


        int[] textureSamplers = new int[RenderData.maxTextures];
        for (int i = 0; i < RenderData.maxTextures; i++) {
            textureSamplers[i] = i;
        }

        dynamicRenderData.setTexture(Texture.create(1,1));
        int whiteTexture = 0xffffffff;
        dynamicRenderData.getTexture().setData(whiteTexture);

        dynamicRenderData.textures[0] = dynamicRenderData.getTexture();

        dynamicRenderData.setShader(Shader.create("shaders/Texture.glsl"));
        dynamicRenderData.getShader().bind();
        dynamicRenderData.getShader().uploadIntArray("u_textures", textureSamplers);

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

        dynamicRenderData.getShader().bind();
        dynamicRenderData.getShader().uploadUniformMat4("u_view_projection", viewProjectionMatrix);

        dynamicRenderData.quadIndexCount = 0;
        dynamicRenderData.quadCount = 0;

        dynamicRenderData.textureIndex = 1;
    }

    public static void end() {
        // draw everything
        float[] allVerts = new float[0];
        for (int i = 0; i < dynamicRenderData.quadCount * 4; i++) { // gather all the floats
            allVerts = QuadVertex.concatAll(allVerts, dynamicRenderData.quadVertices[i].asFloat());
        }

        // Bind the textures in their correct slots
        for(int i = 0; i < dynamicRenderData.textureIndex; i++) {
            dynamicRenderData.textures[i].bind(i);
        }

        dynamicRenderData.getVertexBuffer().setData(allVerts);
        dynamicRenderData.getVertexArray().bind();
        backend.drawIndexed(dynamicRenderData.getVertexArray(), dynamicRenderData.quadIndexCount);
    }

    public static void drawQuad(float x, float y, Mat4f scale, float rotation, Texture texture, int tilingFactor) {
        drawQuad(new Vec3f(x, y, 0.0f), scale, rotation, texture, tilingFactor);
    }

    public static void drawQuad(Vec3f position, Mat4f scale, float rotation, Texture texture, int tilingFactor) {
        Mat4f transform = Mat4f.translate(position).multiply(scale);

        Vec2f[] texCoords = {
                new Vec2f(0.0f, 0.0f),
                new Vec2f(1.0f, 0.0f),
                new Vec2f(1.0f, 1.0f),
                new Vec2f(0.0f, 1.0f)
        };

        int textureIndex = 0; // we beginnen op 1 met echte textures. 0 is zwart.
        int tilingfactor = tilingFactor;

        for (int i = 0; i < dynamicRenderData.textureIndex; i++) {
            // if texture al bestaat, zet index daarop.
            if (texture.equals(dynamicRenderData.textures[i])) {
                textureIndex = i;
            }
        }

        if (textureIndex == 0) {
            textureIndex = dynamicRenderData.textureIndex;
            dynamicRenderData.textures[textureIndex] = texture;
            dynamicRenderData.textureIndex++;
        }
        // create some quads
        for (int i = 0 ; i < 4; i++) {
            QuadVertex vertex = new QuadVertex(); //TODO maken we teveel garbage hier? Misschien is een default initialized quadvertex array een beter idee.
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)] = vertex; // Init
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)].setPosition(transform.multiply(dynamicRenderData.quadVertexPositions[i]));
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)].setTexCoords(texCoords[i]);
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)].setTexIndex(textureIndex); // TODO int ipv float?
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)].setTexTilingFactor(tilingfactor); // TODO int ipv float?
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

//    public static void drawQuad(VertexArray vertexArray, Shader shader, Mat4f transform) {
//        shader.bind();
//        shader.uploadUniformMat4("u_view_projection", viewProjectionMatrix);
//        shader.uploadUniformMat4("u_transform", transform);
//
//        vertexArray.bind();
//        backend.drawIndexed(vertexArray, 0);
//    }

    public static void onWindowResize(WindowResizeEvent e) {
        backend.setViewport(0, 0, e.getWidth(), e.getHeight());
    }

}
