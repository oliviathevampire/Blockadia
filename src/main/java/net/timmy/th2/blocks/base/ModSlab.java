package net.timmy.th2.blocks.base;

import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.timmy.th2.Reference;
import net.timmy.timmylib.blocks.BlockModSlab;
import net.timmy.timmylib.interf.IModBlock;

public abstract class ModSlab extends BlockModSlab implements IModBlock {

    public ModSlab(String name, Material materialIn, boolean doubleSlab) {
        super(name, materialIn, doubleSlab);
    }

    @Override
    public String getModNamespace() {
        return Reference.MODID;
    }

}
