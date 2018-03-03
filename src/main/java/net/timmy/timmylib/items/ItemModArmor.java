package net.timmy.timmylib.items;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.huskylib2.lib.interf.IVariantHolder;
import net.thegaminghuskymc.huskylib2.lib.utils.ProxyRegistry;

public abstract class ItemModArmor extends ItemArmor implements IVariantHolder {

    private final String bareName;

    public ItemModArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);

        setUnlocalizedName(name);
        bareName = name;
        ItemMod.variantHolders.add(this);
        setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public Item setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        setRegistryName(new ResourceLocation(getPrefix() + name));
        ProxyRegistry.register(this);

        return this;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        par1ItemStack.getItemDamage();

        return "item." + getPrefix() + bareName;
    }

    @Override
    public String[] getVariants() {
        return new String[]{bareName};
    }

    @Override
    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }

}
