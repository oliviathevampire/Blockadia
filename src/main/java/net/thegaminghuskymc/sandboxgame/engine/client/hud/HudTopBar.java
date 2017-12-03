package net.thegaminghuskymc.sandboxgame.engine.client.hud;

import net.thegaminghuskymc.sandboxgame.engine.Window;
import net.thegaminghuskymc.sandboxgame.engine.util.HudUtils;
import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class HudTopBar implements IHudComponent {

    private static final String FONT_NAME = "BOLD";

    private long vg;

    private NVGColor colour;

    @Override
    public void init(Window window) throws Exception {
        this.vg = window.getOptions().antialiasing ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.vg == NULL) {
            throw new Exception("Could not init nanovg");
        }

        colour = NVGColor.create();
    }

    @Override
    public void render(Window window) {
        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);

        // Upper ribbon
        nvgBeginPath(vg);
        nvgRect(vg, 0, 0, window.getWidth(), window.getHeight()/10);
        nvgFillColor(vg, HudUtils.rgba(0x23, 0xa1, 0xf1, 200, colour));
        nvgFill(vg);

        nvgEndFrame(vg);

        // Restore state
        window.restoreState();
    }

    @Override
    public void cleanup() {
        nvgDelete(vg);
    }

}