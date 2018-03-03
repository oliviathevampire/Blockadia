package net.timmy.timmylib.recipie;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MultiRecipe extends ModRecipe {
    IRecipe matched;
    private List<IRecipe> subRecipes = new LinkedList();

    public MultiRecipe(ResourceLocation res) {
        super(res);
    }

    public void addRecipe(IRecipe recipe) {
        this.subRecipes.add(recipe);
    }

    public boolean matches(InventoryCrafting inv, World worldIn) {
        this.matched = null;
        Iterator var3 = this.subRecipes.iterator();

        IRecipe recipe;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            recipe = (IRecipe) var3.next();
        } while (!recipe.matches(inv, worldIn));

        this.matched = recipe;
        return true;
    }

    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.matched == null ? ItemStack.EMPTY : this.matched.getCraftingResult(inv);
    }

    public boolean canFit(int width, int height) {
        return false;
    }

    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }
}