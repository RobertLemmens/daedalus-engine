package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.renderer.texture.SubTexture;

public class SpriteComponent extends Component{

    private static final String NAME = "SpriteComponent";

    private SubTexture texture;

    public SpriteComponent(SubTexture texture) {
        this.texture = texture;
    }

    public SubTexture getTexture() {
        return texture;
    }

    public void setTexture(SubTexture texture) {
        this.texture = texture;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onUpdate(float dt) {

    }
}
