package net.timmy.timmylib.items;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Set;


public abstract class ItemModPickaxe extends ItemModTool {

    private static final Set<Block> EFFECTIVE_ON;

    static {
        EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL,
                Blocks.COAL_ORE,
                Blocks.COBBLESTONE,
                Blocks.DETECTOR_RAIL,
                Blocks.DIAMOND_BLOCK,
                Blocks.DIAMOND_ORE,
                Blocks.DOUBLE_STONE_SLAB,
                Blocks.GOLDEN_RAIL,
                Blocks.GOLD_BLOCK,
                Blocks.GOLD_ORE,
                Blocks.ICE,
                Blocks.IRON_BLOCK,
                Blocks.IRON_ORE,
                Blocks.LAPIS_BLOCK,
                Blocks.LAPIS_ORE,
                Blocks.LIT_REDSTONE_ORE,
                Blocks.MOSSY_COBBLESTONE,
                Blocks.NETHERRACK,
                Blocks.PACKED_ICE,
                Blocks.RAIL,
                Blocks.REDSTONE_ORE,
                Blocks.SANDSTONE,
                Blocks.RED_SANDSTONE,
                Blocks.STONE,
                Blocks.STONE_SLAB,
                Blocks.STONE_BUTTON,
                Blocks.STONE_PRESSURE_PLATE);
    }

    public ItemModPickaxe(ToolMaterial material, String name, String... variants) {
        super(1.0F, -2.8F, material, EFFECTIVE_ON, name, variants);
    }

    public void addEfficiencyOn(Block block) {
        EFFECTIVE_ON.add(block);
    }
}
