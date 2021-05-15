package nl.daedalus.engine.audio.openal;

import nl.daedalus.engine.audio.Sound;
import nl.daedalus.engine.math.Vec3f;

import static org.lwjgl.openal.AL10.*;

// Represents something that can emit sound
public class AudioSource extends Sound {

    private int id;
    private Vec3f position;

    public AudioSource(boolean loop, boolean relative) {
        id = alGenSources();

        if (loop) {
            alSourcei(id, AL_LOOPING, AL_TRUE);
        }

        if(relative) {
            alSourcei(id, AL_SOURCE_RELATIVE, AL_TRUE);
        }
    }

    public void play() {
        alSourcePlay(id);
    }

    public void stop() {
        alSourceStop(id);
    }

    public void pause() {
        alSourcePause(id);
    }

    public boolean isPlaying() {
        return alGetSourcei(id, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void setPosition(Vec3f position) {
        this.position = position;
        alSource3f(id, AL_POSITION, position.x(), position.y(), position.z());
    }

    @Override
    public Vec3f getPosition() {
        return position;
    }

    public void setSpeed(Vec3f speed) {
        alSource3f(id, AL_VELOCITY, speed.x(), speed.y(), speed.z());
    }

    public void setGain(float gain) {
        alSourcef(id, AL_GAIN, gain);
    }

    public void setBuffer(int bufferId) {
        stop();
        alSourcei(id, AL_BUFFER, bufferId);
    }

    @Override
    public void dispose() {
        stop();
        alDeleteSources(id);
    }

}
