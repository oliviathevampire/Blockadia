package net.timmy.th2.blocks;

import net.thegaminghuskymc.sandboxgame.client.util.ITooltipFlag;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.timmy.th2.blocks.base.ModBlock;
import net.timmy.timmylib.utils.ItemUtils;

import javax.annotation.Nullable;
import java.util.List;

public class Rune2 extends ModBlock {

    public Rune2(String name) {
        super(Material.ROCK, name);
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

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        ItemUtils.addInformationRunes2(stack, tooltip);
    }

}