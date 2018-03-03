package net.timmy.th2.blocks;

import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.item.EnumDyeColor;

public class BlockColored extends BlockMod {

    public final EnumDyeColor color;

    public BlockColored(String name, EnumDyeColor color) {
        super(Material.ROCK, name + "_" + color.getName());
        this.color = color;
    }

}