//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.client.rendering;

import com.google.common.collect.Maps;
import org.lwjgl.opengl.*;
import team.hdt.blockadia.game_engine_old.common.util.Util;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Locale;
import java.util.Map;

public class OpenGlHelper {
    public static boolean nvidia;
    public static boolean ati;
    public static int GL_FRAMEBUFFER;
    public static int GL_RENDERBUFFER;
    public static int GL_COLOR_ATTACHMENT0;
    public static int GL_DEPTH_ATTACHMENT;
    public static int GL_FRAMEBUFFER_COMPLETE;
    public static int GL_FB_INCOMPLETE_ATTACHMENT;
    public static int GL_FB_INCOMPLETE_MISS_ATTACH;
    public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
    public static int GL_FB_INCOMPLETE_READ_BUFFER;
    private static OpenGlHelper.FboMode framebufferType;
    public static boolean framebufferSupported;
    private static boolean shadersAvailable;
    private static boolean arbShaders;
    public static int GL_LINK_STATUS;
    public static int GL_COMPILE_STATUS;
    public static int GL_VERTEX_SHADER;
    public static int GL_FRAGMENT_SHADER;
    private static boolean arbMultitexture;
    public static int defaultTexUnit;
    public static int lightmapTexUnit;
    public static int GL_TEXTURE2;
    private static boolean arbTextureEnvCombine;
    public static int GL_COMBINE;
    public static int GL_INTERPOLATE;
    public static int GL_PRIMARY_COLOR;
    public static int GL_CONSTANT;
    public static int GL_PREVIOUS;
    public static int GL_COMBINE_RGB;
    public static int GL_SOURCE0_RGB;
    public static int GL_SOURCE1_RGB;
    public static int GL_SOURCE2_RGB;
    public static int GL_OPERAND0_RGB;
    public static int GL_OPERAND1_RGB;
    public static int GL_OPERAND2_RGB;
    public static int GL_COMBINE_ALPHA;
    public static int GL_SOURCE0_ALPHA;
    public static int GL_SOURCE1_ALPHA;
    public static int GL_SOURCE2_ALPHA;
    public static int GL_OPERAND0_ALPHA;
    public static int GL_OPERAND1_ALPHA;
    public static int GL_OPERAND2_ALPHA;
    private static boolean openGL14;
    public static boolean extBlendFuncSeparate;
    public static boolean openGL21;
    public static boolean shadersSupported;
    private static String logText = "";
    private static String cpu;
    public static boolean vboSupported;
    public static boolean vboSupportedAti;
    private static boolean arbVbo;
    public static int GL_ARRAY_BUFFER;
    public static int GL_STATIC_DRAW;
    private static final Map<Integer, String> field_195919_ac =  Util.acceptAndReturn(Maps.newHashMap(), (p_203093_0_) -> {
        p_203093_0_.put(0, "No error");
        p_203093_0_.put(1280, "Enum parameter is invalid for this function");
        p_203093_0_.put(1281, "Parameter is invalid for this function");
        p_203093_0_.put(1282, "Current state is invalid for this function");
        p_203093_0_.put(1283, "Stack overflow");
        p_203093_0_.put(1284, "Stack underflow");
        p_203093_0_.put(1285, "Out of memory");
        p_203093_0_.put(1286, "Operation on incomplete framebuffer");
        p_203093_0_.put(1286, "Operation on incomplete framebuffer");
    });

