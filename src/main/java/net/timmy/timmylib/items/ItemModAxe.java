package net.timmy.timmylib.items;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import java.util.Set;

import static net.minecraft.init.Blocks.*;


public abstract class ItemModAxe extends ItemModTool {

    private static final Set<Block> EFFECTIVE_ON;

    static {
        EFFECTIVE_ON = Sets.newHashSet(PLANKS,
                BOOKSHELF,
                LOG,
                LOG2,
                CHEST,
                PUMPKIN,
                LIT_PUMPKIN,
                MELON_BLOCK,
                LADDER,
                WOODEN_BUTTON,
                WOODEN_PRESSURE_PLATE);
    }

    public ItemModAxe(float attckDmg, float attckSpd, ToolMaterial material, String name, String... variants) {
        super(attckDmg, attckSpd, material, EFFECTIVE_ON, name, variants);

    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        switch (Enchantment.REGISTRY.getNameForObject(enchantment).toString()) {
            case "minecraft:binding_curse":
            case "minecraft:sharpness":
            case "minecraft:smite":
            case "minecraft:bane_of_arthropods":
            case "minecraft:knockback":
            case "minecraft:fire_aspect":
            case "minecraft:looting":
            case "minecraft:mending":
            case "minecraft:unbreaking":
                return true;
        }
        return false;
    }
}
