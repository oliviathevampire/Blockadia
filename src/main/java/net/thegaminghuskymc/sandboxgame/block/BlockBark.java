package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public class BlockBark extends Block {

    public BlockBark(String name) {
        super(Material.WOOD);
        setUnlocalizedName(name + "_planks");
        setRegistryName(new ResourceLocation(name + "_planks"));
    }

}