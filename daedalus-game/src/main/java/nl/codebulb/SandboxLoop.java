package nl.codebulb;

import nl.codebulb.engine.DaedalusInput;
import nl.codebulb.engine.DaedalusLoop;
import nl.codebulb.engine.Shader;
import nl.codebulb.engine.math.Mat4f;
import nl.codebulb.engine.math.Vec3f;
import nl.codebulb.engine.renderer.*;

import static nl.codebulb.engine.KeyCodes.*;

public class SandboxLoop implements DaedalusLoop {

    private OrthographicCamera orthographicCamera;
    private Vec3f cameraPosition;
    private VertexArray vertexArray;
    private Shader shader;

    @Override
    public void onInit() {
        orthographicCamera = new OrthographicCamera(-1.6f, 1.6f, -0.9f, 0.9f);
        cameraPosition = new Vec3f(1.0f);

        shader = new Shader("shaders/ColorShader.glsl");
        shader.link();

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
    }

    @Override
    public void onUpdate() {
        if (DaedalusInput.isKeyPressed(DAE_KEY_UP) || DaedalusInput.isKeyPressed(DAE_KEY_W)) {
            cameraPosition = cameraPosition.addY(0.1f);
        } else if (DaedalusInput.isKeyPressed(DAE_KEY_DOWN) || DaedalusInput.isKeyPressed(DAE_KEY_S)) {
            cameraPosition = cameraPosition.subtractY(0.1f);
        }
        if (DaedalusInput.isKeyPressed(DAE_KEY_LEFT) || DaedalusInput.isKeyPressed(DAE_KEY_A)) {
            cameraPosition = cameraPosition.subtractX(0.1f);
        } else if (DaedalusInput.isKeyPressed(DAE_KEY_RIGHT) || DaedalusInput.isKeyPressed(DAE_KEY_D)) {
            cameraPosition = cameraPosition.addX(0.1f);
        }

        orthographicCamera.setPosition(cameraPosition);
        Renderer.begin(orthographicCamera);
        Mat4f scale = Mat4f.scale(new Vec3f(0.1f));

        for(int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                Vec3f position = new Vec3f(x * 0.11f, y * 0.11f, 0f);
                Mat4f transform = Mat4f.translate(position).multiply(scale);
                Renderer.draw(vertexArray, shader, transform);
            }
        }

        Renderer.end();
    }

    @Override
    public void onEvent() {

    }
}
