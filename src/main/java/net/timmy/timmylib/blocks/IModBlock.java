package net.timmy.timmylib.blocks;

import net.timmy.th2.Reference;

public interface IModBlock extends net.timmy.timmylib.interf.IModBlock {

    @Override
    default String getModNamespace() {
        return Reference.MODID;
    }

}
