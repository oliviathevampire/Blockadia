//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.client.rendering;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.system.MemoryUtil;
import team.hdt.blockadia.game_engine_old.common.util.LWJGLMemoryUntracker;
import team.hdt.blockadia.game_engine_old.common.util.Util;
import team.hdt.blockadia.game_engine_old.common.util.interfaces.Nullable;
import team.hdt.blockadia.game_engine.core.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.test.game.DummyGame;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;

public class GlStateManager {
    private static final FloatBuffer BUF_FLOAT_16 = Util.acceptAndReturn(MemoryUtil.memAllocFloat(16), (p_209238_0_) -> {
        LWJGLMemoryUntracker.func_197933_a(MemoryUtil.memAddress(p_209238_0_));
    });
    private static final FloatBuffer BUF_FLOAT_4 = Util.acceptAndReturn(MemoryUtil.memAllocFloat(4), (p_209236_0_) -> {
        LWJGLMemoryUntracker.func_197933_a(MemoryUtil.memAddress(p_209236_0_));
    });
    private static final GlStateManager.AlphaState field_199299_c = new GlStateManager.AlphaState();
    private static final GlStateManager.BooleanState field_199300_d = new GlStateManager.BooleanState(2896);
    private static final GlStateManager.BooleanState[] field_199301_e = IntStream.range(0, 8).mapToObj((p_199933_0_) -> {
        return new BooleanState(16384 + p_199933_0_);
    }).toArray((p_199930_0_) -> {
        return new BooleanState[p_199930_0_];
    });
    private static final GlStateManager.ColorMaterialState field_199302_f = new GlStateManager.ColorMaterialState();
    private static final GlStateManager.BlendState blendState = new GlStateManager.BlendState();
    private static final GlStateManager.DepthState depthState = new GlStateManager.DepthState();
    private static final GlStateManager.FogState fogState = new GlStateManager.FogState();
    private static final GlStateManager.CullState cullState = new GlStateManager.CullState();
    private static final GlStateManager.PolygonOffsetState polygonOffsetState = new GlStateManager.PolygonOffsetState();
    private static final GlStateManager.ColorLogicState colorLogicState = new GlStateManager.ColorLogicState();
    private static final GlStateManager.TexGenState texGenState = new GlStateManager.TexGenState();
    private static final GlStateManager.ClearState clearState = new GlStateManager.ClearState();
    private static final GlStateManager.StencilState stencilState = new GlStateManager.StencilState();
    private static final GlStateManager.BooleanState field_199303_p = new GlStateManager.BooleanState(2977);
    private static int activeTextureUnit;
    private static final GlStateManager.TextureState[] field_199304_r = IntStream.range(0, 8).mapToObj((p_199931_0_) -> new TextureState()).toArray(TextureState[]::new);
    private static int activeShadeModel = 7425;
    private static final GlStateManager.BooleanState field_199305_t = new GlStateManager.BooleanState(32826);
    private static final GlStateManager.ColorMask field_199306_u = new GlStateManager.ColorMask();
    private static final GlStateManager.Color field_199307_v = new GlStateManager.Color();

    public static void pushAttrib() {
        GL11.glPushAttrib(8256);
    }

    public static void popAttrib() {
        GL11.glPopAttrib();
    }

    public static void disableAlpha() {
        field_199299_c.alphaTest.setDisabled();
    }

    public static void enableAlpha() {
        field_199299_c.alphaTest.setEnabled();
    }

    public static void alphaFunc(int p_alphaFunc_0_, float p_alphaFunc_1_) {
        if (p_alphaFunc_0_ != field_199299_c.func || p_alphaFunc_1_ != field_199299_c.ref) {
            field_199299_c.func = p_alphaFunc_0_;
            field_199299_c.ref = p_alphaFunc_1_;
            GL11.glAlphaFunc(p_alphaFunc_0_, p_alphaFunc_1_);
        }

    }

    public static void enableLighting() {
        field_199300_d.setEnabled();
    }

    public static void disableLighting() {
        field_199300_d.setDisabled();
    }

    public static void enableLight(int p_enableLight_0_) {
        field_199301_e[p_enableLight_0_].setEnabled();
    }

    public static void disableLight(int p_disableLight_0_) {
        field_199301_e[p_disableLight_0_].setDisabled();
    }

    public static void enableColorMaterial() {
        field_199302_f.colorMaterial.setEnabled();
    }

    public static void disableColorMaterial() {
        field_199302_f.colorMaterial.setDisabled();
    }

    public static void colorMaterial(int p_colorMaterial_0_, int p_colorMaterial_1_) {
        if (p_colorMaterial_0_ != field_199302_f.face || p_colorMaterial_1_ != field_199302_f.mode) {
            field_199302_f.face = p_colorMaterial_0_;
            field_199302_f.mode = p_colorMaterial_1_;
            GL11.glColorMaterial(p_colorMaterial_0_, p_colorMaterial_1_);
        }

    }

    public static void glLight(int p_glLight_0_, int p_glLight_1_, FloatBuffer p_glLight_2_) {
        GL11.glLightfv(p_glLight_0_, p_glLight_1_, p_glLight_2_);
    }

    public static void glLightModel(int p_glLightModel_0_, FloatBuffer p_glLightModel_1_) {
        GL11.glLightModelfv(p_glLightModel_0_, p_glLightModel_1_);
    }

    public static void glNormal3f(float p_glNormal3f_0_, float p_glNormal3f_1_, float p_glNormal3f_2_) {
        GL11.glNormal3f(p_glNormal3f_0_, p_glNormal3f_1_, p_glNormal3f_2_);
    }

    public static void disableDepth() {
        depthState.depthTest.setDisabled();
    }

    public static void enableDepth() {
        depthState.depthTest.setEnabled();
    }

