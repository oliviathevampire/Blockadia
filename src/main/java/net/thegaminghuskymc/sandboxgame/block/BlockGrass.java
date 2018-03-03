package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public class BlockGrass extends Block {

    public BlockGrass(String name) {
        super(Material.WOOD);
        setUnlocalizedName("grass_" + name);
        setRegistryName(new ResourceLocation("grass_" + name));
    }

}