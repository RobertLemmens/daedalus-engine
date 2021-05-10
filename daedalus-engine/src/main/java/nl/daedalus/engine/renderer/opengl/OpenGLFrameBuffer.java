package nl.daedalus.engine.renderer.opengl;

import nl.daedalus.engine.core.DaedalusLogger;
import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.renderer.FrameBuffer;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL42.glTexStorage2D;
import static org.lwjgl.opengl.GL45.glCreateTextures;

public class OpenGLFrameBuffer extends FrameBuffer {

    private int id;
    private Vec2f size;
    private int maxSize = 8192;
    private int colorAttachment = 0;
    private int depthAttachment = 0;

    public OpenGLFrameBuffer(Vec2f size) {
        this.size = size;
        invalidate();
    }

    @Override
    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        glViewport(0, 0, (int)size.x(), (int)size.y());
    }

    @Override
    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void dispose() {
       glDeleteFramebuffers(id);
       glDeleteTextures(colorAttachment);
       glDeleteTextures(depthAttachment);
    }

    @Override
    public void resize(int width, int height) {
       resize(new Vec2f(width, height));
    }

    @Override
    public void resize(Vec2f size) {
        if (size.x() <= 0 || size.y() <= 0 || size.x() > maxSize || size.y() > maxSize) {
            DaedalusLogger.warn("Attempted to resize framebuffer out of allowed bounds: (" + size.x() + "," + size.y() + ")");
            return;
        }
        this.size = size;
        invalidate();
    }

    @Override
    public int getColorAttachmentId() {
        return 0;
    }

    @Override
    public Vec2f getSize() {
        return size;
    }

    public void invalidate() {
        if (id != 0) {
            dispose();
        }
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);

        colorAttachment = glCreateTextures(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, colorAttachment);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, (int)size.x(), (int)size.y(), 0, GL_RGBA, GL_UNSIGNED_BYTE, (double[]) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorAttachment, 0);

        depthAttachment = glCreateTextures(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, depthAttachment);
        glTexStorage2D(GL_TEXTURE_2D, 1, GL_DEPTH24_STENCIL8, (int) size.x(), (int) size.y());
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_TEXTURE_2D, depthAttachment, 0);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            DaedalusLogger.error("Framebuffer is incorrect!");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);


    }
}