    public static void initializeTextures() {
        GLCapabilities lvt_0_1_ = GL.getCapabilities();
        arbMultitexture = lvt_0_1_.GL_ARB_multitexture && !lvt_0_1_.OpenGL13;
        arbTextureEnvCombine = lvt_0_1_.GL_ARB_texture_env_combine && !lvt_0_1_.OpenGL13;
        if (arbMultitexture) {
            logText = logText + "Using ARB_multitexture.\n";
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
            GL_TEXTURE2 = 33986;
        } else {
            logText = logText + "Using GL 1.3 multitexturing.\n";
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
            GL_TEXTURE2 = 33986;
        }

        if (arbTextureEnvCombine) {
            logText = logText + "Using ARB_texture_env_combine.\n";
            GL_COMBINE = 34160;
            GL_INTERPOLATE = 34165;
            GL_PRIMARY_COLOR = 34167;
            GL_CONSTANT = 34166;
            GL_PREVIOUS = 34168;
            GL_COMBINE_RGB = 34161;
            GL_SOURCE0_RGB = 34176;
            GL_SOURCE1_RGB = 34177;
            GL_SOURCE2_RGB = 34178;
            GL_OPERAND0_RGB = 34192;
            GL_OPERAND1_RGB = 34193;
            GL_OPERAND2_RGB = 34194;
            GL_COMBINE_ALPHA = 34162;
            GL_SOURCE0_ALPHA = 34184;
            GL_SOURCE1_ALPHA = 34185;
            GL_SOURCE2_ALPHA = 34186;
            GL_OPERAND0_ALPHA = 34200;
            GL_OPERAND1_ALPHA = 34201;
            GL_OPERAND2_ALPHA = 34202;
        } else {
            logText = logText + "Using GL 1.3 texture combiners.\n";
            GL_COMBINE = 34160;
            GL_INTERPOLATE = 34165;
            GL_PRIMARY_COLOR = 34167;
            GL_CONSTANT = 34166;
            GL_PREVIOUS = 34168;
            GL_COMBINE_RGB = 34161;
            GL_SOURCE0_RGB = 34176;
            GL_SOURCE1_RGB = 34177;
            GL_SOURCE2_RGB = 34178;
            GL_OPERAND0_RGB = 34192;
            GL_OPERAND1_RGB = 34193;
            GL_OPERAND2_RGB = 34194;
            GL_COMBINE_ALPHA = 34162;
            GL_SOURCE0_ALPHA = 34184;
            GL_SOURCE1_ALPHA = 34185;
            GL_SOURCE2_ALPHA = 34186;
            GL_OPERAND0_ALPHA = 34200;
            GL_OPERAND1_ALPHA = 34201;
            GL_OPERAND2_ALPHA = 34202;
        }

        extBlendFuncSeparate = lvt_0_1_.GL_EXT_blend_func_separate && !lvt_0_1_.OpenGL14;
        openGL14 = lvt_0_1_.OpenGL14 || lvt_0_1_.GL_EXT_blend_func_separate;
        framebufferSupported = openGL14 && (lvt_0_1_.GL_ARB_framebuffer_object || lvt_0_1_.GL_EXT_framebuffer_object || lvt_0_1_.OpenGL30);
        if (framebufferSupported) {
            logText = logText + "Using framebuffer objects because ";
            if (lvt_0_1_.OpenGL30) {
                logText = logText + "OpenGL 3.0 is supported and separate blending is supported.\n";
                framebufferType = OpenGlHelper.FboMode.BASE;
                GL_FRAMEBUFFER = 36160;
                GL_RENDERBUFFER = 36161;
                GL_COLOR_ATTACHMENT0 = 36064;
                GL_DEPTH_ATTACHMENT = 36096;
                GL_FRAMEBUFFER_COMPLETE = 36053;
                GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            } else if (lvt_0_1_.GL_ARB_framebuffer_object) {
                logText = logText + "ARB_framebuffer_object is supported and separate blending is supported.\n";
                framebufferType = OpenGlHelper.FboMode.ARB;
                GL_FRAMEBUFFER = 36160;
                GL_RENDERBUFFER = 36161;
                GL_COLOR_ATTACHMENT0 = 36064;
                GL_DEPTH_ATTACHMENT = 36096;
                GL_FRAMEBUFFER_COMPLETE = 36053;
                GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            } else if (lvt_0_1_.GL_EXT_framebuffer_object) {
                logText = logText + "EXT_framebuffer_object is supported.\n";
                framebufferType = OpenGlHelper.FboMode.EXT;
                GL_FRAMEBUFFER = 36160;
                GL_RENDERBUFFER = 36161;
                GL_COLOR_ATTACHMENT0 = 36064;
                GL_DEPTH_ATTACHMENT = 36096;
                GL_FRAMEBUFFER_COMPLETE = 36053;
                GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            }
        } else {
            logText = logText + "Not using framebuffer objects because ";
            logText = logText + "OpenGL 1.4 is " + (lvt_0_1_.OpenGL14 ? "" : "not ") + "supported, ";
            logText = logText + "EXT_blend_func_separate is " + (lvt_0_1_.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
            logText = logText + "OpenGL 3.0 is " + (lvt_0_1_.OpenGL30 ? "" : "not ") + "supported, ";
            logText = logText + "ARB_framebuffer_object is " + (lvt_0_1_.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
            logText = logText + "EXT_framebuffer_object is " + (lvt_0_1_.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
        }

        openGL21 = lvt_0_1_.OpenGL21;
        shadersAvailable = openGL21 || lvt_0_1_.GL_ARB_vertex_shader && lvt_0_1_.GL_ARB_fragment_shader && lvt_0_1_.GL_ARB_shader_objects;
        logText = logText + "Shaders are " + (shadersAvailable ? "" : "not ") + "available because ";
        if (shadersAvailable) {
            if (lvt_0_1_.OpenGL21) {
                logText = logText + "OpenGL 2.1 is supported.\n";
                arbShaders = false;
                GL_LINK_STATUS = 35714;
                GL_COMPILE_STATUS = 35713;
                GL_VERTEX_SHADER = 35633;
                GL_FRAGMENT_SHADER = 35632;
            } else {
                logText = logText + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
                arbShaders = true;
                GL_LINK_STATUS = 35714;
                GL_COMPILE_STATUS = 35713;
                GL_VERTEX_SHADER = 35633;
                GL_FRAGMENT_SHADER = 35632;
            }
        } else {
            logText = logText + "OpenGL 2.1 is " + (lvt_0_1_.OpenGL21 ? "" : "not ") + "supported, ";
            logText = logText + "ARB_shader_objects is " + (lvt_0_1_.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
            logText = logText + "ARB_vertex_shader is " + (lvt_0_1_.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
            logText = logText + "ARB_fragment_shader is " + (lvt_0_1_.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
        }

        shadersSupported = framebufferSupported && shadersAvailable;
        String lvt_1_1_ = GL11.glGetString(7936).toLowerCase(Locale.ROOT);
        nvidia = lvt_1_1_.contains("nvidia");
        arbVbo = !lvt_0_1_.OpenGL15 && lvt_0_1_.GL_ARB_vertex_buffer_object;
        vboSupported = lvt_0_1_.OpenGL15 || arbVbo;
        logText = logText + "VBOs are " + (vboSupported ? "" : "not ") + "available because ";
        if (vboSupported) {
            if (arbVbo) {
                logText = logText + "ARB_vertex_buffer_object is supported.\n";
                GL_STATIC_DRAW = 35044;
                GL_ARRAY_BUFFER = 34962;
            } else {
                logText = logText + "OpenGL 1.5 is supported.\n";
                GL_STATIC_DRAW = 35044;
                GL_ARRAY_BUFFER = 34962;
            }
        }

    }

    public static boolean areShadersSupported() {
        return shadersSupported;
    }

    public static String getLogText() {
        return logText;
    }

    public static int glGetProgrami(int p_glGetProgrami_0_, int p_glGetProgrami_1_) {
        return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(p_glGetProgrami_0_, p_glGetProgrami_1_) : GL20.glGetProgrami(p_glGetProgrami_0_, p_glGetProgrami_1_);
    }

    public static void glAttachShader(int p_glAttachShader_0_, int p_glAttachShader_1_) {
        if (arbShaders) {
            ARBShaderObjects.glAttachObjectARB(p_glAttachShader_0_, p_glAttachShader_1_);
        } else {
            GL20.glAttachShader(p_glAttachShader_0_, p_glAttachShader_1_);
        }

    }

    public static void glDeleteShader(int p_glDeleteShader_0_) {
        if (arbShaders) {
            ARBShaderObjects.glDeleteObjectARB(p_glDeleteShader_0_);
        } else {
            GL20.glDeleteShader(p_glDeleteShader_0_);
        }

    }

    public static int glCreateShader(int p_glCreateShader_0_) {
        return arbShaders ? ARBShaderObjects.glCreateShaderObjectARB(p_glCreateShader_0_) : GL20.glCreateShader(p_glCreateShader_0_);
    }

    public static void func_195918_a(int p_195918_0_, CharSequence p_195918_1_) {
        if (arbShaders) {
            ARBShaderObjects.glShaderSourceARB(p_195918_0_, p_195918_1_);
        } else {
            GL20.glShaderSource(p_195918_0_, p_195918_1_);
        }

    }

    public static void glCompileShader(int p_glCompileShader_0_) {
        if (arbShaders) {
            ARBShaderObjects.glCompileShaderARB(p_glCompileShader_0_);
        } else {
            GL20.glCompileShader(p_glCompileShader_0_);
        }

    }

    public static int glGetShaderi(int p_glGetShaderi_0_, int p_glGetShaderi_1_) {
        return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(p_glGetShaderi_0_, p_glGetShaderi_1_) : GL20.glGetShaderi(p_glGetShaderi_0_, p_glGetShaderi_1_);
    }

    public static String glGetShaderInfoLog(int p_glGetShaderInfoLog_0_, int p_glGetShaderInfoLog_1_) {
        return arbShaders ? ARBShaderObjects.glGetInfoLogARB(p_glGetShaderInfoLog_0_, p_glGetShaderInfoLog_1_) : GL20.glGetShaderInfoLog(p_glGetShaderInfoLog_0_, p_glGetShaderInfoLog_1_);
    }

    public static String glGetProgramInfoLog(int p_glGetProgramInfoLog_0_, int p_glGetProgramInfoLog_1_) {
        return arbShaders ? ARBShaderObjects.glGetInfoLogARB(p_glGetProgramInfoLog_0_, p_glGetProgramInfoLog_1_) : GL20.glGetProgramInfoLog(p_glGetProgramInfoLog_0_, p_glGetProgramInfoLog_1_);
    }

    public static void glUseProgram(int p_glUseProgram_0_) {
        if (arbShaders) {
            ARBShaderObjects.glUseProgramObjectARB(p_glUseProgram_0_);
        } else {
            GL20.glUseProgram(p_glUseProgram_0_);
        }

    }

    public static int glCreateProgram() {
        return arbShaders ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
    }

    public static void glDeleteProgram(int p_glDeleteProgram_0_) {
        if (arbShaders) {
            ARBShaderObjects.glDeleteObjectARB(p_glDeleteProgram_0_);
        } else {
            GL20.glDeleteProgram(p_glDeleteProgram_0_);
        }

    }

    public static void glLinkProgram(int p_glLinkProgram_0_) {
        if (arbShaders) {
            ARBShaderObjects.glLinkProgramARB(p_glLinkProgram_0_);
        } else {
            GL20.glLinkProgram(p_glLinkProgram_0_);
        }

    }

    public static int glGetUniformLocation(int p_glGetUniformLocation_0_, CharSequence p_glGetUniformLocation_1_) {
        return arbShaders ? ARBShaderObjects.glGetUniformLocationARB(p_glGetUniformLocation_0_, p_glGetUniformLocation_1_) : GL20.glGetUniformLocation(p_glGetUniformLocation_0_, p_glGetUniformLocation_1_);
    }

    public static void glUniform1(int p_glUniform1_0_, IntBuffer p_glUniform1_1_) {
        if (arbShaders) {
            ARBShaderObjects.glUniform1ivARB(p_glUniform1_0_, p_glUniform1_1_);
        } else {
            GL20.glUniform1iv(p_glUniform1_0_, p_glUniform1_1_);
        }

    }

    public static void glUniform1i(int p_glUniform1i_0_, int p_glUniform1i_1_) {
        if (arbShaders) {
            ARBShaderObjects.glUniform1iARB(p_glUniform1i_0_, p_glUniform1i_1_);
        } else {
            GL20.glUniform1i(p_glUniform1i_0_, p_glUniform1i_1_);
        }

    }

    public static void glUniform1(int p_glUniform1_0_, FloatBuffer p_glUniform1_1_) {
        if (arbShaders) {
            ARBShaderObjects.glUniform1fvARB(p_glUniform1_0_, p_glUniform1_1_);
        } else {
            GL20.glUniform1fv(p_glUniform1_0_, p_glUniform1_1_);
        }

    }

    public static void glUniform2(int p_glUniform2_0_, IntBuffer p_glUniform2_1_) {
        if (arbShaders) {
            ARBShaderObjects.glUniform2ivARB(p_glUniform2_0_, p_glUniform2_1_);
        } else {
            GL20.glUniform2iv(p_glUniform2_0_, p_glUniform2_1_);
        }

    }

    public static void glUniform2(int p_glUniform2_0_, FloatBuffer p_glUniform2_1_) {
        if (arbShaders) {
            ARBShaderObjects.glUniform2fvARB(p_glUniform2_0_, p_glUniform2_1_);
        } else {
            GL20.glUniform2fv(p_glUniform2_0_, p_glUniform2_1_);
        }

    }

    public static void glUniform3(int p_glUniform3_0_, IntBuffer p_glUniform3_1_) {
        if (arbShaders) {
            ARBShaderObjects.glUniform3ivARB(p_glUniform3_0_, p_glUniform3_1_);
        } else {
            GL20.glUniform3iv(p_glUniform3_0_, p_glUniform3_1_);
        }

    }

    public static void glUniform3(int p_glUniform3_0_, FloatBuffer p_glUniform3_1_) {
        if (arbShaders) {
            ARBShaderObjects.glUniform3fvARB(p_glUniform3_0_, p_glUniform3_1_);
        } else {
            GL20.glUniform3fv(p_glUniform3_0_, p_glUniform3_1_);
        }

    }

    public static void glUniform4(int p_glUniform4_0_, IntBuffer p_glUniform4_1_) {
        if (arbShaders) {
            ARBShaderObjects.glUniform4ivARB(p_glUniform4_0_, p_glUniform4_1_);
        } else {
            GL20.glUniform4iv(p_glUniform4_0_, p_glUniform4_1_);
        }

    }

    public static void glUniform4(int p_glUniform4_0_, FloatBuffer p_glUniform4_1_) {
        if (arbShaders) {
            ARBShaderObjects.glUniform4fvARB(p_glUniform4_0_, p_glUniform4_1_);
        } else {
            GL20.glUniform4fv(p_glUniform4_0_, p_glUniform4_1_);
        }

    }

    public static void glUniformMatrix2(int p_glUniformMatrix2_0_, boolean p_glUniformMatrix2_1_, FloatBuffer p_glUniformMatrix2_2_) {
        if (arbShaders) {
            ARBShaderObjects.glUniformMatrix2fvARB(p_glUniformMatrix2_0_, p_glUniformMatrix2_1_, p_glUniformMatrix2_2_);
        } else {
            GL20.glUniformMatrix2fv(p_glUniformMatrix2_0_, p_glUniformMatrix2_1_, p_glUniformMatrix2_2_);
        }

    }

    public static void glUniformMatrix3(int p_glUniformMatrix3_0_, boolean p_glUniformMatrix3_1_, FloatBuffer p_glUniformMatrix3_2_) {
        if (arbShaders) {
            ARBShaderObjects.glUniformMatrix3fvARB(p_glUniformMatrix3_0_, p_glUniformMatrix3_1_, p_glUniformMatrix3_2_);
        } else {
            GL20.glUniformMatrix3fv(p_glUniformMatrix3_0_, p_glUniformMatrix3_1_, p_glUniformMatrix3_2_);
        }

    }

    public static void glUniformMatrix4(int p_glUniformMatrix4_0_, boolean p_glUniformMatrix4_1_, FloatBuffer p_glUniformMatrix4_2_) {
        if (arbShaders) {
            ARBShaderObjects.glUniformMatrix4fvARB(p_glUniformMatrix4_0_, p_glUniformMatrix4_1_, p_glUniformMatrix4_2_);
        } else {
            GL20.glUniformMatrix4fv(p_glUniformMatrix4_0_, p_glUniformMatrix4_1_, p_glUniformMatrix4_2_);
        }

    }

    public static int glGetAttribLocation(int p_glGetAttribLocation_0_, CharSequence p_glGetAttribLocation_1_) {
        return arbShaders ? ARBVertexShader.glGetAttribLocationARB(p_glGetAttribLocation_0_, p_glGetAttribLocation_1_) : GL20.glGetAttribLocation(p_glGetAttribLocation_0_, p_glGetAttribLocation_1_);
    }

    public static int glGenBuffers() {
        return arbVbo ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
    }

    public static void glBindBuffer(int p_glBindBuffer_0_, int p_glBindBuffer_1_) {
        if (arbVbo) {
            ARBVertexBufferObject.glBindBufferARB(p_glBindBuffer_0_, p_glBindBuffer_1_);
        } else {
            GL15.glBindBuffer(p_glBindBuffer_0_, p_glBindBuffer_1_);
        }

    }

    public static void glBufferData(int p_glBufferData_0_, ByteBuffer p_glBufferData_1_, int p_glBufferData_2_) {
        if (arbVbo) {
            ARBVertexBufferObject.glBufferDataARB(p_glBufferData_0_, p_glBufferData_1_, p_glBufferData_2_);
        } else {
            GL15.glBufferData(p_glBufferData_0_, p_glBufferData_1_, p_glBufferData_2_);
        }

    }

    public static void glDeleteBuffers(int p_glDeleteBuffers_0_) {
        if (arbVbo) {
            ARBVertexBufferObject.glDeleteBuffersARB(p_glDeleteBuffers_0_);
        } else {
            GL15.glDeleteBuffers(p_glDeleteBuffers_0_);
        }

    }

    public static boolean useVbo() {
        return vboSupported;
    }

    public static void setActiveTexture(int p_setActiveTexture_0_) {
        if (arbMultitexture) {
            ARBMultitexture.glActiveTextureARB(p_setActiveTexture_0_);
        } else {
            GL13.glActiveTexture(p_setActiveTexture_0_);
        }

    }

    public static void setClientActiveTexture(int p_setClientActiveTexture_0_) {
        if (arbMultitexture) {
            ARBMultitexture.glClientActiveTextureARB(p_setClientActiveTexture_0_);
        } else {
            GL13.glClientActiveTexture(p_setClientActiveTexture_0_);
        }

    }

    public static void setLightmapTextureCoords(int p_setLightmapTextureCoords_0_, float p_setLightmapTextureCoords_1_, float p_setLightmapTextureCoords_2_) {
        if (arbMultitexture) {
            ARBMultitexture.glMultiTexCoord2fARB(p_setLightmapTextureCoords_0_, p_setLightmapTextureCoords_1_, p_setLightmapTextureCoords_2_);
        } else {
            GL13.glMultiTexCoord2f(p_setLightmapTextureCoords_0_, p_setLightmapTextureCoords_1_, p_setLightmapTextureCoords_2_);
        }

    }

    public static void glBlendFunc(int p_glBlendFunc_0_, int p_glBlendFunc_1_, int p_glBlendFunc_2_, int p_glBlendFunc_3_) {
        if (openGL14) {
            if (extBlendFuncSeparate) {
                EXTBlendFuncSeparate.glBlendFuncSeparateEXT(p_glBlendFunc_0_, p_glBlendFunc_1_, p_glBlendFunc_2_, p_glBlendFunc_3_);
            } else {
                GL14.glBlendFuncSeparate(p_glBlendFunc_0_, p_glBlendFunc_1_, p_glBlendFunc_2_, p_glBlendFunc_3_);
            }
        } else {
            GL11.glBlendFunc(p_glBlendFunc_0_, p_glBlendFunc_1_);
        }

    }

    public static String getCpu() {
        return cpu == null ? "<unknown>" : cpu;
    }

    public static String func_195917_n(int p_195917_0_) {
        return (String)field_195919_ac.get(p_195917_0_);
    }

    static enum FboMode {
        BASE,
        ARB,
        EXT;

        private FboMode() {
        }
    }
}
