package net.timmy.th2.blocks;


import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.timmy.th2.blocks.base.ModSlab;

public class Slab extends ModSlab {

    public Slab(String name, boolean doubleSlab) {
        super(name, Material.WOOD, doubleSlab);
        setHardness(2F);
    }

}
