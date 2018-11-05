package team.hdt.blockadia.engine_rewrite.core.render.utils;


import org.lwjgl.opengl.GL30;

import java.nio.IntBuffer;


public class GL4Util {

    public void glBlitFramebufferEXT(final int srcX0, final int srcY0, final int srcX1, final int srcY1,
                                     final int dstX0, final int dstY0, final int dstX1, final int dstY1, final int mask,
                                     final int filter) {
        GL30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
    }


    public void glRenderbufferStorageMultisampleEXT(final int target, final int samples, final int internalFormat,
                                                    final int width, final int height) {
        GL30.glRenderbufferStorageMultisample(target, samples, internalFormat, width, height);
    }


    public void glBindFramebufferEXT(final int target, final int frameBuffer) {
        GL30.glBindFramebuffer(target, frameBuffer);
    }


    public void glBindRenderbufferEXT(final int target, final int renderBuffer) {
        GL30.glBindRenderbuffer(target, renderBuffer);
    }


    public int glCheckFramebufferStatusEXT(final int target) {
        return GL30.glCheckFramebufferStatus(target);
    }


    public void glDeleteFramebuffersEXT(final IntBuffer frameBuffers) {

        GL30.glDeleteFramebuffers(frameBuffers);
    }


    public void glDeleteRenderbuffersEXT(final IntBuffer renderBuffers) {

        GL30.glDeleteRenderbuffers(renderBuffers);
    }


    public void glFramebufferRenderbufferEXT(final int target, final int attachment, final int renderBufferTarget,
                                             final int renderBuffer) {
        GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
    }


    public void glFramebufferTexture2DEXT(final int target, final int attachment, final int texTarget,
                                          final int texture, final int level) {
        GL30.glFramebufferTexture2D(target, attachment, texTarget, texture, level);
    }


    public void glGenFramebuffersEXT(final IntBuffer frameBuffers) {

        GL30.glGenFramebuffers(frameBuffers);
    }


    public void glGenRenderbuffersEXT(final IntBuffer renderBuffers) {

        GL30.glGenRenderbuffers(renderBuffers);
    }


    public void glGenerateMipmapEXT(final int target) {
        GL30.glGenerateMipmap(target);
    }


    public void glRenderbufferStorageEXT(final int target, final int internalFormat, final int width,
                                         final int height) {
        GL30.glRenderbufferStorage(target, internalFormat, width, height);
    }


    public void glFramebufferTextureLayerEXT(final int target, final int attachment, final int texture, final int level,
                                             final int layer) {
        GL30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
    }
}
