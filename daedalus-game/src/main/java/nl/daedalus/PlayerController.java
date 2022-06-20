package nl.daedalus;

import nl.daedalus.engine.core.DaedalusLogger;
import nl.daedalus.engine.scene.Script;

public class PlayerController extends Script {

    @Override
    public void onCreate() {
        DaedalusLogger.info("Created a playercontroller!");
    }

    @Override
    public void onDispose() {

    }

    @Override
    public void onUpdate(float dt) {

    }
}
