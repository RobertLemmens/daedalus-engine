package nl.daedalus.engine.audio.openal;

import nl.daedalus.engine.core.DaedalusLogger;
import nl.daedalus.engine.exceptions.InitException;
import nl.daedalus.engine.math.Vec3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.ALC_MONO_SOURCES;
import static org.lwjgl.openal.ALC11.ALC_STEREO_SOURCES;
import static org.lwjgl.system.MemoryUtil.NULL;

public class OpenALAudioBackend {

    private long device;
    private long context;
    private AudioListener audioListener;
    private List<AudioBuffer> buffers;
    private Map<String, AudioSource> sourceMap;

    public OpenALAudioBackend() {
        buffers = new ArrayList<>();
        sourceMap = new HashMap<>();
        // get camera?
    }

    public void init() {
        device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) {
            throw new InitException("Unable to open OpenAL device");
        }
        ALCCapabilities capabilities = ALC.createCapabilities(device);
        context = alcCreateContext(device, (IntBuffer) null);
        if(context == NULL) {
            throw new InitException("Unable to create OpenAL context");
        }
        alcMakeContextCurrent(context);
        AL.createCapabilities(capabilities);
        audioListener = new AudioListener(new Vec3f(0.0f));

        DaedalusLogger.info("ALC Frequency " + alcGetInteger(device, ALC_FREQUENCY));
        DaedalusLogger.info("ALC Mono Sources " + alcGetInteger(device, ALC_MONO_SOURCES));
        DaedalusLogger.info("ALC Stereo Sources " + alcGetInteger(device, ALC_STEREO_SOURCES));
        int major = alcGetInteger(device, ALC_MAJOR_VERSION);
        int minor = alcGetInteger(device, ALC_MINOR_VERSION);
        DaedalusLogger.info("ALC Version " + major + "." + minor);

    }

    public int createBuffer(String path) {
        AudioBuffer buffer = new AudioBuffer(path);
        buffers.add(buffer);
        return buffer.getId();
    }

    public void createSource(String name) {
        AudioSource source = new AudioSource(false, false);
        sourceMap.put(name, source);
    }

    public void createSource(String name, int bufferId) {
        AudioSource source = new AudioSource(false, false);
        source.setBuffer(bufferId);
        sourceMap.put(name, source);
    }

    public AudioSource getSource(String name) {
        return sourceMap.get(name);
    }

    public List<AudioBuffer> getBuffers() {
        return buffers;
    }

    public Map<String, AudioSource> getSourceMap() {
        return sourceMap;
    }

    public void dispose() {
        alcCloseDevice(device);
    }
}
