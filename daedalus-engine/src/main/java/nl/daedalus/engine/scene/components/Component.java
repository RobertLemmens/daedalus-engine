package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.util.Updatable;

public abstract class Component implements Updatable {
    public abstract String getName();

    public boolean equals(Component other) {
        if (other == null) return false;
        return getName().equals(other.getName());
    }
}
