//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.common.util;

import org.lwjgl.system.Pointer;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LWJGLMemoryUntracker {
    @Nullable
    private static final MethodHandle field_197934_a = Util.get(() -> {
        try {
            Lookup lvt_0_1_ = MethodHandles.lookup();
            Class<?> lvt_1_1_ = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
            Method lvt_2_1_ = lvt_1_1_.getDeclaredMethod("untrack", Long.TYPE);
            lvt_2_1_.setAccessible(true);
            Field lvt_3_1_ = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
            lvt_3_1_.setAccessible(true);
            Object lvt_4_1_ = lvt_3_1_.get(null);
            return lvt_1_1_.isInstance(lvt_4_1_) ? lvt_0_1_.unreflect(lvt_2_1_) : null;
        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | ClassNotFoundException var5) {
            throw new RuntimeException(var5);
        }
    });

    public static void func_197933_a(long p_197933_0_) {
        if (field_197934_a != null) {
            try {
                field_197934_a.invoke(p_197933_0_);
            } catch (Throwable var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static void func_211545_a(Pointer p_211545_0_) {
        func_197933_a(p_211545_0_.address());
    }

}