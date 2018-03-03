package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public class BlockCobblestone extends Block {

    public BlockCobblestone() {
        super(Material.ROCK);
        setUnlocalizedName("cobblestone");
        setRegistryName(new ResourceLocation("cobblestone"));
    }

}
