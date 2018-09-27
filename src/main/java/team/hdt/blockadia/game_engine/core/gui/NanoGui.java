package team.hdt.blockadia.game_engine.core.gui;

import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;

public class NanoGui {

    private static boolean initialized = false;
    private static long vg = -1;

    private static boolean debug = false;

    public static void init() {
        if (initialized) {
            return;
        }
        vg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_STENCIL_STROKES);

        if (vg == MemoryUtil.NULL) {
            throw new RuntimeException("Could not init nanoVG");
        }

        initialized = true;
    }

    public static void enable(int windowWidth, int windowHeight) {
        nvgBeginFrame(vg, windowWidth, windowHeight, 1f);
    }

    public static void disable() {
        nvgEndFrame(vg);
    }

    public static void enableDebug() {
        NanoGui.debug = true;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void dispose() {
        NanoVGGL3.nvgDelete(vg);
    }

    public static long getVG() {
        return vg;
    }

}