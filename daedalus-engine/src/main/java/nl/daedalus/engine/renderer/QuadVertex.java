package nl.daedalus.engine.renderer;

import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.math.Vec4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class QuadVertex {
    private Vec4f position; // X, Y en Z voor depth testing
    private Vec4f color;
    private Vec2f texCoords;
    private float texIndex;
    private float texTilingFactor;

    public float getTexTilingFactor() {
        return texTilingFactor;
    }

    public void setTexTilingFactor(float texTilingFactor) {
        this.texTilingFactor = texTilingFactor;
    }

    public QuadVertex(){}

    public Vec4f getPosition() {
        return position;
    }

    public void setPosition(Vec4f position) {
        this.position = position;
    }

    public Vec4f getColor() {
        return color;
    }

    public void setColor(Vec4f color) {
        this.color = color;
    }

    public Vec2f getTexCoords() {
        return texCoords;
    }

    public float getTexIndex() {
        return texIndex;
    }

    public void setTexIndex(float texIndex) {
        this.texIndex = texIndex;
    }

    public void setTexCoords(Vec2f texCoords) {
        this.texCoords = texCoords;
    }

    public float[] asFloat() {
        return new float[] {
                position.r(), position.g(), position.b(), position.a(),
                texCoords.x(), texCoords.y(),
                texIndex, texTilingFactor,
                color.r(), color.g(), color.b(), color.a()
        };

    }

    public FloatBuffer toBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(6);
        buffer.put(position.asFloats());
        buffer.put(texCoords.asFloat());
        buffer.flip();
        return buffer;
    }

    public static float[] concatAll(float[] first, float[]... rest) {
        int totalLength = first.length;
        for (float[] array : rest) {
            totalLength += array.length;
        }
        float[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (float[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
