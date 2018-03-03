package net.timmy.th2.blocks;

import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.timmy.th2.properties.EnumRuneType;

public class BlockRune1 extends BlockMod {

    public final EnumRuneType color;

    public BlockRune1(String name, EnumRuneType color) {
        super(Material.ROCK, name + "_" + color.getName());
        this.color = color;
    }

}