package nl.daedalus.engine.scene;

import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.Renderer;
import nl.daedalus.engine.scene.components.SpriteComponent;
import nl.daedalus.engine.scene.components.TransformComponent;

public class Scene implements Updatable {

    public Scene() {
    }

    public void onUpdate(float dt) {
        for(Entity e : EntityRegistry.getGroup(TransformComponent.class, SpriteComponent.class)) {
            e.getComponents().forEach(c -> c.onUpdate(dt));
            Renderer.drawQuad(e.getComponent(TransformComponent.class).getTransform(),
                    e.getComponent(SpriteComponent.class).getTexture(), 1, new Vec4f(1.0f));
        }
    }

    public Entity createEntity(String name) {
        return EntityRegistry.createEntity(name);
    }


}
