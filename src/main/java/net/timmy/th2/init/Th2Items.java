package net.timmy.th2.init;

import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.timmy.th2.items.*;
import net.timmy.th2.lib.LibMisc;
import net.timmy.th2.properties.*;

import java.util.EnumMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class Th2Items {

    public static final Map<EnumDyeColor, net.minecraft.item.Item> chalks = new EnumMap<>(EnumDyeColor.class);
    public static final Map<EnumDyeColor, net.minecraft.item.Item> dolls = new EnumMap<>(EnumDyeColor.class);
    public static final Map<EnumWandCoreType, net.minecraft.item.Item> cores = new EnumMap<>(EnumWandCoreType.class);
    public static final Map<EnumWandCapType, net.minecraft.item.Item> caps = new EnumMap<>(EnumWandCapType.class);
    public static final Map<EnumWandCombinationsDefault, net.minecraft.item.Item> wands = new EnumMap<>(EnumWandCombinationsDefault.class);
    public static final Map<EnumRuneType, net.minecraft.item.Item> runes_1 = new EnumMap<>(EnumRuneType.class);
    public static final Map<EnumRuneType, net.minecraft.item.Item> scrolls_1 = new EnumMap<>(EnumRuneType.class);
    public static final Map<EnumRuneType2, net.minecraft.item.Item> runes_2 = new EnumMap<>(EnumRuneType2.class);
    public static final Map<EnumRuneType2, net.minecraft.item.Item> scrolls_2 = new EnumMap<>(EnumRuneType2.class);

    static {
        for(EnumDyeColor color : EnumDyeColor.values()) {
            chalks.put(color, new ItemChalk(color));
            dolls.put(color, new ItemVoodooDoll(color));
        }
        for (EnumWandCoreType type : EnumWandCoreType.values()) {
            cores.put(type, new ItemCore(type));
        }

        for (EnumWandCapType type : EnumWandCapType.values()) {
            caps.put(type, new ItemCap(type));
        }

        for (EnumWandCombinationsDefault type : EnumWandCombinationsDefault.values()) {
            wands.put(type, new ItemWand(type));
        }

        for (EnumRuneType type : EnumRuneType.values()) {
            runes_1.put(type, new ItemRune1(type));
            scrolls_1.put(type, new ItemScroll1(type));
        }

        for (EnumRuneType2 type : EnumRuneType2.values()) {
            runes_2.put(type, new ItemRune2(type));
            scrolls_2.put(type, new ItemScroll2(type));
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<net.minecraft.item.Item> evt) {
        IForgeRegistry<net.minecraft.item.Item> r = evt.getRegistry();

        chalks.values().forEach(r::register);
        dolls.values().forEach(r::register);
        cores.values().forEach(r::register);
        caps.values().forEach(r::register);
        wands.values().forEach(r::register);
        runes_1.values().forEach(r::register);
        runes_2.values().forEach(r::register);
        scrolls_1.values().forEach(r::register);
        scrolls_2.values().forEach(r::register);

    }

}
