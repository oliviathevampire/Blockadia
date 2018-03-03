package net.timmy.th2.blocks;

import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.timmy.th2.properties.EnumRuneType2;

public class BlockRune2 extends BlockMod {

    public final EnumRuneType2 color;

    public BlockRune2(String name, EnumRuneType2 color) {
        super(Material.ROCK, name + "_" + color.getName());
        this.color = color;
    }

}