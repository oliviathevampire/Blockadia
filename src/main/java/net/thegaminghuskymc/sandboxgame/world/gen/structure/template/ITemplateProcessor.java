package net.thegaminghuskymc.sandboxgame.world.gen.structure.template;

import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;

import javax.annotation.Nullable;

public interface ITemplateProcessor {
    @Nullable
    Template.BlockInfo processBlock(World worldIn, BlockPos pos, Template.BlockInfo blockInfoIn);
}