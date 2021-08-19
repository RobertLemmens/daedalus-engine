package nl.daedalus.engine.audio;

import nl.daedalus.engine.audio.openal.AudioBuffer;
import nl.daedalus.engine.audio.openal.AudioSource;
import nl.daedalus.engine.audio.openal.OpenALAudioBackend;
import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.util.Disposable;

import java.util.List;
import java.util.Map;

public abstract class AudioBackend implements Disposable {

    public enum Backends {
        NONE, OPENAL
    }

    public static AudioBackend create() {
        return switch (Constants.AUDIO_BACKEND) {
            case OPENAL -> new OpenALAudioBackend();
            case NONE -> null;
        };
    }

    //TODO extra indirection for Audiobuffers and AudioSources
    public abstract void init();
    public abstract int createBuffer(String buffer);
    public abstract void createSource(String name, int bufferId);
    public abstract AudioSource getSource(String name);
    public abstract List<AudioBuffer> getBuffers();
    public abstract Map<String, AudioSource> getSourceMap();
}
