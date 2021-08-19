package nl.daedalus.engine.audio;

import nl.daedalus.engine.audio.openal.AudioBuffer;

public class AudioManager {

    private static AudioBackend audioBackend;

    public static void init() {
        audioBackend = AudioBackend.create();
        audioBackend.init();
    }

    public static Sound loadSound(String name, String path) {
        int soundId = audioBackend.createBuffer(path);
        audioBackend.createSource(name, soundId);
        return audioBackend.getSource(name);
    }

    public static void disposeAll() {
        audioBackend.getBuffers().forEach(AudioBuffer::dispose);
        audioBackend.getSourceMap().forEach((k,v) -> v.dispose());
        audioBackend.dispose();
    }

}
