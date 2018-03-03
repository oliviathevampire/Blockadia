package net.timmy.th2.init;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.thegaminghuskymc.huskylib2.lib.blocks.BlockModSlab;
import net.thegaminghuskymc.huskylib2.lib.blocks.BlockModStairs;
import net.timmy.th2.Thaumania2;
import net.timmy.th2.blocks.*;
import net.timmy.th2.blocks.base.ModStairs;
import net.timmy.th2.lib.LibMisc;
import net.timmy.th2.properties.*;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class Th2Blocks {

    public static final Map<EnumDyeColor, net.minecraft.block.Block> candles = new EnumMap<>(EnumDyeColor.class);
    static {
        for(EnumDyeColor color : EnumDyeColor.values()) {
            candles.put(color, new BlockCandle(color));
        }
    }

    private static net.minecraft.block.Block altar, awakenedSiderichor, pedestal, rechargePedestal, siderichor, stoneTable, woodTable, researchTable, cauldron;

    private static net.minecraft.block.Block rune, slab, stair;

    private static net.minecraft.block.Block log, leaf, sapling, plank, bark;

    static {
        altar = new Block(Material.IRON, "altar").setCreativeTab(Thaumania2.th2Blocks);
        awakenedSiderichor = new Block(Material.IRON, "awakened_siderichor").setCreativeTab(Thaumania2.th2Blocks);
        pedestal = new Block(Material.IRON, "pedestal").setCreativeTab(Thaumania2.th2Blocks);
        rechargePedestal = new Block(Material.IRON, "recharge_pedestal").setCreativeTab(Thaumania2.th2Blocks);
        siderichor = new Block(Material.IRON, "siderichor").setCreativeTab(Thaumania2.th2Blocks);
        stoneTable = new Block(Material.ROCK, "stone_table").setCreativeTab(Thaumania2.th2Blocks);
        woodTable = new Block(Material.WOOD, "wood_table").setCreativeTab(Thaumania2.th2Blocks);
        researchTable = new Block(Material.WOOD, "research_table").setCreativeTab(Thaumania2.th2Blocks);
        cauldron = new Block(Material.IRON, "cauldron").setCreativeTab(Thaumania2.th2Blocks);

        for (EnumRuneType type : EnumRuneType.values()) {
            rune = new Rune1("runes_" + type.getName()).setCreativeTab(Thaumania2.th2Blocks);

            slab = new Slab(type.getName() + "_slab", false).setCreativeTab(Thaumania2.th2Blocks);
            BlockModSlab.initSlab(rune, type.getMeta(), (BlockModSlab) slab, new Slab(type.getName() + "_slab",true));

            /*stair = new Stairs(type.getName() + "_stair", rune).setCreativeTab(Thaumania2.th2Blocks);
            BlockModStairs.initStairs(rune, type.getMeta(), (BlockModStairs) stair);*/

        }

        for (EnumRuneType2 type : EnumRuneType2.values()) {
            rune = new Rune2("runes_" + type.getName()).setCreativeTab(Thaumania2.th2Blocks);

            slab = new Slab(type.getName() + "_slab",false).setCreativeTab(Thaumania2.th2Blocks);
            BlockModSlab.initSlab(rune, type.getMeta(), (BlockModSlab) slab, new Slab(type.getName() + "_slab",true));

            /*stair = new Stairs(type.getName() + "_stair",rune).setCreativeTab(Thaumania2.th2Blocks);
            BlockModStairs.initStairs(rune, type.getMeta(), (BlockModStairs) stair);*/

        }

        for (EnumWoodLogType type : EnumWoodLogType.values()) {
            log = new Log(type.getName() + "_log").setCreativeTab(Thaumania2.th2Blocks);

            bark = new Block(Material.WOOD, type.getName() + "_bark").setCreativeTab(Thaumania2.th2Blocks);
        }

        for (EnumWoodType type : EnumWoodType.values()) {
            plank = new Block(Material.WOOD, type.getName() + "_plank").setCreativeTab(Thaumania2.th2Blocks);

            slab = new Slab(type.getName() + "_slab", false).setCreativeTab(Thaumania2.th2Blocks);
            BlockModSlab.initSlab(plank, type.getMeta(), (BlockModSlab) slab, new Slab(type.getName() + "_slab", true));

            stair = new Stairs(type.getName() + "_stair", plank).setCreativeTab(Thaumania2.th2Blocks);
            BlockModStairs.initStairs(plank, type.getMeta(), (ModStairs) stair);
        }

        for (EnumWoodLeavesType type : EnumWoodLeavesType.values()) {
            leaf = new Leaf(type.getName() + "_leaf", sapling).setCreativeTab(Thaumania2.th2Blocks);
        }

    }

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<net.minecraft.block.Block> evt) {
        IForgeRegistry<net.minecraft.block.Block> r = evt.getRegistry();

        candles.values().forEach(r::register);
    }

}
