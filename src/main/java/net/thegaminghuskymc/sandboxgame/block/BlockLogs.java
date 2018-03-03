package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

public class BlockLogs extends Block {

    public BlockLogs(String name) {
        super(Material.WOOD);
        setUnlocalizedName(name + "_log");
        setRegistryName(new ResourceLocation(name + "_log"));
    }

}