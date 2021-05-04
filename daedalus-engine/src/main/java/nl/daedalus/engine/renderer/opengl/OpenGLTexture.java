package nl.daedalus.engine.renderer.opengl;

import nl.daedalus.engine.core.DaedalusLogger;
import nl.daedalus.engine.renderer.Texture;
import nl.daedalus.engine.util.FileUtils;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.*;


public class OpenGLTexture extends Texture {

    private int id;
    private int width;
    private int height;

    public OpenGLTexture(String path) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer fileBuffer = null;
        try {
            fileBuffer = FileUtils.ioResourceToByteBuffer(path, 8 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteBuffer fileData = stbi_load_from_memory(fileBuffer, width, height, channels, 0);
        if (fileData == null) {
            DaedalusLogger.error("Could not load texture from file");
        }

        this.width = width.get();
        this.height = height.get();

        int internalFormat = 0;
        int dataFormat = 0;
        if (channels.get() == 3) {
            internalFormat = GL_RGB8;
            dataFormat = GL_RGB;
        } else if (channels.get() == 4) {
            internalFormat = GL_RGBA8;
            dataFormat = GL_RGBA;
        } else {
            // something went wrong....
            DaedalusLogger.error("Could not determine texture internal format");
        }

        id = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(id, 1, internalFormat, this.width, this.height);
        glTextureParameteri(id, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTextureParameteri(id, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTextureSubImage2D(id, 0, 0, 0, this.width, this.height, dataFormat, GL_UNSIGNED_BYTE, fileData);

        stbi_image_free(fileData);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void bind(int slot) {
        glBindTextureUnit(slot, id);
    }

    @Override
    public void unbind() {

    }
}
