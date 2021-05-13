package nl.daedalus.engine.scene.components;

public class CollisionComponent extends Component{

    private final static String NAME = CollisionComponent.class.getName();

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onUpdate(float dt) {
    }
}
