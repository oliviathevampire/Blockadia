package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.properties.PropertyDirection;

public abstract class BlockDirectional extends Block {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    protected BlockDirectional(Material materialIn) {
        super(materialIn);
    }
}