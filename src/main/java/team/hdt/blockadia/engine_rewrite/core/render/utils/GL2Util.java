package team.hdt.blockadia.engine_rewrite.core.render.utils;

import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GL2Util {


    public void glBufferData(final int target, final IntBuffer data, final int usage) {

        GL15.glBufferData(target, data, usage);
    }


    public void glBufferSubData(final int target, final long offset, final IntBuffer data) {

        GL15.glBufferSubData(target, offset, data);
    }


    public void glDrawArraysInstancedARB(final int mode, final int first, final int count, final int primCount) {
        ARBDrawInstanced.glDrawArraysInstancedARB(mode, first, count, primCount);
    }


    public void glDrawBuffers(final IntBuffer bufs) {

        GL20.glDrawBuffers(bufs);
    }


    public void glDrawElementsInstancedARB(final int mode, final int indicesCount, final int type,
                                           final long indicesBufferOffset, final int primCount) {
        ARBDrawInstanced.glDrawElementsInstancedARB(mode, indicesCount, type, indicesBufferOffset, primCount);
    }


    public void glGetMultisample(final int pname, final int index, final FloatBuffer val) {

        ARBTextureMultisample.glGetMultisamplefv(pname, index, val);
    }


    public void glTexImage2DMultisample(final int target, final int samples, final int internalFormat, final int width,
                                        final int height, final boolean fixedSampleLocations) {
        ARBTextureMultisample.glTexImage2DMultisample(target, samples, internalFormat, width, height, fixedSampleLocations);
    }


    public void glVertexAttribDivisorARB(final int index, final int divisor) {
        ARBInstancedArrays.glVertexAttribDivisorARB(index, divisor);
    }


    public Object glFenceSync(final int condition, final int flags) {
        return ARBSync.glFenceSync(condition, flags);
    }


    public int glClientWaitSync(final Object sync, final int flags, final long timeout) {
        return ARBSync.glClientWaitSync((Long) sync, flags, timeout);
    }


    public void glDeleteSync(final Object sync) {
        ARBSync.glDeleteSync((Long) sync);
    }
}