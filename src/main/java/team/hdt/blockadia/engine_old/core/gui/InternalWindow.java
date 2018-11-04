package team.hdt.blockadia.old_engine_code_1.core.gui;

import static org.lwjgl.nanovg.NanoVG.*;

public class InternalWindow extends Node {

    public InternalWindow(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
        drawWindow();
    }

    private void drawWindow() {
        float cornerRadius = 3.0f;

        nvgSave(NanoGui.getVG());

        beginPath();
        roundedRect(x, y, width, height, cornerRadius);
        fillColor(28, 30, 34);
        fill();

        nvgRestore(NanoGui.getVG());

        nvgBoxGradient(NanoGui.getVG(), x, y + 2, width, height, cornerRadius * 2, 10, rgba(0, 0, 0, 128), rgba(0, 0, 0, 0), paint);
        beginPath();
        rect(x - 10, y - 10, width + 20, height + 30);
        roundedRect(x, y, width, height, cornerRadius);
        nvgPathWinding(NanoGui.getVG(), NVG_HOLE);
        nvgFillPaint(NanoGui.getVG(), paint);
        fill();

        nvgLinearGradient(NanoGui.getVG(), x, y, x, y + 15, rgba(255, 255, 255, 8), rgba(0, 0, 0, 16), paint);
        beginPath();
        roundedRect(x + 1, y + 1, width - 2, 30, cornerRadius - 1);
        fillPaint();
        fill();
        beginPath();
        nvgMoveTo(NanoGui.getVG(), x + 0.5f, y + 0.5f + 30);
        nvgLineTo(NanoGui.getVG(), x + 0.5f + width - 1, y + 0.5f + 30);
        strokeColor(229, 35, 32, 32);
        stroke();

        nvgRestore(NanoGui.getVG());
    }

}