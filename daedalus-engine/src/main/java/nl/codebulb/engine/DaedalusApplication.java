package nl.codebulb.engine;

import nl.codebulb.engine.math.Vector4f;
import nl.codebulb.engine.renderer.*;
import org.lwjgl.glfw.GLFWErrorCallback;


import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public abstract class DaedalusApplication {

    // handle naar native window
    private long window;
    private DaedalusLoop daedalusLoop;
    private RendererContext rendererContext;

    protected void run(DaedalusLoop daedalusLoop) {
        DaedalusOptions daedalusOptions = new DaedalusOptions(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, Constants.TITLE);
        run(daedalusLoop,daedalusOptions);
    }

    protected void run(DaedalusLoop daedalusLoop, DaedalusOptions options) {
        this.daedalusLoop = daedalusLoop;
        if (daedalusLoop == null) {
            DaedalusLogger.error("Daedalusloop is null. Please provide a valid class implementing DaedalusLoop in the run method.\nExitting...");
            System.exit(1);
        }
        if (options == null) {
            DaedalusLogger.error("DaedalusOptions is null. Please provide a valid DaedalusOptions class in the run method.\nExitting...");
            System.exit(1);
        }

        init(options);
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void initGLFW(DaedalusOptions options) {
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        /// Create the window
        window = glfwCreateWindow(options.width(), options.height(), options.title(), NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
    }

    private void init(DaedalusOptions options) {
        DaedalusLogger.info("Initializing Daedalus");

        // Initialize window with glfw
        initGLFW(options);

        // init opengl context
        rendererContext = RendererContext.create(window);
        rendererContext.setVsync(true);

        // Make the window visible
        glfwShowWindow(window); // seems to be the default, but we'll add it anyway
    }

    private void loop() {
        Renderer.init();
        Renderer.setClearColor(new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));

        Shader shader2 = new Shader("shaders/ColorShader.glsl");
        shader2.link();

        // Bind vertex array
        VertexArray vertexArray = VertexArray.create();

        // setup vertex buffer
        float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f};
        VertexBuffer vertexBuffer = VertexBuffer.create(vertices);
        vertexArray.addVertexBuffer(vertexBuffer);

        // setup index buffer
        int[] indices = {0,1,2};
        IndexBuffer indexBuffer = IndexBuffer.create(indices);
        vertexArray.addIndexBuffer(indexBuffer);

        while ( !glfwWindowShouldClose(window) ) {
            Renderer.clear();
            daedalusLoop.onUpdate();

            Renderer.begin();

            shader2.bind();
            Renderer.draw(vertexArray);

            Renderer.end();
            rendererContext.swapBuffers();
            glfwPollEvents();
        }
    }

}
