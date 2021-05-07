package nl.daedalus.engine.renderer.data;

import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.math.Vec4f;

public class QuadVertex {

    private Vec3f position;
    private Vec4f color;
    private Vec2f texCoord;
    float texIndex;
    float tilingFactor;

    public QuadVertex() {

    }

    public Vec3f getPosition() {
        return position;
    }

    public void setPosition(Vec3f position) {
        this.position = position;
    }

    public Vec4f getColor() {
        return color;
    }

    public void setColor(Vec4f color) {
        this.color = color;
    }

    public Vec2f getTexCoord() {
        return texCoord;
    }

    public void setTexCoord(Vec2f texCoord) {
        this.texCoord = texCoord;
    }

    public float getTexIndex() {
        return texIndex;
    }

    public void setTexIndex(float texIndex) {
        this.texIndex = texIndex;
    }

    public float getTilingFactor() {
        return tilingFactor;
    }

    public void setTilingFactor(float tilingFactor) {
        this.tilingFactor = tilingFactor;
    }
}
