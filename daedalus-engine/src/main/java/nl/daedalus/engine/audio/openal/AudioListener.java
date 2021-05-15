package nl.daedalus.engine.audio.openal;

import nl.daedalus.engine.math.Vec3f;

import static org.lwjgl.openal.AL10.*;

public class AudioListener {

    public AudioListener(Vec3f position) {
        alListener3f(AL_POSITION, position.x(), position.y(), position.z());
        alListener3f(AL_VELOCITY, 0 ,0 ,0);
    }

    public void setSpeed(Vec3f speed) {
        alListener3f(AL_VELOCITY, speed.x(), speed.y(), speed.z());
    }

    public void setPosition(Vec3f position) {
        alListener3f(AL_POSITION, position.x(), position.y(), position.z());
    }

    public void setOrientation(Vec3f at, Vec3f up) {
        float[] orientationData = new float[6];
        orientationData[0] = at.x();
        orientationData[1] = at.y();
        orientationData[2] = at.z();
        orientationData[3] = up.x();
        orientationData[4] = up.y();
        orientationData[5] = up.z();
        alListenerfv(AL_ORIENTATION, orientationData);
    }

}
