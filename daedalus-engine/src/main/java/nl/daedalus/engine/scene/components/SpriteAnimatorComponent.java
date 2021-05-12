package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.core.DaedalusLogger;
import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.renderer.texture.SubTexture;

public class SpriteAnimatorComponent extends Component {

    public enum AnimationDirection {
        RIGHT_TO_LEFT, LEFT_TO_RIGHT, TOP_TO_BOTTOM, BOTTOM_TO_TOP
    }

    private SubTexture[][] spriteSheet;
    private static final String NAME = "AnimatedSpriteComponent";
    private AnimationDirection animationDirection = AnimationDirection.LEFT_TO_RIGHT;
    private Vec2f activeTexture;
    private float animationsPerSecond = 4.0f;
    private float accum = 0;
    private SpriteComponent spriteComponent;

    public SpriteAnimatorComponent(SubTexture[][] spriteSheet, SpriteComponent spriteComponent) {
        this.spriteComponent = spriteComponent;
        this.spriteSheet = spriteSheet;
        activeTexture = new Vec2f(0, 0);
    }

    @Override
    public void onUpdate(float dt) {
        accum += dt;
        if (accum > 1.0f / animationsPerSecond) {
            nextFrame();
            accum = 0;
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    public void setupAnimation(AnimationDirection direction) {
        if (this.spriteSheet.length <= 1 && this.spriteSheet[0].length <= 1) {
            DaedalusLogger.error("There arent enough frames in the supplied spritesheet to animate");
        }
        this.animationDirection = direction;
    }

    public void setAnimationsPerSecond(float amount) {
        this.animationsPerSecond = amount;
    }

    public void nextFrame() {
        switch (animationDirection) {
            case RIGHT_TO_LEFT -> {
            }
            case LEFT_TO_RIGHT -> {
                if (activeTexture.x() < spriteSheet[0].length - 1) {
                    activeTexture = new Vec2f(activeTexture.x() + 1, activeTexture.y());
                } else {
                    activeTexture = new Vec2f(0, activeTexture.y());
                }
                spriteComponent.setTexture(spriteSheet[(int)activeTexture.y()][(int)activeTexture.x()]);
            }
            case TOP_TO_BOTTOM -> {
                if (activeTexture.y() < spriteSheet.length - 1) {
                    activeTexture = new Vec2f(activeTexture.x(), activeTexture.y() + 1);
                } else {
                    activeTexture = new Vec2f(activeTexture.x(), 0);
                }
                spriteComponent.setTexture(spriteSheet[(int)activeTexture.y()][(int)activeTexture.x()]);
            }
            case BOTTOM_TO_TOP -> {
            }
        }
    }
}
