package nl.daedalus.engine.core;

import nl.daedalus.engine.audio.AudioBackend;
import nl.daedalus.engine.renderer.RendererBackend;

public final class Constants {

    public static final String TITLE = "Daedalus Engine";
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final boolean VSYNC = true;
    public static final RendererBackend.Backends BACKEND = RendererBackend.Backends.OPENGL;
    public static final AudioBackend.Backends AUDIO_BACKEND = AudioBackend.Backends.OPENAL;

}
