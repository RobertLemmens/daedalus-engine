package nl.daedalus.engine.core;

import nl.daedalus.engine.events.*;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWScrollCallback;


import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public abstract class DaedalusApplication {

    // handle naar native window
    private static long window;
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

        // maybe clean up other stuff aswell

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
            if (action == GLFW_PRESS) {
                KeyPressedEvent e = new KeyPressedEvent(key);
                daedalusLoop.onEvent(e);
            }
            if (action == GLFW_RELEASE) {
                KeyReleasedEvent e = new KeyReleasedEvent(key);
                daedalusLoop.onEvent(e);
            }
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        glfwSetMouseButtonCallback(window, (window, button, action, mod) -> {
            if (action == GLFW_PRESS) {
                MousePressedEvent e = new MousePressedEvent(button);
                daedalusLoop.onEvent(e);
            }
        });

        glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
            MouseScrolledEvent e = new MouseScrolledEvent((float)xoffset, (float)yoffset);
            daedalusLoop.onEvent(e);
        });

        glfwSetWindowSizeCallback(window, (window, width, height) -> {
            WindowResizeEvent e = new WindowResizeEvent(width, height);
            daedalusLoop.onEvent(e);
            onWindowResize(e);
        });
    }

    private void init(DaedalusOptions options) {
        DaedalusLogger.info("Initializing Daedalus");

        // Initialize window with glfw
        initGLFW(options);

        // init opengl context
        rendererContext = RendererContext.create(window);
        rendererContext.setVsync(Constants.VSYNC);

        // Init daedalus loop
        daedalusLoop.onInit();

        // Make the window visible
        glfwShowWindow(window); // seems to be the default, but we'll add it anyway
    }

    private void loop() {
        Renderer.init();
        Renderer.setClearColor(new Vec4f(0.2f, 0.2f, 0.2f, 0.0f));

        float lastTimeFrame = 0;
        while ( !glfwWindowShouldClose(window) ) {
            float time = (float)glfwGetTime(); //TODO temp
            float currentTime = time - lastTimeFrame;
            lastTimeFrame = time;

            Renderer.clear();

            daedalusLoop.onUpdate(currentTime);

            rendererContext.swapBuffers();
            glfwPollEvents();
        }
    }

    public static long getWindow() {
        return window;
    }

    public void onWindowResize(WindowResizeEvent e) {
        Renderer.onWindowResize(e);
    }

}
