package net.timmy.timmylib.utils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.InvocationTargetException;

public class HuskyRegistry {

    public static void registerBlock(Block block, String name) {
        block.setRegistryName(name);
        ForgeRegistries.BLOCKS.register(block);
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
    }

    public static void registerBlock(Block block, ResourceLocation name) {
        block.setRegistryName(name);
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
        ForgeRegistries.BLOCKS.register(block);
    }

    public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name) {
        block.setRegistryName(name);
        ForgeRegistries.BLOCKS.register(block);
        try {
            ItemBlock itemBlock = itemclass.getConstructor(Block.class).newInstance(block);
            itemBlock.setRegistryName(name);
            ForgeRegistries.ITEMS.register(itemBlock);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, ResourceLocation name) {
        block.setRegistryName(name);
        ForgeRegistries.BLOCKS.register(block);
        try {
            ItemBlock itemBlock = itemclass.getConstructor(Block.class).newInstance(block);
            itemBlock.setRegistryName(name);
            ForgeRegistries.ITEMS.register(itemBlock);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void registerBlock(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
    }

    public static void registerItem(Item item) {
        ForgeRegistries.ITEMS.register(item);
    }

    public static void registerItem(Item item, ResourceLocation name) {
        item.setRegistryName(name);
        ForgeRegistries.ITEMS.register(item);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModel(Item i, int meta) {
        ResourceLocation loc = i.getRegistryName();
        ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(loc, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModel(Block b, int meta) {
        registerItemModel(Item.getItemFromBlock(b), meta);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModel(Item i, int meta, String variant) {
        ResourceLocation loc = i.getRegistryName();
        if (!(i instanceof ItemBlock))
            loc = new ResourceLocation(loc.getResourceDomain(), "item/" + loc.getResourcePath());
        ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(loc, "type=" + variant));
    }
}
