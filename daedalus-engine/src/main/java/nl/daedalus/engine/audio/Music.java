package nl.daedalus.engine.audio;

import nl.daedalus.engine.util.Disposable;

public class Music implements Disposable {

    private int id;
    private float playbackPosition;
    private boolean looping;
    private boolean playing;
    private float volume;

    @Override
    public void dispose() {

    }
}
