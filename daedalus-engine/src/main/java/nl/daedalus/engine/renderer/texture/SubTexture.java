package nl.daedalus.engine.renderer.texture;

import nl.daedalus.engine.math.Vec2f;

public class SubTexture {
    private Texture texture;
    private Vec2f[] texCoords = new Vec2f[4];

    public SubTexture(Texture texture, Vec2f min, Vec2f max) {
        this.texture = texture;
        texCoords[0] = new Vec2f(min.x(), min.y());
        texCoords[1] = new Vec2f(max.x(), min.y());
        texCoords[2] = new Vec2f(max.x(), max.y());
        texCoords[3] = new Vec2f(min.x(), max.y());
    }

    public static SubTexture fromCoords(Texture texture, Vec2f coords, Vec2f cellSize, Vec2f spriteSize) {
        Vec2f min = new Vec2f((coords.x() * cellSize.x()) / texture.getWidth(), (coords.y() * cellSize.y()) / texture.getHeight() );
        Vec2f max = new Vec2f((coords.x() + spriteSize.x()) * cellSize.x() / texture.getWidth(), (coords.y() + spriteSize.y()) * cellSize.y() / texture.getHeight());
        return new SubTexture(texture, min, max);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vec2f[] getTexCoords() {
        return texCoords;
    }

    public void setTexCoords(Vec2f[] texCoords) {
        this.texCoords = texCoords;
    }
}
