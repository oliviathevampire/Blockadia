package net.timmy.th2.blocks;

import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.timmy.th2.blocks.base.ModBlock;

public class Block extends ModBlock {

    public Block(Material material, String name) {
        super(material, name);
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