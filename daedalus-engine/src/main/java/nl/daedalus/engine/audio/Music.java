package nl.daedalus.engine.audio;

import nl.daedalus.engine.util.Disposable;

/**
 * Wrapper for sounds that are music.
 * Sets some defaults, like looping and provides some convenience for handling music
 */
public class Music implements Disposable {

    private int id;
    private float playbackPosition;
    private boolean looping;
    private boolean playing;
    private float volume;
    private Sound sound;

    public Music(Sound sound) {
        this.sound = sound;
    }

    public Sound getSound() {
        return sound;
    }

    public void play() {
        playbackPosition = 0;
        sound.play();
    }

    @Override
    public void dispose() {
        if (sound != null) {
            sound.dispose();
        }
    }

    public static Music fromSound(Sound sound) {
        return new Music(sound);
    }
}
