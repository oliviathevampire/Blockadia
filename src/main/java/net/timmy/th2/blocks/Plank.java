package net.timmy.th2.blocks;

import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;
import net.timmy.th2.blocks.base.ModBlock;

public class Plank extends ModBlock {

    public Plank(String name) {
        super(Material.WOOD, name);
    }

    @Override
    public IProperty getVariantProp() {
        return null;
    }

    @Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[0];
    }

    @Override
    public Class getVariantEnum() {
        return null;
    }
}