package net.timmy.th2.blocks.base;

import net.minecraft.block.state.IBlockState;
import net.thegaminghuskymc.huskylib2.lib.blocks.BlockModStairs;
import net.timmy.th2.Reference;

public class ModStairs extends BlockModStairs {

    public ModStairs(String name, IBlockState state) {
        super(name, state);
    }

    @Override
    public String getModNamespace() {
        return Reference.MODID;
    }

}
