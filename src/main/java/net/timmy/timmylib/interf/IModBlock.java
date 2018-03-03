package net.timmy.timmylib.interf;

import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;

public interface IModBlock extends IVariantHolder, IVariantEnumHolder {

    public String getBareName();

    public IProperty getVariantProp();

    public IProperty[] getIgnoredProperties();

    public default boolean shouldDisplayVariant(int variant) {
        return true;
    }

}
