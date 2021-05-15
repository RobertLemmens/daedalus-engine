package nl.daedalus.engine.input;

import nl.daedalus.engine.core.DaedalusApplication;
import nl.daedalus.engine.math.Vec2f;

import static org.lwjgl.glfw.GLFW.*;

public class DaedalusInput {

    public static boolean isKeyPressed(int keycode) {
        int state = glfwGetKey(DaedalusApplication.getWindow(), keycode);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

    public static boolean isKeyReleased(int keycode) {
        int state = glfwGetKey(DaedalusApplication.getWindow(), keycode);
        return state == GLFW_RELEASE;
    }

    public static boolean isMouseButtonPressed(int button) {
        int state = glfwGetMouseButton(DaedalusApplication.getWindow(), button);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

    public static Vec2f getMousePosition() {
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        glfwGetCursorPos(DaedalusApplication.getWindow(), xpos, ypos);
        return new Vec2f((float) xpos[0], (float) ypos[0]);
    }

}
