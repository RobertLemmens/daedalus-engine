package nl.daedalus.engine.renderer;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.renderer.opengl.OpenGLShader;

public abstract class Shader {

    public static Shader create(String shaderSrc) {
        return switch (Constants.BACKEND) {
            case NONE -> null;
            case OPENGL -> new OpenGLShader(shaderSrc);
            case VULKAN -> null;
        };
    }

    public enum Datatype {
        FLOAT(4), FLOAT2(8), FLOAT3(12), FLOAT4(16),
        MAT3(36), MAT4(48),
        INT(4), INT2(8), INT3(12), INT4(16),
        BOOL(1);

        private final int size;

        Datatype(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    public abstract void link();
    public abstract void bind();
    public abstract void unbind();
    public abstract void uploadUniformMat4(String name, Mat4f transform);

}