    public static void depthFunc(int p_depthFunc_0_) {
        if (p_depthFunc_0_ != depthState.depthFunc) {
            depthState.depthFunc = p_depthFunc_0_;
            GL11.glDepthFunc(p_depthFunc_0_);
        }

    }

    public static void depthMask(boolean p_depthMask_0_) {
        if (p_depthMask_0_ != depthState.maskEnabled) {
            depthState.maskEnabled = p_depthMask_0_;
            GL11.glDepthMask(p_depthMask_0_);
        }

    }

    public static void disableBlend() {
        blendState.blend.setDisabled();
    }

    public static void enableBlend() {
        blendState.blend.setEnabled();
    }

    public static void blendFunc(GlStateManager.SourceFactor p_blendFunc_0_, GlStateManager.DestFactor p_blendFunc_1_) {
        blendFunc(p_blendFunc_0_.factor, p_blendFunc_1_.factor);
    }

    public static void blendFunc(int p_blendFunc_0_, int p_blendFunc_1_) {
        if (p_blendFunc_0_ != blendState.srcFactor || p_blendFunc_1_ != blendState.dstFactor) {
            blendState.srcFactor = p_blendFunc_0_;
            blendState.dstFactor = p_blendFunc_1_;
            GL11.glBlendFunc(p_blendFunc_0_, p_blendFunc_1_);
        }

    }

    public static void tryBlendFuncSeparate(GlStateManager.SourceFactor p_tryBlendFuncSeparate_0_, GlStateManager.DestFactor p_tryBlendFuncSeparate_1_, GlStateManager.SourceFactor p_tryBlendFuncSeparate_2_, GlStateManager.DestFactor p_tryBlendFuncSeparate_3_) {
        tryBlendFuncSeparate(p_tryBlendFuncSeparate_0_.factor, p_tryBlendFuncSeparate_1_.factor, p_tryBlendFuncSeparate_2_.factor, p_tryBlendFuncSeparate_3_.factor);
    }

    public static void tryBlendFuncSeparate(int p_tryBlendFuncSeparate_0_, int p_tryBlendFuncSeparate_1_, int p_tryBlendFuncSeparate_2_, int p_tryBlendFuncSeparate_3_) {
        if (p_tryBlendFuncSeparate_0_ != blendState.srcFactor || p_tryBlendFuncSeparate_1_ != blendState.dstFactor || p_tryBlendFuncSeparate_2_ != blendState.srcFactorAlpha || p_tryBlendFuncSeparate_3_ != blendState.dstFactorAlpha) {
            blendState.srcFactor = p_tryBlendFuncSeparate_0_;
            blendState.dstFactor = p_tryBlendFuncSeparate_1_;
            blendState.srcFactorAlpha = p_tryBlendFuncSeparate_2_;
            blendState.dstFactorAlpha = p_tryBlendFuncSeparate_3_;
            OpenGlHelper.glBlendFunc(p_tryBlendFuncSeparate_0_, p_tryBlendFuncSeparate_1_, p_tryBlendFuncSeparate_2_, p_tryBlendFuncSeparate_3_);
        }

    }

    public static void glBlendEquation(int p_glBlendEquation_0_) {
        GL14.glBlendEquation(p_glBlendEquation_0_);
    }

    public static void enableOutlineMode(int p_enableOutlineMode_0_) {
        BUF_FLOAT_4.put(0, (float)(p_enableOutlineMode_0_ >> 16 & 255) / 255.0F);
        BUF_FLOAT_4.put(1, (float)(p_enableOutlineMode_0_ >> 8 & 255) / 255.0F);
        BUF_FLOAT_4.put(2, (float)(p_enableOutlineMode_0_ >> 0 & 255) / 255.0F);
        BUF_FLOAT_4.put(3, (float)(p_enableOutlineMode_0_ >> 24 & 255) / 255.0F);
        glTexEnv(8960, 8705, BUF_FLOAT_4);
        glTexEnvi(8960, 8704, 34160);
        glTexEnvi(8960, 34161, 7681);
        glTexEnvi(8960, 34176, 34166);
        glTexEnvi(8960, 34192, 768);
        glTexEnvi(8960, 34162, 7681);
        glTexEnvi(8960, 34184, 5890);
        glTexEnvi(8960, 34200, 770);
    }

    public static void disableOutlineMode() {
        glTexEnvi(8960, 8704, 8448);
        glTexEnvi(8960, 34161, 8448);
        glTexEnvi(8960, 34162, 8448);
        glTexEnvi(8960, 34176, 5890);
        glTexEnvi(8960, 34184, 5890);
        glTexEnvi(8960, 34192, 768);
        glTexEnvi(8960, 34200, 770);
    }

    public static void enableFog() {
        fogState.fog.setEnabled();
    }

    public static void disableFog() {
        fogState.fog.setDisabled();
    }

    public static void setFog(GlStateManager.FogMode p_setFog_0_) {
        setFog(p_setFog_0_.capabilityId);
    }

    private static void setFog(int p_setFog_0_) {
        if (p_setFog_0_ != fogState.mode) {
            fogState.mode = p_setFog_0_;
            GL11.glFogi(2917, p_setFog_0_);
        }

    }

    public static void setFogDensity(float p_setFogDensity_0_) {
        if (p_setFogDensity_0_ != fogState.density) {
            fogState.density = p_setFogDensity_0_;
            GL11.glFogf(2914, p_setFogDensity_0_);
        }

    }

    public static void setFogStart(float p_setFogStart_0_) {
        if (p_setFogStart_0_ != fogState.start) {
            fogState.start = p_setFogStart_0_;
            GL11.glFogf(2915, p_setFogStart_0_);
        }

    }

    public static void setFogEnd(float p_setFogEnd_0_) {
        if (p_setFogEnd_0_ != fogState.end) {
            fogState.end = p_setFogEnd_0_;
            GL11.glFogf(2916, p_setFogEnd_0_);
        }

    }

