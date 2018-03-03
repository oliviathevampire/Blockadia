package net.timmy.timmylib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockBase2 extends Block {

    public BlockBase2(String modID, String name, CreativeTabs creativetab) {
        super(Material.ROCK);
        setUnlocalizedName(name);
        setRegistryName(modID, name);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(creativetab);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

}
