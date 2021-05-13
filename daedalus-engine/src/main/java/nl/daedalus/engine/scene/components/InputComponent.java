package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.util.InputProcessor;

public class InputComponent extends Component  {

    private InputProcessor processor;

    public InputComponent(InputProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void onUpdate(float dt) {
        processor.onInput();
    }

    @Override
    public String getName() {
        return null;
    }

}