    public static void glFog(int p_glFog_0_, FloatBuffer p_glFog_1_) {
        GL11.glFogfv(p_glFog_0_, p_glFog_1_);
    }

    public static void glFogi(int p_glFogi_0_, int p_glFogi_1_) {
        GL11.glFogi(p_glFogi_0_, p_glFogi_1_);
    }

    public static void enableCull() {
        cullState.cullFace.setEnabled();
    }

    public static void disableCull() {
        cullState.cullFace.setDisabled();
    }

    public static void cullFace(GlStateManager.CullFace p_cullFace_0_) {
        cullFace(p_cullFace_0_.mode);
    }

    private static void cullFace(int p_cullFace_0_) {
        if (p_cullFace_0_ != cullState.mode) {
            cullState.mode = p_cullFace_0_;
            GL11.glCullFace(p_cullFace_0_);
        }

    }

    public static void glPolygonMode(int p_glPolygonMode_0_, int p_glPolygonMode_1_) {
        GL11.glPolygonMode(p_glPolygonMode_0_, p_glPolygonMode_1_);
    }

    public static void enablePolygonOffset() {
        polygonOffsetState.polygonOffsetFill.setEnabled();
    }

    public static void disablePolygonOffset() {
        polygonOffsetState.polygonOffsetFill.setDisabled();
    }

    public static void doPolygonOffset(float p_doPolygonOffset_0_, float p_doPolygonOffset_1_) {
        if (p_doPolygonOffset_0_ != polygonOffsetState.factor || p_doPolygonOffset_1_ != polygonOffsetState.units) {
            polygonOffsetState.factor = p_doPolygonOffset_0_;
            polygonOffsetState.units = p_doPolygonOffset_1_;
            GL11.glPolygonOffset(p_doPolygonOffset_0_, p_doPolygonOffset_1_);
        }

    }

    public static void enableColorLogic() {
        colorLogicState.colorLogicOp.setEnabled();
    }

    public static void disableColorLogic() {
        colorLogicState.colorLogicOp.setDisabled();
    }

    public static void colorLogicOp(GlStateManager.LogicOp p_colorLogicOp_0_) {
        colorLogicOp(p_colorLogicOp_0_.opcode);
    }

    public static void colorLogicOp(int p_colorLogicOp_0_) {
        if (p_colorLogicOp_0_ != colorLogicState.opcode) {
            colorLogicState.opcode = p_colorLogicOp_0_;
            GL11.glLogicOp(p_colorLogicOp_0_);
        }

    }

    public static void enableTexGenCoord(GlStateManager.TexGen p_enableTexGenCoord_0_) {
        texGenCoord(p_enableTexGenCoord_0_).textureGen.setEnabled();
    }

    public static void disableTexGenCoord(GlStateManager.TexGen p_disableTexGenCoord_0_) {
        texGenCoord(p_disableTexGenCoord_0_).textureGen.setDisabled();
    }

    public static void texGen(GlStateManager.TexGen p_texGen_0_, int p_texGen_1_) {
        GlStateManager.TexGenCoord lvt_2_1_ = texGenCoord(p_texGen_0_);
        if (p_texGen_1_ != lvt_2_1_.param) {
            lvt_2_1_.param = p_texGen_1_;
            GL11.glTexGeni(lvt_2_1_.coord, 9472, p_texGen_1_);
        }

    }

    public static void texGen(GlStateManager.TexGen p_texGen_0_, int p_texGen_1_, FloatBuffer p_texGen_2_) {
        GL11.glTexGenfv(texGenCoord(p_texGen_0_).coord, p_texGen_1_, p_texGen_2_);
    }

    private static GlStateManager.TexGenCoord texGenCoord(GlStateManager.TexGen p_texGenCoord_0_) {
        return texGenState.s;
    }

    public static void setActiveTexture(int p_setActiveTexture_0_) {
        if (activeTextureUnit != p_setActiveTexture_0_ - OpenGlHelper.defaultTexUnit) {
            activeTextureUnit = p_setActiveTexture_0_ - OpenGlHelper.defaultTexUnit;
            OpenGlHelper.setActiveTexture(p_setActiveTexture_0_);
        }

    }

    public static void enableTexture2D() {
        field_199304_r[activeTextureUnit].texture2DState.setEnabled();
    }

    public static void disableTexture2D() {
        field_199304_r[activeTextureUnit].texture2DState.setDisabled();
    }

    public static void glTexEnv(int p_glTexEnv_0_, int p_glTexEnv_1_, FloatBuffer p_glTexEnv_2_) {
        GL11.glTexEnvfv(p_glTexEnv_0_, p_glTexEnv_1_, p_glTexEnv_2_);
    }

    public static void glTexEnvi(int p_glTexEnvi_0_, int p_glTexEnvi_1_, int p_glTexEnvi_2_) {
        GL11.glTexEnvi(p_glTexEnvi_0_, p_glTexEnvi_1_, p_glTexEnvi_2_);
    }

    public static void glTexEnvf(int p_glTexEnvf_0_, int p_glTexEnvf_1_, float p_glTexEnvf_2_) {
        GL11.glTexEnvf(p_glTexEnvf_0_, p_glTexEnvf_1_, p_glTexEnvf_2_);
    }

    public static void glTexParameterf(int p_glTexParameterf_0_, int p_glTexParameterf_1_, float p_glTexParameterf_2_) {
        GL11.glTexParameterf(p_glTexParameterf_0_, p_glTexParameterf_1_, p_glTexParameterf_2_);
    }

    public static void glTexParameteri(int p_glTexParameteri_0_, int p_glTexParameteri_1_, int p_glTexParameteri_2_) {
        GL11.glTexParameteri(p_glTexParameteri_0_, p_glTexParameteri_1_, p_glTexParameteri_2_);
    }

