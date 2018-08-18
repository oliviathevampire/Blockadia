package team.hdt.blockadia.game_engine_old.client.rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLAllocation {
    public static synchronized int generateDisplayLists(int p_generateDisplayLists_0_) {
        int lvt_1_1_ = GlStateManager.glGenLists(p_generateDisplayLists_0_);
        if (lvt_1_1_ == 0) {
            int lvt_2_1_ = GlStateManager.glGetError();
            String lvt_3_1_ = "No error code reported";
            if (lvt_2_1_ != 0) {
                lvt_3_1_ = OpenGlHelper.func_195917_n(lvt_2_1_);
            }

            throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + p_generateDisplayLists_0_ + ", GL error (" + lvt_2_1_ + "): " + lvt_3_1_);
        } else {
            return lvt_1_1_;
        }
    }

    public static synchronized void deleteDisplayLists(int p_deleteDisplayLists_0_, int p_deleteDisplayLists_1_) {
        GlStateManager.glDeleteLists(p_deleteDisplayLists_0_, p_deleteDisplayLists_1_);
    }

    public static synchronized void deleteDisplayLists(int p_deleteDisplayLists_0_) {
        deleteDisplayLists(p_deleteDisplayLists_0_, 1);
    }

    public static synchronized ByteBuffer createDirectByteBuffer(int p_createDirectByteBuffer_0_) {
        return ByteBuffer.allocateDirect(p_createDirectByteBuffer_0_).order(ByteOrder.nativeOrder());
    }

    public static FloatBuffer createDirectFloatBuffer(int p_createDirectFloatBuffer_0_) {
        return createDirectByteBuffer(p_createDirectFloatBuffer_0_ << 2).asFloatBuffer();
    }
}
