package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public class BlockStainedGlass extends Block {

    public BlockStainedGlass(String name) {
        super(Material.GLASS);
        setUnlocalizedName(name + "_stained_glass");
        setRegistryName(new ResourceLocation(name + "_stained_glass"));
    }

}