    public static int glGetTexLevelParameteri(int p_glGetTexLevelParameteri_0_, int p_glGetTexLevelParameteri_1_, int p_glGetTexLevelParameteri_2_) {
        return GL11.glGetTexLevelParameteri(p_glGetTexLevelParameteri_0_, p_glGetTexLevelParameteri_1_, p_glGetTexLevelParameteri_2_);
    }

    public static int generateTexture() {
        return GL11.glGenTextures();
    }

    public static void deleteTexture(int p_deleteTexture_0_) {
        GL11.glDeleteTextures(p_deleteTexture_0_);
        GlStateManager.TextureState[] var1 = field_199304_r;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GlStateManager.TextureState lvt_4_1_ = var1[var3];
            if (lvt_4_1_.textureName == p_deleteTexture_0_) {
                lvt_4_1_.textureName = -1;
            }
        }

    }

    public static void bindTexture(int p_bindTexture_0_) {
        if (p_bindTexture_0_ != field_199304_r[activeTextureUnit].textureName) {
            field_199304_r[activeTextureUnit].textureName = p_bindTexture_0_;
            GL11.glBindTexture(3553, p_bindTexture_0_);
        }

    }

    public static void glTexImage2D(int p_glTexImage2D_0_, int p_glTexImage2D_1_, int p_glTexImage2D_2_, int p_glTexImage2D_3_, int p_glTexImage2D_4_, int p_glTexImage2D_5_, int p_glTexImage2D_6_, int p_glTexImage2D_7_, @Nullable IntBuffer p_glTexImage2D_8_) {
        GL11.glTexImage2D(p_glTexImage2D_0_, p_glTexImage2D_1_, p_glTexImage2D_2_, p_glTexImage2D_3_, p_glTexImage2D_4_, p_glTexImage2D_5_, p_glTexImage2D_6_, p_glTexImage2D_7_, p_glTexImage2D_8_);
    }

    public static void func_199298_a(int p_199298_0_, int p_199298_1_, int p_199298_2_, int p_199298_3_, int p_199298_4_, int p_199298_5_, int p_199298_6_, int p_199298_7_, long p_199298_8_) {
        GL11.glTexSubImage2D(p_199298_0_, p_199298_1_, p_199298_2_, p_199298_3_, p_199298_4_, p_199298_5_, p_199298_6_, p_199298_7_, p_199298_8_);
    }

    public static void func_199295_a(int p_199295_0_, int p_199295_1_, int p_199295_2_, int p_199295_3_, long p_199295_4_) {
        GL11.glGetTexImage(p_199295_0_, p_199295_1_, p_199295_2_, p_199295_3_, p_199295_4_);
    }

    public static void enableNormalize() {
        field_199303_p.setEnabled();
    }

    public static void disableNormalize() {
        field_199303_p.setDisabled();
    }

    public static void shadeModel(int p_shadeModel_0_) {
        if (p_shadeModel_0_ != activeShadeModel) {
            activeShadeModel = p_shadeModel_0_;
            GL11.glShadeModel(p_shadeModel_0_);
        }

    }

    public static void enableRescaleNormal() {
        field_199305_t.setEnabled();
    }

    public static void disableRescaleNormal() {
        field_199305_t.setDisabled();
    }

    public static void viewport(int p_viewport_0_, int p_viewport_1_, int p_viewport_2_, int p_viewport_3_) {
        GlStateManager.Viewport.INSTANCE.field_199289_b = p_viewport_0_;
        GlStateManager.Viewport.INSTANCE.field_199290_c = p_viewport_1_;
        GlStateManager.Viewport.INSTANCE.field_199291_d = p_viewport_2_;
        GlStateManager.Viewport.INSTANCE.field_199292_e = p_viewport_3_;
        GL11.glViewport(p_viewport_0_, p_viewport_1_, p_viewport_2_, p_viewport_3_);
    }

    public static void colorMask(boolean p_colorMask_0_, boolean p_colorMask_1_, boolean p_colorMask_2_, boolean p_colorMask_3_) {
        if (p_colorMask_0_ != field_199306_u.red || p_colorMask_1_ != field_199306_u.green || p_colorMask_2_ != field_199306_u.blue || p_colorMask_3_ != field_199306_u.alpha) {
            field_199306_u.red = p_colorMask_0_;
            field_199306_u.green = p_colorMask_1_;
            field_199306_u.blue = p_colorMask_2_;
            field_199306_u.alpha = p_colorMask_3_;
            GL11.glColorMask(p_colorMask_0_, p_colorMask_1_, p_colorMask_2_, p_colorMask_3_);
        }

    }

    public static void clearDepth(double p_clearDepth_0_) {
        if (p_clearDepth_0_ != clearState.depth) {
            clearState.depth = p_clearDepth_0_;
            GL11.glClearDepth(p_clearDepth_0_);
        }

    }

    public static void clearColor(float p_clearColor_0_, float p_clearColor_1_, float p_clearColor_2_, float p_clearColor_3_) {
        if (p_clearColor_0_ != clearState.color.red || p_clearColor_1_ != clearState.color.green || p_clearColor_2_ != clearState.color.blue || p_clearColor_3_ != clearState.color.alpha) {
            clearState.color.red = p_clearColor_0_;
            clearState.color.green = p_clearColor_1_;
            clearState.color.blue = p_clearColor_2_;
            clearState.color.alpha = p_clearColor_3_;
            GL11.glClearColor(p_clearColor_0_, p_clearColor_1_, p_clearColor_2_, p_clearColor_3_);
        }

    }

    public static void clear(int p_clear_0_) {
        GL11.glClear(p_clear_0_);
        if (DummyGame.IS_RUNNING_ON_MAC) {
            glGetError();
        }

    }

    public static void matrixMode(int p_matrixMode_0_) {
        GL11.glMatrixMode(p_matrixMode_0_);
    }

    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }

    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    public static void getFloat(int p_getFloat_0_, FloatBuffer p_getFloat_1_) {
        GL11.glGetFloatv(p_getFloat_0_, p_getFloat_1_);
    }

    public static void ortho(double p_ortho_0_, double p_ortho_2_, double p_ortho_4_, double p_ortho_6_, double p_ortho_8_, double p_ortho_10_) {
        GL11.glOrtho(p_ortho_0_, p_ortho_2_, p_ortho_4_, p_ortho_6_, p_ortho_8_, p_ortho_10_);
    }

    public static void rotate(float p_rotate_0_, float p_rotate_1_, float p_rotate_2_, float p_rotate_3_) {
        GL11.glRotatef(p_rotate_0_, p_rotate_1_, p_rotate_2_, p_rotate_3_);
    }

    public static void scale(float p_scale_0_, float p_scale_1_, float p_scale_2_) {
        GL11.glScalef(p_scale_0_, p_scale_1_, p_scale_2_);
    }

    public static void scale(double p_scale_0_, double p_scale_2_, double p_scale_4_) {
        GL11.glScaled(p_scale_0_, p_scale_2_, p_scale_4_);
    }

    public static void translate(float p_translate_0_, float p_translate_1_, float p_translate_2_) {
        GL11.glTranslatef(p_translate_0_, p_translate_1_, p_translate_2_);
    }

    public static void translate(double p_translate_0_, double p_translate_2_, double p_translate_4_) {
        GL11.glTranslated(p_translate_0_, p_translate_2_, p_translate_4_);
    }

    public static void multMatrix(FloatBuffer p_multMatrix_0_) {
        GL11.glMultMatrixf(p_multMatrix_0_);
    }

    public static void func_199294_a(Matrix4fs p_199294_0_) {
        p_199294_0_.load(BUF_FLOAT_16);
        BUF_FLOAT_16.rewind();
        GL11.glMultMatrixf(BUF_FLOAT_16);
    }

    public static void color(float p_color_0_, float p_color_1_, float p_color_2_, float p_color_3_) {
        if (p_color_0_ != field_199307_v.red || p_color_1_ != field_199307_v.green || p_color_2_ != field_199307_v.blue || p_color_3_ != field_199307_v.alpha) {
            field_199307_v.red = p_color_0_;
            field_199307_v.green = p_color_1_;
            field_199307_v.blue = p_color_2_;
            field_199307_v.alpha = p_color_3_;
            GL11.glColor4f(p_color_0_, p_color_1_, p_color_2_, p_color_3_);
        }

    }

    public static void color(float p_color_0_, float p_color_1_, float p_color_2_) {
        color(p_color_0_, p_color_1_, p_color_2_, 1.0F);
    }

    public static void resetColor() {
        field_199307_v.red = -1.0F;
        field_199307_v.green = -1.0F;
        field_199307_v.blue = -1.0F;
        field_199307_v.alpha = -1.0F;
    }

    public static void func_204611_f(int p_204611_0_, int p_204611_1_, int p_204611_2_) {
        GL11.glNormalPointer(p_204611_0_, p_204611_1_, (long)p_204611_2_);
    }

    public static void glNormalPointer(int p_glNormalPointer_0_, int p_glNormalPointer_1_, ByteBuffer p_glNormalPointer_2_) {
        GL11.glNormalPointer(p_glNormalPointer_0_, p_glNormalPointer_1_, p_glNormalPointer_2_);
    }

    public static void glTexCoordPointer(int p_glTexCoordPointer_0_, int p_glTexCoordPointer_1_, int p_glTexCoordPointer_2_, int p_glTexCoordPointer_3_) {
        GL11.glTexCoordPointer(p_glTexCoordPointer_0_, p_glTexCoordPointer_1_, p_glTexCoordPointer_2_, (long)p_glTexCoordPointer_3_);
    }

    public static void glTexCoordPointer(int p_glTexCoordPointer_0_, int p_glTexCoordPointer_1_, int p_glTexCoordPointer_2_, ByteBuffer p_glTexCoordPointer_3_) {
        GL11.glTexCoordPointer(p_glTexCoordPointer_0_, p_glTexCoordPointer_1_, p_glTexCoordPointer_2_, p_glTexCoordPointer_3_);
    }

    public static void glVertexPointer(int p_glVertexPointer_0_, int p_glVertexPointer_1_, int p_glVertexPointer_2_, int p_glVertexPointer_3_) {
        GL11.glVertexPointer(p_glVertexPointer_0_, p_glVertexPointer_1_, p_glVertexPointer_2_, (long)p_glVertexPointer_3_);
    }

    public static void glVertexPointer(int p_glVertexPointer_0_, int p_glVertexPointer_1_, int p_glVertexPointer_2_, ByteBuffer p_glVertexPointer_3_) {
        GL11.glVertexPointer(p_glVertexPointer_0_, p_glVertexPointer_1_, p_glVertexPointer_2_, p_glVertexPointer_3_);
    }

    public static void glColorPointer(int p_glColorPointer_0_, int p_glColorPointer_1_, int p_glColorPointer_2_, int p_glColorPointer_3_) {
        GL11.glColorPointer(p_glColorPointer_0_, p_glColorPointer_1_, p_glColorPointer_2_, (long)p_glColorPointer_3_);
    }

    public static void glColorPointer(int p_glColorPointer_0_, int p_glColorPointer_1_, int p_glColorPointer_2_, ByteBuffer p_glColorPointer_3_) {
        GL11.glColorPointer(p_glColorPointer_0_, p_glColorPointer_1_, p_glColorPointer_2_, p_glColorPointer_3_);
    }

    public static void glDisableClientState(int p_glDisableClientState_0_) {
        GL11.glDisableClientState(p_glDisableClientState_0_);
    }

    public static void glEnableClientState(int p_glEnableClientState_0_) {
        GL11.glEnableClientState(p_glEnableClientState_0_);
    }

    public static void glDrawArrays(int p_glDrawArrays_0_, int p_glDrawArrays_1_, int p_glDrawArrays_2_) {
        GL11.glDrawArrays(p_glDrawArrays_0_, p_glDrawArrays_1_, p_glDrawArrays_2_);
    }

    public static void glLineWidth(float p_glLineWidth_0_) {
        GL11.glLineWidth(p_glLineWidth_0_);
    }

    public static void callList(int p_callList_0_) {
        GL11.glCallList(p_callList_0_);
    }

    public static void glDeleteLists(int p_glDeleteLists_0_, int p_glDeleteLists_1_) {
        GL11.glDeleteLists(p_glDeleteLists_0_, p_glDeleteLists_1_);
    }

    public static void glNewList(int p_glNewList_0_, int p_glNewList_1_) {
        GL11.glNewList(p_glNewList_0_, p_glNewList_1_);
    }

    public static void glEndList() {
        GL11.glEndList();
    }

    public static int glGenLists(int p_glGenLists_0_) {
        return GL11.glGenLists(p_glGenLists_0_);
    }

    public static void glPixelStorei(int p_glPixelStorei_0_, int p_glPixelStorei_1_) {
        GL11.glPixelStorei(p_glPixelStorei_0_, p_glPixelStorei_1_);
    }

    public static void func_199297_b(int p_199297_0_, float p_199297_1_) {
        GL11.glPixelTransferf(p_199297_0_, p_199297_1_);
    }

    public static void func_199296_a(int p_199296_0_, int p_199296_1_, int p_199296_2_, int p_199296_3_, int p_199296_4_, int p_199296_5_, long p_199296_6_) {
        GL11.glReadPixels(p_199296_0_, p_199296_1_, p_199296_2_, p_199296_3_, p_199296_4_, p_199296_5_, p_199296_6_);
    }

    public static int glGetError() {
        return GL11.glGetError();
    }

    public static String glGetString(int p_glGetString_0_) {
        return GL11.glGetString(p_glGetString_0_);
    }

    public static void enableBlendProfile(GlStateManager.Profile p_enableBlendProfile_0_) {
        p_enableBlendProfile_0_.apply();
    }

    public static void disableBlendProfile(GlStateManager.Profile p_disableBlendProfile_0_) {
        p_disableBlendProfile_0_.clean();
    }

    public enum Profile {
        DEFAULT {
            public void apply() {
                GlStateManager.disableAlpha();
                GlStateManager.alphaFunc(519, 0.0F);
                GlStateManager.disableLighting();
                GlStateManager.glLightModel(2899, RenderHelper.setColorBuffer(0.2F, 0.2F, 0.2F, 1.0F));

                for(int lvt_1_1_ = 0; lvt_1_1_ < 8; ++lvt_1_1_) {
                    GlStateManager.disableLight(lvt_1_1_);
                    GlStateManager.glLight(16384 + lvt_1_1_, 4608, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
                    GlStateManager.glLight(16384 + lvt_1_1_, 4611, RenderHelper.setColorBuffer(0.0F, 0.0F, 1.0F, 0.0F));
                    if (lvt_1_1_ == 0) {
                        GlStateManager.glLight(16384 + lvt_1_1_, 4609, RenderHelper.setColorBuffer(1.0F, 1.0F, 1.0F, 1.0F));
                        GlStateManager.glLight(16384 + lvt_1_1_, 4610, RenderHelper.setColorBuffer(1.0F, 1.0F, 1.0F, 1.0F));
                    } else {
                        GlStateManager.glLight(16384 + lvt_1_1_, 4609, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
                        GlStateManager.glLight(16384 + lvt_1_1_, 4610, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
                    }
                }

                GlStateManager.disableColorMaterial();
                GlStateManager.colorMaterial(1032, 5634);
                GlStateManager.disableDepth();
                GlStateManager.depthFunc(513);
                GlStateManager.depthMask(true);
                GlStateManager.disableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.glBlendEquation(32774);
                GlStateManager.disableFog();
                GlStateManager.glFogi(2917, 2048);
                GlStateManager.setFogDensity(1.0F);
                GlStateManager.setFogStart(0.0F);
                GlStateManager.setFogEnd(1.0F);
                GlStateManager.glFog(2918, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
                if (GL.getCapabilities().GL_NV_fog_distance) {
                    GlStateManager.glFogi(2917, 34140);
                }

                GlStateManager.doPolygonOffset(0.0F, 0.0F);
                GlStateManager.disableColorLogic();
                GlStateManager.colorLogicOp(5379);
                GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
                GlStateManager.texGen(GlStateManager.TexGen.S, 9216);
                GlStateManager.texGen(GlStateManager.TexGen.S, 9474, RenderHelper.setColorBuffer(1.0F, 0.0F, 0.0F, 0.0F));
                GlStateManager.texGen(GlStateManager.TexGen.S, 9217, RenderHelper.setColorBuffer(1.0F, 0.0F, 0.0F, 0.0F));
                GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
                GlStateManager.texGen(GlStateManager.TexGen.T, 9216);
                GlStateManager.texGen(GlStateManager.TexGen.T, 9474, RenderHelper.setColorBuffer(0.0F, 1.0F, 0.0F, 0.0F));
                GlStateManager.texGen(GlStateManager.TexGen.T, 9217, RenderHelper.setColorBuffer(0.0F, 1.0F, 0.0F, 0.0F));
                GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
                GlStateManager.texGen(GlStateManager.TexGen.R, 9216);
                GlStateManager.texGen(GlStateManager.TexGen.R, 9474, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
                GlStateManager.texGen(GlStateManager.TexGen.R, 9217, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
                GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
                GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
                GlStateManager.texGen(GlStateManager.TexGen.Q, 9474, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
                GlStateManager.texGen(GlStateManager.TexGen.Q, 9217, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
                GlStateManager.setActiveTexture(0);
                GlStateManager.glTexParameteri(3553, 10240, 9729);
                GlStateManager.glTexParameteri(3553, 10241, 9986);
                GlStateManager.glTexParameteri(3553, 10242, 10497);
                GlStateManager.glTexParameteri(3553, 10243, 10497);
                GlStateManager.glTexParameteri(3553, 33085, 1000);
                GlStateManager.glTexParameteri(3553, 33083, 1000);
                GlStateManager.glTexParameteri(3553, 33082, -1000);
                GlStateManager.glTexParameterf(3553, 34049, 0.0F);
                GlStateManager.glTexEnvi(8960, 8704, 8448);
                GlStateManager.glTexEnv(8960, 8705, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
                GlStateManager.glTexEnvi(8960, 34161, 8448);
                GlStateManager.glTexEnvi(8960, 34162, 8448);
                GlStateManager.glTexEnvi(8960, 34176, 5890);
                GlStateManager.glTexEnvi(8960, 34177, 34168);
                GlStateManager.glTexEnvi(8960, 34178, 34166);
                GlStateManager.glTexEnvi(8960, 34184, 5890);
                GlStateManager.glTexEnvi(8960, 34185, 34168);
                GlStateManager.glTexEnvi(8960, 34186, 34166);
                GlStateManager.glTexEnvi(8960, 34192, 768);
                GlStateManager.glTexEnvi(8960, 34193, 768);
                GlStateManager.glTexEnvi(8960, 34194, 770);
                GlStateManager.glTexEnvi(8960, 34200, 770);
                GlStateManager.glTexEnvi(8960, 34201, 770);
                GlStateManager.glTexEnvi(8960, 34202, 770);
                GlStateManager.glTexEnvf(8960, 34163, 1.0F);
                GlStateManager.glTexEnvf(8960, 3356, 1.0F);
                GlStateManager.disableNormalize();
                GlStateManager.shadeModel(7425);
                GlStateManager.disableRescaleNormal();
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.clearDepth(1.0D);
                GlStateManager.glLineWidth(1.0F);
                GlStateManager.glNormal3f(0.0F, 0.0F, 1.0F);
                GlStateManager.glPolygonMode(1028, 6914);
                GlStateManager.glPolygonMode(1029, 6914);
            }

            public void clean() {
            }
        },
        PLAYER_SKIN {
            public void apply() {
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            }

            public void clean() {
                GlStateManager.disableBlend();
            }
        },
        TRANSPARENT_MODEL {
            public void apply() {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                GlStateManager.alphaFunc(516, 0.003921569F);
            }

            public void clean() {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.depthMask(true);
            }
        };

        Profile() {
        }

        public abstract void apply();

        public abstract void clean();
    }

    public enum DestFactor {
        CONSTANT_ALPHA(32771),
        CONSTANT_COLOR(32769),
        DST_ALPHA(772),
        DST_COLOR(774),
        ONE(1),
        ONE_MINUS_CONSTANT_ALPHA(32772),
        ONE_MINUS_CONSTANT_COLOR(32770),
        ONE_MINUS_DST_ALPHA(773),
        ONE_MINUS_DST_COLOR(775),
        ONE_MINUS_SRC_ALPHA(771),
        ONE_MINUS_SRC_COLOR(769),
        SRC_ALPHA(770),
        SRC_COLOR(768),
        ZERO(0);

        public final int factor;

        DestFactor(int p_i46519_3_) {
            this.factor = p_i46519_3_;
        }
    }

    public enum SourceFactor {
        CONSTANT_ALPHA(32771),
        CONSTANT_COLOR(32769),
        DST_ALPHA(772),
        DST_COLOR(774),
        ONE(1),
        ONE_MINUS_CONSTANT_ALPHA(32772),
        ONE_MINUS_CONSTANT_COLOR(32770),
        ONE_MINUS_DST_ALPHA(773),
        ONE_MINUS_DST_COLOR(775),
        ONE_MINUS_SRC_ALPHA(771),
        ONE_MINUS_SRC_COLOR(769),
        SRC_ALPHA(770),
        SRC_ALPHA_SATURATE(776),
        SRC_COLOR(768),
        ZERO(0);

        public final int factor;

        SourceFactor(int p_i46514_3_) {
            this.factor = p_i46514_3_;
        }
    }

    static class BooleanState {
        private final int capability;
        private boolean currentState;

        public BooleanState(int p_i46267_1_) {
            this.capability = p_i46267_1_;
        }

        public void setDisabled() {
            this.setState(false);
        }

        public void setEnabled() {
            this.setState(true);
        }

        public void setState(boolean p_setState_1_) {
            if (p_setState_1_ != this.currentState) {
                this.currentState = p_setState_1_;
                if (p_setState_1_) {
                    GL11.glEnable(this.capability);
                } else {
                    GL11.glDisable(this.capability);
                }
            }

        }
    }

    static class Color {
        public float red;
        public float green;
        public float blue;
        public float alpha;

        public Color() {
            this(1.0F, 1.0F, 1.0F, 1.0F);
        }

        public Color(float p_i46265_1_, float p_i46265_2_, float p_i46265_3_, float p_i46265_4_) {
            this.red = 1.0F;
            this.green = 1.0F;
            this.blue = 1.0F;
            this.alpha = 1.0F;
            this.red = p_i46265_1_;
            this.green = p_i46265_2_;
            this.blue = p_i46265_3_;
            this.alpha = p_i46265_4_;
        }
    }

    static class ColorMask {
        public boolean red;
        public boolean green;
        public boolean blue;
        public boolean alpha;

        private ColorMask() {
            this.red = true;
            this.green = true;
            this.blue = true;
            this.alpha = true;
        }
    }

    public enum TexGen {
        S,
        T,
        R,
        Q;

        TexGen() {
        }
    }

    static class TexGenCoord {
        public GlStateManager.BooleanState textureGen;
        public int coord;
        public int param = -1;

        public TexGenCoord(int p_i46254_1_, int p_i46254_2_) {
            this.coord = p_i46254_1_;
            this.textureGen = new GlStateManager.BooleanState(p_i46254_2_);
        }
    }

    static class TexGenState {
        public GlStateManager.TexGenCoord s;
        public GlStateManager.TexGenCoord t;
        public GlStateManager.TexGenCoord r;
        public GlStateManager.TexGenCoord q;

        private TexGenState() {
            this.s = new GlStateManager.TexGenCoord(8192, 3168);
            this.t = new GlStateManager.TexGenCoord(8193, 3169);
            this.r = new GlStateManager.TexGenCoord(8194, 3170);
            this.q = new GlStateManager.TexGenCoord(8195, 3171);
        }
    }

    static class StencilState {
        public GlStateManager.StencilFunc func;
        public int mask;
        public int fail;
        public int zfail;
        public int zpass;

        private StencilState() {
            this.func = new GlStateManager.StencilFunc();
            this.mask = -1;
            this.fail = 7680;
            this.zfail = 7680;
            this.zpass = 7680;
        }
    }

    static class StencilFunc {
        public int func;
        public int mask;

        private StencilFunc() {
            this.func = 519;
            this.mask = -1;
        }
    }

    static class ClearState {
        public double depth;
        public GlStateManager.Color color;

        private ClearState() {
            this.depth = 1.0D;
            this.color = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    static class ColorLogicState {
        public GlStateManager.BooleanState colorLogicOp;
        public int opcode;

        private ColorLogicState() {
            this.colorLogicOp = new GlStateManager.BooleanState(3058);
            this.opcode = 5379;
        }
    }

    static class PolygonOffsetState {
        public GlStateManager.BooleanState polygonOffsetFill;
        public GlStateManager.BooleanState polygonOffsetLine;
        public float factor;
        public float units;

        private PolygonOffsetState() {
            this.polygonOffsetFill = new GlStateManager.BooleanState(32823);
            this.polygonOffsetLine = new GlStateManager.BooleanState(10754);
        }
    }

    static class CullState {
        public GlStateManager.BooleanState cullFace;
        public int mode;

        private CullState() {
            this.cullFace = new GlStateManager.BooleanState(2884);
            this.mode = 1029;
        }
    }

    static class FogState {
        public GlStateManager.BooleanState fog;
        public int mode;
        public float density;
        public float start;
        public float end;

        private FogState() {
            this.fog = new GlStateManager.BooleanState(2912);
            this.mode = 2048;
            this.density = 1.0F;
            this.end = 1.0F;
        }
    }

    static class DepthState {
        public GlStateManager.BooleanState depthTest;
        public boolean maskEnabled;
        public int depthFunc;

        private DepthState() {
            this.depthTest = new GlStateManager.BooleanState(2929);
            this.maskEnabled = true;
            this.depthFunc = 513;
        }
    }

    static class BlendState {
        public GlStateManager.BooleanState blend;
        public int srcFactor;
        public int dstFactor;
        public int srcFactorAlpha;
        public int dstFactorAlpha;

        private BlendState() {
            this.blend = new GlStateManager.BooleanState(3042);
            this.srcFactor = 1;
            this.dstFactor = 0;
            this.srcFactorAlpha = 1;
            this.dstFactorAlpha = 0;
        }
    }

    static class ColorMaterialState {
        public GlStateManager.BooleanState colorMaterial;
        public int face;
        public int mode;

        private ColorMaterialState() {
            this.colorMaterial = new GlStateManager.BooleanState(2903);
            this.face = 1032;
            this.mode = 5634;
        }
    }

    static class AlphaState {
        public GlStateManager.BooleanState alphaTest;
        public int func;
        public float ref;

        private AlphaState() {
            this.alphaTest = new GlStateManager.BooleanState(3008);
            this.func = 519;
            this.ref = -1.0F;
        }
    }

    static class TextureState {
        public GlStateManager.BooleanState texture2DState;
        public int textureName;

        private TextureState() {
            this.texture2DState = new GlStateManager.BooleanState(3553);
        }
    }

    public enum Viewport {
        INSTANCE;

        protected int field_199289_b;
        protected int field_199290_c;
        protected int field_199291_d;
        protected int field_199292_e;

        Viewport() {
        }
    }

    public enum LogicOp {
        AND(5377),
        AND_INVERTED(5380),
        AND_REVERSE(5378),
        CLEAR(5376),
        COPY(5379),
        COPY_INVERTED(5388),
        EQUIV(5385),
        INVERT(5386),
        NAND(5390),
        NOOP(5381),
        NOR(5384),
        OR(5383),
        OR_INVERTED(5389),
        OR_REVERSE(5387),
        SET(5391),
        XOR(5382);

        public final int opcode;

        LogicOp(int p_i46517_3_) {
            this.opcode = p_i46517_3_;
        }
    }

    public enum CullFace {
        FRONT(1028),
        BACK(1029),
        FRONT_AND_BACK(1032);

        public final int mode;

        CullFace(int p_i46520_3_) {
            this.mode = p_i46520_3_;
        }
    }

    public enum FogMode {
        LINEAR(9729),
        EXP(2048),
        EXP2(2049);

        public final int capabilityId;

        FogMode(int p_i46518_3_) {
            this.capabilityId = p_i46518_3_;
        }
    }
}
