package net.thegaminghuskymc.sandboxgame.engine.util;

import org.lwjgl.nanovg.NVGColor;

public class HudUtils {

    public static NVGColor rgba(int r, int g, int b, int a, NVGColor colour) {
        colour.r(r / 255.0f);
        colour.g(g / 255.0f);
        colour.b(b / 255.0f);
        colour.a(a / 255.0f);

        return colour;
    }

}
