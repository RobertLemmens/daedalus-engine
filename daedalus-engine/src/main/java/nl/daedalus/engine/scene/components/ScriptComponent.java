package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.scene.Entity;
import nl.daedalus.engine.scene.Script;
import nl.daedalus.engine.util.Creatable;

import java.lang.reflect.InvocationTargetException;

public class ScriptComponent extends Component implements Creatable {

    private static final String NAME = "ScriptComponent";

    private Script script;
    private boolean initialized = false;
    Class<? extends Script> t;

    public ScriptComponent(Class<? extends Script> t) {
        this.t = t;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public <T extends Script> void init(T t) {
        this.script = t;
        initialized = true;
    }

    public <T extends Script> void init()  {
        try {
            this.script = t.getDeclaredConstructor().newInstance();
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
        script.setEntity(entity);
    }

    public void onCreate() {
        script.onCreate();
    }

    public void onDispose() {
        script.onDispose();
    }

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void onUpdate(float dt) {
        script.onUpdate(dt);
    }
}
