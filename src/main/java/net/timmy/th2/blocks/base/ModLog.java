package net.timmy.th2.blocks.base;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.BlockRotatedPillar;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.world.IBlockAccess;
import net.timmy.th2.Reference;
import net.timmy.timmylib.interf.IModBlock;
import net.timmy.timmylib.items.blocks.ItemModBlock;
import net.timmy.timmylib.utils.ProxyRegistry;

public class ModLog extends BlockRotatedPillar implements IModBlock {

    private final String[] variants;
    private final String bareName;

    protected ModLog(String name, String... variants) {
        super(Material.WOOD);
        if (variants.length == 0) variants = new String[]{name};
        bareName = name;
        this.variants = variants;
        setHardness(2.0F);
        if (registerInConstruction()) setUnlocalizedName(name);
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public Block setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        setRegistryName(getPrefix(), name);
        ProxyRegistry.register(this);
        ProxyRegistry.register(new ItemModBlock(this, new ResourceLocation(this.getPrefix(), name)));
        return this;
    }

    @Override
    public String getModNamespace() {
        return Reference.MODID;
    }

    public boolean registerInConstruction() {
        return true;
    }

    @Override
    public ModLog setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    public String getBareName() {
        return bareName;
    }

    @Override
    public String[] getVariants() {
        return variants;
    }

    @Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[0];
    }

    @Override
    public IProperty getVariantProp() {
        return null;
    }

    @Override
    public Class getVariantEnum() {
        return null;
    }
}
