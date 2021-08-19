package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.scene.Entity;
import nl.daedalus.engine.scene.ScriptableEntity;

import java.lang.reflect.InvocationTargetException;

public class ScriptComponent extends Component {

    private static final String NAME = "ScriptComponent";

    private ScriptableEntity scriptableEntity;
    private boolean initialized = false;
    Class<? extends ScriptableEntity> t;

    public ScriptComponent(Class<? extends ScriptableEntity> t) {
        this.t = t;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public <T extends ScriptableEntity> void init(T t) {
        this.scriptableEntity = t;
        initialized = true;
    }

    public <T extends ScriptableEntity> void init()  {
        try {
            this.scriptableEntity = t.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        initialized = true;
    }

    public void setEntity(Entity entity) {
        scriptableEntity.setEntity(entity);
    }

    public void onCreate() {
        scriptableEntity.onCreate();
    }

    public void onDispose() {
        scriptableEntity.onDispose();
    }

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void onUpdate(float dt) {
        scriptableEntity.onUpdate(dt);
    }
}
