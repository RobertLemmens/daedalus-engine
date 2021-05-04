package nl.daedalus.engine.input;

import nl.daedalus.engine.core.DaedalusApplication;

import static org.lwjgl.glfw.GLFW.*;

public class DaedalusInput {

    public static boolean isKeyPressed(int keycode) {
        int state = glfwGetKey(DaedalusApplication.getWindow(), keycode);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

}
