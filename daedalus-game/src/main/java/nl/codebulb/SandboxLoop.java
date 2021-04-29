package nl.codebulb;

import nl.codebulb.engine.DaedalusLoop;

public class SandboxLoop implements DaedalusLoop {
    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate() {
        System.out.println("Hello from main loop");
    }

    @Override
    public void onEvent() {

    }
}
