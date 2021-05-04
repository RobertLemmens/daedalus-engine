package nl.codebulb.engine;

import static org.lwjgl.glfw.GLFW.*;

public class DaedalusInput {

    public static boolean isKeyPressed(int keycode) {
        int state = glfwGetKey(DaedalusApplication.getWindow(), keycode);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

}
