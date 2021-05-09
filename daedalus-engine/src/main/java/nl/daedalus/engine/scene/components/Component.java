package nl.daedalus.engine.scene.components;

public abstract class Component {
    public abstract String getName();

    public abstract void onUpdate(float dt);

    public boolean equals(Component other) {
        if (other == null) return false;
        return getName().equals(other.getName());
    }
}
