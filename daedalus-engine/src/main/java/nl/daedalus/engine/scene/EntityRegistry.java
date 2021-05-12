package nl.daedalus.engine.scene;

import nl.daedalus.engine.scene.components.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRegistry {

    // Unique store for component classes. Every entity will store components of Class<?> in the same location
    // which will help our lookup times when we start doing more advanced stuff with our simple ECS and increase entity count
    // Lookups should always go through here. Unregistered components should not exist, which this make sure.
    private static Map<Class<?>, Integer> componentMap = new HashMap<>();
    private static Map<String, Entity> entityMap = new HashMap<>(); //TODO we will want something faster later on

    private static int componentIndex = 0;
    private static int entityIndex = 0;

    public static int getForComponent(Class<?> componentClass) {
         Integer index = componentMap.get(componentClass);
         if (index == null) {
             componentMap.put(componentClass, componentIndex);
             index = componentIndex;
             componentIndex++;
         }
         return index;
    }

    public static Entity createEntity(String name) {
        Entity entity = new Entity(entityIndex, name);
        entityMap.put(name, entity);
        entityIndex++;
        return entity;
    }

    public static Entity getEntity(String name) {
        return entityMap.get(name);
    }

    public static List<Entity> getGroup(Class<?>... className) {
        int[] indexes = new int[className.length];
        for (int i = 0; i < className.length; i++) {
            indexes[0] = getForComponent(className[i]);
        }
        // we weten nu op welke indexes de components leven
        List<Entity> result = new ArrayList<>();
        entityMap.forEach((k,v) -> { //todo we will want something faster
            int counter = 0;
            for(int i = 0; i < indexes.length; i++) {
                if (v.get(indexes[i]) != null)
                    counter++;
            }
            if (counter == indexes.length)
                result.add(v);
        });
        return result;
    }

}
