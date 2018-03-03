package net.thegaminghuskymc.sandboxgame.item.crafting;

import net.thegaminghuskymc.sandboxgame.inventory.InventoryCrafting;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;
import net.thegaminghuskymc.sgf.common.ForgeHooks;
import net.thegaminghuskymc.sgf.registries.IForgeRegistryEntry;

public interface IRecipe extends IForgeRegistryEntry<IRecipe> {
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(InventoryCrafting inv, World worldIn);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(InventoryCrafting inv);

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    boolean canFit(int width, int height);

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    ItemStack getRecipeOutput();

    default NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }

    default NonNullList<Ingredient> getIngredients() {
        return NonNullList.<Ingredient>create();
    }

    default boolean isDynamic() {
        return false;
    }

    default String getGroup() {
        return "";
    }
}