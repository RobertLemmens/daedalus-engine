package nl.daedalus.engine.audio;

import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.util.Disposable;

public abstract class Sound implements Disposable {

    public abstract void play();
    public abstract void stop();
    public abstract void pause();
    public abstract void setPosition(Vec3f position);
    public abstract Vec3f getPosition();

}
