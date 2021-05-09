package nl.daedalus.engine.renderer;

import nl.daedalus.engine.events.WindowResizeEvent;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.camera.OrthographicCamera;
import nl.daedalus.engine.renderer.texture.SubTexture;
import nl.daedalus.engine.renderer.texture.Texture;

public final class Renderer {

    private static RendererBackend backend;
    private static RenderData dynamicRenderData;
    private static Vec4f noTint = new Vec4f(1.0f, 1.0f, 1.0f, 1.0f);

    public static void init() {
        backend = RendererBackend.create();
        dynamicRenderData = new RenderData();
        dynamicRenderData.setVertexArray(VertexArray.create());
        dynamicRenderData.setVertexBuffer(VertexBuffer.create(RenderData.maxVertices *
                (
                        Shader.Datatype.FLOAT4.getSize() +
                                Shader.Datatype.FLOAT4.getSize() +
                                Shader.Datatype.FLOAT2.getSize() +
                                Shader.Datatype.FLOAT.getSize() +
                                Shader.Datatype.FLOAT.getSize()
                )));
        BufferLayout dynamicLayout = new BufferLayout();
        dynamicLayout.addElement("a_position", Shader.Datatype.FLOAT4);
        dynamicLayout.addElement("a_texcoord", Shader.Datatype.FLOAT2);
        dynamicLayout.addElement("a_texindex", Shader.Datatype.FLOAT);
        dynamicLayout.addElement("a_textilingfactor", Shader.Datatype.FLOAT);
        dynamicLayout.addElement("a_color", Shader.Datatype.FLOAT4);
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

    public static void begin(OrthographicCamera orthographicCamera) {
        dynamicRenderData.getShader().bind();
        dynamicRenderData.getShader().uploadUniformMat4("u_view_projection", orthographicCamera.getViewProjectionMatrix());

        // Reset renderdata
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

    public static void drawQuad(float x, float y, float xScale, float yScale, SubTexture subTexture) {
        drawQuad(x, y, xScale, yScale, subTexture, 1, noTint);
    }

    public static void drawQuad(float x, float y, float xScale, float yScale, SubTexture subTexture, int tilingFactor, Vec4f tint) {
        drawQuad(new Vec3f(x, y, 0.1f), Mat4f.scale(new Vec3f(xScale, yScale, 1.0f)), subTexture, tilingFactor, tint);
    }

    /**
     * Draw a quad with a subtexture
     *
     * @param position
     * @param scale
     * @param subTexture
     * @param tilingFactor
     * @param tint
     */
    public static void drawQuad(Vec3f position, Mat4f scale, SubTexture subTexture, int tilingFactor, Vec4f tint) {
        Mat4f transform = Mat4f.translate(position).multiply(scale);
        drawQuad(transform, subTexture, tilingFactor, tint);
    }

    public static void drawQuad(Mat4f transform, SubTexture subTexture, int tilingFactor, Vec4f tint) {
        Texture texture = subTexture.getTexture();
        Vec2f[] texCoords = subTexture.getTexCoords();

        int textureIndex = 0; // we beginnen op 1 met echte textures. 0 is wit.

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

        drawQuad(transform, texCoords, textureIndex, tilingFactor, tint);

    }

    public static void drawRotatedQuad(float x, float y, float rotation, Mat4f scale, Texture texture, int tilingFactor, Vec4f tint) {
        drawRotatedQuad(new Vec3f(x,y, 0.0f), rotation, scale, texture, tilingFactor, tint);
    }

    /**
     * Draw a rotated quad with a texture
     *
     * @param position
     * @param rotation
     * @param scale
     * @param texture
     * @param tilingFactor
     * @param tint
     */
    public static void drawRotatedQuad(Vec3f position, float rotation, Mat4f scale, Texture texture, int tilingFactor, Vec4f tint) {
        Mat4f transform = Mat4f.translate(position).multiply(Mat4f.rotate(rotation, new Vec3f(0.0f, 0.0f, 1.0f))).multiply(scale);

        int textureIndex = 0; // we beginnen op 1 met echte textures. 0 is wit.

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

        drawQuad(transform, dynamicRenderData.defaultTexCoords, textureIndex, tilingFactor, tint);
    }

    public static void drawQuad(float x, float y, float xScale, float yScale, Texture texture, int tilingFactor, Vec4f tint) {
        drawQuad(new Vec3f(x, y, 0.0f), Mat4f.scale(new Vec3f(xScale, yScale, 1.0f)), texture, tilingFactor, tint);
    }

    /**
     * Draw a quad with a texture
     *
     * @param position
     * @param scale
     * @param texture
     * @param tilingFactor
     * @param tint
     */
    public static void drawQuad(Vec3f position, Mat4f scale, Texture texture, int tilingFactor, Vec4f tint) {
        Mat4f transform = Mat4f.translate(position).multiply(scale);

        int textureIndex = 0; // we beginnen op 1 met echte textures. 0 is wit.

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

        drawQuad(transform, dynamicRenderData.defaultTexCoords, textureIndex, tilingFactor, tint);
    }

    public static void drawRotatedQuad(float x, float y, float rotation, float xScale, float yScale, Vec4f color) {
       drawRotatedQuad(new Vec3f(x, y, 0.0f), rotation, Mat4f.scale(new Vec3f(xScale, yScale, 1.0f)), color);
    }

    /**
     * Draw a rotated quad with a colour
     *
     * @param position
     * @param rotation
     * @param scale
     * @param color
     */
    public static void drawRotatedQuad(Vec3f position, float rotation, Mat4f scale, Vec4f color) {
        Mat4f transform = Mat4f.translate(position).multiply(Mat4f.rotate(rotation, new Vec3f(0.0f, 0.0f, 1.0f))).multiply(scale);

        int textureIndex = 0;
        int tilingFactor = 1;

        drawQuad(transform, dynamicRenderData.defaultTexCoords, textureIndex, tilingFactor, color);
    }

    public static void drawQuad(float x, float y, float xScale, float yScale, Vec4f color) {
        drawQuad(new Vec3f(x, y, 0.0f), Mat4f.scale(new Vec3f(xScale, yScale, 1.0f)), color);
    }

    /**
     * Draw a quad with a colour
     *
     * @param position
     * @param scale
     * @param color
     */
    public static void drawQuad(Vec3f position, Mat4f scale, Vec4f color) {
        Mat4f transform = Mat4f.translate(position).multiply(scale);

       int textureIndex = 0;
       int tilingFactor = 1;

       drawQuad(transform, dynamicRenderData.defaultTexCoords, textureIndex, tilingFactor, color);
    }


    /**
     * Create vertices and add to vertice array, draw in end();
     *
     * @param transform
     * @param texCoords
     * @param textureIndex
     * @param tilingFactor
     * @param color
     */
    private static void drawQuad(Mat4f transform, Vec2f[] texCoords, int textureIndex, int tilingFactor, Vec4f color) {
        // create some quads
        for (int i = 0 ; i < 4; i++) {
            QuadVertex vertex = new QuadVertex();
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)] = vertex; // Init
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)].setPosition(transform.multiply(dynamicRenderData.quadVertexPositions[i]));
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)].setTexCoords(texCoords[i]);
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)].setTexIndex(textureIndex); // TODO int ipv float?
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)].setTexTilingFactor(tilingFactor); // TODO int ipv float?
            dynamicRenderData.quadVertices[i + (dynamicRenderData.quadCount * 4)].setColor(color);
        }

        dynamicRenderData.quadIndexCount += 6;
        dynamicRenderData.quadCount++;
    }

    public static void onWindowResize(WindowResizeEvent e) {
        backend.setViewport(0, 0, e.getWidth(), e.getHeight());
    }

}
