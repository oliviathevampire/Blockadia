package net.thegaminghuskymc.sandboxgame.stats;

import net.thegaminghuskymc.sandboxgame.item.crafting.CraftingManager;
import net.thegaminghuskymc.sandboxgame.item.crafting.IRecipe;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;
import net.thegaminghuskymc.sgf.registries.ForgeRegistry;

import java.util.BitSet;
import javax.annotation.Nullable;

public class RecipeBook
{
    protected final BitSet recipes = new BitSet();
    /** Recipes the player has not yet seen, so the GUI can play an animation */
    protected final BitSet newRecipes = new BitSet();
    protected boolean isGuiOpen;
    protected boolean isFilteringCraftable;

    public void copyFrom(RecipeBook that)
    {
        this.recipes.clear();
        this.newRecipes.clear();
        this.recipes.or(that.recipes);
        this.newRecipes.or(that.newRecipes);
    }

    public void unlock(IRecipe recipe)
    {
        if (!recipe.isDynamic())
        {
            this.recipes.set(getRecipeId(recipe));
        }
    }

    public boolean isUnlocked(@Nullable IRecipe recipe)
    {
        return this.recipes.get(getRecipeId(recipe));
    }

    public void lock(IRecipe recipe)
    {
        int i = getRecipeId(recipe);
        this.recipes.clear(i);
        this.newRecipes.clear(i);
    }

    @Deprecated //DO NOT USE
    protected static int getRecipeId(@Nullable IRecipe recipe)
    {
        int ret = CraftingManager.REGISTRY.getIDForObject(recipe);
        if (ret == -1)
        {
            ret = ((ForgeRegistry<IRecipe>)ForgeRegistries.RECIPES).getID(recipe.getRegistryName());
            if (ret == -1)
                throw new IllegalArgumentException(String.format("Attempted to get the ID for a unknown recipe: %s Name: %s", recipe, recipe.getRegistryName()));
        }
        return ret;
    }

    @SideOnly(Side.CLIENT)
    public boolean isNew(IRecipe recipe)
    {
        return this.newRecipes.get(getRecipeId(recipe));
    }

    public void markSeen(IRecipe recipe)
    {
        this.newRecipes.clear(getRecipeId(recipe));
    }

    public void markNew(IRecipe recipe)
    {
        this.newRecipes.set(getRecipeId(recipe));
    }

    @SideOnly(Side.CLIENT)
    public boolean isGuiOpen()
    {
        return this.isGuiOpen;
    }

    public void setGuiOpen(boolean open)
    {
        this.isGuiOpen = open;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFilteringCraftable()
    {
        return this.isFilteringCraftable;
    }

    public void setFilteringCraftable(boolean shouldFilter)
    {
        this.isFilteringCraftable = shouldFilter;
    }
}