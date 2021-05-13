package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.util.Updatable;

//TODO im not sure where i want to go with this yet. Maybe a FreeformComponent
public class MovementComponent extends Component{

    private static final String NAME = "MovementComponent";
    private TransformComponent transformComponent;
    private Updatable updatable;

    public MovementComponent(Updatable updatable) {
        this.updatable = updatable;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onUpdate(float dt) {
        updatable.onUpdate(dt);
    }
}
