package nl.daedalus.engine.audio;

import nl.daedalus.engine.audio.openal.OpenALAudioBackend;

public class AudioBackend {
    public enum Backends {
        NONE, OPENAL
    }


    //TODO generify
    public static OpenALAudioBackend create() {
        return new OpenALAudioBackend();
    }


}
