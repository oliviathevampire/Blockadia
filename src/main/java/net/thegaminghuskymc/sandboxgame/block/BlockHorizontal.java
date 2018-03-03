package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.MapColor;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.properties.PropertyDirection;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;

public abstract class BlockHorizontal extends Block {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    protected BlockHorizontal(Material materialIn) {
        super(materialIn);
    }

    protected BlockHorizontal(Material materialIn, MapColor colorIn) {
        super(materialIn, colorIn);
    }
}