package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public class BlockAir extends Block {

    public BlockAir() {
        super(Material.WOOD);
        setUnlocalizedName("air");
        setRegistryName(new ResourceLocation("air"));
    }

}