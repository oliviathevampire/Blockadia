package net.timmy.timmylib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.huskylib2.lib.api.IModelRegister;
import net.thegaminghuskymc.huskylib2.lib.items.blocks.ItemModBlock;
import net.thegaminghuskymc.huskylib2.lib.utils.ModelHandler;
import net.thegaminghuskymc.huskylib2.lib.utils.ProxyRegistry;
import net.timmy.th2.items.Item;

public abstract class BlockMod extends Block implements IModBlock, IModelRegister {

    private final String[] variants;
    private String bareName;

    public BlockMod(Material materialIn, String name, String... variants) {
        super(materialIn);
        if (variants.length == 0) {
            variants = new String[]{name};
        }
        this.bareName = name;
        this.variants = variants;
        this.setUnlocalizedName(name);
    }

    public Block setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        this.setRegistryName(this.getPrefix(), name);
        ProxyRegistry.register(this);
        ProxyRegistry.register(this.createItemBlock(new ResourceLocation(this.getPrefix(), name)));
        return this;
    }

    public ItemBlock createItemBlock(ResourceLocation name) {
        return new ItemModBlock(this, name);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.SOLID;
    }

    public String[] getVariants() {
        return this.variants;
    }

    @Override
    public String getBareName() {
        return bareName;
    }

    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }

    public EnumRarity getBlockRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    public IProperty[] getIgnoredProperties() {
        return new IProperty[0];
    }

    public IProperty getVariantProp() {
        return null;
    }

    public Class getVariantEnum() {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        if(Item.getItemFromBlock(this) != Items.AIR)
            ModelHandler.registerBlockToState(this, 0, getDefaultState());
    }

    @Override
    public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

}