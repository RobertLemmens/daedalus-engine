package nl.daedalus;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.core.DaedalusLoop;
import nl.daedalus.engine.renderer.Shader;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.renderer.*;
import nl.daedalus.engine.renderer.camera.OrthographicCameraController;

public class SandboxLoop implements DaedalusLoop {

    private OrthographicCameraController cameraController;
    private VertexArray vertexArray;
    private VertexArray squareVertexArray;
    private Shader shader;
    private Shader textureShader;
    private Texture checkerboard;

    @Override
    public void onInit() {

        cameraController = new OrthographicCameraController((float)Constants.WINDOW_WIDTH / (float)Constants.WINDOW_HEIGHT, true);

        shader = Shader.create("shaders/ColorShader.glsl");
        textureShader = Shader.create("shaders/Texture.glsl");
        checkerboard = Texture.create("textures/Checkerboard.png");

        // Bind vertex array
        vertexArray = VertexArray.create();
        // setup vertex buffer
        float[] vertices = {
                -0.5f, -0.5f, 0.0f, 0.8f, 0.2f, 0.8f, 1.0f,
                0.5f, -0.5f, 0.0f, 0.2f, 0.3f, 0.8f, 1.0f,
                0.5f,  0.5f, 0.0f, 0.8f, 0.8f, 0.2f, 1.0f,
                -0.5f,  0.5f, 0.0f, 0.8f, 0.8f, 0.2f, 1.0f
        };
        VertexBuffer vertexBuffer = VertexBuffer.create(vertices);
        BufferLayout vertexBufferLayout = new BufferLayout();
        vertexBufferLayout.addElement("a_position", Shader.Datatype.FLOAT3);
        vertexBufferLayout.addElement("a_color", Shader.Datatype.FLOAT4);
        vertexBuffer.setLayout(vertexBufferLayout);
        vertexArray.addVertexBuffer(vertexBuffer);

        // setup index buffer
        int[] indices = {0,1,2,2,3,0};
        IndexBuffer indexBuffer = IndexBuffer.create(indices);
        vertexArray.addIndexBuffer(indexBuffer);





        // setup square with texcoords
        float[] squareVertices = {
                -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.0f,  1.0f, 0.0f,
                0.5f,  0.5f, 0.0f,  1.0f, 1.0f,
                -0.5f,  0.5f, 0.0f, 0.0f, 1.0f,
        };
        squareVertexArray = VertexArray.create();
        VertexBuffer squareVertexBuffer = VertexBuffer.create(squareVertices);
        BufferLayout squareLayout = new BufferLayout();
        squareLayout.addElement("a_position", Shader.Datatype.FLOAT3);
        squareLayout.addElement("a_texcoords", Shader.Datatype.FLOAT2);
        squareVertexBuffer.setLayout(squareLayout);
        squareVertexArray.addVertexBuffer(squareVertexBuffer);
        // setup square index buffer
        int[] squareIndices = {0,1,2,2,3,0};
        IndexBuffer squareIndexBuffer = IndexBuffer.create(squareIndices);
        squareVertexArray.addIndexBuffer(squareIndexBuffer);

    }

    @Override
    public void onUpdate(float dt) {

        cameraController.onUpdate(dt);

        Renderer.begin(cameraController.getCamera());
        Mat4f scale = Mat4f.scale(new Vec3f(0.1f));

        for(int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                Vec3f position = new Vec3f(x * 0.11f, y * 0.11f, 0f);
                Mat4f transform = Mat4f.translate(position).multiply(scale);
                Renderer.draw(vertexArray, shader, transform);
            }
        }

        checkerboard.bind(0);
        Renderer.draw(squareVertexArray, textureShader, new Mat4f());

        Renderer.end();
    }

    @Override
    public void onEvent() {

    }
}
