package net.timmy.timmylib.items;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.huskylib2.lib.interf.IVariantHolder;
import net.thegaminghuskymc.huskylib2.lib.utils.ProxyRegistry;
import net.thegaminghuskymc.huskylib2.lib.utils.TooltipHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemMod extends Item implements IVariantHolder {

    public static final ArrayList variantHolders = new ArrayList();

    private final String[] variants;
    private final String bareName;

    public ItemMod(String name, String... variants) {
        setUnlocalizedName(name);
        if (variants.length > 1)
            setHasSubtypes(true);

        if (variants.length == 0)
            variants = new String[]{name};

        bareName = name;
        this.variants = variants;
        variantHolders.add(this);
    }

    @Override
    public Item setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(this.getPrefix(),  name));
        ProxyRegistry.register(this);
        return this;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        int dmg = par1ItemStack.getItemDamage();
        String[] variants = getVariants();

        String name;
        if (dmg >= variants.length)
            name = bareName;
        else name = variants[dmg];

        return "item." + name;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (isInCreativeTab(tab))
            for (int i = 0; i < getVariants().length; i++)
                subItems.add(new ItemStack(this, 1, i));
    }

    @Override
    public String[] getVariants() {
        return variants;
    }

    @Override
    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }

	@SideOnly(Side.CLIENT)
	public static void tooltipIfShift(List<String> tooltip, Runnable r) {
		TooltipHandler.tooltipIfShift(tooltip, r);
	}

	@SideOnly(Side.CLIENT)
	public static void addToTooltip(List<String> tooltip, String s, Object... format) {
		TooltipHandler.addToTooltip(tooltip, s, format);
	}

	@SideOnly(Side.CLIENT)
	public static String local(String s) {
		return TooltipHandler.local(s);
	}

}
