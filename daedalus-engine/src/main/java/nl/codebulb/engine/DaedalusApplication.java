package nl.codebulb.engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

public abstract class DaedalusApplication {

    // handle naar native window
    private long window;
    private DaedalusLoop daedalusLoop;

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

    private void init(DaedalusOptions options) {
        DaedalusLogger.info("Initializing openGL");
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Create the window
        window = glfwCreateWindow(options.width(), options.height(), options.title(), NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        Shader shader = new Shader();
        shader.attachVertexShader("", "");
        shader.link();

        // Bind vertex array
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Bind vertex buffer
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f};

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();

        glBufferData(GL_ARRAY_BUFFER, buffer , GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Bind index buffer
        int indexbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexbo);
        int[] indices = {0,1,2};
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glBindVertexArray(0);
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            daedalusLoop.onUpdate();
            shader.bind();
            glBindVertexArray(vaoID);
            glEnableVertexAttribArray(0);
            glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);
            glDisableVertexAttribArray(0);
//
            glBindVertexArray(0);
            Shader.unbind();
            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();
        }
    }

}
