package net.timmy.timmylib.interf;

import static net.timmy.th2.Reference.MODID;

public interface IVariantHolder {

    String[] getVariants();

    default String getUniqueModel() {
        return null;
    }

    String getModNamespace();

    default String getPrefix() {
        return MODID;
    }
}
