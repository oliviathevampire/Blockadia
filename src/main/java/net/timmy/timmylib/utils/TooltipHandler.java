package net.timmy.timmylib.utils;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;


public final class TooltipHandler {
    public TooltipHandler() {
    }

    @SideOnly(Side.CLIENT)
    public static void tooltipIfShift(List<String> tooltip, Runnable r) {
        if (GuiScreen.isShiftKeyDown()) {
            r.run();
        } else {
            addToTooltip(tooltip, "arl.misc.shiftForInfo");
        }

    }

    @SideOnly(Side.CLIENT)
    public static void addToTooltip(List<String> tooltip, String s, Object... format) {
        s = local(s).replaceAll("&", "§");
        Object[] formatVals = new String[format.length];

        for (int i = 0; i < format.length; ++i) {
            formatVals[i] = local(format[i].toString()).replaceAll("&", "§");
        }

        if (formatVals != null && formatVals.length > 0) {
            s = String.format(s, formatVals);
        }

        tooltip.add(s);
    }

    @SideOnly(Side.CLIENT)
    public static String local(String s) {
        return I18n.translateToLocal(s);
    }
}
