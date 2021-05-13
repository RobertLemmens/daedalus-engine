package nl.daedalus.engine.scene;


import nl.daedalus.engine.scene.components.Component;
import nl.daedalus.engine.util.Bag;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private int id;
    private String name;
    private Bag<Component> components;
    private List<Component> componentList;

    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
        componentList = new ArrayList<>();
        components = new Bag<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public Entity add(Component component) { //TODO notify system nodig voor onAdd en onRemove, dan kunnen we hier interactie op krijgen
        addInternal(component);
        return this;
    }

    private boolean addInternal(Component component) {
        Class<? extends Component> componentClass = component.getClass();
        Component old = getComponent(componentClass);

        if (component.equals(old)) {
            return false;
        }

        int index = EntityRegistry.getForComponent(componentClass);
        components.set(index, component);
        componentList.add(component);
        return true;
    }

    public <T extends Component> T get(int index) {
        return (T)components.get(index);
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        int index = EntityRegistry.getForComponent(componentClass);

        if (index < components.getCapacity()) {
            return (T) components.get(index);
        } else {
            return null;
        }
    }

    public List<Component> getComponents() {
        return componentList;
    }

}
