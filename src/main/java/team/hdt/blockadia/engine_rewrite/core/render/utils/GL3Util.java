package team.hdt.blockadia.engine_rewrite.core.render.utils;

import org.lwjgl.opengl.EXTFramebufferBlit;
import org.lwjgl.opengl.EXTFramebufferMultisample;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTTextureArray;

import java.nio.IntBuffer;

public class GL3Util {


    public void glBlitFramebufferEXT(final int srcX0, final int srcY0, final int srcX1, final int srcY1,
                                     final int dstX0, final int dstY0, final int dstX1, final int dstY1, final int mask,
                                     final int filter) {
        EXTFramebufferBlit.glBlitFramebufferEXT(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
    }


    public void glRenderbufferStorageMultisampleEXT(final int target, final int samples, final int internalFormat,
                                                    final int width, final int height) {
        EXTFramebufferMultisample.glRenderbufferStorageMultisampleEXT(target, samples, internalFormat, width, height);
    }


    public void glBindFramebufferEXT(final int target, final int frameBuffer) {
        EXTFramebufferObject.glBindFramebufferEXT(target, frameBuffer);
    }


    public void glBindRenderbufferEXT(final int target, final int renderBuffer) {
        EXTFramebufferObject.glBindRenderbufferEXT(target, renderBuffer);
    }


    public int glCheckFramebufferStatusEXT(final int target) {
        return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);
    }


    public void glDeleteFramebuffersEXT(final IntBuffer frameBuffers) {

        EXTFramebufferObject.glDeleteFramebuffersEXT(frameBuffers);
    }


    public void glDeleteRenderbuffersEXT(final IntBuffer renderBuffers) {

        EXTFramebufferObject.glDeleteRenderbuffersEXT(renderBuffers);
    }


    public void glFramebufferRenderbufferEXT(final int target, final int attachment, final int renderBufferTarget,
                                             final int renderBuffer) {
        EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderBufferTarget, renderBuffer);
    }


    public void glFramebufferTexture2DEXT(final int target, final int attachment, final int texTarget,
                                          final int texture, final int level) {
        EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, texTarget, texture, level);
    }


    public void glGenFramebuffersEXT(final IntBuffer frameBuffers) {

        EXTFramebufferObject.glGenFramebuffersEXT(frameBuffers);
    }


    public void glGenRenderbuffersEXT(final IntBuffer renderBuffers) {

        EXTFramebufferObject.glGenRenderbuffersEXT(renderBuffers);
    }


    public void glGenerateMipmapEXT(final int target) {
        EXTFramebufferObject.glGenerateMipmapEXT(target);
    }


    public void glRenderbufferStorageEXT(final int target, final int internalFormat, final int width,
                                         final int height) {
        EXTFramebufferObject.glRenderbufferStorageEXT(target, internalFormat, width, height);
    }


    public void glFramebufferTextureLayerEXT(final int target, final int attachment, final int texture, final int level,
                                             final int layer) {
        EXTTextureArray.glFramebufferTextureLayerEXT(target, attachment, texture, level, layer);
    }


}
