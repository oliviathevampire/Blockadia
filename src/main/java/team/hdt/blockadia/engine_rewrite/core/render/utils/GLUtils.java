package team.hdt.blockadia.engine_rewrite.core.render.utils;

import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class GLUtils {


    public void resetStats() {
    }


    public void glActiveTexture(final int texture) {
        GL13.glActiveTexture(texture);
    }


    public void glAlphaFunc(final int func, final float ref) {
        GL11.glAlphaFunc(func, ref);
    }


    public void glAttachShader(final int program, final int shader) {
        GL20.glAttachShader(program, shader);
    }


    public void glBeginQuery(final int target, final int query) {
        GL15.glBeginQuery(target, query);
    }


    public void glBindBuffer(final int target, final int buffer) {
        GL15.glBindBuffer(target, buffer);
    }


    public void glBindTexture(final int target, final int texture) {
        GL11.glBindTexture(target, texture);
    }


    public void glBlendEquationSeparate(final int colorMode, final int alphaMode) {
        GL20.glBlendEquationSeparate(colorMode, alphaMode);
    }


    public void glBlendFunc(final int sfactor, final int dfactor) {
        GL11.glBlendFunc(sfactor, dfactor);
    }


    public void glBlendFuncSeparate(final int sfactorRGB, final int dfactorRGB, final int sfactorAlpha,
                                    final int dfactorAlpha) {
        GL14.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
    }


    public void glBufferData(final int target, final long dataSize, final int usage) {
        GL15.glBufferData(target, dataSize, usage);
    }


    public void glBufferData(final int target, final FloatBuffer data, final int usage) {

        GL15.glBufferData(target, data, usage);
    }


    public void glBufferData(final int target, final ShortBuffer data, final int usage) {

        GL15.glBufferData(target, data, usage);
    }


    public void glBufferData(final int target, final ByteBuffer data, final int usage) {

        GL15.glBufferData(target, data, usage);
    }


    public void glBufferSubData(final int target, final long offset, final FloatBuffer data) {

        GL15.glBufferSubData(target, offset, data);
    }


    public void glBufferSubData(final int target, final long offset, final ShortBuffer data) {

        GL15.glBufferSubData(target, offset, data);
    }


    public void glBufferSubData(final int target, final long offset, final ByteBuffer data) {

        GL15.glBufferSubData(target, offset, data);
    }


    public void glClear(final int mask) {
        GL11.glClear(mask);
    }


    public void glClearColor(final float red, final float green, final float blue, final float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }


    public void glColorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        GL11.glColorMask(red, green, blue, alpha);
    }


    public void glCompileShader(final int shader) {
        GL20.glCompileShader(shader);
    }


    public void glCompressedTexImage2D(final int target, final int level, final int internalFormat, final int width,
                                       final int height, final int border, final ByteBuffer data) {

        GL13.glCompressedTexImage2D(target, level, internalFormat, width, height, border, data);
    }


    public void glCompressedTexImage3D(final int target, final int level, final int internalFormat, final int width,
                                       final int height, final int depth, final int border, final ByteBuffer data) {

        GL13.glCompressedTexImage3D(target, level, internalFormat, width, height, depth, border, data);
    }


    public void glCompressedTexSubImage2D(final int target, final int level, final int xoffset, final int yoffset,
                                          final int width, final int height, final int format, final ByteBuffer data) {

        GL13.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, data);
    }


    public void glCompressedTexSubImage3D(final int target, final int level, final int xoffset, final int yoffset,
                                          final int zoffset, final int width, final int height, final int depth,
                                          final int format, final ByteBuffer data) {

        GL13.glCompressedTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, data);
    }


    public int glCreateProgram() {
        return GL20.glCreateProgram();
    }


    public int glCreateShader(final int shaderType) {
        return GL20.glCreateShader(shaderType);
    }


    public void glCullFace(final int mode) {
        GL11.glCullFace(mode);
    }


    public void glDeleteBuffers(final IntBuffer buffers) {

        GL15.glDeleteBuffers(buffers);
    }


    public void glDeleteProgram(final int program) {
        GL20.glDeleteProgram(program);
    }


    public void glDeleteShader(final int shader) {
        GL20.glDeleteShader(shader);
    }


    public void glDeleteTextures(final IntBuffer textures) {

        GL11.glDeleteTextures(textures);
    }


    public void glDepthFunc(final int func) {
        GL11.glDepthFunc(func);
    }


    public void glDepthMask(final boolean flag) {
        GL11.glDepthMask(flag);
    }


    public void glDepthRange(final double nearVal, final double farVal) {
        GL11.glDepthRange(nearVal, farVal);
    }


    public void glDetachShader(final int program, final int shader) {
        GL20.glDetachShader(program, shader);
    }


    public void glDisable(final int cap) {
        GL11.glDisable(cap);
    }


    public void glDisableVertexAttribArray(final int index) {
        GL20.glDisableVertexAttribArray(index);
    }


    public void glDrawArrays(final int mode, final int first, final int count) {
        GL11.glDrawArrays(mode, first, count);
    }


    public void glDrawBuffer(final int mode) {
        GL11.glDrawBuffer(mode);
    }


    public void glDrawRangeElements(final int mode, final int start, final int end, final int count, final int type,
                                    final long indices) {
        GL12.glDrawRangeElements(mode, start, end, count, type, indices);
    }


    public void glEnable(final int cap) {
        GL11.glEnable(cap);
    }


    public void glEnableVertexAttribArray(final int index) {
        GL20.glEnableVertexAttribArray(index);
    }


    public void glEndQuery(final int target) {
        GL15.glEndQuery(target);
    }


    public void glGenBuffers(final IntBuffer buffers) {

        GL15.glGenBuffers(buffers);
    }


    public void glGenTextures(final IntBuffer textures) {

        GL11.glGenTextures(textures);
    }


    public void glGenQueries(final int num, final IntBuffer ids) {
        GL15.glGenQueries(ids);
    }


    public void glGetBoolean(final int pname, final ByteBuffer params) {

        GL11.glGetBooleanv(pname, params);
    }


    public void glGetBufferSubData(final int target, final long offset, final ByteBuffer data) {

        GL15.glGetBufferSubData(target, offset, data);
    }


    public int glGetError() {
        return GL11.glGetError();
    }


    public void glGetInteger(final int pname, final IntBuffer params) {

        GL11.glGetIntegerv(pname, params);
    }


    public void glGetProgram(final int program, final int pname, final IntBuffer params) {

        GL20.glGetProgramiv(program, pname, params);
    }


    public void glGetShader(final int shader, final int pname, final IntBuffer params) {

        GL20.glGetShaderiv(shader, pname, params);
    }


    public String glGetString(final int name) {
        return GL11.glGetString(name);
    }


    public String glGetString(final int name, final int index) {
        return GL30.glGetStringi(name, index);
    }


    public boolean glIsEnabled(final int cap) {
        return GL11.glIsEnabled(cap);
    }


    public void glLineWidth(final float width) {
        GL11.glLineWidth(width);
    }


    public void glLinkProgram(final int program) {
        GL20.glLinkProgram(program);
    }


    public void glPixelStorei(final int pname, final int param) {
        GL11.glPixelStorei(pname, param);
    }


    public void glPointSize(final float size) {
        GL11.glPointSize(size);
    }


    public void glPolygonMode(final int face, final int mode) {
        GL11.glPolygonMode(face, mode);
    }


    public void glPolygonOffset(final float factor, final float units) {
        GL11.glPolygonOffset(factor, units);
    }


    public void glReadBuffer(final int mode) {
        GL11.glReadBuffer(mode);
    }


    public void glReadPixels(final int x, final int y, final int width, final int height, final int format,
                             final int type, final ByteBuffer data) {

        GL11.glReadPixels(x, y, width, height, format, type, data);
    }


    public void glReadPixels(final int x, final int y, final int width, final int height, final int format,
                             final int type, final long offset) {
        GL11.glReadPixels(x, y, width, height, format, type, offset);
    }


    public void glScissor(final int x, final int y, final int width, final int height) {
        GL11.glScissor(x, y, width, height);
    }


    public void glStencilFuncSeparate(final int face, final int func, final int ref, final int mask) {
        GL20.glStencilFuncSeparate(face, func, ref, mask);
    }


    public void glStencilOpSeparate(final int face, final int sfail, final int dpfail, final int dppass) {
        GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
    }


    public void glTexImage2D(final int target, final int level, final int internalFormat, final int width,
                             final int height, final int border, final int format, final int type,
                             final ByteBuffer data) {

        GL11.glTexImage2D(target, level, internalFormat, width, height, border, format, type, data);
    }


    public void glTexImage3D(final int target, final int level, final int internalFormat, final int width,
                             final int height, final int depth, final int border, final int format, final int type,
                             final ByteBuffer data) {

        GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, data);
    }


    public void glTexParameterf(final int target, final int pname, final float param) {
        GL11.glTexParameterf(target, pname, param);
    }


    public void glTexParameteri(final int target, final int pname, final int param) {
        GL11.glTexParameteri(target, pname, param);
    }


    public void glTexSubImage2D(final int target, final int level, final int xoffset, final int yoffset,
                                final int width, final int height, final int format, final int type,
                                final ByteBuffer data) {

        GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, data);
    }


    public void glTexSubImage3D(final int target, final int level, final int xoffset, final int yoffset,
                                final int zoffset, final int width, final int height, final int depth, final int format,
                                final int type, final ByteBuffer data) {

        GL12.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
    }


    public void glUniform1(final int location, final FloatBuffer value) {

        GL20.glUniform1fv(location, value);
    }


    public void glUniform1(final int location, final IntBuffer value) {

        GL20.glUniform1iv(location, value);
    }


    public void glUniform1f(final int location, final float v0) {
        GL20.glUniform1f(location, v0);
    }


    public void glUniform1i(final int location, final int v0) {
        GL20.glUniform1i(location, v0);
    }


    public void glUniform2(final int location, final IntBuffer value) {

        GL20.glUniform2iv(location, value);
    }


    public void glUniform2(final int location, final FloatBuffer value) {

        GL20.glUniform2fv(location, value);
    }


    public void glUniform2f(final int location, final float v0, final float v1) {
        GL20.glUniform2f(location, v0, v1);
    }


    public void glUniform3(final int location, final IntBuffer value) {

        GL20.glUniform3iv(location, value);
    }


    public void glUniform3(final int location, final FloatBuffer value) {

        GL20.glUniform3fv(location, value);
    }


    public void glUniform3f(final int location, final float v0, final float v1, final float v2) {
        GL20.glUniform3f(location, v0, v1, v2);
    }


    public void glUniform4(final int location, final FloatBuffer value) {

        GL20.glUniform4fv(location, value);
    }


    public void glUniform4(final int location, final IntBuffer value) {

        GL20.glUniform4iv(location, value);
    }


    public void glUniform4f(final int location, final float v0, final float v1, final float v2, final float v3) {
        GL20.glUniform4f(location, v0, v1, v2, v3);
    }


    public void glUniformMatrix3(final int location, final boolean transpose, final FloatBuffer value) {

        GL20.glUniformMatrix3fv(location, transpose, value);
    }


    public void glUniformMatrix4(final int location, final boolean transpose, final FloatBuffer value) {

        GL20.glUniformMatrix4fv(location, transpose, value);
    }


    public void glUseProgram(final int program) {
        GL20.glUseProgram(program);
    }


    public void glVertexAttribPointer(final int index, final int size, final int type, final boolean normalized,
                                      final int stride, final long pointer) {
        GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }


    public void glViewport(final int x, final int y, final int width, final int height) {
        GL11.glViewport(x, y, width, height);
    }


    public int glGetAttribLocation(final int program, final String name) {
        // NOTE: LWJGL requires null-terminated strings
        return GL20.glGetAttribLocation(program, name + "\0");
    }


    public int glGetUniformLocation(final int program, final String name) {
        // NOTE: LWJGL requires null-terminated strings
        return GL20.glGetUniformLocation(program, name + "\0");
    }


    public void glShaderSource(final int shader, final String[] strings, final IntBuffer length) {

        GL20.glShaderSource(shader, strings);
    }


    public String glGetProgramInfoLog(final int program, final int maxSize) {
        return GL20.glGetProgramInfoLog(program, maxSize);
    }


    public long glGetQueryObjectui64(int query, int target) {
        return ARBTimerQuery.glGetQueryObjectui64(query, target);
    }


    public int glGetQueryObjectiv(int query, int pname) {
        return GL15.glGetQueryObjecti(query, pname);
    }

    public String glGetShaderInfoLog(int shader, int maxSize) {
        return GL20.glGetShaderInfoLog(shader, maxSize);
    }


    public void glBindFragDataLocation(final int program, final int colorNumber, final String name) {
        GL30.glBindFragDataLocation(program, colorNumber, name);
    }


    public void glBindVertexArray(final int array) {
        GL30.glBindVertexArray(array);
    }


    public void glGenVertexArrays(final IntBuffer arrays) {

        GL30.glGenVertexArrays(arrays);
    }


    public void glPatchParameter(final int count) {
        GL40.glPatchParameteri(GL40.GL_PATCH_VERTICES, count);
    }

    public int glGetProgramResourceIndex(final int program, final int programInterface, final String name) {
        return GL43.glGetProgramResourceIndex(program, programInterface, name);
    }

    public void glShaderStorageBlockBinding(final int program, final int storageBlockIndex, final int storageBlockBinding) {
        GL43.glShaderStorageBlockBinding(program, storageBlockIndex, storageBlockBinding);
    }

    public void glDeleteVertexArrays(final IntBuffer arrays) {

        ARBVertexArrayObject.glDeleteVertexArrays(arrays);
    }

    public int glGetUniformBlockIndex(final int program, final String uniformBlockName) {
        return GL31.glGetUniformBlockIndex(program, uniformBlockName);
    }

    public void glBindBufferBase(final int target, final int index, final int buffer) {
        GL30.glBindBufferBase(target, index, buffer);
    }


    public void glUniformBlockBinding(final int program, final int uniformBlockIndex, final int uniformBlockBinding) {
        GL31.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
    }
}
