package net.timmy.timmylib.blocks;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.MapColor;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.item.ItemBlock;
import net.thegaminghuskymc.sandboxgame.util.BlockRenderLayer;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.timmy.timmylib.utils.ProxyRegistry;

import java.util.Random;

public class BlockBase extends Block {

    public BlockBase(String modID, String name, CreativeTabs creativetab) {
        super(Material.ROCK);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(creativetab);
        if (registerInConstruction()) setUnlocalizedName(modID, name);
    }

    public BlockBase(String modID, String name, CreativeTabs creativetab, Material material, MapColor mapColor) {
        super(material, mapColor);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(creativetab);
        if (registerInConstruction()) setUnlocalizedName(modID, name);
    }

    public BlockBase(String modID, String name, CreativeTabs creativetab, Material material) {
        super(material, material.getMaterialMapColor());
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(creativetab);
        if (registerInConstruction()) setUnlocalizedName(modID, name);
    }

    public boolean registerInConstruction() {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + getRegistryName().toString().substring(4);
    }

    public Block setUnlocalizedName(String modId, String name) {
        super.setUnlocalizedName(name);
        setRegistryName(modId, name);
        ProxyRegistry.register(this);
        ProxyRegistry.register(new ItemBlock(this).setRegistryName(new ResourceLocation(modId, name)));
        return this;
    }

}
