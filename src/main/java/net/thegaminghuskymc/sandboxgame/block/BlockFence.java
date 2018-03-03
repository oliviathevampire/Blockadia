package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public class BlockFence extends Block {

    public BlockFence(String name) {
        super(Material.WOOD);
        setUnlocalizedName("fence_" + name);
        setRegistryName(new ResourceLocation("fence_" + name));
    }

}