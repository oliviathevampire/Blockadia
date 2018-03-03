package net.thegaminghuskymc.sandboxgame.block;


import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public class BlockDirt extends Block {

    public BlockDirt(String name) {
        super(Material.WOOD);
        setUnlocalizedName("dirt_" + name);
        setRegistryName(new ResourceLocation("dirt_" + name));
    }

}