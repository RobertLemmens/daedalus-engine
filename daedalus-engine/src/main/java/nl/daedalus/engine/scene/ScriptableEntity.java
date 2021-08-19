package nl.daedalus.engine.scene;

import nl.daedalus.engine.scene.components.Component;

public abstract class ScriptableEntity {

    private Entity entity;

    public <T extends Component> T getComponent(Class<T> t) {
        return entity.getComponent(t);
    }

    public void setEntity(Entity e) {
        this.entity = entity;
    }

    public abstract void onCreate();
    public abstract void onDispose();
    public abstract void onUpdate(float dt);

}
