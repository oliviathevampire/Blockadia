package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public class BlockStone extends Block {

    public BlockStone(String name) {
        super(Material.WOOD);
        setUnlocalizedName("stone_" + name);
        setRegistryName(new ResourceLocation("stone_" + name));
    }

}